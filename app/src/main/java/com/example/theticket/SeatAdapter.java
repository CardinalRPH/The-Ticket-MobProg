package com.example.theticket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.ViewHolder> {
    View view;
    Context context;
    ArrayList<String> arrayList;
    inhseat inhseat;

    ArrayList<String> arrayList_x = new ArrayList<String>();

    public SeatAdapter(Context context, ArrayList<String> arrayList, com.example.theticket.inhseat inhseat) {
        this.context = context;
        this.arrayList = arrayList;
        this.inhseat = inhseat;
    }

    public View getView() {
        return view;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.seat_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(arrayList != null && arrayList.size()>0) {
            holder.checkBox.setText(arrayList.get(position));
            holder.checkBox.setOnClickListener(view -> {
                if(holder.checkBox.isChecked()) {
//                    Toast.makeText(context, "gegew", Toast.LENGTH_SHORT).show();
                    arrayList_x.add((arrayList.get(position)));
                } else  {
//                    Toast.makeText(context, "gegex", Toast.LENGTH_SHORT).show();
                    arrayList_x.remove(arrayList.get(position));
                }
                inhseat.onseatcg(arrayList_x);
            });
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkbox);

        }
    }
}
