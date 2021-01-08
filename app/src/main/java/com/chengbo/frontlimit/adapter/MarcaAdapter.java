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
import com.chengbo.frontlimit.model.Marca;

import java.util.List;

public class MarcaAdapter extends RecyclerView.Adapter<MarcaAdapter.ViewHolder> {

    private Context context;
    private List<Marca> marcalist;

    public OnCallBack onCallBack;

    public MarcaAdapter(Context context, List<Marca> marcalist) {
        this.context = context;
        this.marcalist = marcalist;
    }

    public void setOnCallBack(OnCallBack onCallBack) {
        this.onCallBack = onCallBack;
    }

    @NonNull
    @Override
    public MarcaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_simple, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarcaAdapter.ViewHolder holder, int position) {

        holder.nomeop_item.setText(marcalist.get(position).getNomeMarca());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onCallBack.onButtonDeleteClick(marcalist.get(position));
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onCallBack.onButtonEditClick(marcalist.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return marcalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nomeop_item;
        private ImageButton btnDelete, btnEdit;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            nomeop_item = itemView.findViewById(R.id.item_name);
            btnDelete = itemView.findViewById(R.id.item_delete);
            btnEdit =  itemView.findViewById(R.id.item_edit);
        }
    }

    public interface OnCallBack{

        void onButtonDeleteClick(Marca marca);
        void onButtonEditClick(Marca marca);
    }
}
