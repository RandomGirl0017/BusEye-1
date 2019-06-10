package com.example.lucas.buseye.control;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.lucas.buseye.model.Linha;
import com.example.lucas.buseye.model.OlhoVivo;
import com.example.lucas.buseye.model.Ponto;
import com.example.lucas.buseye.view.MapsActivity;
import com.example.lucas.buseye.view.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PontoControle {
    public static Ponto ponto = new Ponto();
    public static Ponto getPonto(){return  ponto;}
    public static void setPonto(Ponto ponto) {ponto = ponto;}

    //Métodos

    /***
     * Recupera o valor código dos pontos a partir dos objetos Trips no BD
     * chama o método buscarPontosPos
     * @param trip recebe uma String com o valor Trip_Id de LinhaBD
     */
    public static void buscarPontoId(String trip){

        OlhoVivo helper = OlhoVivo.getInstance();
        trip=trip.replaceAll("\"","");
        Log.d("PONTO",trip);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, "https://buseye-bd.firebaseio.com/stopTimes/.json?orderBy=\"tripId\"&equalTo=\"\\\""+trip+"\\\"\"",null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject resp) {
                List<String> pontoCod = new ArrayList<>();
                Log.d("TAMANHOLISTALONG",String.valueOf(pontoCod.size()));
                try {
                    JSONArray nome = resp.names();
                    for(int i = 0; i < nome.length(); i++)
                    {
                        JSONObject json = resp.getJSONObject(nome.get(i).toString());
                        try {

                            //ponto.setCodigo(json.get("stopId").toString().replaceAll(" ['\\'] " ,"").trim());
                            pontoCod.add(json.get("stopId").toString().replaceAll(" ['\\'] ", "").trim());
                            Log.d("STOPID", String.valueOf(pontoCod.get(i)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }catch (Exception e){
                    Log.d(e.toString(),"quase");
                    e.printStackTrace();
                }
                Log.d("TAMANHOLISTALONG",String.valueOf(pontoCod.size()));
                buscarPontoPos(pontoCod);
                pontoCod.clear();
            }
        },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("AQUI",error.toString());
            }
        });
        helper.add(request);
        //Log.d("RESPOSTA",resposta.toString());
    }

    /***
     * Busca a Latitude e Longitude de cada ponto presente na Lista. Chama o método mostrarPontos na MapsActivity
     * @param pontoCod recebe uma lsita com os valores Stop_id de cada ponto que a Linha atende nas viagens com o sentido escolhido
     */
    public static void buscarPontoPos(List<String> pontoCod) {
        OlhoVivo helper = OlhoVivo.getInstance();
        //JSONArray resp = new JSONArray();
        for (String id : pontoCod) {
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.GET, "https://buseye-bd.firebaseio.com/stops/.json?orderBy=\"stopId\"&equalTo=" + id, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject resp) {
                    try {
                        List<Ponto> pontos = new ArrayList<>();
                        Log.d("TAMANHOLISTALONG",String.valueOf(pontos.size()));
                        for (int i = 0; i < resp.length(); i++) {
                            Iterator<String> keys = resp.keys();
                            // get some_name_i_wont_know in str_Name
                            String str_Name = keys.next();
                            // get the value i care about
                            JSONObject json = resp.getJSONObject(str_Name);
                            JSONArray names = resp.names();

                            try {
                                //endereço Ponto
                                ponto.setEndereco(json.get("stopId").toString().replaceAll(" ['\\']['\"'] ", "").trim());
                                // Ponto LAt
                                ponto.setPosY(json.get("stopLat").toString().replaceAll(" ['\\']['\"'] ", "").trim());
                                Log.d("POSY", ponto.getPosY());
                                // Ponto Longt
                                ponto.setPosX(json.get("stopLong").toString().replaceAll(" ['\\'] ", "").trim());
                                // Ponto Nome
                                ponto.setNome(json.get("stopName").toString().replaceAll(" ['\\'] ", "").trim());

                                pontos.add(ponto);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("TAMANHOLISTALONG",String.valueOf(pontos.size()));
                        MapsActivity.mostrarPontos(pontos);
                    } catch (Exception e) {
                        Log.d(e.toString(), "POSYquase");
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

    }
}
