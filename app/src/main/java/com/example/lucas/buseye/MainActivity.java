package com.example.lucas.buseye;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText buscaOlhoVivo;
    private TextView mostraBuscaOlhoVivo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buscaOlhoVivo = (EditText) findViewById(R.id.buscaOlhoVIvo);
        mostraBuscaOlhoVivo = (TextView) findViewById(R.id.mostraBuscaOlhoVIvo);
    }

    //busca APIOLHOVIVO
    void makeOLhoVivoBusca(EditText buscaOlhoVivo, TextView mostraBuscaOlhoVivo){
        String OlhoVivoBusca = buscaOlhoVivo.getText().toString();
        URL OlhoVivoBuscaUrl = ChamarOlhoVivo.buildUrl(OlhoVivoBusca);
        mostraBuscaOlhoVivo.setText(OlhoVivoBuscaUrl.toString());
        String resultadoBuscaOlhoVivo = null;
        new OlhoVivoQueryTask().execute(OlhoVivoBuscaUrl);
    }

    public class OlhoVivoQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            URL buscaUrl = urls[0];
            String olhoVivoBuscaResutados = null;
            try{
                olhoVivoBuscaResutados = ConexaoUrl.respostaDoUrl(buscaUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return olhoVivoBuscaResutados;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null && !s.equals("")){
                mostraBuscaOlhoVivo.setText(s);
            }
        }
    }

    public void abrirMapa(View view) throws IOException {
       /* Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);*/
        makeOLhoVivoBusca(buscaOlhoVivo,mostraBuscaOlhoVivo);
    }
}
