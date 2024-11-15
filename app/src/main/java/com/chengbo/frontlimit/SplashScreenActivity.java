package com.chengbo.frontlimit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.chengbo.frontlimit.activity.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                abrirPagLogin();
            }
        },2000);
    }
 
    private void abrirPagLogin() {
        Intent pagLogin = new Intent (SplashScreenActivity.this, LoginActivity.class);
        startActivity(pagLogin);
        finish();
    }
}