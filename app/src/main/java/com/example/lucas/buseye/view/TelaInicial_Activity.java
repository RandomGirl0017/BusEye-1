package com.example.lucas.buseye.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.lucas.buseye.R;
import com.example.lucas.buseye.model.ConectaAPI;

import org.json.JSONException;

public class TelaInicial_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        ///////////////HAMBURGUER
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (this, drawer, toolbar, R.string.nav_open, R.string.nav_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void abrirRotas(View view) throws JSONException {
        ConectaAPI.autenticarAPI();
        Intent intent = new Intent(this, RotasActivity.class);
        startActivity(intent);
        // ConectaAPI.autenticarAPI();
        // Linha.buscarLinha("PERI ALTO");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getItemId()==R.id.hamb_home){
            Intent intent  = new Intent (this, TelaInicial_Activity.class);
            startActivity(intent);
        }
        else if(menuItem.getItemId()==R.id.hamb_favori){
            Intent intent  = new Intent (this, RotasActivity.class);
            startActivity(intent);
        }
        else if(menuItem.getItemId()==R.id.hamb_conf){
            Intent intent  = new Intent (this, RotaInicioFim_Activity.class);
            startActivity(intent);
        }

        return false;
    }
}


