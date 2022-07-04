package com.example.theticket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class lokasi_adaptor extends ArrayAdapter<lokasi_array> {
    private Context mcontext;
    private int mresource;
    public lokasi_adaptor(@NonNull Context context, int resource, @NonNull ArrayList<lokasi_array> objects) {
        super(context, resource, objects);
        this.mcontext = context;
        this.mresource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);

        convertView = layoutInflater.inflate(mresource,parent,false);

        TextView  lokasi = convertView.findViewById(R.id.lokasi);

        lokasi.setText(getItem(position).getLokasi());


        return convertView;
    }


}
