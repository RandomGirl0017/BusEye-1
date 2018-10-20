package com.example.lucas.buseye.control;

import android.os.AsyncTask;

import com.example.lucas.buseye.model.Linha;
import com.example.lucas.buseye.model.Onibus;
import com.example.lucas.buseye.model.Ponto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OnibusControle {

    public static List<Onibus> onibus = new ArrayList<>();
    public List<Onibus> getOnibus() { return onibus; }
    public void setOnibus(List<Onibus> onibus) {
        onibus = onibus;
    }

    //Metodos
    public static class  chegadaOnibusLinha extends AsyncTask<Void,Void,List<Onibus>>{

    //RECEBE UM LINHA E UM PONTO PARA QUE OS ONIBUS SEJAM BUSCADOS
        Linha linha = new Linha();
        Ponto ponto = new Ponto();
        chegadaOnibusLinha(Linha linha, Ponto ponto){
            this.linha = linha;
            this.ponto = ponto;
        }
        @Override
        protected List<Onibus> doInBackground(Void...voids) {
//TODO VERIFICAR METODO
            List<Onibus> onibusArr = new ArrayList<>();
            linha = linha;
            //    onibusArr = new buscaVeiculos(linha);
            JSONArray resp = new JSONArray();
            resp = ConectaAPI.buscar("/Previsao?codigoParada=" + ponto.getCodigo() + "&codigoLinha=" + linha.getCodigoLinha());
            for (int i = 0; i < resp.length(); i++) {
                try {
                    JSONObject json = resp.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return onibusArr;
        }
    }

    public static class buscaVeiculos extends  AsyncTask<Void,Void,List<Onibus>>{
        Linha linha = new Linha();
        buscaVeiculos(Linha linha){this.linha = linha;}

        @Override
        protected List<Onibus> doInBackground(Void... voids) {
            JSONArray resp = new JSONArray();
            resp = ConectaAPI.buscar("/Parada//Posicao/Linha?codigoLinha="+linha.getCodigoLinha());

            for (int i = 0; i < resp.length(); i++) {
                JSONObject json = null;
                try {
                    json = resp.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Onibus oni = new Onibus();

                //Acssivel?
                String acessivel= null;
                try {
                    acessivel = json.get("a").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (acessivel == "true"){
                    oni.setAcessivel(true);
                }else{
                    oni.setAcessivel(false);
                }
                //Longitute Veiculo
                try {
                    oni.setPosX(json.get("px").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Latitude Veiculo
                try {
                    oni.setPosY(json.get("py").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return onibus;
        }
    }
}

