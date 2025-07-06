package com.pratica.checkinlocais.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.Manifest;
import com.pratica.checkinlocais.R;
import com.pratica.checkinlocais.data.BancoDados;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LocationListener {

    private LocationManager lm;
    private Criteria criteria;
    private final int GPSPERMISSION = 1;
    public int dist = 0;
    private String local;
    private String categoria;
    private double lat;
    private double log;
    public String provider;
    BancoDados bd;
    ArrayList<String> localidades;
    ArrayAdapter<String> adaptadortext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bd = BancoDados.getInstance();

        localidades = new ArrayList<>();
        Cursor c = bd.buscar("Checkin", new String[] {"Local"}, "", null);

        while(c.moveToNext()){
            int idx = c.getColumnIndex("Local");
            localidades.add(c.getString(idx)); // recebe o valor da coluna
        }

        Log.i("Teste","Autocomplet populado");
        adaptadortext = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, localidades);
        AutoCompleteTextView locais = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        locais.setAdapter(adaptadortext);

        Log.i("Teste", "Iniciando spinner");
        Spinner categorias = (Spinner) findViewById(R.id.Categorias);
        categorias.setOnItemSelectedListener(this);

        c = bd.buscar("Categoria", new String[]{"idCategoria", "nome"}, "", "");
        ArrayList<String> nomesCategorias = new ArrayList<>();
        Log.i("Teste", "Cursor");
        if(c.getCount() > 0){
            while(c.moveToNext()){
                //int idxID = c.getColumnIndex("idCategoria");
                //int id = c.getInt(idxID);
                int idxNome = c.getColumnIndex("nome");
                Log.i("Teste", c.getString(idxNome));
                nomesCategorias.add(c.getString(idxNome));
            }
            Log.i("Teste", "populei");
            c.close();
            Log.e("Teste", "populei2");
        }
        Log.i("Teste", "Spinner populado");
        Log.i("Teste", nomesCategorias.get(0) + " " + nomesCategorias.get(1));
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nomesCategorias);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorias.setAdapter(adaptador);
        bd.fechar();

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        PackageManager pc = getPackageManager();
        boolean gps = pc.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
        if(gps){ //verifica se tem gps e define o criterio como fine
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
        }
        else{ //se não, usa wifi
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        }

        Toolbar header = findViewById(R.id.toolbar);
        setSupportActionBar(header);
    }


    public void onItemSelected(AdapterView parent, View view, int pos, long id) {
        this.categoria = parent.getItemAtPosition(pos).toString();

    }

    public void onNothingSelected(AdapterView parent){

    }

    public void onClickCheckin(View view) {
        Log.i("Teste", "Checkin");
        AutoCompleteTextView campoLocal = findViewById(R.id.autoComplete);
        this.local = campoLocal.getText().toString();
        BancoDados bd = BancoDados.getInstance();

        int quantiadeVisitas = 0;
        boolean localJaExiste = false;

        Cursor c = bd.buscar("Checkin", new String[] {"Local", "qtdVisitas"}, "Local = '" + this.local + "'", null);
        if(c.getCount() > 0){ // caso ja tenha visitado esse local anteriormente
            while(c.moveToNext()){
                Log.i("Teste", "Quantidade visitas");
                int idx = c.getColumnIndex("qtdVisitas"); // recebe o idx da coluna qtdVisitas
                quantiadeVisitas = c.getInt(idx); // recebe o valor da coluna qtdVisitas
                localJaExiste = true;
            }
        }
        Log.i("Teste", "Quantidade visitas2" + quantiadeVisitas);
        ContentValues infos = new ContentValues();
        infos.put("Local", this.local);
        infos.put("qtdVisitas", quantiadeVisitas + 1);
        infos.put("cat", this.categoria);
        infos.put("latitude", this.lat);
        infos.put("longitude", this.log);

        if (localJaExiste) {
            Log.i("Teste", "Atualizando tabela");
            bd.atualizar("Checkin", infos, "Local = '" + this.local + "'");
            Log.i("Teste", "Local atualizado: " + this.local);
        } else {
            bd.inserir("Checkin", infos);
            Log.i("Teste", "Novo local inserido: " + this.local);

            // 5. ATUALIZAR A UI - Apenas se for um novo local
            // Adiciona o novo local à lista do adaptador
            if (!localidades.contains(this.local)) {
                localidades.add(this.local);
                // Notifica o adaptador que a fonte de dados mudou!
                adaptadortext.notifyDataSetChanged();
            }
        }
        campoLocal.setText("");

        Log.i("Teste", "Finalizei");
        recreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Log.i("Teste", "Entrei");
        if (itemId == R.id.Mapa) {
            Log.i("Teste", "Mapa");
            if(this.lat==0.0 && this.log==0.0){
                Toast.makeText(this,"Por favor, espere a aplicação achar sua localização",Toast.LENGTH_SHORT).show();
                return false;
            }
            Intent it = new Intent(this, MapaCheckin.class);
            it.putExtra("lat", lat);
            it.putExtra("log", log);
            startActivity(it);
            return true;

        } else if (itemId == R.id.GestaoCheckin) {
            Intent it = new Intent(this, GestaoCheckin.class);
            startActivity(it);
            return true;

        } else if (itemId == R.id.LugaresVisitados) {
            Log.i("Teste", "Relatorio");
            Intent it = new Intent(this, RelatorioLocaisMaisVisitados.class);
            startActivity(it);
            return true;

        } else {
            //se nenhum dos itens foi clicado, deixa o sistema tratar
            return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onStart(){
        super.onStart();
        //pega o provedor e chama metodo para achar localização em tempo real
        provider = lm.getBestProvider(criteria,true);
        findlocation();
    }

    @Override
    protected void onDestroy(){
        if (this.bd != null) {
            this.bd.fechar(); //fecha banco de dados
        }
        if (lm != null) {
            lm.removeUpdates(this); //remove os updates da localização
        }
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(Location location){ //atualiza a localização
        if(location!=null){
            //pega latitude e longitude do ponto atual e seta na tela
            Log.i("Teste","Localidade mudou");
            lat = location.getLatitude();
            log = location.getLongitude();
            TextView latidude = findViewById(R.id.latitude);
            TextView longitude = findViewById(R.id.longitude);
            latidude.setText(lat+"");
            longitude.setText(log+"");
        }
    }

    public void requestGPSPermission(){
        //pede permissao de gps caso nao tenha
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this,"Permita o uso da câmera para ler QR Code!",Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        GPSPERMISSION);
                Log.i("Teste","Devo dar explicação");
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        GPSPERMISSION);
                Log.i("Teste","Pede a permissão");

            }
        }else{
            findlocation(); //se ja tiver permissao, acha localização
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //se permissao ja dada, acha localização atual
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            findlocation();
            Log.i("Teste","Deu a permissão");
        } else {
            Log.i("Teste","Não permitiu");
        }
    }

    public void findlocation(){
        //se uso de gps permitido, acha loc atual
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (provider != null) {
                Log.i("Teste","Busca localização");
                lm.requestLocationUpdates(provider, 5000, dist, this);
            }
        } else {
            // Permissão ainda não foi concedida, solicite novamente ou trate isso
            requestGPSPermission();
            Toast.makeText(this, "Permissão de localização não concedida", Toast.LENGTH_SHORT).show();
        }
    }
}