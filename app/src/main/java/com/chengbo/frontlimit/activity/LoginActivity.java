package com.chengbo.frontlimit.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chengbo.frontlimit.R;
import com.chengbo.frontlimit.model.AgenteModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText logEmail, logPass;
    private Button logBtn;

    public FirebaseDatabase fmbase;
    public DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        logEmail = (EditText) findViewById(R.id.login_email);
        logPass = (EditText) findViewById(R.id.login_password);
        logBtn = (Button) findViewById(R.id.btn_login);
        
    }

    public void abrirPagRegista(View view){

        Intent intent = new Intent(LoginActivity.this, RegisteActivity.class);
        startActivity(intent);
    }

    public void abrirPrincipal(View view){

        Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    private Boolean validarEmail (){

        String val = logEmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()){

            logEmail.setError("Introduza Email");
            logEmail.requestFocus();
            return false;
        }else if (!val.matches(emailPattern)){

            logEmail.setError("Email inválida !!!");
            logEmail.requestFocus();
            return false;
        } else{
            logEmail.setError(null);
            return true;
        }
    }

    private Boolean validarPass () {

        String val = logPass.getText().toString();

        if (val.isEmpty()){

            logPass.setError("Introduza a Password");
            logPass.requestFocus();
            return false;
        }else {

            logPass.setError(null);
            return true;
        }
    }

    public void loginb (){

        if (!validarEmail() | !validarPass()){

            return;
        }else {

            //isUser();
        }
    }

    /*private void isUser(){

        String userEnterEmail =  logEmail.getText().toString().trim();
        String userEnterPass =  logPass.getText().toString().trim();

        reference = FirebaseDatabase.getInstance().getReference("agente");

        Query checkUser =  reference.orderByChild("email").equalTo(userEnterPass);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    String passBD =  snapshot.child(userEnterEmail).child("password").getValue(String.class);

                    if (passBD.equals(userEnterPass)){

                        String nomeBD =  snapshot.child(userEnterEmail).child("nome").getValue(String.class);
                        String generoBD =  snapshot.child(userEnterEmail).child("genero").getValue(String.class);
                        String datanascimentoBD =  snapshot.child(userEnterEmail).child("datanascimento").getValue(String.class);
                        String documentoIdentificacaoBD =  snapshot.child(userEnterEmail).child("documentoIdentificacao").getValue(String.class);
                        String emailBD =  snapshot.child(userEnterEmail).child("email").getValue(String.class);
                        String telemovelBD =  snapshot.child(userEnterEmail).child("telemovel").getValue(String.class);
                        String moradaBD =  snapshot.child(userEnterEmail).child("morada").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), UserProfile.class);

                        intent.putExtra("");

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }*/
}