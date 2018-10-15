package com.example.lucas.buseye.model;

import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


//aqui preciso arrumar os tokens "esconder ele"
public class ConectaAPI {
    static private String authUrl = "/Login/Autenticar?token=6646f77742d36ab30a967cdcb444ecd175ef4e685ff93abd29803d56786f6436";
    static private OlhoVivo helper = OlhoVivo.getInstance();
    static private String RECENT_API_ENDPOINT = "http://api.olhovivo.sptrans.com.br/v2.1";
    static private String rawCookies;
    static private Map<String, String> responseHeaders;
    //static private String buscaUrl = "/Linha/Buscar?termosBusca=8000";
    static String resposta = "";
    static JSONArray resp = new JSONArray();


    //GET SET
    public static String getAuthUrl() {
        return authUrl;
    }

    public static void setAuthUrl(String authUrl) {
        ConectaAPI.authUrl = authUrl;
    }

    public static OlhoVivo getHelper() {
        return helper;
    }

    public static void setHelper(OlhoVivo helper) {
        ConectaAPI.helper = helper;
    }

    public static String getRecentApiEndpoint() {
        return RECENT_API_ENDPOINT;
    }

    public static void setRecentApiEndpoint(String recentApiEndpoint) {
        RECENT_API_ENDPOINT = recentApiEndpoint;
    }

    public static String getRawCookies() {
        return rawCookies;
    }

    public static void setRawCookies(String rawCookies) {
        ConectaAPI.rawCookies = rawCookies;
    }

    public static Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }

    public static void setResponseHeaders(Map<String, String> responseHeaders) {
        ConectaAPI.responseHeaders = responseHeaders;
    }
/*
    public static String getBuscaUrl() {
        return buscaUrl;
    }

    public static void setBuscaUrl(String buscaUrl) {
        ConectaAPI.buscaUrl = buscaUrl;
    }
*/

    //METODOS
    public static void autenticarAPI() {
        StringRequest request = new StringRequest(Request.Method.POST, RECENT_API_ENDPOINT + authUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                        } catch (Exception e) {
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //implementar erro com TOAST
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json;charset=UTF-8");
                return headers;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                // since we don't know which of the two underlying network vehicles
                // will Volley use, we have to handle and store session cookies manually
                Log.i("response", response.headers.toString());
                responseHeaders = response.headers;
                rawCookies = responseHeaders.get("Set-Cookie");
                Log.i("cookies", rawCookies);
                return super.parseNetworkResponse(response);
            }
        };
        helper.add(request);
    }


    public static JSONArray buscar(String buscarURL){
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, RECENT_API_ENDPOINT + buscarURL,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    /*
                    Linha linha = new Linha();
                    linha.setNumLinha(response.substring(response.indexOf("cl")+4,response.indexOf(",")));
                     Log.d("LINHAAA",linha.getNumLinha());
                    Log.d("RESPOSTA",response.toString());
                    resposta=response.toString();
*/

                    resp = response;
                    Log.d("RESPO2",resp.toString());
                }catch (Exception e){
                    Log.d(e.toString(),"quase");
                }
            }
        },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERRO!",error.toString());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json;charset=UTF-8");
                headers.put("Cookie",rawCookies);
                return headers;
            }
        };
        helper.add(request);
        //Log.d("RESPOSTA",resposta.toString());
        return resp;
    }
}

//ATENÇÂO IGNOREM ESSES Log.d() PUS ELES PARA PODER VER OS ERROS AQUI!