package com.chengbo.frontlimit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chengbo.frontlimit.R;
import com.chengbo.frontlimit.model.Categoria;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {

    private Context context;
    private List<Categoria> categorialist;

    public OnCallBack onCallBack;

    public CategoriaAdapter(Context context, List<Categoria> categorialist) {
        this.context = context;
        this.categorialist = categorialist;
    }

    public void setOnCallBack(OnCallBack onCallBack) {
        this.onCallBack = onCallBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_simple, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nomeop_item.setText(categorialist.get(position).getNomeCategoria());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onCallBack.onButtonDeleteClick(categorialist.get(position));
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onCallBack.onButtonEditClick(categorialist.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categorialist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nomeop_item, titulo;
        private ImageButton btnDelete, btnEdit;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            titulo = itemView.findViewById(R.id.textView19);
            nomeop_item = itemView.findViewById(R.id.item_name);
            btnDelete = itemView.findViewById(R.id.item_delete);
            btnEdit =  itemView.findViewById(R.id.item_edit);

            titulo.setText("Nome da Categoria:");

        }
    }

    public interface OnCallBack{

        void onButtonDeleteClick(Categoria categoria);
        void onButtonEditClick(Categoria categoria);
    }
}
