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
import com.example.lucas.buseye.view.MapsActivity;
import com.example.lucas.buseye.view.SearchView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LinhaControle {
    private static List<Linha> linhaRetorno = new ArrayList<>();
    static List<String> buscaLinha = new ArrayList<>();
    public static LinhaBd linha = new LinhaBd();
    public static List<Linha> getLinhaRetorno() {
        return linhaRetorno;
    }

    public static void setLinhaRetorno(List<Linha> linhaRetorno) {
        LinhaControle.linhaRetorno = linhaRetorno;
    }

    /**
     * /@param buscaLinha recebe nome ou número da linha (pode ser o nome errado, a API faz busca fonética)
     *
     * @return uma lista com as Linhas que possuem o nome ou o número indicado
     * @throws JSONException
     */
    public static void mostrarTodasLinhas() {
        OlhoVivo helper = OlhoVivo.getInstance();

        //JSONArray resp = new JSONArray();

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, "https://buseye-bd.firebaseio.com/trips.json", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray resp) {
                Log.d("TAMANHO", resp.toString());
                try {
                    for (int i = 1; i < resp.length(); i++) {
                        String linha = "";
                        JSONObject json = null;
                        try {
                            json = resp.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("+!ER", "é o nulo");
                        }
                        //Codigo da linha
                        try {
                            linha = json.get("routeId").toString().replaceAll("\"","")+"/";
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("CODIGOLINHA", linha);

                        //número da linha
                        try {
                            linha += " " + json.get("tripHeadSign").toString().replaceAll("\"","");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("NUMLINHA", linha);
                        //buscaLinha.add(linha);
                        SearchView.linhas.add(linha);
                    }

                    SearchView.atualizarLista();
                } catch (Exception e) {
                    Log.d(e.toString(), "quase");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERRO!", error.toString());
            }
        });
        helper.add(request);
        //Log.d("RESPOSTA",resposta.toString());
    }

    public static void buscarLinha(String index) {
        OlhoVivo helper = OlhoVivo.getInstance();

        String buscar = "";

        final String[] busca = index.split("/");
        Log.d("BUSCA1",busca[0]);
        buscar = busca[0];
        buscar = buscar.replaceAll("\"","");
        busca[1]=busca[1].trim();
        busca[1] = "\""+busca[1]+"\"";
        Log.d("INDEX",buscar);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, "https://buseye-bd.firebaseio.com/trips/.json?orderBy=\"routeId\"&equalTo=\"\\\""+buscar+"\\\"\"", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject resp) {
                try {
                    for (int i = 0; i < 2; i++) {

                        Iterator<String> keys = resp.keys();
                        // get some_name_i_wont_know in str_Name
                        String str_Name=keys.next();
                        // get the value i care about
                        JSONObject value = resp.getJSONObject(str_Name);
                        JSONArray names = resp.names();

                        String testa = value.get("tripHeadSign").toString().replaceAll("['\\']","");
                        Log.d("+VALUES",testa.replaceAll("\"","").trim());
                        Log.d("+VALUESB",busca[1].trim());
                        if( testa.trim().equals(busca[1])){

                            //numero da linha
                            try {
                                linha.setRoute_id(value.get("routeId").toString().replaceAll(" ['\\'] " ,null).trim());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //nome da linha
                            try {
                                linha.setTrip_headsign(value.get("tripHeadSign").toString().replaceAll(" ['\\'] " ,null).trim());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //numero + sentido
                            try {
                                linha.setTrip_id(value.get("tripId").toString().replaceAll(" ['\\'] " ,"").trim());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //direção
                            try {
                                linha.setDirection_id(value.get("directionId").toString().replaceAll(" ['\\'] " ,"").trim());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //shapeID
                            try {
                                linha.setShape_id(value.get("shapeId").toString().replaceAll(" ['\\'] " ,"").trim());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            PontoControle.buscarPontoId(linha.getTrip_id());
                            RotaControle.mostrarRota(linha.getShape_id());
                            //MapsActivity.mostrarOnibus(linha);
                            OnibusControle.buscarCodigoLinha(linha);
                            //linhaControle.get(linha)
                            //
                            break;
                        }
                        resp.remove(names.getString(0));
                }
                }catch(Exception e) {
                    Log.d(e.toString(), "quase");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERRO!", error.toString());
            }
        });
        helper.add(request);
    }
}