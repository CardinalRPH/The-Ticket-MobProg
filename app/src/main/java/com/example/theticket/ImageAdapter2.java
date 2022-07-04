package com.example.theticket;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter2 extends RecyclerView.Adapter<ImageAdapter2.ViewHolder> {
    private ArrayList<String> imageList;
    private Context context;
    private List<String> titles;
    private List<String> thecode;
    private List<String> codeimg;

    public ImageAdapter2(ArrayList<String> imageList, List<String> titles,  List<String> thecode, List<String> codeimg, Context context) {
        this.imageList = imageList;
        this.context = context;
        this.titles = titles;
        this.thecode = thecode;
        this.codeimg = codeimg;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_grid_layout2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter2.ViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        Glide.with(this.context).load(imageList.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView2xx);
            imageView = itemView.findViewById(R.id.imageView2xx);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context,"Text is"+titles.get(getAdapterPosition()), Toast.LENGTH_LONG).show();
                    Intent i =new Intent(context, UpShowDetail.class);
                    i.putExtra("thecode", thecode.get(getAdapterPosition()));
                    i.putExtra("codeimg", codeimg.get(getAdapterPosition()));
                    context.startActivity(i);
                }
            });
        }
    }
}
