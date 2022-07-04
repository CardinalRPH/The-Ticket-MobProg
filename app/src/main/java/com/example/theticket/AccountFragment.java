package com.example.theticket;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.theticket.databinding.ActivitySignupBinding;
import com.example.theticket.databinding.FragmentAccountBinding;
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


public class AccountFragment extends Fragment {

    private DatabaseReference dbref;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    StorageReference storageReference;

    private FragmentAccountBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Glide.with(this).load(R.drawable.prof).circleCrop().into(binding.photoU);

        storageReference = FirebaseStorage.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        
        nameload(mUser.getUid());

        picload(mUser.getUid());

        setupAction();


        return view;
    }

    private void setupAction() {
        binding.btneditacc.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), Editaccount.class);
            startActivity(i);
        });
        binding.btneditpic.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), Editprofilepicture.class);
            startActivity(i);
        });
        binding.btnaboutus.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), Aboutus.class);
            startActivity(i);
        });
        binding.btnlogout.setOnClickListener(view -> {
            mAuth.getInstance().signOut();
            Intent i = new Intent(getActivity(), MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            getActivity().finish();
        });
    }

    private void nameload(String uid) {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://the-ticket-3f573-default-rtdb.asia-southeast1.firebasedatabase.app");
        dbref = db.getReference("/user_data/"+uid+"/name/full_name");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nameis = snapshot.getValue(String.class).toString();
                binding.nameU.setText(nameis);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void picload(String uid) {
        StorageReference fileref = storageReference.child("users/"+uid+"/profile.jpg");
        fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext()).load(uri).circleCrop().into(binding.photoU);
            }
        });

//        Glide.with(this).load(R.drawable.prof).circleCrop().into(binding.photoU);
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//    }

}