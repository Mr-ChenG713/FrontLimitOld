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
import com.chengbo.frontlimit.adapter.OpAdapter;
import com.chengbo.frontlimit.model.OrgaoPolicial;
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

public class OpcActivity extends AppCompatActivity {

    public Toolbar toolbar;

    private Button btn_save;
    private RecyclerView recyclerView;

    private FirebaseDatabase fmbase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = fmbase.getReference("orgaopolicial");

    private List<OrgaoPolicial> listopc = new ArrayList<>();
    private OpAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opc);

        //toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Orgão Policial Criminal");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_save = (Button) findViewById(R.id.opc_inserir);
        recyclerView = (RecyclerView) findViewById(R.id.opc_view);
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

        EditText nomeOPC = dialog.findViewById(R.id.dialog_edittext);
        Button btnaddopc = dialog.findViewById(R.id.btn_dialog_add);

        btnaddopc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(nomeOPC.getText())){

                    nomeOPC.setError("Inserir o nome de Orgão Policial");
                    nomeOPC.requestFocus();
                }else{

                    adicionarDadoFirebase(nomeOPC.getText().toString());
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void adicionarDadoFirebase(String text) {

        OrgaoPolicial orgaoPolicial = new OrgaoPolicial();
        orgaoPolicial.setOragaoPolicialid(UUID.randomUUID().toString());
        orgaoPolicial.setNomeOrgaoPolicial(text);

        reference.child(orgaoPolicial.getOragaoPolicialid()).setValue(orgaoPolicial);
        Toast.makeText(getBaseContext(),"Inserir com Sucesso !!!", Toast.LENGTH_SHORT).show();
    }

    private void listarDados() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                listopc.clear();
                for (DataSnapshot snapshot: datasnapshot.getChildren()){
                    OrgaoPolicial value = snapshot.getValue(OrgaoPolicial.class);
                    listopc.add(value);
                }
                adapter = new OpAdapter(OpcActivity.this, listopc);
                recyclerView.setAdapter(adapter);
                setClick();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setClick() {
        adapter.setOnCallBack(new OpAdapter.OnCallBack() {
            @Override
            public void onButtonDeleteClick(OrgaoPolicial orgaoPolicial) {

                deleteOp(orgaoPolicial);
            }

            @Override
            public void onButtonEditClick(OrgaoPolicial orgaoPolicial) {

                showDialogUpdate(orgaoPolicial);
            }
        });
    }

    private void deleteOp (final OrgaoPolicial orgaoPolicial){

        reference.child(orgaoPolicial.getOragaoPolicialid()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getBaseContext(),"Eliminar: " + orgaoPolicial.getNomeOrgaoPolicial() + " com Sucesso !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialogUpdate(OrgaoPolicial orgaoPolicial){

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

        EditText nomeOPC = dialog.findViewById(R.id.dialog_edittext);
        nomeOPC.setText(orgaoPolicial.getNomeOrgaoPolicial());
        Button btnaddopc = dialog.findViewById(R.id.btn_dialog_add);
        btnaddopc.setText("Atualizar");

        btnaddopc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(nomeOPC.getText())){

                    nomeOPC.setError("Inserir o nome de Orgão Policial");
                    nomeOPC.requestFocus();
                }else{

                    updateOPC(orgaoPolicial, nomeOPC.getText().toString());
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void updateOPC(OrgaoPolicial orgaoPolicial, String newNomeOrgaoPolicial) {

        reference.child(orgaoPolicial.getOragaoPolicialid()).child("nomeOrgaoPolicial").setValue(newNomeOrgaoPolicial).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(getBaseContext(),"Atualizar com Sucesso !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}