package com.example.myblockcall.modal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myblockcall.R;

import java.util.List;

public class AdapterHolder extends RecyclerView.Adapter<AdapterHolder.ViewHolder> {
    Context context;
    List<DataModal> dataModalList;

    public AdapterHolder(Context context, List<DataModal> dataModalList) {
        this.context = context;
        this.dataModalList = dataModalList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.modal,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.name.setText(dataModalList.get(position).getDataName());
            holder.mobile.setText(dataModalList.get(position).getDataNumber());

    }

    @Override
    public int getItemCount() {
        return dataModalList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView name,mobile;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.txtname);
            mobile=itemView.findViewById(R.id.txtmobile);

        }

    }
}
