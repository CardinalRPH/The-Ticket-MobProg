package com.example.theticket;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.theticket.R;
import com.example.theticket.databinding.FragmentAccountBinding;
import com.example.theticket.databinding.FragmentHomeBinding;
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


public class HomeFragment extends Fragment {

    private DatabaseReference dbref;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    StorageReference storageReference;

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Glide.with(this).load(R.drawable.prof).circleCrop().into(binding.imageView2);

        storageReference = FirebaseStorage.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        nameload(mUser.getUid());

        picload(mUser.getUid());

        setupAction();

        return view;
    }

    private void setupAction() {
        binding.textView10N.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), NowShow.class);
            startActivity(i);
            //
        });
        binding.textView10.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), Upshow.class);
            startActivity(i);
        });
        ///
    }

    private void picload(String uid) {
        StorageReference fileref = storageReference.child("users/" + uid + "/profile.jpg");
        fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext()).load(uri).circleCrop().into(binding.imageView2);
            }
        });
    }

    private void nameload(String uid) {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://the-ticket-3f573-default-rtdb.asia-southeast1.firebasedatabase.app");
        dbref = db.getReference("/user_data/"+uid+"/name/full_name");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nameis = snapshot.getValue(String.class).toString();
                binding.textView8.setText(nameis);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}