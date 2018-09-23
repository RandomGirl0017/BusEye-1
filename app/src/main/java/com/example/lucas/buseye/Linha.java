package com.example.lucas.buseye;

import java.util.ArrayList;
import java.util.List;

public class Linha {
   private String nome, horario, sentido, numLinha, qntdeOnibusCirculando;
    private List<String> trajeto = new ArrayList<>();
    private double extensao;
    private boolean noturno;
    private List<Onibus> onibus = new ArrayList<>();

    //GET SET
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Onibus getOnibus() {
        return onibus;
    }

    public void setOnibus(Onibus onibus) {
        this.onibus = onibus;
    }

    //CONSTRUTOR
    public Linha(String nome, String horario, String sentido, String numLinha, List<String> trajeto, double extensao, boolean noturno, Onibus onibus) {
        this.nome = nome;
        this.horario = horario;
        this.sentido = sentido;
        this.numLinha = numLinha;
        this.trajeto = trajeto;
        this.extensao = extensao;
        this.noturno = noturno;
        this.onibus = onibus;
    }

    //METODOS

public String pegarLinha(){
        String noturno = (this.isNoturno())? "sim" : "não";

        //String linha = getString(R.string.linha);

        return "Linha:" + this.numLinha + "-"  + this.nome +"\nHorário:"+
                this.horario + "\nNoturno: "+ noturno;
       // return linha;
    }

    public List<String> mostrarOnibus(){
        Onibus on = this.onibus;
        return on.getGeoPosicao();
    }

/*
    public String getNome() {
        return nome;
    }

    public String getHorario() {
        return horario;
    }

    public String getSentido() {
        return sentido;
    }

    public String getNumLinha() {
        return numLinha;
    }

    public List<String> getTrajeto() {
        return trajeto;
    }

    public double getExtensao() {
        return extensao;
    }

    public boolean isNoturno() {
        return noturno;
    }

    public Onibus getOnibus() {
        return onibus;
    }*/

}
