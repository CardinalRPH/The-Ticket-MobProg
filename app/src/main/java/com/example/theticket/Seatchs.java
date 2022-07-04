package com.example.theticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theticket.databinding.ActivityDateViewBinding;
import com.example.theticket.databinding.ActivitySeatchsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class Seatchs extends AppCompatActivity implements inhseat {

    DatabaseReference databaseReference;

    private ActivitySeatchsBinding binding;

    RecyclerView recyclerView;
    SeatAdapter adapter;
    ArrayList<String> arrayList = new ArrayList<String>();
    ArrayList<String> arrthis = new ArrayList<String>();
    String[] tmpst;

    TextView tview;

    String movcodex, loccodex, datecodex, timecodex;

    Integer price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeatchsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.recvseat;
        tview = binding.priceis;


//        arrayList.add(1);
//        arrayList.add(2);
//        arrayList.add(3);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                movcodex = null;
                loccodex = null;
                datecodex = null;
                timecodex = null;
            } else {
                movcodex = extras.getString("moviecode");
                loccodex = extras.getString("loccode");
                datecodex = extras.getString("datecode");
                timecodex = extras.getString("timecode");
            }
        } else {
            movcodex = (String) savedInstanceState.getSerializable("moviecode");
            loccodex = (String) savedInstanceState.getSerializable("loccode");
            datecodex = (String) savedInstanceState.getSerializable("datecode");
            timecodex = (String) savedInstanceState.getSerializable("timecode");
        }
        arrayList = new ArrayList<String>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SeatAdapter(this, arrayList, this );
        recyclerView.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance("https://the-ticket-3f573-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("/movie_data/"+movcodex+"/location/"+loccodex+"/date/"+datecodex+"/time/"+timecodex+"/seat");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    String tmp = dataSnapshot.getValue().toString().substring(dataSnapshot.getValue().toString().lastIndexOf("=") + 1);
                    String tmpbrck = tmp.replaceAll("\\}", "");
                    Log.d("0venos", tmpbrck);
                    Log.d("0venosis", timecodex);
                    Log.d("0venos", String.valueOf(dataSnapshot.getValue()));
                    String[] numberstar = tmpbrck.split(",");

                    for(String vv :numberstar) {
                        arrayList.add(vv);
                    }


                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        setupAction();

    }

    private void setupAction() {
        binding.buttonseat.setOnClickListener(view -> {
            Intent i =new Intent(Seatchs.this, Summary.class);
            String allarr = arrayList.toString().replaceAll("\\[", "").replaceAll("\\]", "");
            String selarr = arrthis.toString().replaceAll("\\[", "").replaceAll("\\]", "");
            i.putExtra("moviecode", movcodex);
            i.putExtra("loccode", loccodex);
            i.putExtra("datecode", datecodex);
            i.putExtra("timecode", timecodex);
            i.putExtra("allarr", allarr);
            i.putExtra("selarr", selarr);
            i.putExtra("price", price);

            startActivity(i);
            Log.d("dddd",  arrayList.toString());
//            Toast.makeText(this, arrayList.toString(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, arrthis.toString(), Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void onseatcg(ArrayList<String> arrayList) {
        price = 50000*arrayList.size();
        tview.setText("Rp. "+price.toString());
        arrthis=new ArrayList<>(arrayList);
        //Todo
//        Toast.makeText(this, "ppp"+arrayList.toString(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "woops"+price.toString(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, arrayList.toString(), Toast.LENGTH_SHORT).show();

    }
}