package com.example.lucas.buseye.control;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lucas.buseye.R;
import com.example.lucas.buseye.model.ConectaAPI;
import com.example.lucas.buseye.model.Linha;
import com.example.lucas.buseye.model.Ponto;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    /*
    String buscaUrl = "/Linha/Buscar?termosBusca=8000";
    OlhoVivo helper = OlhoVivo.getInstance();
    String RECENT_API_ENDPOINT = "http://api.olhovivo.sptrans.com.br/v2.1";
    String rawCookies;
    Map<String, String> responseHeaders;
*/
    String aux;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnMap = (Button) findViewById(R.id.btMap);
        //buscaOlhoVivo = (EditText) findViewById(R.id.buscaOlhoVIvo);
        //mostraBuscaOlhoVivo = (TextView) findViewById(R.id.mostraBuscaOlhoVIvo);
        aux = "";
        //ConectaAPI.autenticarAPI();
    }

    public void abrirMapa(View view) throws JSONException {
      ConectaAPI.autenticarAPI();
        aux ="/Linha/Buscar?termosBusca=8000";
      //aux =  ConectaAPI.buscar(aux);
      // Log.d("AUX",aux);
        Linha.buscarLinha();
    }




    public void abrirRotas(View view){
        Intent intent = new Intent(this, RotasActivity.class);
        startActivity(intent);
    }
}