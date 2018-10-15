package com.example.lucas.buseye.control;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.lucas.buseye.R;
import com.example.lucas.buseye.model.Linha;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText txtBuscar;
    private Button btBuscar;
    private ListView lvRetornoLinha;
    final List<String> linhaString = new ArrayList<>();
    List<Linha> linhaRetorno = new ArrayList<>();
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button btBuscar = (Button) findViewById(R.id.realizaBusca);
        EditText txtBuscar = (EditText) findViewById(R.id.buscaLinha);
        ListView lvRetornoLinha = (ListView) findViewById(R.id.retornoLinhas);

            adapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, linhaString);

            lvRetornoLinha.setAdapter(adapter);

            lvRetornoLinha.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, final View view,
                                        int position, long id) {
                    final String item = (String) parent.getItemAtPosition(position);
                    view.animate().setDuration(2000).alpha(0)
                            .withEndAction(new Runnable() {

                                @Override
                                public void run() {
                                    linhaString.remove(item);
                                    adapter.notifyDataSetChanged();
                                    view.setAlpha(1);
                                }
                            });
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    public void Api(View view) throws JSONException {
       linhaRetorno = Linha.buscarLinha("PERY ALTO");
       if(linhaRetorno.size() > 0) {
           for (Linha l : linhaRetorno) {
               linhaString.add(l.getNumLinha() + " " + l.getNomeTP() + " / " + l.getNomeTS());

           }

           adapter.addAll(linhaString);
           //adapter.notifyDataSetChanged();
       }
    }
}
