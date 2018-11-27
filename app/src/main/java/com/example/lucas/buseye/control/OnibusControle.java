package com.example.lucas.buseye.control;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.lucas.buseye.model.Linha;
import com.example.lucas.buseye.model.LinhaBd;
import com.example.lucas.buseye.model.OlhoVivo;
import com.example.lucas.buseye.model.Onibus;
import com.example.lucas.buseye.model.Ponto;
import com.example.lucas.buseye.view.MapsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnibusControle {

    //Metodos
    public static void buscarCodigoLinha(final LinhaBd linha){
        OlhoVivo helper = OlhoVivo.getInstance();
        String linhaString = linha.getRoute_id();
        linhaString = linhaString.replaceAll("\"","");
        final String[] letreiroNum = linhaString.split("-");
        //letreiroNum[0] = letreiroNum[0].replaceAll("\"","");
        Log.d("LETREIRO",letreiroNum[0].toString()+" "+letreiroNum[1].toString());
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, "http://api.olhovivo.sptrans.com.br/v2.1/Linha/Buscar?termosBusca="+letreiroNum[0],null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String sent = linha.getDirection_id().replaceAll("\"","");
                        int sentido = Integer.parseInt(sent);
                        sentido++;
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject json = null;
                            try {
                                json = response.getJSONObject(i);
                                Log.d("RESPONSE",response.toString());

                                if(letreiroNum[1].equals(json.get("tl").toString()) && sentido == json.getInt("sl")){
                                  String codigoLinha = json.get("cl").toString();
                                    Log.d("LINHACODIGO",codigoLinha);
                                    MapsActivity.repetirBuscaOnibus(codigoLinha);
                                    //buscarOnibusPosicao(codigoLinha);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERRINHA!",error.toString());
                        ConectaAPI.autenticarAPI();
                        buscarCodigoLinha(linha);
                    }
                })
                {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json;charset=UTF-8");
                        headers.put("Cookie", ConectaAPI.getRawCookies());
                        return headers;
                    }
                };
        helper.add(request);
    }

    public static void buscarOnibusPosicao(String codigoLinha){
        OlhoVivo helper = OlhoVivo.getInstance();
        ///Posicao/Linha?codigoLinha={codigoLinha}
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, "http://api.olhovivo.sptrans.com.br/v2.1//Posicao/Linha?codigoLinha="+codigoLinha,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject resp) {
                JSONArray arrResp = null;
                List<Onibus> listaOnibus = new ArrayList<>();
                try {
                    arrResp = resp.getJSONArray("vs");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listaOnibus.clear();
                for (int i = 0; i < arrResp.length(); i++) {
                    JSONObject json = null;
                    Onibus onibus = new Onibus();
                    try {
                        json = arrResp.getJSONObject(i);
                        onibus.setPrefixo(json.get("p").toString());
                        Log.d("PREFIXO",onibus.getPrefixo());
                        onibus.setAcessivel(json.getBoolean("a"));
                        onibus.setPosY(Double.parseDouble(json.get("py").toString()));
                        onibus.setPosX(Double.parseDouble(json.get("px").toString()));
                        Log.d("PREFIXO",String.valueOf(onibus.getPosX()));
                        listaOnibus.add(onibus);
                        Log.d("PREFIXO",String.valueOf(listaOnibus.get(i).getPrefixo()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0 ; i<listaOnibus.size()-1;i++) {
                    Log.d("ONIPRE",String.valueOf(listaOnibus.get(i).getPrefixo()));
                }
                MapsActivity.mostrarOnibus(listaOnibus);
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERRONIBUS!",error.toString());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json;charset=UTF-8");
                headers.put("Cookie", ConectaAPI.getRawCookies());
                return headers;
            }
        };
        helper.add(request);
    }
}

