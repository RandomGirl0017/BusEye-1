package com.example.lucas.buseye.model;

import java.util.ArrayList;
import java.util.List;

public class Ponto {
    private Linha linha;
    private boolean corredor,coberto,acessivel;
    private String nome, posX,posY, endereco;

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



    //CONSTRUTOR

    public Ponto(Linha linha, boolean corredor, boolean coberto, boolean acessivel, String nome, String posX, String posY, String endereco) {
        this.linha = linha;
        this.corredor = corredor;
        this.coberto = coberto;
        this.acessivel = acessivel;
        this.nome = nome;
        this.posX = posX;
        this.posY = posY;
        this.endereco = endereco;
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
    }


    //Métodos

    public void buscarPontos(Ponto ponto, String buscaPonto){
        String aux = ConectaAPI.buscar("/Parada/Buscar?termosBusca="+buscaPonto);

        //Nome da parada
        ponto.setNome(aux.substring(aux.indexOf("np")+4,aux.indexOf(",")));

        //Endereço
        ponto.setEndereco(aux.substring(aux.indexOf("ed")+4,aux.indexOf(",")));

        //Posição Y - Latitude
        ponto.setPosY(aux.substring(aux.indexOf("py")+4,aux.indexOf(",")));

        //Posição X - Altitude
        ponto.setPosX(aux.substring(aux.indexOf("px")+4,aux.indexOf(",")));

    }
}
