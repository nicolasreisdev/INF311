package com.pratica.firsttask;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Relatorio extends ListActivity {

    private ArrayList<Integer> ids;
    protected double latitude;
    protected double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Toast", "Relatório");
        Intent it = getIntent();

        String colunas[] = {"msg", "Timestamp", "idLocation"};

        Cursor cursor = BancoDados.getInstance().buscar("Logs", colunas, "", "");
        List<String> logs = new ArrayList<>();
        Log.i("Toast", "Relatório2");
        ids = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                Log.i("Toast", "Relatório3");
                int idxID = cursor.getColumnIndexOrThrow("msg");
                int idxDate = cursor.getColumnIndexOrThrow("Timestamp");
                int idxLocationID = cursor.getColumnIndexOrThrow("idLocation");
                int locationID = cursor.getInt(idxLocationID);
                String log = cursor.getString(idxID);
                String timestamp = cursor.getString(idxDate);
                logs.add(log + " - " + timestamp);
                ids.add(locationID);
            }while(cursor.moveToNext());
        }
        cursor.close();
        Log.i("Toast", "Relatório5");
        ArrayAdapter<String> list = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,logs);
        setListAdapter(list);

    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Log.i("Toast", "Entrei");
        int locationID = ids.get(position);
        String[] colunas = {"loc.Lat", "loc.Lng"};
        String where = "loc.idLocal = log.idLocation and loc.idLocal = " + locationID;
        Cursor cursor = BancoDados.getInstance().buscar("Location loc, Logs log", colunas, where, "");
        Log.i("Toast", "Busquei");
        if(cursor.moveToFirst()){
            int idxLat = cursor.getColumnIndexOrThrow("Lat");
            int idxLng = cursor.getColumnIndexOrThrow("Lng");
            latitude = cursor.getDouble(idxLat);
            longitude = cursor.getDouble(idxLng);
        }
        cursor.close();

        Toast.makeText(this, "Latitude: " + latitude +
                "\nLongitude: " + longitude, Toast.LENGTH_SHORT).show();

    }

}