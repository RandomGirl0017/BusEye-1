package com.example.lucas.buseye.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.lucas.buseye.R;

public class RotasActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout hamb2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telarota_busca);
        ///////////////HAMBURGUER
        Toolbar toolbar = findViewById(R.id.toolbar2);
        hamb2 = findViewById(R.id.drawer_layout2);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (this, hamb2, toolbar, R.string.nav_open, R.string.nav_close);
        hamb2.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);
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
