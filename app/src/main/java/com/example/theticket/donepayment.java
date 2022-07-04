package com.example.theticket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.theticket.databinding.ActivityDonepaymentBinding;
import com.example.theticket.databinding.ActivityPaymentmethodBinding;

public class donepayment extends AppCompatActivity {
    private ActivityDonepaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonepaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupAction();
    }

    private void setupAction() {
        binding.btnsccs1.setOnClickListener(view -> {
            Intent intent = new Intent(donepayment.this,PrimaryMenu.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        });
    }
}