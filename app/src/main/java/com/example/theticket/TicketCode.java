package com.example.theticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.theticket.databinding.ActivityDateViewBinding;
import com.example.theticket.databinding.ActivityTicketCodeBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class TicketCode extends AppCompatActivity {
    private ActivityTicketCodeBinding binding;
    String ticketkeys;
    private DatabaseReference dbref;
    FirebaseDatabase db = FirebaseDatabase.getInstance("https://the-ticket-3f573-default-rtdb.asia-southeast1.firebasedatabase.app");
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTicketCodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storageReference = FirebaseStorage.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                ticketkeys = null;
            } else {
                ticketkeys = extras.getString("tickcode");
            }
        } else {
            ticketkeys = (String) savedInstanceState.getSerializable("tickcode");
        }
        filmname();
        ticketcode();
        isactive();
        duris();
        shtimes();
        dateis();
        seatis();
        locis();
        datepch();
        loadqr();
        setupAction();

    }

    private void setupAction() {
        binding.backtck.setOnClickListener(view -> {
            Intent i = new Intent(this, PrimaryMenu.class);
            startActivity(i);
            finish();
        });
    }

    private void loadqr() {
        StorageReference fileref = storageReference.child("users/"+mUser.getUid()+"/ticket/"+ticketkeys+".jpg");
        fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri).into(binding.qris);
            }
        });
    }

    private void datepch() {
        dbref = db.getReference("/user_data/"+mUser.getUid()+"/theticket/"+ticketkeys+"/ticket/datepch");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String datais = snapshot.getValue(String.class).toString();
                binding.purrchased.setText(datais);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void isactive() {
        dbref = db.getReference("/user_data/"+mUser.getUid()+"/theticket/"+ticketkeys+"/ticket/daate");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String datex = snapshot.getValue(String.class).toString();
                dbref = db.getReference("/user_data/"+mUser.getUid()+"/theticket/"+ticketkeys+"/ticket/shtimesx");
                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String timex = snapshot.getValue(String.class).toString();
                        String ctime = datex.concat(" ").concat(timex);

                        Date datenow = new Date();
                        SimpleDateFormat formatternow = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        String datecal = formatternow.format(datenow);

                        try {
                            Date date1 = formatternow.parse(ctime);
                            Date date2 = formatternow.parse(datecal);
                            if(date1.after(date2)){
                                binding.textView4.setText("Active");
                            }
                            if(date1.before(date2)){
                                binding.textView4.setText("Inactive");
                            }
                            Log.d("date1", ctime);
                            Log.d("date2", datecal);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void locis() {
        dbref = db.getReference("/user_data/"+mUser.getUid()+"/theticket/"+ticketkeys+"/ticket/locx");
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

    private void seatis() {
        dbref = db.getReference("/user_data/"+mUser.getUid()+"/theticket/"+ticketkeys+"/ticket/selseat");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String datais = snapshot.getValue(String.class).toString();
                binding.seatno.setText(datais);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void dateis() {
        dbref = db.getReference("/user_data/"+mUser.getUid()+"/theticket/"+ticketkeys+"/ticket/daate");
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

    private void shtimes() {
        dbref = db.getReference("/user_data/"+mUser.getUid()+"/theticket/"+ticketkeys+"/ticket/shtimesx");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String datais = snapshot.getValue(String.class).toString();
                binding.shwtime.setText(datais);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void duris() {
        dbref = db.getReference("/user_data/"+mUser.getUid()+"/theticket/"+ticketkeys+"/ticket/dur");
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

    private void ticketcode() {
        dbref = db.getReference("/user_data/"+mUser.getUid()+"/theticket/"+ticketkeys+"/ticket/tck");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String datais = snapshot.getValue(String.class).toString();
                binding.tcCode.setText(datais);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void filmname() {
        dbref = db.getReference("/user_data/"+mUser.getUid()+"/theticket/"+ticketkeys+"/ticket/titlex");
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