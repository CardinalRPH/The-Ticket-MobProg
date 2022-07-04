package com.example.theticket;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.MyViewHolder> {
    Context context;
    List<Adate> list;
    String movcodex;
    String loccodex;

    public DateAdapter(Context context, List<Adate> list, String movcodex, String loccodex) {
        this.context = context;
        this.list = list;
        this.movcodex=movcodex;
        this.loccodex=loccodex;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_date,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Adate adt = list.get(position);
        holder.datex.setText(adt.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView datex;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            datex = itemView.findViewById(R.id.theid);

            itemView.setOnClickListener(view -> {
                Intent i =new Intent(context, TimeRes.class);
                i.putExtra("moviecode", movcodex);
                i.putExtra("loccode", loccodex);
                i.putExtra("datecode", String.valueOf(getAdapterPosition()+1));
                context.startActivity(i);

            });
        }
    }
}