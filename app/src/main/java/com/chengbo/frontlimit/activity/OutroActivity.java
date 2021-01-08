package com.chengbo.frontlimit.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chengbo.frontlimit.R;

public class OutroActivity extends AppCompatActivity {

    public Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outro);

        //toolBar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Outras Informações");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //打开Layout
    public void abrirMarca (View view){

        Intent intent =  new Intent(OutroActivity.this, MarcaActivity.class);
        startActivity(intent);
    }
    public void abrirOPC (View view){

        Intent intent =  new Intent(OutroActivity.this, OpcActivity.class);
        startActivity(intent);
    }
    public void abrirFronteira (View view){

        Intent intent =  new Intent(OutroActivity.this, FronteiraActivity.class);
        startActivity(intent);
    }
    public void abrirCategoria (View view){

        Intent intent =  new Intent(OutroActivity.this, CategoriaActivity.class);
        startActivity(intent);
    }
    public void abrirMercadoria (View view){

        Intent intent =  new Intent(OutroActivity.this, MercadoriaActivity.class);
        startActivity(intent);
    }
    public void abrirPais (View view){

        Intent intent =  new Intent(OutroActivity.this, PaisActivity.class);
        startActivity(intent);
    }
    public void abrirCidade (View view){

        Intent intent =  new Intent(OutroActivity.this, CidadeActivity.class);
        startActivity(intent);
    }
}