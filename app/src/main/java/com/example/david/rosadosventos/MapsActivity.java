package com.example.david.rosadosventos;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Map<String, Cache> markers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




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
        try {
            CacheDataBase cache=new CacheDataBase(this);

            for (Cache c : cache.getCaches()) {
                Log.d("mapa", c.toString());
                // Add a marker in Sydney and move the camera
                LatLng marker = new LatLng(c.getLatitud(), c.getLongitud());
                Marker m = mMap.addMarker(new MarkerOptions().position(marker).title(c.getNombre()));
                // a medida que añadimos marcadores al mapa, los guardamos en un hashmap para luego identificarlos
                markers.put(m.getId(), c);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
            }
            //Nos permite hacer clik sobre un marcador del mapa
            mMap.setOnMarkerClickListener(this);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MapsActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // buscamos en el hashmap el marcador y sabemos de qué caché se trata
        Cache cache = markers.get(marker.getId());
        Log.d("MAPA", "código: " + cache.getCodigo());
        startActivity(CacheInfoActivity.createIntent(this, cache.getCodigo()));
        //Esto nos dara la posición de la latitud y longitud del tesoro
                Log.i("GoogleMapActivity", "onMarkerClick");
                Toast.makeText(getApplicationContext(),
                                "Esta es la posición del tesoro:\n " + marker.getPosition(), Toast.LENGTH_LONG)
                               .show();




        return false;

    }


}