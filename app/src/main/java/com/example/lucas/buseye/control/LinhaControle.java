package com.example.lucas.buseye.control;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.lucas.buseye.model.Linha;
import com.example.lucas.buseye.model.LinhaBd;
import com.example.lucas.buseye.model.OlhoVivo;
import com.example.lucas.buseye.view.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinhaControle {
    private static List<Linha> linhaRetorno = new ArrayList<>();
    static List<String> buscaLinha = new ArrayList<>();

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
                    for (int i = 0; i < resp.length(); i++) {
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
                            linha = json.get("routeId").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("CODIGOLINHA", linha);

                        //número da linha
                        try {
                            linha += " " + json.get("tripHeadSign").toString();
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

    public static void buscarLinha(int i) {
        OlhoVivo helper = OlhoVivo.getInstance();

        //JSONArray resp = new JSONArray();
       String index = SearchView.linhas.get(i).replaceFirst(" ",",");
       Log.d("+TESTE+",index);
        String[] busca = index.split(",");
        JsonArrayRequest request = new JsonArrayRequest(
                //TODO arrumar o mecanismo de busca!!!!
                Request.Method.GET, "https://buseye-bd.firebaseio.com/trips/"+busca[1]+".json", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray resp) {
                Log.d("TAMANHO", resp.toString());
                try {
                    for (int i = 0; i < resp.length(); i++) {
                        LinhaBd linha = new LinhaBd();
                        JSONObject json = null;
                        try {
                            json = resp.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("+!ER", "é o nulo");
                        }
                        //numero da linha
                        try {
                            linha.setRoute_id(json.get("routeId").toString().replaceAll(" ['\\'] " ,null).trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //nome da linha
                        try {
                            linha.setTrip_headsign(json.get("tripHeadSign").toString().replaceAll(" ['\\'] " ,null).trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("HEADSIGN", linha.getTrip_headsign());
                        //numero + sentido
                        try {
                            linha.setTrip_headsign(json.get("tripId").toString().replaceAll(" ['\\'] " ,null).trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //direção
                        try {
                            linha.setDirection_id(json.get("directionId").toString().replaceAll(" ['\\'] " ,null).trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //shapeID
                        try {
                            linha.setShape_id(json.get("shapeId").toString().replaceAll(" ['\\'] " ,null).trim());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    RotaControle.mostrarRota(linha.getRoute_id());
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






    /**
     *
     *  /@param linha recebe uma linha
     * @return uma lista com as linhas que operam no sentido informado na busca
     * @throws JSONException
     *
    private class buscarLinhaSentido extends AsyncTask<Linha,Void,List<Linha>>{
        @Override
        protected List<Linha> doInBackground(Linha... linha) {
            JSONArray resp = new JSONArray();
            for (Linha l :linha)
            {
                resp = ConectaAPI.buscar("/Linha/BuscarLinhaSentido?termosBusca="+l.getCodigoLinha()+"&sentido="+l.getSentido());
            }


            
         /*
        *
        * Esse é o modo de passar os parametros de uma lista

        Log.d("RETORNO",linhaRetorno.toString());
        for (Linha lind : linhaRetorno){
            Log.d("LINHAS12",lind.getNumLinha().toString());
        }
            return linhaRetorno;
    }
    }
*/
