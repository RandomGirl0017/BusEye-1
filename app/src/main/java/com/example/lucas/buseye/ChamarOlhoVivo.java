package com.example.lucas.buseye;

import android.net.Uri;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ChamarOlhoVivo {
    final static String OLHOVIVO_BASE_URL = "http://api.olhovivo.sptrans.com.br/v2.1./";
    final static String PARAM_QUERY = "q";
    final static  String PARAM_SORT = "sort";
    final static String sortBy = "Stars";
    private final static String chave = "6646f77742d36ab30a967cdcb444ecd175ef4e685ff93abd29803d56786f6436";


    public static URL buildUrl(String olhoVivoBuscaQuery) {
        Uri builtUri =
                Uri.parse(OLHOVIVO_BASE_URL).buildUpon()

                        .appendQueryParameter(PARAM_QUERY,olhoVivoBuscaQuery)
                        .appendQueryParameter(PARAM_SORT,sortBy)
                        .build();
        URL url = null;
        try{
            url = new URL(builtUri.toString());
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static void olhoVivoAuten(String chave) throws IOException {
      URL url = buildUrl("http://api.olhovivo.sptrans.com.br/v2.1./Login/Autenticar?token="+chave);
        ConexaoUrl.respostaDoUrl(url);
        
    }
}
