package com.example.lucas.buseye.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Onibus {
    private String  prefixo;
    double posX, posY;
    private boolean acessivel;

    //GET SET

    public String getPrefixo() {
        return prefixo;
    }

    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }

    public boolean isAcessivel() {
        return acessivel;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(Double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(Double posY) {
        this.posY = posY;
    }

    public void setAcessivel(boolean acessivel) {
        this.acessivel = acessivel;
    }


    //CONSTRUTOR
    public Onibus() {
        this.acessivel = false;
        this.posX = 0;
        this.posY = 0;
    }


    //METODOS
/*
    public void buscaPrevisaoChegada(Ponto ponto,Linha linha) throws JSONException {
        String auxArr = ConectaAPI.buscar("/Previsao?codigoParada="+ponto.getCodigo()+"&codigoLinha="+linha.getCodigoLinha());
        String aux = auxArr.toString();
        JSONObject onibusObj = new JSONObject(aux);
        JSONArray onibusArr = onibusObj.getJSONArray("vs");
        Log.d("ARRAY",onibusArr.toString());
         List<Onibus> onibusLista = new ArrayList<>();
        for (int i = 0; i < onibusArr.length(); i++) {
            //JSONObject explrObject = onibusArr.getJSONObject(i);
            Onibus onibus = new Onibus();

            //é acessivel?
            onibus.setAcessivel(onibus.isAcessivel(aux.substring(aux.indexOf("a")+4,aux.indexOf(","))));

            //Posição Y - Latitude
            onibus.setPosY(aux.substring(aux.indexOf("py")+4,aux.indexOf(",")));

            //Posição X - Altitude
            onibus.setPosX(aux.substring(aux.indexOf("px")+4,aux.indexOf(",")));

            //coloca no Array Onibus(dessa classe)
            onibusLista.add(onibus);
        }
        //Coloca essa lista de ônibus na classe Linha
        linha.setOnibus(onibusLista);
    }


    public boolean isAcessivel(String verAcessivel) {
        if (verAcessivel == "true"){
            acessivel = true;
        }else{
            acessivel = false;
        }
        return acessivel;
    }

  /*  public void mostrarOnibus(){
        //TODO implementar
        List<Onibus> on = this.onibus;
        List<String> any = new ArrayList<>();
        for (Onibus oni : onibus) {
            //   any = oni.getGeoPosicao();
        }
        return any;
    }*/
}
