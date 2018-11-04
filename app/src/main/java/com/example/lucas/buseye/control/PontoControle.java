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
    public static List<Ponto> pontos = new ArrayList<>();
    public static List<Ponto> getPontos() {
        return pontos;
    }
    public void setPontos(List<Ponto> pontos) {
        pontos = pontos;
    }

    public static List<String> pontoCod = new ArrayList<>();

    public static Ponto ponto = new Ponto();
    public static Ponto getPonto(){return  ponto;}
    public static void setPonto(Ponto ponto) {ponto = ponto;}

    //Métodos

    public static void buscarPontoId(String trip){

        OlhoVivo helper = OlhoVivo.getInstance();
        //final Ponto ponto = new Ponto();
        //JSONArray resp = new JSONArray();
        trip=trip.replaceAll("\"","");
        Log.d("PONTO",trip);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, "https://buseye-bd.firebaseio.com/stopTimes/.json?orderBy=\"tripId\"&equalTo=\"\\\""+trip+"\\\"\"",null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject resp) {

                try {

                    JSONArray nome = resp.names();

                        for(int i = 0; i < nome.length(); i++){
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
                buscarPontoPos(pontoCod);
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

    public static void buscarPontoPos(List<String> pontoCod) {
        OlhoVivo helper = OlhoVivo.getInstance();

        //JSONArray resp = new JSONArray();
        for (String id : pontoCod) {
            Log.d("TAMANHO", id);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.GET, "https://buseye-bd.firebaseio.com/stops/.json?orderBy=\"stopId\"&equalTo=" + id, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject resp) {

                    try {

                        for (int i = 0; i < resp.length(); i++) {
                            Iterator<String> keys = resp.keys();
                            // get some_name_i_wont_know in str_Name
                            String str_Name = keys.next();
                            // get the value i care about
                            JSONObject json = resp.getJSONObject(str_Name);
                            JSONArray names = resp.names();

                            try {
                                //endereço Ponto
                                ponto.setEndereco(json.get("stopId").toString().replaceAll(" ['\\'] ", "").trim());
                                // Ponto LAt
                                ponto.setPosY(json.get("stopLat").toString().replaceAll(" ['\\'] ", "").trim());
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



    public static class buscarPontosPorLinha extends AsyncTask<Void, Void, List<Ponto>> {

        //RECEBE LINHA COMO PARAMETRO PARA O METODO
        Linha linha = new Linha();
        public buscarPontosPorLinha(Linha linha) {
            this.linha = linha;
        }

        @Override
        protected List<Ponto> doInBackground(Void... voids) {
            JSONArray resp = new JSONArray();
            resp = ConectaAPI.buscar("/Parada/BuscarParadasPorLinha?codigoLinha=" + Integer.parseInt(linha.getCodigoLinha()));
            Log.d("++CDLI", resp.toString());

            for (int i = 0; i < resp.length(); i++) {
                JSONObject json = null;
                try {
                    json = resp.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Ponto ponto = new Ponto();
                //Codigo da parada

                try {
                    ponto.setCodigo(json.get("cp").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Nome da parada
                try {
                    ponto.setNome(json.get("np").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Endereço
                try {
                    ponto.setEndereco(json.get("ed").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Posição Y - Latitude
                try {
                    ponto.setPosY(json.get("py").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Posição X - Altitude
                try {
                    ponto.setPosX(json.get("px").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                pontos.add(ponto);
            }

            //VERIFICASE É NULO
            if (pontos.size() > 0) {
                return pontos;
            } else {
                Ponto p = new Ponto();
                pontos.add(p);
                return pontos;
            }
        }
    }
}
