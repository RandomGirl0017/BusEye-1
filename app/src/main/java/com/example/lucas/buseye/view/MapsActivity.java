package com.example.lucas.buseye.view;

import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
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
import com.example.lucas.buseye.control.OnibusControle;
import com.example.lucas.buseye.control.PontoControle;
import com.example.lucas.buseye.model.Linha;
import com.example.lucas.buseye.model.LinhaBd;
import com.example.lucas.buseye.model.Onibus;
import com.example.lucas.buseye.model.Ponto;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
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
    static Marker MarkerOnibus = null;

    private static GoogleMap mMap;

    final List<String> linhaString = new ArrayList<>();
    List<Linha> linhaRetorno = new ArrayList<>();
    List<Ponto> listaPonto = new ArrayList<>();
    static ArrayAdapter adapter;
    static List<Marker>listaMarker = new ArrayList<>();

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

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            mMap.setMapStyle( MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
        } catch (Resources.NotFoundException e) {
            Log.e("MAPAS", "Can't find style. Error: ", e);
        }
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
            polyline1.color(Color.LTGRAY);
        mMap.addPolyline(polyline1);
            adapter.notifyDataSetChanged();
    }

    public static void mostrarPontos(final List<Ponto> listaPonto){
        new mostrarPontosAsync().execute(listaPonto);
    }
    private static class mostrarPontosAsync extends AsyncTask<List<Ponto>, Void,LatLng>{

        @Override
        protected LatLng doInBackground(List<Ponto>... lists) {
            for (List<Ponto> LP : lists){
                for(Ponto p : LP){
                    double y = Double.parseDouble(p.getPosY());
                    double x =  Double.parseDouble(p.getPosX());
                    LatLng lat = new LatLng(y,x);
                    return lat;
                }
            }

            return null;
        }
        @Override
        protected void onPostExecute(LatLng loc){
            Circle circle = mMap.addCircle(new CircleOptions()
                    .center(loc)
                    .radius(3)
                    .strokeColor(Color.RED)
                    .fillColor(Color.BLUE));
            circle.setZIndex(1);
        }
    }

    public static void repetirBuscaOnibus (final String codigolinha){
       final Handler handler = new Handler();
      final Runnable run = new Runnable(){
           @Override
           public void run() {
               // Do some task on delay
               OnibusControle.buscarOnibusPosicao(codigolinha);
               handler.postDelayed(this, 30000);
           }
       };
        handler.postDelayed(run, 30000);
    }

    public static void mostrarOnibus(List<Onibus> listaOnibus){
        Log.d("QNTONIBUS",String.valueOf(listaOnibus.size()));
        if (listaOnibus.size() > 0){

        new mostrarOnibusAsync().execute(listaOnibus);
        }
    }
    private static class mostrarOnibusAsync extends AsyncTask<List<Onibus>, Void,List<MarkerOptions>>{
        @Override
        protected List<MarkerOptions> doInBackground(List<Onibus>... lists) {
            List<MarkerOptions> listaMarker = new ArrayList<>();
            LatLng lat = new LatLng(0,0);

            for (List<Onibus> LO : lists){
                Log.d("POSONIBUS",String.valueOf(LO.size()));
                for(Onibus o : LO){

                    double y = o.getPosY();
                    double x =  o.getPosX();
                    lat = new LatLng(y,x);
                    Log.d("ONIPOS",String.valueOf(lat));
                    MarkerOptions marker = new MarkerOptions()
                            .position(lat);
                    if(o.isAcessivel()) {
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.black_bus));
                    }else{
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.yellow_bus));
                    }
                 /*   CircleOptions circleOp = new CircleOptions().center(lat)
                            .radius(30)
                            .strokeColor(Color.BLACK)
                            .fillColor(Color.GREEN);*/
                    listaMarker.add(marker);
                }
            }
            //OnibusControle.listaOnibus.clear();
            return listaMarker;
        }
        @Override
        protected void onPostExecute(List<MarkerOptions> listaOnibus){

            if ( listaMarker.size() > 0 ){
                for(Marker c: listaMarker){
                    c.remove();
                }
            }
            for ( MarkerOptions bus: listaOnibus ) {

                MarkerOnibus = mMap.addMarker(bus);
                MarkerOnibus.setZIndex(1);
                listaMarker.add(MarkerOnibus);
            }
        }
    }
}