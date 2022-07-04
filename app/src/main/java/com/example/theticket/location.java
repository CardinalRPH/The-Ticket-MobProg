package com.example.theticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.theticket.databinding.ActivityLocationBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class location extends AppCompatActivity {
    private ActivityLocationBinding binding;
    
    private DatabaseReference dbref;
    FirebaseDatabase db = FirebaseDatabase.getInstance("https://the-ticket-3f573-default-rtdb.asia-southeast1.firebasedatabase.app");

    ArrayList<lokasi_array> arrayList = new ArrayList<>();

    ListView listView;

    private String movcode;

    int sizeis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listView = findViewById(R.id.listviewlokasi);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                movcode = null;
            } else {
                movcode = extras.getString("moviecode");
            }
        } else {
            movcode = (String) savedInstanceState.getSerializable("moviecode");
        }

        arrayList.add(new lokasi_array("Summarecon Mall Serpong"));
        arrayList.add(new lokasi_array("Lippo Mall Karawaci"));
        arrayList.add(new lokasi_array("The Breeze"));
        arrayList.add(new lokasi_array("Mall Tangerang City"));

//        getsizeis();
//        getarray();
        
        //make custom adaptor
        lokasi_adaptor lokasi_adaptor = new lokasi_adaptor(this,R.layout.lokasi_list,arrayList);

        listView.setAdapter(lokasi_adaptor);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(location.this,DateView.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                intent.putExtra("moviecode", movcode);
                intent.putExtra("loccode",indxconv(i));
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(location.this,MapsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                intent.putExtra("moviecode", movcode);
                intent.putExtra("loccode",indxconv(i));
                startActivity(intent);
                Toast.makeText(getApplicationContext().getApplicationContext(), "Go To Maps", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        setupAction();
    }
//    private void getsizeis() {
//        dbref = db.getReference("cinema/location");
//        dbref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                sizeis = (int) snapshot.getChildrenCount();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    private String indxconv(int index) {
        int tmp = index+1;
        return String.valueOf(tmp);
    }

    private void setupAction() {
        binding.backID23.setOnClickListener(view -> {
            Intent intent = new Intent(location.this,NowShow.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        });
    }

//    private void getarray() {
//        arrayList.clear();
//        for(int i = 1; i<=sizeis; i++) {
//            dbref = db.getReference("cinema/location/"+i+"/loc");
//            dbref.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    arrayList.add(new lokasi_array(snapshot.getValue(String.class).toString()));
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//
//        }
//
//    }
}