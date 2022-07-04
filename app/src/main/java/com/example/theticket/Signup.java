package com.example.theticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.theticket.databinding.ActivityMainBinding;
import com.example.theticket.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Signup extends AppCompatActivity {


    private ActivitySignupBinding binding;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    EditText namaE, emailE, passwdE;

    String emailPattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth.getInstance().signOut();
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupAction();

        namaE = binding.username;
        emailE = binding.email;
        passwdE = binding.password;

    }

    private void setupAction() {
        binding.btnnext.setOnClickListener(view -> {
            if(namaE.getText().toString().isEmpty()||passwdE.getText().toString().isEmpty()|| emailE.getText().toString().isEmpty()) {
                Toast.makeText(this,"All Field Must Fill", Toast.LENGTH_LONG).show();
            } else if(!emailE.getText().toString().matches(emailPattern)) {
                emailE.setError("Email Not Valid");
            } else if (passwdE.getText().toString().length()<6) {
                Toast.makeText(this,"Password must 6 or more char adn lowercase", Toast.LENGTH_LONG).show();
            } else {
                Intent i = new Intent(Signup.this, upload_photo_up.class);
                i.putExtra("namaP", namaE.getText().toString());
                i.putExtra("emailP", emailE.getText().toString());
                i.putExtra("passP", passwdE.getText().toString());
                startActivity(i);
            }
        });
        binding.backUp.setOnClickListener(view -> {
            startActivity(new Intent(Signup.this, MainActivity.class));
        });
    }


//    public void back1(View view) {
//        Intent intent = new Intent(Signup.this,MainActivity.class);
//        startActivity(intent);
//    }
//
//    public void createnext(View view) {
//        Intent intent = new Intent(Signup.this,upload_photo_up.class);
//        startActivity(intent);
//    }
}