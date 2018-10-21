package com.example.lucas.buseye.view;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lucas.buseye.R;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout hamb3;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        //SEARCH VIEW
        ListView lv = (ListView) findViewById(R.id.listViewCountry);
        ArrayList<String> arrayCountry = new ArrayList<>();
        arrayCountry.addAll(Arrays.asList(getResources().getStringArray(R.array.array_country)));

        adapter = new ArrayAdapter<>(
                SearchView.this,
                android.R.layout.simple_list_item_1,
                arrayCountry);
        lv.setAdapter(adapter);


        //HAMBURGUER
        Toolbar toolbar = findViewById(R.id.toolbar_search);
        hamb3 = findViewById(R.id.search_layout);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (this, hamb3, toolbar, R.string.nav_open, R.string.nav_close);
        hamb3.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view_search);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        android.widget.SearchView searchView = (android.widget.SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        //CODIGO PRA ADICIONAR ACTIVITYS NO BOTÃO DO  HAMBURGUER

         /*  if (menuItem.getItemId() == R.id.hamb_home) {
            Intent intent = new Intent(this, TelaInicial_Activity.class);
            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.hamb_favori) {
            Intent intent = new Intent(this, RotasActivity.class);
            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.hamb_conf) {
            Intent intent = new Intent(this, RotaInicioFim_Activity.class);


        }
*/
        return false;
    }
}
