package com.pratica.firsttask;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class MainActivity extends ListActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_menu);
        String palavras[] = {"MOC", "Viçosa", "DPI", "Relatório"};
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,palavras);
        setListAdapter(adp);
    }


    // método para tratar o clique na lista de opções
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent it = new Intent(this,Mapa.class);

        // inserindo dados no banco de dados
        ContentValues values = new ContentValues();
        Date data = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String timestamp = formato.format(data); // data formatada
        String toast = l.getItemAtPosition(position).toString();
        Toast.makeText(this, toast ,Toast.LENGTH_SHORT).show();


        switch (position){
            case 0:
                values.put("msg", "MOC");
                values.put("Timestamp", timestamp);
                values.put("idLocation", position);
                BancoDados.getInstance().inserir("Logs", values);
                it.putExtra("local", "MOC");
                startActivity(it);
                break;
            case 1:
                values.put("msg", "VICOSA");
                values.put("Timestamp", timestamp);
                values.put("idLocation", position);
                BancoDados.getInstance().inserir("Logs", values);
                it.putExtra("local", "VICOSA");
                startActivity(it);
                break;
            case 2:
                values.put("msg", "DPI");
                values.put("Timestamp", timestamp);
                values.put("idLocation", position);
                BancoDados.getInstance().inserir("Logs", values);
                it.putExtra("local", "DPI");
                startActivity(it);
                break;
            case 3:
                Log.i("Relatorio", "Entrei");
                Intent itRelatorio = new Intent(this, Relatorio.class);
                startActivity(itRelatorio);
                break;
            case 4:
                finish();
                break;
        }
    }
}