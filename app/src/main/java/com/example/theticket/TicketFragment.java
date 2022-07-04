package com.example.theticket;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theticket.databinding.FragmentAccountBinding;
import com.example.theticket.databinding.FragmentTicketBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class TicketFragment extends Fragment {
    private FragmentTicketBinding binding;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    RecyclerView recyclerView;
    List<setgetActive> list;
    List<ticketkey> listkey;
    DatabaseReference databaseReference;
    ActiveListAdapter adapter;
    String movcodex, loccodex;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTicketBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        recyclerView = binding.rcyvwactive;
        databaseReference = FirebaseDatabase.getInstance("https://the-ticket-3f573-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("/user_data/"+mUser.getUid()+"/theticket/");
        listkey = new ArrayList<>();
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this.requireContext()));
        adapter = new ActiveListAdapter(this.requireContext(), list, movcodex, loccodex, listkey);
        recyclerView.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    String keyis = dataSnapshot.getKey().toString();
                    listkey.add(new ticketkey(keyis));
                    Log.d("lordlondo", String.valueOf(dataSnapshot.getKey()));
                    FirebaseDatabase db = FirebaseDatabase.getInstance("https://the-ticket-3f573-default-rtdb.asia-southeast1.firebasedatabase.app");
                    DatabaseReference dbref = db.getReference("/user_data/"+mUser.getUid()+"/theticket/"+keyis+"/ticket/titlex");
                    dbref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String datais = snapshot.getValue(String.class).toString();
                            list.add(new setgetActive(datais));
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    Log.d("uwawa", dataSnapshot.getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;
    }
}