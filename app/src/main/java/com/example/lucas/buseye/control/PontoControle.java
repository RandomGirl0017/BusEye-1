package com.example.lucas.buseye.control;

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


    public List<Ponto> getPontos() {
        return pontos;
    }
    public void setPontos(List<Ponto> pontos) {
        pontos = pontos;
    }

    //Métodos

    /**
     *
     * @param buscaPonto Endereço, nome ou codigo de um ponto
     * @return o Ponto
     * @throws JSONException
     */
    public static Ponto buscarPonto(String buscaPonto) throws JSONException {

        Ponto ponto = new Ponto();
        JSONArray resp = new JSONArray();
        resp = ConectaAPI.buscar("/Parada/Buscar?termosBusca=" + buscaPonto);

        for (int i = 0; i < resp.length(); i++) {
            JSONObject json = resp.getJSONObject(i);

            //Codigo da parada

            ponto.setCodigo(json.get("cp").toString());

            //Nome da parada
            ponto.setNome(json.get("np").toString());

            //Endereço
            ponto.setEndereco(json.get("ed").toString());

            //Posição Y - Latitude
            ponto.setPosY(json.get("py").toString());

            //Posição X - Altitude
            ponto.setPosX(json.get("px").toString());
        }
        return ponto;
    }

    /**
     *
     * @param linha Linha a ter os pontos pesquisados
     * @return  uma lista com todos os Pontos da linha pesquisada
     * @throws JSONException
     */
    public static List<Ponto> buscarPontosPorLinha(Linha linha)throws JSONException {
        JSONArray resp = new JSONArray();
        resp = ConectaAPI.buscar("/Parada/BuscarParadasPorLinha?codigoLinha=" + Integer.parseInt(linha.getCodigoLinha()));
        Log.d("++CDLI",resp.toString());

        for (int i = 0; i < resp.length(); i++) {
            JSONObject json = resp.getJSONObject(i);
            Ponto ponto = new Ponto();
            //Codigo da parada

            ponto.setCodigo(json.get("cp").toString());

            //Nome da parada
            ponto.setNome(json.get("np").toString());

            //Endereço
            ponto.setEndereco(json.get("ed").toString());

            //Posição Y - Latitude
            ponto.setPosY(json.get("py").toString());

            //Posição X - Altitude
            ponto.setPosX(json.get("px").toString());

            pontos.add(ponto);
        }

        //VERIFICASE É NULO
        if(pontos.size() > 0)
        {
            return pontos;
        }else{
            Ponto p = new Ponto();
            pontos.add(p);
            return pontos;
        }
    }

}
