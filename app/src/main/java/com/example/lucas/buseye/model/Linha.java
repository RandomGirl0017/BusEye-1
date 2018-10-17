package com.example.lucas.buseye.model;

import com.example.lucas.buseye.control.LinhaControle;

import java.util.ArrayList;
import java.util.List;

public class Linha {
   private String nomeTP, nomeTS, horario, sentido, numLinha, qntdeOnibusCirculando, codigoLinha;
    private List<String> trajeto = new ArrayList<>();
    private double extensao;
    private boolean noturno;

    //GET SET
    public String getCodigoLinha() {
        return codigoLinha;
    }

    public void setCodigoLinha(String codigoLinha) {
        this.codigoLinha = codigoLinha;
    }

    public String getNomeTP() {
        return nomeTP;
    }

    public void setNomeTP(String nomeTP) {
        this.nomeTP = nomeTP;
    }

    public String getNomeTS() {
        return nomeTS;
    }

    public void setNomeTS(String nomeTS) {
        this.nomeTS = nomeTS;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getSentido() {
        return sentido;
    }

    public void setSentido(String sentido) {
        this.sentido = sentido;
    }

    public String getNumLinha() {
        return numLinha;
    }

    public void setNumLinha(String numLinha) {
        this.numLinha = numLinha;
    }

    public List<String> getTrajeto() {
        return trajeto;
    }

    public void setTrajeto(List<String> trajeto) {
        this.trajeto = trajeto;
    }

    public double getExtensao() {
        return extensao;
    }

    public void setExtensao(double extensao) {
        this.extensao = extensao;
    }

    public boolean isNoturno() {
        return noturno;
    }

    public void setNoturno(boolean noturno) {
        this.noturno = noturno;
    }

    public String getQntdeOnibusCirculando() {
        return qntdeOnibusCirculando;
    }

    public void setQntdeOnibusCirculando(String qntdeOnibusCirculando) {
        this.qntdeOnibusCirculando = qntdeOnibusCirculando;
    }

    //CONSTRUTOR
    public Linha() {
        this.nomeTP = "";
        this.nomeTS="";
        this.horario = "";
        this.sentido = "";
        this.numLinha = "";
        this.qntdeOnibusCirculando = "";
        this.codigoLinha="";
    }

}
