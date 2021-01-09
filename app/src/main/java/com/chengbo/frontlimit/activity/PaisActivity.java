package com.chengbo.frontlimit.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chengbo.frontlimit.R;
import com.chengbo.frontlimit.adapter.PaisAdapter;
import com.chengbo.frontlimit.model.Pais;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PaisActivity extends AppCompatActivity {

    public Toolbar toolbar; 

    private Button btn_save;
    private RecyclerView recyclerView;

    private FirebaseDatabase fmbase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = fmbase.getReference("pais");

    private List<Pais> listpais = new ArrayList<>();
    private PaisAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pais);

        //toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("País");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_save = (Button) findViewById(R.id.pais_inserir);
        recyclerView = (RecyclerView) findViewById(R.id.pais_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //打开dialog
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();
            }
        });

        listarDados();
    }

    private void showDialog() {

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//before
        dialog.setContentView(R.layout.dialog_add_simple);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow().getAttributes()));
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        //
        Button btnclose = dialog.findViewById(R.id.btn_dialog_exit);

        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        EditText nomePais = dialog.findViewById(R.id.dialog_edittext);
        Button btnaddpais = dialog.findViewById(R.id.btn_dialog_add);

        btnaddpais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(nomePais.getText())){

                    nomePais.setError("Inserir o nome de País");
                    nomePais.requestFocus();
                }else{

                    adicionarDadoFirebase(nomePais.getText().toString());
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void adicionarDadoFirebase(String text) {

        Pais pais = new Pais();
        pais.setPaisid(UUID.randomUUID().toString());
        pais.setNomePais(text);

        reference.child(pais.getPaisid()).setValue(pais);
        Toast.makeText(getBaseContext(),"Inserir com Sucesso !!!", Toast.LENGTH_SHORT).show();
    }

    private void listarDados() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                listpais.clear();
                for (DataSnapshot snapshot: datasnapshot.getChildren()){
                    Pais value = snapshot.getValue(Pais.class);
                    listpais.add(value);
                }
                adapter = new PaisAdapter(PaisActivity.this, listpais);
                recyclerView.setAdapter(adapter);
                setClick();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setClick() {
        adapter.setOnCallBack(new PaisAdapter.OnCallBack() {
            @Override
            public void onButtonDeleteClick(Pais pais) {

                deleteOp(pais);
            }

            @Override
            public void onButtonEditClick(Pais pais) {

                showDialogUpdate(pais);
            }
        });
    }

    private void deleteOp (final Pais pais){

        reference.child(pais.getPaisid()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getBaseContext(),"Eliminar: " + pais.getNomePais() + " com Sucesso !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialogUpdate(Pais pais){

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//before
        dialog.setContentView(R.layout.dialog_add_simple);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow().getAttributes()));
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        //
        Button btnclose = dialog.findViewById(R.id.btn_dialog_exit);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        EditText nomePais = dialog.findViewById(R.id.dialog_edittext);
        nomePais.setText(pais.getNomePais());
        Button btnaddpais = dialog.findViewById(R.id.btn_dialog_add);
        btnaddpais.setText("Atualizar");

        btnaddpais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(nomePais.getText())){

                    nomePais.setError("Inserir o nome de País");
                    nomePais.requestFocus();
                }else{

                    updatePais(pais, nomePais.getText().toString());
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void updatePais(Pais pais, String newNomePais) {

        reference.child(pais.getPaisid()).child("nomePais").setValue(newNomePais).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(getBaseContext(),"Atualizar com Sucesso !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}