package com.example.lucas.buseye.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class Linha {
   private String nomeTP, nomeTS, horario, sentido, numLinha, qntdeOnibusCirculando, codigoLinha;
    private List<String> trajeto = new ArrayList<>();
    private double extensao;
    private boolean noturno;
    private List<Onibus> onibus = new ArrayList<>();
    private List<Ponto> pontos = new ArrayList<>();

    //GET SET

    public List<Ponto> getPontos() {
        return pontos;
    }

    public void setPontos(List<Ponto> pontos) {
        this.pontos = pontos;
    }

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

    public List<Onibus> getOnibus() {
        return onibus;
    }

    public void setOnibus(List<Onibus> onibus) {
        this.onibus = onibus;
    }

    //CONSTRUTOR

    public Linha(String nomeTP, String nomeTS, String horario, String sentido, String numLinha, String qntdeOnibusCirculando, List<String> trajeto, double extensao, boolean noturno, List<Onibus> onibus,String codigoLinha) {
        this.nomeTP = nomeTP;
        this.nomeTS = nomeTS;
        this.horario = horario;
        this.sentido = sentido;
        this.numLinha = numLinha;
        this.qntdeOnibusCirculando = qntdeOnibusCirculando;
        this.trajeto = trajeto;
        this.extensao = extensao;
        this.noturno = noturno;
        this.onibus = onibus;
        this.codigoLinha = codigoLinha;
    }
    public Linha() {
        this.nomeTP = "";
        this.nomeTS="";
        this.horario = "";
        this.sentido = "";
        this.numLinha = "";
        this.qntdeOnibusCirculando = "";
        this.codigoLinha="";
    }

    public static List<Linha> buscarLinha() throws JSONException {
        String linhasConst = "";
        List<Linha> linhaRetorno = new ArrayList<>();
        JSONArray resp = new JSONArray();
        resp = ConectaAPI.buscar();
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
        for (Linha lind : linhaRetorno){
            Log.d("LINHAS12",lind.getNumLinha().toString());
        }
        return linhaRetorno;
    }
}
