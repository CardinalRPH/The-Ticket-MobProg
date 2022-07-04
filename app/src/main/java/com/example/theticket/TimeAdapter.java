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

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.MyViewHolder>{
    Context context;
    List<Adate> list;
    String movcodex;
    String loccodex;
    String datecodex;

    public TimeAdapter(Context context, List<Adate> list, String movcodex, String loccodex, String datecodex) {
        this.context = context;
        this.list = list;
        this.movcodex = movcodex;
        this.loccodex = loccodex;
        this.datecodex = datecodex;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_time,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeAdapter.MyViewHolder holder, int position) {
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
            datex = itemView.findViewById(R.id.theidtime);

            itemView.setOnClickListener(view -> {
                Intent i =new Intent(context, Seatchs.class);
                i.putExtra("moviecode", movcodex);
                i.putExtra("loccode", loccodex);
                i.putExtra("datecode", datecodex);
                i.putExtra("timecode", String.valueOf(getAdapterPosition()+1));
                context.startActivity(i);

//                Toast.makeText(context.getApplicationContext(), "ehee"+getAdapterPosition(), Toast.LENGTH_SHORT).show();
            });
        }
    }
}
