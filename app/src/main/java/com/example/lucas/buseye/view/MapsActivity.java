package com.example.lucas.buseye.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.lucas.buseye.R;
import com.example.lucas.buseye.control.LinhaControle;
import com.example.lucas.buseye.control.OnibusControle;
import com.example.lucas.buseye.model.Linha;
import com.example.lucas.buseye.model.LinhaBd;
import com.example.lucas.buseye.model.Onibus;
import com.example.lucas.buseye.model.Ponto;
import com.google.android.gms.common.api.GoogleApiClient;
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
import com.google.android.gms.maps.model.PolylineOptions;


import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    FloatingActionButton fab_plus,fab1,fab2;
    Animation open,close,clock,antclock;
    boolean isOpen=false;
    static Marker MarkerOnibus = null;

    private static GoogleMap mMap;

    static List<LinhaBd> listaFavoritos = new ArrayList<>();
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
        /*
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Favoritar(view);
                Context context = getApplicationContext();
                CharSequence text = "Hello KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK LOLZMAN!!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context,text,duration);
                toast.show();
            }
        });
*/
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
            mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
        } catch (Resources.NotFoundException e) {
            Log.e("MAPAS", "Can't find style. Error: ", e);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                }
            } else {
               mMap.setMyLocationEnabled(true);
            }
        }
        else {
            mMap.setMyLocationEnabled(true);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                } else {
                    mMap.setMyLocationEnabled(false);
                }
                return;
            }
        }
    }

    public void Favoritar(View view) {
        for (LinhaBd temp:listaFavoritos) {
            if ((temp.equals(LinhaControle.linha))) {
                Toast.makeText(this, "Favorito já Existente", 2000).show();
                return;
            }
        }
        listaFavoritos.add(LinhaControle.linha);
        Snackbar snackbar = Snackbar
                .make(view, "Favorito Adicionado", Snackbar.LENGTH_SHORT);
        snackbar.show();
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
                    Log.d("POSDOUBLEs",p.getPosY() );
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
        handler.postDelayed(run, 1000);
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