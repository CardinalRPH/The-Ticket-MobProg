package com.example.theticket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.theticket.databinding.ActivityMainBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupAction();


    }

    private void setupAction() {
        binding.btnSignin.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, Signin.class); //change here
            Toast.makeText(getApplicationContext(),"Login Successfully", Toast.LENGTH_LONG).show();
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        });
        binding.btnSignup.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, Signup.class); //change here
            Toast.makeText(getApplicationContext(),"Login Successfully", Toast.LENGTH_LONG).show();
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        });
    }

//    public void Signin(View view) {
//        Intent intent = new Intent(MainActivity.this,Signin.class);
//        startActivity(intent);
//    }
//    public void Signup(View view) {
//        Intent intent = new Intent(MainActivity.this, Signup.class);
//        startActivity(intent);
//    }
}