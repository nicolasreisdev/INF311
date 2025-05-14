package com.pratica.firsttask;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;

public class Mapa extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    public LocationManager lm;
    public Criteria criteria;
    public String provider;
    public int TEMPO_REQUISICAO_LATLONG = 5000;
    public int DISTANCIA_MIN_METROS = 0;

    protected double latitude;
    protected double longitude;

    //private final LatLng VICOSA = new LatLng(-20.75824678663597, -42.88029027646495);
    //private final LatLng MOC = new LatLng(-16.717496756180157, -43.87682393125447);
    //private final LatLng DPI = new LatLng(-20.76481707003259, -42.86854663598881);
    private LatLng Marker;
    private LatLng ATUAL;


    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        Intent it = getIntent();
        String local = it.getStringExtra("local");
        Log.i("Mapa", "Cheguei");
        if (local.equals("MOC")) {
            Log.i("Teste", "Entrei");
            String[] colunas = {"Lat", "Lng"};
            String where = "nome = 'MOC'";

            Cursor cursor = BancoDados.getInstance().buscar("Location", colunas,where, "");

            if(cursor.moveToFirst()){
                int idxLat = cursor.getColumnIndexOrThrow("Lat");
                int idxLng = cursor.getColumnIndexOrThrow("Lng");
                latitude = cursor.getDouble(idxLat);
                longitude = cursor.getDouble(idxLng);
                Log.i("COORDENADAS", "Latitude: " + latitude + ", Longitude: " + longitude);
            }else{
                Log.i("COORDENADAS", "Local 'MOC' não encontrado.");
            }

            cursor.close();
            Log.i("Teste", "Consultei");
            Marker = new LatLng(latitude, longitude);
            Log.i("Mapa", "Entrei");
        }
        else if(local.equals("VICOSA")){
            String[] colunas = {"Lat", "Lng"};
            String where = "nome = 'VICOSA'";

            Cursor cursor = BancoDados.getInstance().buscar("Location", colunas,where, "");

            if(cursor.moveToFirst()){
                int idxLat = cursor.getColumnIndexOrThrow("Lat");
                int idxLng = cursor.getColumnIndexOrThrow("Lng");
                latitude = cursor.getDouble(idxLat);
                longitude = cursor.getDouble(idxLng);
                Log.i("COORDENADAS", "Latitude: " + latitude + ", Longitude: " + longitude);
            }else{
                Log.i("COORDENADAS", "Local 'MOC' não encontrado.");
            }
            cursor.close();
            Marker = new LatLng(latitude, longitude);
            Log.i("Mapa", "Entrei");
        }
        else if(local.equals("DPI")){
            String[] colunas = {"Lat", "Lng"};
            String where = "nome = 'DPI'";

            Cursor cursor = BancoDados.getInstance().buscar("Location", colunas,where, "");

            if(cursor.moveToFirst()){
                int idxLat = cursor.getColumnIndexOrThrow("Lat");
                int idxLng = cursor.getColumnIndexOrThrow("Lng");
                latitude = cursor.getDouble(idxLat);
                longitude = cursor.getDouble(idxLng);
                Log.i("COORDENADAS", "Latitude: " + latitude + ", Longitude: " + longitude);
            }else{
                Log.i("COORDENADAS", "Local 'MOC' não encontrado.");
            }

            cursor.close();
            Marker = new LatLng(latitude, longitude);
            Log.i("Mapa", "Entrei");
        }

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        PackageManager packageManager = getPackageManager();
        boolean hasGPS = packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);

        if(hasGPS){
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
        }else {
            Log.i("GPS", "Não tem GPS");
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        }


        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        provider = lm.getBestProvider(criteria, true);

        requestLocation();
    }

    @Override
    protected void onDestroy(){
        lm.removeUpdates(this);

        super.onDestroy();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("Atual", "Cheguei");
        if (location != null) {

            final Location pontoAleatorio = new Location(provider);
            pontoAleatorio.setLatitude(-20.75824678663597);
            pontoAleatorio.setLongitude(-42.88029027646495);

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            double velocidade = location.getSpeed() * 3.6;
            double distancia = location.distanceTo(pontoAleatorio);

            ATUAL = new LatLng(latitude, longitude);

            DecimalFormat df = new DecimalFormat("0.##");

            Toast.makeText(this, "Latitude: " + latitude +
                                            "\nLongitude: " + longitude +
                                            "\nVelocidade: " + df.format(velocidade) +
                                            "\nDistancia: " + df.format(distancia), Toast.LENGTH_SHORT).show();
        }
    }

    public void requestLocation(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{
            Log.i("Teste", "Possui permissão");
            if(provider != null){
                lm.requestLocationUpdates(provider, TEMPO_REQUISICAO_LATLONG, DISTANCIA_MIN_METROS, this);
            }
            Location location = lm.getLastKnownLocation(provider);
            if(location != null){
                onLocationChanged(location);
            }
        }
    }

    public void onProviderDisabled(String provider) {
        Log.i("GPS", "Desativado");
    }
    public void onProviderEnabled(String provider) {
        Log.i("GPS", "Ativado");
    }
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i("GPS", "Status: " + status);
    }

    public void onClick_MOC(View v) {

        String[] colunas = {"Lat", "Lng"};
        String where = "nome = 'MOC'";

        Cursor cursor = BancoDados.getInstance().buscar("Location", colunas,where, "");

        if(cursor.moveToFirst()){
            int idxLat = cursor.getColumnIndexOrThrow("Lat");
            int idxLng = cursor.getColumnIndexOrThrow("Lng");
            latitude = cursor.getDouble(idxLat);
            longitude = cursor.getDouble(idxLng);
            Log.i("COORDENADAS", "Latitude: " + latitude + ", Longitude: " + longitude);
        }else{
            Log.i("COORDENADAS", "Local 'MOC' não encontrado.");
        }

        cursor.close();

        LatLng MOC = new LatLng(latitude, longitude);
        Log.i("Mapa", "Entrei");


        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        map.addMarker(new MarkerOptions()
                .position(MOC).title("")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        );
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                        .target(MOC)
                        .tilt(60)
                        .zoom(15)
                        .build()
        );
        map.animateCamera(update);
    }

    public void onClick_VICOSA(View v) {

        String[] colunas = {"Lat", "Lng"};
        String where = "nome = 'VICOSA'";

        Cursor cursor = BancoDados.getInstance().buscar("Location", colunas,where, "");

        if(cursor.moveToFirst()){
            int idxLat = cursor.getColumnIndexOrThrow("Lat");
            int idxLng = cursor.getColumnIndexOrThrow("Lng");
            latitude = cursor.getDouble(idxLat);
            longitude = cursor.getDouble(idxLng);
            Log.i("COORDENADAS", "Latitude: " + latitude + ", Longitude: " + longitude);
        }else{
            Log.i("COORDENADAS", "Local 'MOC' não encontrado.");
        }

        cursor.close();

        LatLng VICOSA = new LatLng(latitude, longitude);
        Log.i("Mapa", "Entrei");

        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.addMarker(new MarkerOptions()
                .position(VICOSA).title("")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        );
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                        .target(VICOSA)
                        .tilt(60)
                        .zoom(15)
                        .build()
        );
        map.animateCamera(update);
    }

    public void onClick_DPI(View v) {

        String[] colunas = {"Lat", "Lng"};
        String where = "nome = 'DPI'";

        Cursor cursor = BancoDados.getInstance().buscar("Location", colunas,where, "");

        if(cursor.moveToFirst()){
            int idxLat = cursor.getColumnIndexOrThrow("Lat");
            int idxLng = cursor.getColumnIndexOrThrow("Lng");
            latitude = cursor.getDouble(idxLat);
            longitude = cursor.getDouble(idxLng);
            Log.i("COORDENADAS", "Latitude: " + latitude + ", Longitude: " + longitude);
        }else{
            Log.i("COORDENADAS", "Local 'MOC' não encontrado.");
        }

        cursor.close();

        LatLng DPI = new LatLng(latitude, longitude);
        Log.i("Mapa", "Entrei");

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.addMarker(new MarkerOptions()
                .position(DPI).title("")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        );
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                        .target(DPI)
                        .tilt(60)
                        .zoom(15)
                        .build()
        );
        map.animateCamera(update);
    }

    public void currentLocation(View v) {
        Log.i("Atual", "Entrei");
        requestLocation();
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.addMarker(new MarkerOptions()
                .position(ATUAL).title("")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        );
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                        .target(ATUAL)
                        .tilt(60)
                        .zoom(15)
                        .build()
        );
        map.animateCamera(update);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i("Teste", "onMapReady");
        map = googleMap;
        map.addMarker(new MarkerOptions()
                .position(Marker).title("")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        );
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                        .target(Marker)
                        .tilt(60)
                        .zoom(15)
                        .build()
        );
        map.animateCamera(update);
    }
}