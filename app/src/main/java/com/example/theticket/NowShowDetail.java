package com.example.theticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.theticket.databinding.ActivityNowShowDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class NowShowDetail extends AppCompatActivity {
    private ActivityNowShowDetailBinding binding;

    private String thecode, codeimg;

    StorageReference storageReference;
    private DatabaseReference dbref;
    FirebaseDatabase db = FirebaseDatabase.getInstance("https://the-ticket-3f573-default-rtdb.asia-southeast1.firebasedatabase.app");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNowShowDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storageReference = FirebaseStorage.getInstance().getReference();


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                thecode = null;
                codeimg = null;
            } else {
                thecode = extras.getString("thecode");
                codeimg = extras.getString("codeimg");
            }
        } else {
            thecode = (String) savedInstanceState.getSerializable("thecode");
            codeimg = (String) savedInstanceState.getSerializable("codeimg");
        }

        setupAction();
        picload();
        detailsload1();
    }

    private void detailsload1() {
       director();
       movname();
       ratting();
       duration();
       lang();
       sub();
       star();
       genre();
       synopsis();
    }

    private void synopsis() {
        dbref = db.getReference("movie_data/"+thecode+"/Synopsis");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String datais = snapshot.getValue(String.class).toString();
                binding.synopsis.setText(datais);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void genre() {
        dbref = db.getReference("movie_data/"+thecode+"/Genre");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String datais = snapshot.getValue(String.class).toString();
                binding.genre.setText(datais);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void sub() {
        dbref = db.getReference("movie_data/"+thecode+"/Sub");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String datais = snapshot.getValue(String.class).toString();
                binding.sub.setText(datais);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void star() {
        dbref = db.getReference("movie_data/"+thecode+"/Starring");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String datais = snapshot.getValue(String.class).toString();
                binding.starring.setText(datais);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void lang() {
        dbref = db.getReference("movie_data/"+thecode+"/Lang");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String datais = snapshot.getValue(String.class).toString();
                binding.lang.setText(datais);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void duration() {
        dbref = db.getReference("movie_data/"+thecode+"/Dur");
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

    private void ratting() {
        dbref = db.getReference("movie_data/"+thecode+"/Rtg");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String datais = snapshot.getValue(String.class).toString();
                binding.rat.setText(datais);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void movname() {
        dbref = db.getReference("movie_data/"+thecode+"/Title");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String datais = snapshot.getValue(String.class).toString();
                binding.titlemov.setText(datais);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void director() {
        dbref = db.getReference("movie_data/"+thecode+"/Director");
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

    private void picload() {
        Toast.makeText(getApplicationContext(),"Text is"+codeimg, Toast.LENGTH_LONG).show();
        StorageReference fileref = storageReference.child("movie/nowshow/"+codeimg);
        fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri).into(binding.movimg);
            }
        });
    }

    private void setupAction() {
        binding.backtck.setOnClickListener(view -> {
            Intent intent = new Intent(NowShowDetail.this,NowShow.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        });

        binding.button2.setOnClickListener(view -> {
            Intent intent = new Intent(NowShowDetail.this,location.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            intent.putExtra("moviecode", thecode);
            startActivity(intent);
        });
    }
}