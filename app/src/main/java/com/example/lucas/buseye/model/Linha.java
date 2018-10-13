package com.example.lucas.buseye.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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




    //METODOS

    public static void buscarLinha(Linha linha){
       String aux = ConectaAPI.buscar("/Linha/Buscar?termosBusca=8000");

       //Código da Linha
        linha.setCodigoLinha(aux.substring(aux.indexOf("cl")+4,aux.indexOf(",")));

       //Número da Linha
        linha.setNumLinha(aux.substring(aux.indexOf("lt")+4,aux.indexOf(",")));
        Log.d("LINHAAA",linha.getNumLinha());

        //Sentido Term Priinc(1) ou Term Sec(2)
        linha.setSentido(aux.substring(aux.indexOf("sl")+4,aux.indexOf(",")));

        //Letreiro TerminalPrincipal
        linha.setNomeTP(aux.substring(aux.indexOf("tp")+4,aux.indexOf(",")));

        //Letreiro TerminalSecundario
        linha.setNomeTS(aux.substring(aux.indexOf("ts")+4,aux.indexOf(",")));
    }

    public static void buscarSentidoLinha(Linha linha, String codigoLinha, String sentido){
        String aux = ConectaAPI.buscar("/Linha/BuscarLinhaSentido?termosBusca="+codigoLinha+"&sentido="+sentido);

        //Código da Linha
        linha.setCodigoLinha(aux.substring(aux.indexOf("cl")+4,aux.indexOf(",")));

        //Número da Linha
        linha.setNumLinha(aux.substring(aux.indexOf("lt")+4,aux.indexOf(",")));
        Log.d("LINHAAA",linha.getNumLinha());

        //Sentido Term Priinc(1) ou Term Sec(2)
        linha.setSentido(aux.substring(aux.indexOf("sl")+4,aux.indexOf(",")));

        //Letreiro TerminalPrincipal
        linha.setNomeTP(aux.substring(aux.indexOf("tp")+4,aux.indexOf(",")));

        //Letreiro TerminalSecundario
        linha.setNomeTS(aux.substring(aux.indexOf("ts")+4,aux.indexOf(",")));
    }

    /*
    *
    * @param(1) código da linha a ser psquisada.
     */
    public static void buscarPontos (Linha linha, String codigoLinha){
        String aux = ConectaAPI.buscar("/Parada/BuscarParadasPorLinha?codigoLinha="+codigoLinha);
        aux = aux.replaceAll("^\\[|]$", "");
        aux = aux.replaceAll("[{]", "");
        List<String> myList = new ArrayList<String>(Arrays.asList(aux.split("}")));
        for (String ponto : myList) {
            Ponto pontoArr = new Ponto();
            //Nome da parada
            pontoArr.setNome(aux.substring(aux.indexOf("np")+4,aux.indexOf(",")));

            //Endereço
            pontoArr.setEndereco(aux.substring(aux.indexOf("ed")+4,aux.indexOf(",")));

            //Posição Y - Latitude
            pontoArr.setPosY(aux.substring(aux.indexOf("py")+4,aux.indexOf(",")));

            //Posição X - Altitude
            pontoArr.setPosX(aux.substring(aux.indexOf("px")+4,aux.indexOf(",")));
            linha.pontos.add(pontoArr);
        }
    }

    public void buscarOnibus(Linha linha, String codigoLinha) throws JSONException {
        String aux = ConectaAPI.buscar("/Posicao/Linha?codigoLinha="+codigoLinha);
        JSONObject onibusObj = new JSONObject(aux);
        JSONArray onibusArr = onibusObj.getJSONArray("vs");
        for (int i = 0; i < onibusArr.length(); i++) {
            JSONObject explrObject = onibusArr.getJSONObject(i);
        }
        //TODO rever ônibus de acordo com a documentação e instanciar aqui

    }

    public List<String> mostrarOnibus(){
        List<Onibus> on = this.onibus;
        List<String> any = new ArrayList<>();
        for (Onibus oni : onibus) {
            any = oni.getGeoPosicao();
        }
        return any;
    }
}
