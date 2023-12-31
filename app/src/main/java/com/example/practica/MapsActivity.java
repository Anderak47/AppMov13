package com.example.practica;

import android.Manifest;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.practica.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LocationManager mLocationManager;
    public double latitud;
    public double longitud;
    public String nombrePisaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        if (intent != null){
            latitud = intent.getDoubleExtra("latitud",0.8);
            longitud = intent.getDoubleExtra("longitud",0.5);
            nombrePisaje = intent.getStringExtra("name");
            Log.i("MAIN_APP: Location - ", "Latitude: " + longitud);
            Log.i("MAIN_APP: Location - ", "Longitude: " + latitud);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //ACA CARGA EL MAPA DE ACUERDO A LAS CORDENADAS

        // Add a marker in Sydney and move the camera
        LatLng coordenadas = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(coordenadas).title(nombrePisaje));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadas, 8f));

    }

}