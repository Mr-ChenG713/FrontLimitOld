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
import com.chengbo.frontlimit.adapter.MarcaAdapter;
import com.chengbo.frontlimit.model.Marca;
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


public class MarcaActivity extends AppCompatActivity{

    public Toolbar toolbar;

    private Button btn_save;
    private RecyclerView recyclerView;

    private FirebaseDatabase fmbase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = fmbase.getReference("marca");

    private List<Marca> listmarca = new ArrayList<>();
    private MarcaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marca);

        //toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Marca Trandporte");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_save = (Button) findViewById(R.id.marca_inserir);
        recyclerView = (RecyclerView) findViewById(R.id.marca_view);
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

        EditText nomeMarca = dialog.findViewById(R.id.dialog_edittext);
        Button btnaddmarca = dialog.findViewById(R.id.btn_dialog_add);

        btnaddmarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(nomeMarca.getText())){

                    nomeMarca.setError("Inserir o nome de Marca Transporte");
                    nomeMarca.requestFocus();
                }else{

                    adicionarDadoFirebase(nomeMarca.getText().toString());
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void adicionarDadoFirebase(String text) {

        Marca marca = new Marca();
        marca.setMarcaid(UUID.randomUUID().toString());
        marca.setNomeMarca(text);

        reference.child(marca.getMarcaid()).setValue(marca);
        Toast.makeText(getBaseContext(),"Inserir com Sucesso !!!", Toast.LENGTH_SHORT).show();
    }

    private void listarDados() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                listmarca.clear();
                for (DataSnapshot snapshot: datasnapshot.getChildren()){

                    Marca value = snapshot.getValue(Marca.class);
                    listmarca.add(value);
                }

                adapter = new MarcaAdapter(MarcaActivity.this, listmarca);
                recyclerView.setAdapter(adapter);
                setClick();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setClick() {

        adapter.setOnCallBack(new MarcaAdapter.OnCallBack() {
            @Override
            public void onButtonDeleteClick(Marca marca) {

                deleteMarca(marca);
            }

            @Override
            public void onButtonEditClick(Marca marca) {

                showDialogUpdate(marca);
            }
        });
    }

    private void deleteMarca (final Marca marca){

        reference.child(marca.getMarcaid()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getBaseContext(),"Eliminar: " + marca.getNomeMarca() + " com Sucesso !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialogUpdate(Marca marca){

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

        EditText nomeMarca = dialog.findViewById(R.id.dialog_edittext);
        nomeMarca.setText(marca.getNomeMarca());
        Button btnaddmarca = dialog.findViewById(R.id.btn_dialog_add);
        btnaddmarca.setText("Atualizar");

        btnaddmarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(nomeMarca.getText())){

                    nomeMarca.setError("Inserir o nome da Marca Transporte");
                    nomeMarca.requestFocus();
                }else{

                    updateMarca(marca, nomeMarca.getText().toString());
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void updateMarca(Marca marca, String newNomeMarca) {

        reference.child(marca.getMarcaid()).child("nomeMarca").setValue(newNomeMarca).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(getBaseContext(),"Atualizar com Sucesso !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}