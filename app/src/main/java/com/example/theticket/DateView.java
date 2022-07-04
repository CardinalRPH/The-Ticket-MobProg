package com.example.theticket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theticket.databinding.ActivityDateViewBinding;
import com.example.theticket.databinding.ActivityLocationBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DateView extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Adate> list;
    DatabaseReference databaseReference;
    DateAdapter adapter;

    private ActivityDateViewBinding binding;
//    FirebaseDatabase db = FirebaseDatabase.getInstance("https://the-ticket-3f573-default-rtdb.asia-southeast1.firebasedatabase.app");

//    String[] date={"21-10-2022", "22-10-2022", "23-10-2022", "24-10-2022", "25-10-2022"};
    String movcodex, loccodex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDateViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                movcodex = null;
                loccodex = null;
            } else {
                movcodex = extras.getString("moviecode");
                loccodex = extras.getString("loccode");
            }
        } else {
            movcodex = (String) savedInstanceState.getSerializable("moviecode");
            loccodex = (String) savedInstanceState.getSerializable("loccode");
        }

        recyclerView = binding.rcyvw;
        databaseReference = FirebaseDatabase.getInstance("https://the-ticket-3f573-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("/movie_data/"+movcodex+"/location/"+loccodex+"/date/");

        setupAction();

        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DateAdapter(this, list, movcodex, loccodex);
        recyclerView.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Log.d("fxccc", String.valueOf(dataSnapshot.getKey()));
                    String tmp = dataSnapshot.getValue().toString();
                    String tmpbrck = tmp.replaceAll("[(){}]","");
                    String tmpeq = tmpbrck.replaceAll("=", "");
                    String rmvcma = tmpeq.replace(",","");
                    String removefw = rmvcma.substring(0,tmpeq.indexOf(' '));
                    String rmv3ch = removefw.substring(3);


                    Log.d("thiis date", dataSnapshot.getValue().toString());


                    list.add(new Adate(rmv3ch));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void setupAction() {
        binding.backID3.setOnClickListener(view -> {
            Intent intent = new Intent(DateView.this,location.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        });
    }

    public static int findIndex(String arr[], String t) {

        // if array is Null
        if (arr == null) {
            return -1;
        }
        // find length of array
        int len = arr.length;
        int i = 0;

        for (i = 0; i < len; i++) {
            if (arr[i].equals(t)) {
                return i;
            }
        }
        return -1;
    }

//
//    private void getsizeis() {
//        databaseReference = db.getReference("/movie_data/" + movcodex + "/location/" + loccodex + "/date");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int sizeis = (int) snapshot.getChildrenCount();
//
//                Log.d("movcodexis", movcodex);
//                Log.d("loccodexis", loccodex);
//                Log.d("siziis", String.valueOf(sizeis));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }


}
