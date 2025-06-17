package com.pratica.checkinlocais.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pratica.checkinlocais.R;
import com.pratica.checkinlocais.data.BancoDados;

import java.util.ArrayList;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private String local;
    private String categoria;
    private double lat;
    private double log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Teste", "Iniciando spinner");
        Spinner categorias = (Spinner) findViewById(R.id.Categorias);
        categorias.setOnItemSelectedListener(this);
        BancoDados bd = BancoDados.getInstance();
        Cursor c = bd.buscar("Categoria", new String[]{"idCategoria", "nome"}, "", "");
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
    }


    public void onItemSelected(AdapterView parent, View view, int pos, long id) {
        this.categoria = parent.getItemAtPosition(pos).toString();

    }

    public void onNothingSelected(AdapterView parent){

    }

    public void onClickCheckin(View view) {
        AutoCompleteTextView local = findViewById(R.id.Local);
        this.local = local.getText().toString();
        BancoDados bd = BancoDados.getInstance();
        int quantiadeVisitas = 0;
        Cursor c = bd.buscar("Checkin c", new String[] {"c.Local local", "c.qtdVisitas qtdVisitas", "c.cat cat"}, "c.Local = '" + this.local + "'", null);
        if(c.getCount() > 0){ // caso ja tenha visitado esse local anteriormente
            while(c.moveToNext()){
                int idx = c.getColumnIndex("qtdVisitas"); // recebe o idx da coluna qtdVisitas
                quantiadeVisitas = c.getInt(idx); // recebe o valor da coluna qtdVisitas
            }
        }
        ContentValues infos = new ContentValues();
        infos.put("Local", this.local);
        infos.put("qtdVisitas", quantiadeVisitas++);
        infos.put("cat", this.categoria);
        infos.put("latitude", this.lat);
        infos.put("longitude", this.log);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_mapa) {
            //leva pro mapa de checkin
            return true;

        } else if (itemId == R.id.action_gestao) {
            //gestao de checkin
            return true;

        } else if (itemId == R.id.action_lugares) {
            //lugares mais visitados
            return true;

        } else {
            //se nenhum dos itens foi clicado, deixa o sistema tratar
            return super.onOptionsItemSelected(item);
        }
    }


}