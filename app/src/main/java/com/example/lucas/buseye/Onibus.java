package com.example.lucas.buseye;

import java.util.ArrayList;
import java.util.List;

public class Onibus {
    private String prevChegada, direcao,previsao;
    private boolean acessivel;
    private List<String> geoPosicao = new ArrayList<>();


    //GET SET
    public void setPrevChegada(String prevChegada) {
        this.prevChegada = prevChegada;
    }

    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }

    public void setPrevisao(String previsao) {
        this.previsao = previsao;
    }

    public void setQntdeCirculando(String qntdeCirculando) {
        this.qntdeCirculando = qntdeCirculando;
    }

    public void setAcessivel(boolean acessivel) {
        this.acessivel = acessivel;
    }

    public void setGeoPosicao(List<String> geoPosicao) {
        this.geoPosicao = geoPosicao;
    }

    //CONSTRUTOR
    public Onibus(String prevChegada, String direcao, String previsao, String qntdeCirculando, boolean acessivel, List<String> geoPosicao) {
        this.prevChegada = prevChegada;
        this.direcao = direcao;
        this.previsao = previsao;
        this.qntdeCirculando = qntdeCirculando;
        this.acessivel = acessivel;
        this.geoPosicao = geoPosicao;
    }
    //FIM

    //METODOS
    public List<String> getGeoPosicao() {
        return geoPosicao;
    }

    public String getQntdeCirculando() {
        return qntdeCirculando;
    }

    public String getPrevChegada() {
        return prevChegada;
    }

    public String getDirecao() {
        return direcao;
    }

    public String getPrevisao() {
        return previsao;
    }

    public boolean isAcessivel() {
        return acessivel;
    }

}
