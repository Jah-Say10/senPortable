package com.example.senportable;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback
{
    private GoogleMap map;

    private Double latitude;
    private Double longitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent recoverOrder = getIntent();
        if(recoverOrder != null)
        {
            latitude = Double.valueOf(recoverOrder.getStringExtra("lat"));
            longitude = Double.valueOf(recoverOrder.getStringExtra("lon"));
        }
        else
        {
            latitude = 0.0;
            longitude = 0.0;
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap)
    {
        // Initialize the map
        map = googleMap;

        // Make the coordinate
        LatLng coordonate = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(coordonate).title("Votre position"));
        map.moveCamera(CameraUpdateFactory.newLatLng(coordonate));
    }
}
