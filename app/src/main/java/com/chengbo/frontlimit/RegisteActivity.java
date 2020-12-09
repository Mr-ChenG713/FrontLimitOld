package com.chengbo.frontlimit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegisteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);

        
    }

    public void abrirPagLogin(View view){

        Intent intent = new Intent(RegisteActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}