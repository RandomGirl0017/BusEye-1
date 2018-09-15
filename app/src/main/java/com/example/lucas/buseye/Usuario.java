package com.example.lucas.buseye;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario {

    private String nome;
    private String email;
    private List<String> posicao = new ArrayList<>();
    private Date dataDeCriacao;

    //GET SET
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getPosicao() {
        return posicao;
    }
    public void setPosicao(List<String> posicao) {
        this.posicao = posicao;
    }

    public Date getDataDeCriacao() {
        return dataDeCriacao;
    }
    public void setDataDeCriacao(Date dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }
    //END GET/SET


   //CONSTRUCTOR
    public Usuario(String nome, String email, List<String> posicao, Date dataDeCriacao) {
        this.nome = nome;
        this.email = email;
        this.posicao = posicao;
        this.dataDeCriacao = dataDeCriacao;
    }

    //METHODS
    public String guardarBusca(Linha busca){
        return null;
    }

    public void mostraBusca(){

    }
    public void enviaFoto(){

    }
    //END METHODS

}
