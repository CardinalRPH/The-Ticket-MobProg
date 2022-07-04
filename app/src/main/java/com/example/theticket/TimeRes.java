package com.example.theticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.theticket.databinding.ActivityDateViewBinding;
import com.example.theticket.databinding.ActivityTimeResBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TimeRes extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Adate> list;
    DatabaseReference databaseReference;
    TimeAdapter adapter;

    private ActivityTimeResBinding binding;
//    FirebaseDatabase db = FirebaseDatabase.getInstance("https://the-ticket-3f573-default-rtdb.asia-southeast1.firebasedatabase.app");

    //    String[] date={"21-10-2022", "22-10-2022", "23-10-2022", "24-10-2022", "25-10-2022"};
    String movcodex, loccodex, datecodex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTimeResBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                movcodex = null;
                loccodex = null;
                datecodex = null;
            } else {
                movcodex = extras.getString("moviecode");
                loccodex = extras.getString("loccode");
                datecodex = extras.getString("datecode");
            }
        } else {
            movcodex = (String) savedInstanceState.getSerializable("moviecode");
            loccodex = (String) savedInstanceState.getSerializable("loccode");
            datecodex = (String) savedInstanceState.getSerializable("datecode");
        }

        recyclerView = binding.rcyvwx;
        databaseReference = FirebaseDatabase.getInstance("https://the-ticket-3f573-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("/movie_data/"+movcodex+"/location/"+loccodex+"/date/"+datecodex+"/time");

        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TimeAdapter(this, list, movcodex, loccodex, datecodex);
        recyclerView.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Log.d("xenos", String.valueOf(dataSnapshot.getValue()));
                    String tmp = dataSnapshot.getValue().toString().substring(dataSnapshot.getValue().toString().lastIndexOf("=") + 1);
                    String tmpbrck = tmp.replaceAll("\\}", "");


                    list.add(new Adate(tmpbrck));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
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

//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////        String month = parent.getItemAtPosition(position).toString();
//        int month = findIndex(Time, parent.getItemAtPosition(position).toString());
//
//        Intent intent = new Intent(TimeRes.this,DateView.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        finish();
//        intent.putExtra("moviecodex", movcodex);
//        intent.putExtra("loccodex",loccodex);
//        intent.putExtra("datecodex",datecodex);
//        intent.putExtra("timecodex",month);
//        startActivity(intent);
//        //String month = months[position];
//        //String month = ((TextView) view).getText().toString();
//        //String month = lvMonth.getItemAtPosition(position).toString();
//    }
}