package com.example.lucas.buseye.control;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.lucas.buseye.model.LinhaBd;
import com.example.lucas.buseye.model.OlhoVivo;
import com.example.lucas.buseye.view.MapsActivity;
import com.example.lucas.buseye.view.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RotaControle {
    static List<String> latLong = new ArrayList<>();

    public static void mostrarRota(String linha) {
        OlhoVivo helper = OlhoVivo.getInstance();


        //JSONArray resp = new JSONArray();

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, "https://buseye-bd.firebaseio.com/shapes/"+linha+".json", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray resp) {
                try {
                    for (int i = 0; i < resp.length(); i++) {
                        LinhaBd linha = new LinhaBd();
                        JSONObject json = null;
                        try {
                            json = resp.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("+!ER", "é o nulo");
                        }
                        //numero da linha
                        try {
                            String lat = json.get("lat").toString().replaceAll("['\\'],\"" ,null).trim();
                            String lon = json.get("long").toString().replaceAll("['\\'],\"" ,null).trim();
                            latLong.add(lat+","+lon);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d("LATLONG",latLong.get(1));
                    MapsActivity.mostrarRota(latLong);
                }catch(Exception e) {
                    Log.d(e.toString(), "quase");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERRO!", error.toString());
            }
        });
        helper.add(request);
    }

}
