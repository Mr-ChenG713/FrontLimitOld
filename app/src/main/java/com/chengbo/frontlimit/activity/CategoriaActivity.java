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
import com.chengbo.frontlimit.adapter.CategoriaAdapter;
import com.chengbo.frontlimit.model.Categoria;
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

public class CategoriaActivity extends AppCompatActivity {

    public Toolbar toolbar;

    private Button btn_save;
    private RecyclerView recyclerView;

    private FirebaseDatabase fmbase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = fmbase.getReference("categoria");

    private List<Categoria> listcategoria = new ArrayList<>();
    private CategoriaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        //toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categoria de Mecadoria");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_save = (Button) findViewById(R.id.catego_inserir);
        recyclerView = (RecyclerView) findViewById(R.id.catego_view);
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

        EditText nomecategoria = dialog.findViewById(R.id.dialog_edittext);
        Button btnaddcategoria = dialog.findViewById(R.id.btn_dialog_add);

        btnaddcategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(nomecategoria.getText())){

                    nomecategoria.setError("Inserir o nome de Orgão Policial");
                    nomecategoria.requestFocus();
                }else{

                    adicionarDadoFirebase(nomecategoria.getText().toString());
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void adicionarDadoFirebase(String text) {

        Categoria categoria = new Categoria();
        categoria.setCategoriaid(UUID.randomUUID().toString());
        categoria.setNomeCategoria(text);

        reference.child(categoria.getCategoriaid()).setValue(categoria);
        Toast.makeText(getBaseContext(),"Inserir com Sucesso !!!", Toast.LENGTH_SHORT).show();
    }

    private void listarDados() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                listcategoria.clear();
                for (DataSnapshot snapshot: datasnapshot.getChildren()){
                    Categoria value = snapshot.getValue(Categoria.class);
                    listcategoria.add(value);
                }
                adapter = new CategoriaAdapter(CategoriaActivity.this, listcategoria);
                recyclerView.setAdapter(adapter);
                setClick();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setClick() {
        adapter.setOnCallBack(new CategoriaAdapter.OnCallBack() {
            @Override
            public void onButtonDeleteClick(Categoria categoria) {
                deleteOp(categoria);
            }

            @Override
            public void onButtonEditClick(Categoria categoria) {
                showDialogUpdate(categoria);
            }
        });
    }

    private void deleteOp (final Categoria categoria){

        reference.child(categoria.getCategoriaid()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getBaseContext(),"Eliminar: " + categoria.getNomeCategoria() + " com Sucesso !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialogUpdate(Categoria categoria){

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

        EditText nomeCategoria = dialog.findViewById(R.id.dialog_edittext);
        nomeCategoria.setText(categoria.getNomeCategoria());
        Button btnaddcategoria = dialog.findViewById(R.id.btn_dialog_add);
        btnaddcategoria.setText("Atualizar");

        btnaddcategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(nomeCategoria.getText())){

                    nomeCategoria.setError("Inserir o nome de Categoria");
                    nomeCategoria.requestFocus();
                }else{

                    updateCategoria(categoria, nomeCategoria.getText().toString());
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void updateCategoria(Categoria categoria, String newNomeCategoria) {

        reference.child(categoria.getCategoriaid()).child("nomeCategoria").setValue(newNomeCategoria).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(getBaseContext(),"Atualizar com Sucesso !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}