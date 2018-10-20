package com.example.lucas.buseye.control;

import android.os.AsyncTask;
import android.util.Log;

import com.example.lucas.buseye.model.Linha;
import com.example.lucas.buseye.model.Ponto;

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
    public static class buscarPonto extends AsyncTask<String, Void, Ponto> {

        @Override
        protected Ponto doInBackground(String... strings) {
            JSONArray resp = new JSONArray();
            resp = ConectaAPI.buscar("/Parada/Buscar?termosBusca=" + strings);

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
