package com.example.carapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * SplashActivity.java - Classe responsável pelo splash
 * @author  Gregory Perozzo
 * @version 1.0
 */
public class SplashActivity extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //getSupportActionBar().hide();
        Handler handler = new Handler();
        handler.postDelayed(this, 2000);
    }

    @Override
    public void run() {
        Intent contacts = new Intent(SplashActivity.this, LoginActivity.class);
        SplashActivity.this.startActivity(contacts);
        SplashActivity.this.finish();
    }
}
