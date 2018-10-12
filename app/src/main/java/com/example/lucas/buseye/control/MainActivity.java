package com.example.lucas.buseye.control;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lucas.buseye.R;
import com.example.lucas.buseye.model.ConectaAPI;
import com.example.lucas.buseye.model.Linha;

public class MainActivity extends AppCompatActivity {

    private EditText buscaOlhoVivo;
    private Button btnMap;
    public TextView mostraBuscaOlhoVivo;

    /*
    String buscaUrl = "/Linha/Buscar?termosBusca=8000";
    OlhoVivo helper = OlhoVivo.getInstance();
    String RECENT_API_ENDPOINT = "http://api.olhovivo.sptrans.com.br/v2.1";
    String rawCookies;
    Map<String, String> responseHeaders;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMap = (Button) findViewById(R.id.btMap);
        //buscaOlhoVivo = (EditText) findViewById(R.id.buscaOlhoVIvo);
        //mostraBuscaOlhoVivo = (TextView) findViewById(R.id.mostraBuscaOlhoVIvo);
        ConectaAPI.autenticarAPI();
    }

    public void abrirMapa(View view){
        ConectaAPI.autenticarAPI();
        Linha linha = new Linha();
        ConectaAPI.buscarLinha(linha);
    }
}