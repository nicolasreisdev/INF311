package com.pratica.checkinlocais.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pratica.checkinlocais.R;
import com.pratica.checkinlocais.data.BancoDados;

public class Mapa extends Fragment implements OnMapReadyCallback {

    private GoogleMap mapa;
    private LatLng posAtual;
    private MapView mapView;

    public Mapa() {
    }

    public static Mapa newInstance(LatLng latLng) {
        Mapa fragment = new Mapa();
        Bundle init = new Bundle();
        init.putDouble("lat", latLng.latitude);
        init.putDouble("log", latLng.longitude);
        fragment.setArguments(init);
        return fragment;
    }

    @Override
    // @Nullable
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            double lat = getArguments().getDouble("lat");
            double lng = getArguments().getDouble("log");
            this.posAtual = new LatLng(lat, lng);
        }
    }

    //@Nullable
    //@NonNull
    //@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i("mapa_assincrono", "comecei a criar");
        View view = inflater.inflate(R.layout.activity_mapa, container, false);

        mapView = view.findViewById(R.id.MapaFragment);
        mapView.onCreate(savedInstanceState); // Chamar o ciclo de vida do MapView
        mapView.getMapAsync(this); // Registra o callback para quando o mapa estiver pronto

        return view;
    }

    public void mudaMapa(String tipo) {
        if (tipo.equals("hibrido"))
            mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        else
            mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mapView != null) {
            mapView.onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mapView != null) {
            mapView.onStop();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (mapView != null) {
            mapView.onDestroy(); // Destroi o MapView para liberar recursos
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            mapView.onLowMemory();
        }
    }

    //@NonNull
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null) {
            mapView.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i("mapa_assincrono", "iniciou mapa");
        Log.i("mapa_assincrono", "lat=" + posAtual.latitude + " long" + posAtual.longitude);
        mapa = googleMap;
        Cursor cursor = BancoDados.getInstance().buscar("Checkin", new String[]{"Local", "qtdVisitas", "cat", "latitude", "longitude"}, "", "");
        cursor.moveToFirst();
        while(cursor.moveToNext()){
            //percorre o cursor para pegar os dados
            int idx = cursor.getColumnIndex("Local");
            String local = cursor.getString(idx);
            idx = cursor.getColumnIndex("qtdVisitas");
            int qtdVisitas = cursor.getInt(idx);
            idx = cursor.getColumnIndex("cat");
            String categoria = cursor.getString(idx);
            idx = cursor.getColumnIndex("latitude");
            double latitude = Double.parseDouble(cursor.getString(idx));
            idx = cursor.getColumnIndex("longitude");
            double longitude = Double.parseDouble(cursor.getString(idx));

            LatLng pos = new LatLng(latitude, longitude);
            //adiciona um marcador para cada local onde foi realizado checkin
            mapa.addMarker(new MarkerOptions()
                    .position(pos)
                    .title(local)
                    .snippet("Categoria: " + categoria + " Visitas: " + qtdVisitas)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            Log.i("mapa_assincrono", local + " " + qtdVisitas + " " + categoria + " " + latitude + " " + longitude);

        }

        CameraUpdate c = CameraUpdateFactory.newCameraPosition( //centraliza a camera na posição atual do usuario
                new CameraPosition.Builder()
                        .target(posAtual)
                        .tilt(60) //inclinação da camera
                        .zoom(19)
                        .build());
        mapa.animateCamera(c);
    }
}
