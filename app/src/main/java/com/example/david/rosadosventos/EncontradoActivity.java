package com.example.david.rosadosventos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class EncontradoActivity extends AppCompatActivity {

    String codigo;
    //lanzamos el scanner tal como indica en :https://github.com/journeyapps/zxing-android-embedded
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encontrado);

        codigo=getIntent().getStringExtra("codigo");
        new IntentIntegrator(this).initiateScan(); // `this` is the current Activity
    }

    public static Intent createIntent(Context context, String codigo) {
        Intent intent = new Intent(context, EncontradoActivity.class);
        intent.putExtra("codigo", codigo);

        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                String codigoScan = result.getContents();
                if (codigo.equals(codigoScan)) {
                    Toast.makeText(this, "Lo has encontrado! ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "El código no se corresponde con el caché buscado.", Toast.LENGTH_LONG).show();
                }
                finish();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}