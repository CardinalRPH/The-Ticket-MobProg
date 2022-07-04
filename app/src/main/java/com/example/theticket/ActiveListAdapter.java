package com.example.theticket;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ActiveListAdapter extends RecyclerView.Adapter<ActiveListAdapter.MyViewHolder>{
    Context context;
    List<setgetActive> list;
    List<ticketkey> listkey;
    String movcodex;
    String loccodex;

    public ActiveListAdapter(Context context, List<setgetActive> list, String movcodex, String loccodex, List<ticketkey> listkey) {
        this.context = context;
        this.list = list;
        this.movcodex = movcodex;
        this.loccodex = loccodex;
        this.listkey = listkey;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_activelist,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        setgetActive adt = list.get(position);
        holder.datex.setText(adt.getFilm_name());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView datex;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            datex = itemView.findViewById(R.id.theidactive);

            itemView.setOnClickListener(view -> {
                ticketkey tck = listkey.get(getAdapterPosition());
                Log.d("lordlondo", tck.getThekeys());
                Intent i =new Intent(context, TicketCode.class);
                i.putExtra("tickcode", tck.getThekeys());
                context.startActivity(i);

                Toast.makeText(context.getApplicationContext(), "ehee"+getAdapterPosition(), Toast.LENGTH_SHORT).show();
            });
        }
    }
}
