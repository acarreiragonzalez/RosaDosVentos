package com.example.david.rosadosventos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;


public class CacheInfoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String mCodigo;
    private com.example.david.rosadosventos.CacheDataBase mCaches;
    private Cache cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cacheinfo);
        //Cogemos el fragmento para poner la descripcion
        DescripcionFragment descripcionF = (DescripcionFragment) getFragmentManager().findFragmentById(R.id.fragment2);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment);

        //Obtenemos el codigo del intent
        String codigo = getIntent().getStringExtra("codigo");
        Log.d("INFO", "codigo recibido: " + codigo);

//cogemos la informacion del cache que tiene ese codigo
        try {
            CacheDataBase cachedb=new CacheDataBase(this);
            cache = cachedb.getCache(codigo);
            Log.d("INFO", "cache: " + cache.toString());

            //Ponemos la descripcion en el fragment
            descripcionF.setDescripcion(cache.getDescripcion());

            //Preparamos el mapa. Cuando acabe, se llamará a onMapReady()
            mapFragment.getMapAsync(this);



        } catch (IOException e) {
            e.printStackTrace();
        }


       Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(BusquedaActivity.createIntent(CacheInfoActivity.this, cache.getCodigo()));
            }
        });

    }



    public static Intent createIntent(Context context, String codigo) {
        Intent intent = new Intent(context, CacheInfoActivity.class);
        intent.putExtra("codigo", codigo);

        return intent;
    }

    /**
     * Una vez cargado el mapa, este metodo hara que nos centre el market y ajuste el zoon tambien hemos cambiado el tipo de mapa
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng marker = new LatLng(cache.getLatitud(), cache.getLongitud());
        googleMap.addMarker(new MarkerOptions().position(marker).title(cache.getNombre()));
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 16));
    }
}