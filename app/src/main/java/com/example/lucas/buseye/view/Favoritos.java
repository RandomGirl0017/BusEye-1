package com.example.lucas.buseye.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.lucas.buseye.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Favoritos extends AppCompatActivity {
  //  private static List LinhasFaves;
    //private static String linha;
    //File Fave = new File(Environment.getDataDirectory() + File.separator + "fave.txt");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
    }


}
