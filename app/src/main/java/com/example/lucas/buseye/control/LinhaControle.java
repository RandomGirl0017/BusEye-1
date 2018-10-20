package com.example.lucas.buseye.control;

import android.os.AsyncTask;
import android.util.Log;

import com.example.lucas.buseye.model.Linha;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LinhaControle {
    private static List<Linha> linhaRetorno = new ArrayList<>();
    public static List<Linha> getLinhaRetorno() {
        return linhaRetorno;
    }
    public static void setLinhaRetorno(List<Linha> linhaRetorno) {
        LinhaControle.linhaRetorno = linhaRetorno;
    }

    /**
     *
     *  /@param buscaLinha recebe nome ou número da linha (pode ser o nome errado, a API faz busca fonética)
     * @return uma lista com as Linhas que possuem o nome ou o número indicado
     * @throws JSONException
     */
    public static class  buscarLinha extends AsyncTask<Void,Void,List<Linha>> {
        String buscaLinha;
        public buscarLinha(String buscaLinha){
            this.buscaLinha = buscaLinha;
        }
        @Override
        protected List<Linha> doInBackground(Void...voids)  {
            JSONArray resp = new JSONArray();
                resp = ConectaAPI.buscar("/Linha/Buscar?termosBusca=" + buscaLinha);
                Log.d("RESP1", resp.toString());
            for (int i = 0; i < resp.length(); i++){
                Linha linha = new Linha();
                JSONObject json = null;
                try {
                    json = resp.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Codigo da linha
                try {
                    linha.setCodigoLinha(json.get("cl").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("CODIGOLINHA",linha.getCodigoLinha());

                //número da linha
                try {
                    linha.setNumLinha(json.get("lt").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("NUMLINHA",linha.getNumLinha());

                //Sentido Term Princ ou Term Sec
                try {
                    linha.setSentido(json.get("sl").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Letreiro Term Princi
                try {
                    linha.setNomeTP(json.get("tp").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Letreiro Term Sec
                try {
                    linha.setNomeTS(json.get("ts").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("NOMETS",linha.getNomeTS());

                linhaRetorno.add(linha);
            }
            for (Linha l: linhaRetorno) {
                Log.d("RETORNO",l.getCodigoLinha());
            }

        /*for (Linha lind : linhaRetorno){
            Log.d("LINHAS12",lind.getNumLinha().toString());
            //TODO implementar para retornar somente o sentido escolhido....
            buscarLinhaSentido(lind);
        }*/
            return linhaRetorno;
        }
    }

    /**
     *
     *  /@param linha recebe uma linha
     * @return uma lista com as linhas que operam no sentido informado na busca
     * @throws JSONException
     */
    private class buscarLinhaSentido extends AsyncTask<Linha,Void,List<Linha>>{
        @Override
        protected List<Linha> doInBackground(Linha... linha) {
            JSONArray resp = new JSONArray();
            for (Linha l :linha)
            {
                resp = ConectaAPI.buscar("/Linha/BuscarLinhaSentido?termosBusca="+l.getCodigoLinha()+"&sentido="+l.getSentido());
            }


            for (int i = 0; i < resp.length(); i++){
                Linha linhaSentido = new Linha();
                JSONObject json = null;
                try {
                    json = resp.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Codigo da linha
                try {
                    linhaSentido.setCodigoLinha(json.get("cl").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("CODIGOLINHA",linhaSentido.getCodigoLinha());

                //número da linha
                try {
                    linhaSentido.setNumLinha(json.get("lt").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("NUMLINHA",linhaSentido.getNumLinha());

                //Sentido Term Princ ou Term Sec
                try {
                    linhaSentido.setSentido(json.get("sl").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Letreiro Term Princi
                try {
                    linhaSentido.setNomeTP(json.get("tp").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Letreiro Term Sec
                try {
                    linhaSentido.setNomeTS(json.get("ts").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("NOMETS",linhaSentido.getNomeTS());

                linhaRetorno.add(linhaSentido);
        }
         /*
        *
        * Esse é o modo de passar os parametros de uma lista

        Log.d("RETORNO",linhaRetorno.toString());
        for (Linha lind : linhaRetorno){
            Log.d("LINHAS12",lind.getNumLinha().toString());
        } */
            return linhaRetorno;
    }
    }


}
