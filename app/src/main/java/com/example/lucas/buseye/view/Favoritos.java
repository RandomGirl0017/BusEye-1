package com.example.lucas.buseye.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Favoritos extends AppCompatActivity {
  //  private static List LinhasFaves;
    //private static String linha;
    //File Fave = new File(Environment.getDataDirectory() + File.separator + "fave.txt");
  FloatingActionButton fab_plus,fab1,fab2,fab3;
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
        setContentView(R.layout.activity_favoritos);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();


        //SEARCH VIEW
        ListView lv = (ListView) findViewById(R.id.lista_favoritos);
        linhas= new ArrayList<>();
        LinhaControle.mostrarTodasLinhas();

        adapter = new ArrayAdapter<>(
                Favoritos.this,
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



        //MENU INFERIOR


        //bottomNav.setOnNavigationItemSelectedListener(navListener);



        //FLOATING BUTTON

        fab_plus = (FloatingActionButton)findViewById(R.id.fab_plus);
        fab1 = (FloatingActionButton)findViewById(R.id.fab2_plus);
        fab2 = (FloatingActionButton)findViewById(R.id.fab3_plus);
        fab3 = (FloatingActionButton)findViewById(R.id.fab4_plus);


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
                    fab3.startAnimation(close);
                    fab3.setClickable(false);
                    fab_plus.startAnimation(antclock);
                    isOpen=false;

                }else{
                    fab1.startAnimation(open);
                    fab1.setClickable(true);
                    fab2.startAnimation(open);
                    fab2.setClickable(true);
                    fab3.startAnimation(open);
                    fab3.setClickable(true);
                    fab_plus.startAnimation(clock);
                    isOpen=true;

                }
            }
        });
    }


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


    //HAMBURGUER


    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //CODIGO PRA ADICIONAR ACTIVITYS NO BOTÃO DO  HAMBURGUER

        if (menuItem.getItemId() == R.id.hamb_criar) {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        } else if (menuItem.getItemId() == R.id.hamb_favori) {
            Intent intent = new Intent(this, Favoritos.class);
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
