package com.example.theticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.theticket.databinding.ActivitySeatchsBinding;
import com.example.theticket.databinding.ActivitySummaryBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Summary extends AppCompatActivity {


    private ActivitySummaryBinding binding;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private DatabaseReference dbref;
    FirebaseDatabase db = FirebaseDatabase.getInstance("https://the-ticket-3f573-default-rtdb.asia-southeast1.firebasedatabase.app");
    String movcodex, loccodex, datecodex, timecodex, allar, selarr;
    Integer price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySummaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                movcodex = null;
                loccodex = null;
                datecodex = null;
                timecodex = null;
                allar = null;
                selarr = null;
                price = null;
            } else {
                movcodex = extras.getString("moviecode");
                loccodex = extras.getString("loccode");
                datecodex = extras.getString("datecode");
                timecodex = extras.getString("timecode");
                allar = extras.getString("allarr");
                selarr = extras.getString("selarr");
                price = extras.getInt("price");
            }
        } else {
            movcodex = (String) savedInstanceState.getSerializable("moviecode");
            loccodex = (String) savedInstanceState.getSerializable("loccode");
            datecodex = (String) savedInstanceState.getSerializable("datecode");
            timecodex = (String) savedInstanceState.getSerializable("timecode");
            allar = (String) savedInstanceState.getSerializable("allarr");
            selarr = (String) savedInstanceState.getSerializable("selarr");
            price = (Integer) savedInstanceState.getSerializable("price");
        }

        filmname();
        duration();
        showtime();
        date();
        seatno();
        locationx();
        priceis();
        setupAction();
    }

    private void setupAction() {
        binding.letspay.setOnClickListener(view -> {
            Intent i =new Intent(Summary.this, paymentmethod.class);
            i.putExtra("moviecode", movcodex);
            i.putExtra("loccode", loccodex);
            i.putExtra("datecode", datecodex);
            i.putExtra("timecode", timecodex);
            i.putExtra("allarr", allar);
            i.putExtra("selarr", selarr);
            i.putExtra("price", price);
            startActivity(i);
        });
        binding.btnfinishUP2.setOnClickListener(view -> {
            Intent intent = new Intent(Summary.this,NowShow.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        });
    }

    private void priceis() {
        binding.textView4.setText(String.valueOf(price));
    }

    private void seatno() {
        binding.seatno.setText(selarr);
    }

    private void locationx() {
        dbref = db.getReference("movie_data/"+movcodex+"/location/"+loccodex+"/loc");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String datais = snapshot.getValue(String.class).toString();
                binding.director.setText(datais);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void date() {
        dbref = db.getReference("movie_data/"+movcodex+"/location/"+loccodex+"/date/"+datecodex+"/dtd");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String datais = snapshot.getValue(String.class).toString();
                binding.date.setText(datais);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showtime() {
        dbref = db.getReference("movie_data/"+movcodex+"/location/"+loccodex+"/date/"+datecodex+"/time/"+timecodex+"/tm");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String datais = snapshot.getValue(String.class).toString();
                Log.d("this key", snapshot.getKey());
                binding.shwtime.setText(datais);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void duration() {
        dbref = db.getReference("movie_data/"+movcodex+"/Dur");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String datais = snapshot.getValue(String.class).toString();
                binding.durtime.setText(datais);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void filmname() {
        dbref = db.getReference("movie_data/"+movcodex+"/Title");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String datais = snapshot.getValue(String.class).toString();
                binding.textView3.setText(datais);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}