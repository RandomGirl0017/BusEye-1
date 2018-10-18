package com.example.lucas.buseye.control;

import android.util.Log;

import com.example.lucas.buseye.model.Linha;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LinhaControle {
    static List<Linha> linhaRetorno = new ArrayList<>();
    public static List<Linha> getLinhaRetorno() {
        return linhaRetorno;
    }
    public static void setLinhaRetorno(List<Linha> linhaRetorno) {
        LinhaControle.linhaRetorno = linhaRetorno;
    }

    /**
     *
     * @param buscaLinha recebe nome ou número da linha (pode ser o nome errado, a API faz busca fonética)
     * @return uma lista com as Linhas que possuem o nome ou o número indicado
     * @throws JSONException
     */
    public static List<Linha> buscarLinha(String buscaLinha) throws JSONException {
        JSONArray resp = new JSONArray();

            resp = ConectaAPI.buscar("/Linha/Buscar?termosBusca=" + buscaLinha);

        Log.d("RESP1",resp.toString());

        for (int i = 0; i < resp.length(); i++){
            Linha linha = new Linha();
            JSONObject json = resp.getJSONObject(i);

            //Codigo da linha
            linha.setCodigoLinha(json.get("cl").toString());
            Log.d("CODIGOLINHA",linha.getCodigoLinha());

            //número da linha
            linha.setNumLinha(json.get("lt").toString());
            Log.d("NUMLINHA",linha.getNumLinha());

            //Sentido Term Princ ou Term Sec
            linha.setSentido(json.get("sl").toString());

            //Letreiro Term Princi
            linha.setNomeTP(json.get("tp").toString());

            //Letreiro Term Sec
            linha.setNomeTS(json.get("ts").toString());
            Log.d("NOMETS",linha.getNomeTS());

            linhaRetorno.add(linha);
        }
        Log.d("RETORNO",linhaRetorno.toString());
        /*for (Linha lind : linhaRetorno){
            Log.d("LINHAS12",lind.getNumLinha().toString());
            //TODO implementar para retornar somente o sentido escolhido....
            buscarLinhaSentido(lind);
        }*/
        return linhaRetorno;
    }

    /**
     *
     * @param linha recebe uma linha
     * @return uma lista com as linhas que operam no sentido informado na busca
     * @throws JSONException
     */
    public static List<Linha> buscarLinhaSentido(Linha linha) throws  JSONException{
        JSONArray resp = new JSONArray();
        resp = ConectaAPI.buscar("/Linha/BuscarLinhaSentido?termosBusca="+linha.getCodigoLinha()+"&sentido="+linha.getSentido());

        for (int i = 0; i < resp.length(); i++){
            Linha linhaSentido = new Linha();
            JSONObject json = resp.getJSONObject(i);

            //Codigo da linha
            linhaSentido.setCodigoLinha(json.get("cl").toString());
            Log.d("CODIGOLINHA",linhaSentido.getCodigoLinha());

            //número da linha
            linhaSentido.setNumLinha(json.get("lt").toString());
            Log.d("NUMLINHA",linhaSentido.getNumLinha());

            //Sentido Term Princ ou Term Sec
            linhaSentido.setSentido(json.get("sl").toString());

            //Letreiro Term Princi
            linhaSentido.setNomeTP(json.get("tp").toString());

            //Letreiro Term Sec
            linhaSentido.setNomeTS(json.get("ts").toString());
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
