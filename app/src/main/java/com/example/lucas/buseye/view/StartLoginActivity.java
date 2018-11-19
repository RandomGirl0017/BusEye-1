package com.example.lucas.buseye.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.util.Util;

public class StartLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent activityIntent;

       /* // go straight to main if a token is stored
        if (Util.getToken() != null) {
            activityIntent = new Intent(this, SearchView.class);
        } else {
            activityIntent = new Intent(this, LogInActivity.class);
        }

        startActivity(activityIntent);
        finish();
    }*/
    }
}
