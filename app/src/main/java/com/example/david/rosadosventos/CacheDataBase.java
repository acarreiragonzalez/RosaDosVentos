package com.example.david.rosadosventos;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by David on 14/01/2016.
 */
public class CacheDataBase {

    HashMap<String, Cache>caches;

    /**
     *1 Inicializamos lista de caches y leemos del fichero los datos
     */
    public CacheDataBase(Context context) throws IOException {
        this.caches = new HashMap<>() ;
        InputStream inputstr = context.getResources().openRawResource(R.raw.cachesdatabase);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputstr));

        String line;
        while ((line = br.readLine()) != null) {
            //leer linea y guardar objeto en el hashmap
            String[] datos = line.split("\\|");
            Cache c = new Cache();
            c.setCodigo(datos[0]);
            c.setLatitud(Double.parseDouble(datos[1]));
            c.setLongitud(Double.parseDouble(datos[2]));
            c.setNombre(datos[3]);
            c.setDescripcion(datos[4]);
            caches.put(c.codigo,c);
        }
    }

    /**
     * Devuelve la colección entera de caches
     * @return
     */
    public Collection<Cache> getCaches() {
        return caches.values();
    }

    /**
     * Devuelve el caché con el código dado
     * @param codigo
     * @return
     */
    public Cache getCache(String codigo) {
        return caches.get(codigo);
    }
}
