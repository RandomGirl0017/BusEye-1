package com.example.lucas.buseye.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Ponto {
    private Linha linha;
    private boolean corredor,coberto,acessivel;
    private String nome, posX,posY, endereco, codigo;

    //GET SET
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Linha getLinha() {
        return linha;
    }

    public void setLinha(Linha linha) {
        this.linha = linha;
    }

    public boolean isCorredor() {
        return corredor;
    }

    public void setCorredor(boolean corredor) {
        this.corredor = corredor;
    }

    public boolean isCoberto() {
        return coberto;
    }

    public void setCoberto(boolean coberto) {
        this.coberto = coberto;
    }

    public boolean isAcessivel() {
        return acessivel;
    }

    public void setAcessivel(boolean acessivel) {
        this.acessivel = acessivel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPosX() {
        return posX;
    }

    public void setPosX(String posX) {
        this.posX = posX;
    }

    public String getPosY() {
        return posY;
    }

    public void setPosY(String posY) {
        this.posY = posY;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    //CONSTRUTOR

    public Ponto(Linha linha, boolean corredor, boolean coberto, boolean acessivel, String nome, String posX, String posY, String endereco,String codigo) {
        this.linha = linha;
        this.corredor = corredor;
        this.coberto = coberto;
        this.acessivel = acessivel;
        this.nome = nome;
        this.posX = posX;
        this.posY = posY;
        this.endereco = endereco;
        this.codigo = codigo;
    }

    public Ponto() {
      //  this.linha = "";
        this.corredor = false;
        this.coberto = false;
        this.acessivel = false;
        this.nome = "";
        this.posX = "";
        this.posY = "";
        this.endereco="";
        this.codigo="";
    }


    //Métodos
    public static Ponto buscarPontos(String buscaPonto) throws JSONException {

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

    public static List<Onibus> chegadaOnibusLinha(String buscaPonto, String buscaLinha)throws JSONException {
        //TODO IMPLEMENTAR FINALIZAR
        List<Linha> linArr = new ArrayList<>();
        List<Onibus> onibusArr = new ArrayList<>();
        Ponto ponto = new Ponto();
        ponto = buscarPontos(buscaPonto);
        linArr = Linha.buscarLinha(buscaLinha);


        for (Linha linha : linArr) {
            onibusArr = Linha.buscaVeiculos(linha);
            JSONArray resp = new JSONArray();
            resp = ConectaAPI.buscar("/Previsao?codigoParada=" + ponto.getCodigo() + "&codigoLinha=" + linha.getCodigoLinha());
            for (int i = 0; i < resp.length(); i++) {
                JSONObject json = resp.getJSONObject(i);

            }
        }
        return onibusArr;
    }
}
