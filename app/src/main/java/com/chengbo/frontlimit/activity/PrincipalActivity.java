package com.chengbo.frontlimit.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.chengbo.frontlimit.R;
import com.google.android.material.navigation.NavigationView;

public class PrincipalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //-----variaveis
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //-----Toolbar
        setSupportActionBar(toolbar);

        //Navigation Menu
        navigationView.bringToFront();//menu背影
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


    }

    //留海问题
    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){

            drawerLayout.closeDrawer(GravityCompat.START);
        }else{

            super.onBackPressed();
        }
    }

    //draw menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.nav_perfil:
                abrirPerfil();
                break;
            case R.id.nav_outros:
                abrirOutros();
                break;
            case R.id.nav_sobre:
                abrirSobre();
                break;
            case R.id.nav_exit:

                break;
        }
        //drawerLayout.closeDrawer(GravityCompat.START); //点击菜单立刻关闭
        return true;
    }

    private void abrirPerfil (){

        Intent intent = new Intent(PrincipalActivity.this, AgentePerfilActivity.class);
        startActivity(intent);
    }

    private void abrirOutros (){

        Intent intent = new Intent(PrincipalActivity.this, OutroActivity.class);
        startActivity(intent);
    }

    private void abrirSobre (){

        Intent intent = new Intent(PrincipalActivity.this, SobreActivity.class);
        startActivity(intent);
    }


}