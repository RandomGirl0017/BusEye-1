package com.example.lucas.buseye;

import java.util.Date;
import java.util.List;

public class Login  {
   private String eMail, senha;
    private Usuario usuario;

    //GET SET
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    //FIM GET SET

    //CONSTRUTOR
    public Login(String eMail, String senha, Usuario usuario) {
        this.eMail = eMail;
        this.senha = senha;
        this.usuario = usuario;
    }
    //FIM CONSTRUTOR

    //METODOS
    public String recuperarSenha(){
        return null;
    }

    public String gravarSenha(){
        return  null;
    }
}
