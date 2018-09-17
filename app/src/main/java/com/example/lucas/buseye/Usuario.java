package com.example.lucas.buseye;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario {

    private String nome;
    private List<String> posicao = new ArrayList<>();
    private Date dataDeCriacao;
    private Login login;
    private Avatar avatar;

    //GET SET
    
    public Avatar getAvatar(){
        return avatar;
    }
    public void setAvatar(Avatar avatar){
        this.avatar = avatar;
    }
    
    public Login getLogin() {
        return usuario;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
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
    public Usuario(String nome, Login login, Avatar avatar, List<String> posicao, Date dataDeCriacao) {
        this.nome = nome;
        this.login = login;
        this.Avatar = avatar;
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
