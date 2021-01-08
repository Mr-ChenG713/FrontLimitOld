package com.chengbo.frontlimit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.chengbo.frontlimit.R;
import com.chengbo.frontlimit.model.Agente;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisteActivity extends AppCompatActivity {

    private EditText regNome, regData, regDoc, regEmail, regPass, regTele, regMora;
    private RadioGroup regGene;
    private RadioButton regGeneMF;
    private Button regBtn;

    public FirebaseDatabase fmbase;
    public DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);

        regNome = (EditText) findViewById(R.id.reg_nome);
        regData = (EditText) findViewById(R.id.reg_data);
        regDoc = (EditText) findViewById(R.id.reg_doc);
        regEmail = (EditText) findViewById(R.id.reg_mail);
        regPass = (EditText) findViewById(R.id.reg_passw);
        regTele = (EditText) findViewById(R.id.reg_tele);
        regMora = (EditText) findViewById(R.id.reg_mora);
        regGene = (RadioGroup) findViewById(R.id.radiogroup);
        regBtn = (Button) findViewById(R.id.btn_registar);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegistarUser();
            }
        });

    }

    public void abrirPagLogin(View view){

        Intent intent = new Intent(RegisteActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void selectRadionButton (View v){

        int radiobuttonId = regGene.getCheckedRadioButtonId();
        regGeneMF = (RadioButton) findViewById(radiobuttonId);
    }

    private Boolean validarNome (){

        String val = regNome.getText().toString();
        String sinal = "\\A\\w{4,20}\\z";

        if (val.isEmpty()){

            regNome.setError("Introduza o nome");
            regNome.requestFocus();
            return false;
        }else if (!val.matches(sinal)){

            regNome.setError("Carater não permitido");
            return false;
        }else {

            regNome.setError(null);
            return true;
        }
    }

    /*private Boolean validarGenro (){
    }*/

    private Boolean validarData (){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        sdf.setLenient(false);

        String val = regData.getText().toString();

        try {
            Date date = sdf.parse(val);
            return true;
        }catch (ParseException e){

            e.printStackTrace();
            regData.setError("Introduza a data dd/mm/aaaa");
            regData.requestFocus();
            return false;
        }
    }

    private Boolean validarDoc (){

        String val = regDoc.getText().toString();

        if (val.isEmpty()){

            regDoc.setError("Introduza o numero de documento identificação");
            regDoc.requestFocus();
            return false;
        }else {

            regDoc.setError(null);
            return true;
        }
    }

    private Boolean validarEmail (){

        String val = regEmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()){

            regEmail.setError("Introduza Email");
            regEmail.requestFocus();
            return false;
        }else if (!val.matches(emailPattern)){

            regEmail.setError("Email inválida !!!");
            regEmail.requestFocus();
            return false;
        } else{

            regEmail.setError(null);
            return true;
        }
    }

    private Boolean validarPass (){

        String val = regPass.getText().toString();

        if (val.isEmpty()){

            regPass.setError("Introduza a Password");
            regPass.requestFocus();
            return false;
        }else {

            regPass.setError(null);
            return true;
        }
    }

    private Boolean validarTele (){

        String val = regTele.getText().toString();

        if (val.isEmpty()){

            regTele.setError("Introduza o número de telemovel");
            regTele.requestFocus();
            return false;
        }else {

            regTele.setError(null);
            return true;
        }
    }

    private Boolean validarMorada (){

        String val = regMora.getText().toString();

        if (val.isEmpty()){

            regMora.setError("Introduza a morada");
            regMora.requestFocus();
            return false;
        }else {

            regMora.setError(null);
            return true;
        }
    }

    public void RegistarUser(){

        fmbase = FirebaseDatabase.getInstance();

        reference = fmbase.getReference();

        if (!validarNome() | !validarData() | !validarDoc() | !validarEmail() | !validarPass() | !validarTele() | !validarMorada()){

            return;
        }

        //buscar todos valores
        String agenteId = fmbase.getReference().getKey();
        String nome = regNome.getText().toString();
        String genero = regGeneMF.getText().toString();
        String datanascimento = regData.getText().toString();
        String docm = regDoc.getText().toString();
        String email = regEmail.getText().toString();
        String password = regPass.getText().toString();
        String telemovel = regTele.getText().toString();
        String morada = regMora.getText().toString();

        Agente agente = new Agente(agenteId, nome, genero, datanascimento, docm, email, password, telemovel, morada);

        reference.child("agente").push().setValue(agente);

        Toast.makeText(getBaseContext(),"Registar com Sucesso !!!", Toast.LENGTH_SHORT).show();
    }
}