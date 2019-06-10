package com.example.lucas.buseye.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lucas.buseye.R;
import com.example.lucas.buseye.control.LinhaControle;
import com.example.lucas.buseye.control.PontoControle;
import com.example.lucas.buseye.model.MapsFragment;
import com.example.lucas.buseye.model.SearchViewFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

   // FloatingActionButton fab_plus,fab1,fab2,fab3;
          Animation open,close,clock,antclock;
        boolean isOpen=false;
        private DrawerLayout hamb3;
        static ArrayAdapter<String> adapter;
        public static List<String> linhas ;

        public static List<String> getLinhas() {
            return linhas;
        }

        public static void setLinhas(List<String> linhas) {
            SearchView.linhas = linhas;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search_view);


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();


            //SEARCH VIEW
            ListView lv = (ListView) findViewById(R.id.listViewCountry);
            linhas= new ArrayList<>();
            LinhaControle.mostrarTodasLinhas();

            adapter = new ArrayAdapter<>(
                    SearchView.this,
                    android.R.layout.simple_list_item_1,
                    linhas);
            lv.setAdapter(adapter);
            //click
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(final AdapterView<?> parent, final View view, final int position, long id)
                {

                    String index = adapter.getItem(position);
                    Log.d("BUSCA21",index);
                    LinhaControle.buscarLinha(index);
                    abrirMapa();
                }
            });

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


            //MENU INFERIOR

       /* BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);*/


    }

   /* //FRAGMENTOS
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.home_bottom:
                            selectedFragment = new SearchViewFragment();
                            break;
                        case R.id.location_bottom:
                            selectedFragment = new MapsFragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };*/

    //ADAPTER
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

    //HAMBURGUEr
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
    //CODIGO PRA ADICIONAR ACTIVITYS NO BOTÃO DO  HAMBURGUER

          if (menuItem.getItemId() == R.id.hamb_favori) {
            Intent intent = new Intent(this, Favoritos.class);
            startActivity(intent);
        }
        else if(menuItem.getItemId() == R.id.hamb_conf){
               Intent intent = new Intent(this,Perfil.class);
               startActivity(intent);
           }

        return false;

    }

    public static void atualizarLista(){
        Log.d("TAAG",linhas.toString());
        adapter.notifyDataSetChanged();
    }
    public void abrirMapa(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
