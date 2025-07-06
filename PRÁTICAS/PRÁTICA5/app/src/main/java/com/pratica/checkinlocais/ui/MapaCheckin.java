package com.pratica.checkinlocais.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.model.LatLng;
import com.pratica.checkinlocais.R;

public class MapaCheckin extends AppCompatActivity {
    private LatLng posAtual;
    private Mapa mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_checkin);

        Intent it = getIntent();
        posAtual = new LatLng(it.getDoubleExtra("lat", 0), it.getDoubleExtra("log", 0));

        mapa = Mapa.newInstance(posAtual);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Mapa, mapa);
        transaction.commit();

        Toolbar header = findViewById(R.id.Header);
        setSupportActionBar(header);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuinterior, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.voltar) {
            finish();
            return true;
        } else if (itemId == R.id.gestaoCheckin) {
            Intent it = new Intent(this, GestaoCheckin.class);
            startActivity(it);
            return true;

        } else if (itemId == R.id.relatorio) {
            Intent it = new Intent(this, RelatorioLocaisMaisVisitados.class);
            startActivity(it);
            return true;

        } else if (itemId == R.id.tipoMapa) {
            return true;
        } else if (itemId == R.id.Normal) {
            mapa.mudaMapa("normal");
        } else if (itemId == R.id.Hibrido) {
            mapa.mudaMapa("hibrido");
        } else {
            //se nenhum dos itens foi clicado, deixa o sistema tratar
            return super.onOptionsItemSelected(item);
        }
        return false;
    }
}
