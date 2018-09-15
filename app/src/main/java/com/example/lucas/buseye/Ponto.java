package com.example.lucas.buseye;

import java.util.ArrayList;
import java.util.List;

public class Ponto {
    private Linha linha;
    private boolean corredor,coberto,acessivel;
    private  List<String> localicao = new ArrayList<>();

    //GET SET

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

    public List<String> getLocalicao() {
        return localicao;
    }

    public void setLocalicao(List<String> localicao) {
        this.localicao = localicao;
    }

    //CONSTRUTOR

    public Ponto(Linha linha, boolean corredor, boolean coberto, boolean acessivel, List<String> localicao) {
        this.linha = linha;
        this.corredor = corredor;
        this.coberto = coberto;
        this.acessivel = acessivel;
        this.localicao = localicao;
    }


}
