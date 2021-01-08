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
import com.chengbo.frontlimit.model.OrgaoPolicial;

import java.util.List;

public class OpAdapter extends RecyclerView.Adapter<OpAdapter.ViewHolder> {

    private Context context;
    private List<OrgaoPolicial> opclist;

    public OnCallBack onCallBack;

    public OpAdapter(Context context, List<OrgaoPolicial> opclist) {
        this.context = context;
        this.opclist = opclist;
    }

    public void setOnCallBack(OnCallBack onCallBack) {
        this.onCallBack = onCallBack;
    }

    @NonNull
    @Override
    public OpAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_simple, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OpAdapter.ViewHolder holder, int position) {

        holder.nomeop_item.setText(opclist.get(position).getNomeOrgaoPolicial());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onCallBack.onButtonDeleteClick(opclist.get(position));
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onCallBack.onButtonEditClick(opclist.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return opclist.size();
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

        void onButtonDeleteClick(OrgaoPolicial orgaoPolicial);
        void onButtonEditClick(OrgaoPolicial orgaoPolicial);
    }
}
