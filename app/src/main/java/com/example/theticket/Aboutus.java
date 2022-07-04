package com.example.theticket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.theticket.databinding.ActivityAboutusBinding;
import com.example.theticket.databinding.ActivityUploadPhotoUpBinding;

public class Aboutus extends AppCompatActivity {
    private ActivityAboutusBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupAction();
    }

    private void setupAction() {
        binding.btnokeditaboutus.setOnClickListener(view -> {
            Intent intent = new Intent(Aboutus.this,PrimaryMenu.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        });
    }

}