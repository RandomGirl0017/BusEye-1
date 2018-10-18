package com.example.lucas.buseye.model;

import com.example.lucas.buseye.control.LinhaControle;
import com.example.lucas.buseye.control.OnibusControle;
import com.example.lucas.buseye.control.PontoControle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Ponto {
    private List<Linha> listaLinha;
    private boolean corredor,coberto,acessivel;
    private String nome, posX,posY, endereco, codigo;

    //GET SET
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<Linha> getListaLinha() {
        return listaLinha;
    }

    public void setListaLinha(List<Linha> listaLinha) {
        this.listaLinha = listaLinha;
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
    public Ponto() {
        this.listaLinha = new ArrayList<>();
        this.corredor = false;
        this.coberto = false;
        this.acessivel = false;
        this.nome = "";
        this.posX = "";
        this.posY = "";
        this.endereco="";
        this.codigo="";
    }
}
