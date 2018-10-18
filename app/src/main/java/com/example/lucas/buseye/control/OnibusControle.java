package com.example.lucas.buseye.control;

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
    /**
     * TODO implementar
     *
     * @param buscaPonto  Endereço, nome ou codigo de um ponto para o método PontoControle
     * @param buscaLinha recebe nome ou número da linha (pode ser o nome errado, a API faz busca fonética)
     * @return Lista com Onibus informando posição e horário para esse ponto
     * @throws JSONException
     */
    public static List<Onibus> chegadaOnibusLinha(String buscaPonto, String buscaLinha)throws JSONException {
        //TODO IMPLEMENTAR FINALIZAR
        List<Linha> linArr = new ArrayList<>();
        List<Onibus> onibusArr = new ArrayList<>();
        Ponto ponto = new Ponto();
        ponto = PontoControle.buscarPonto(buscaPonto);
        linArr = LinhaControle.buscarLinha(buscaLinha);

        for (Linha linha : linArr) {
            onibusArr = buscaVeiculos(linha);
            JSONArray resp = new JSONArray();
            resp = ConectaAPI.buscar("/Previsao?codigoParada=" + ponto.getCodigo() + "&codigoLinha=" + linha.getCodigoLinha());
            for (int i = 0; i < resp.length(); i++) {
                JSONObject json = resp.getJSONObject(i);

            }
        }
        return onibusArr;
    }

    /**
     * Pega a posição de todos os onibus para essa linha
     * @param linha Linha dos onibus a serem mostrados
     * @return
     * @throws JSONException
     */
    public static List<Onibus> buscaVeiculos(Linha linha) throws JSONException {
        JSONArray resp = new JSONArray();
        resp = ConectaAPI.buscar("/Parada//Posicao/Linha?codigoLinha="+linha.getCodigoLinha());

        for (int i = 0; i < resp.length(); i++) {
            JSONObject json = resp.getJSONObject(i);
            Onibus oni = new Onibus();

            //Acssivel?
            String acessivel=json.get("a").toString();
            if (acessivel == "true"){
                oni.setAcessivel(true);
            }else{
                oni.setAcessivel(false);
            }
            //Longitute Veiculo
            oni.setPosX(json.get("px").toString());

            //Latitude Veiculo
            oni.setPosY(json.get("py").toString());
        }
        return onibus;
    }

}
