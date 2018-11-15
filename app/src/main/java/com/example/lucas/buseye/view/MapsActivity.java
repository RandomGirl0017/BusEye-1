package com.example.lucas.buseye.view;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.toolbox.StringRequest;
import com.example.lucas.buseye.R;
import com.example.lucas.buseye.control.LinhaControle;
import com.example.lucas.buseye.control.PontoControle;
import com.example.lucas.buseye.model.Linha;
import com.example.lucas.buseye.model.Ponto;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    FloatingActionButton fab_plus,fab1,fab2;
    Animation open,close,clock,antclock;
    boolean isOpen=false;

    private static GoogleMap mMap;

    final List<String> linhaString = new ArrayList<>();
    List<Linha> linhaRetorno = new ArrayList<>();
    List<Ponto> listaPonto = new ArrayList<>();
    static ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //FLOATING BUTTON

        fab_plus = (FloatingActionButton)findViewById(R.id.fab_plus);
        fab1 = (FloatingActionButton)findViewById(R.id.fab2_plus);
        fab2 = (FloatingActionButton)findViewById(R.id.fab3_plus);


        open = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        clock = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        antclock = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);

        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen){

                    fab1.startAnimation(close);
                    fab1.setClickable(false);
                    fab2.startAnimation(close);
                    fab2.setClickable(false);
                    fab_plus.startAnimation(antclock);
                    isOpen=false;

                }else{
                    fab1.startAnimation(open);
                    fab1.setClickable(true);
                    fab2.startAnimation(open);
                    fab2.setClickable(true);
                    fab_plus.startAnimation(clock);
                    isOpen=true;

                }
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

/*
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-11, 11);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    public static void mostrarRota(List<String> latLongCru) {
        Log.d("LG",String.valueOf(latLongCru.size()));
        PolylineOptions polyline1 =  new PolylineOptions();
        List<LatLng> listLatLong = new ArrayList<>();
        double lat , longt;

            for (String s:latLongCru) {
                String[] latLong = s.split(",");
                Log.d("++LAT",latLong[1]);
                 lat = Double.parseDouble(latLong[0]);
                longt = Double.parseDouble(latLong[1]);
                LatLng l = new LatLng(lat,longt);
                listLatLong.add(l);
            }
        polyline1.addAll(listLatLong);
            polyline1.color(Color.CYAN);
        mMap.addPolyline(polyline1);
            adapter.notifyDataSetChanged();
    }

    public static void mostrarPontos (final List<Ponto> listaPonto){

                LatLng pontos = new LatLng(0,0);
                for (Ponto p: listaPonto ) {
                    double y = Double.parseDouble(p.getPosY());
                    double x =  Double.parseDouble(p.getPosX());
                    Log.d("POSXY",String.valueOf(y)+" "+String.valueOf(x));
                    pontos = new LatLng( y,x );
                    mMap.addMarker( new MarkerOptions().position(pontos).title(p.getNome()));
                }
              //  mMap.moveCamera(CameraUpdateFactory.newLatLng(pontos));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pontos, 15), 200, null);


            //    adapter.notifyDataSetChanged();
    }
}