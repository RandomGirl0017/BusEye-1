package com.example.lucas.buseye.control;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.lucas.buseye.model.Linha;
import com.example.lucas.buseye.model.OlhoVivo;
import com.example.lucas.buseye.model.Ponto;
import com.example.lucas.buseye.view.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PontoControle {
    public static List<Ponto> pontos = new ArrayList<>();
    public static List<Ponto> getPontos() {
        return pontos;
    }
    public void setPontos(List<Ponto> pontos) {
        pontos = pontos;
    }

    public static Ponto ponto = new Ponto();
    public static Ponto getPonto(){return  ponto;}
    public static void setPonto(Ponto ponto) {ponto = ponto;}

    //Métodos

    public static void buscarPonto(int i){

        OlhoVivo helper = OlhoVivo.getInstance();

        //JSONArray resp = new JSONArray();
        SearchView.linhas.get(i);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, "https://buseye-bd.firebaseio.com/stops/",null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray resp) {
                Log.d("TAMANHO",resp.toString());
                try {
                    for (int i = 0; i < resp.length(); i++) {
                        String linha = "";
                        JSONObject json = null;
                        try {
                            json = resp.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("+!ER","é o nulo");
                        }
                        //Codigo da linha
                        try {
                            linha = json.get("routeShortName").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("CODIGOLINHA", linha);

                        //número da linha
                        try {
                            linha += " " + json.get("routeLongName").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("NUMLINHA", linha);
                        //buscaLinha.add(linha);
                        SearchView.linhas.add(linha);
                    }

                    SearchView.atualizarLista();
                }catch (Exception e){
                    Log.d(e.toString(),"quase");
                }
            }
        },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERRO!",error.toString());
            }
        });
        helper.add(request);
        //Log.d("RESPOSTA",resposta.toString());
    }




/*
            JSONArray resp = new JSONArray();
            resp = ConectaAPI.buscar();

            for (int i = 0; i < resp.length(); i++) {
                JSONObject json = null;
                try {
                    json = resp.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
            }
            return ponto;
        }
*/

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
