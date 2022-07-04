package com.example.theticket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.theticket.databinding.ActivityEditaccountBinding;
import com.example.theticket.databinding.ActivitySigninBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Editaccount extends AppCompatActivity {
    private ActivityEditaccountBinding binding;

    ProgressDialog progressDialog;

    private DatabaseReference dbref;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditaccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        progressDialog=new ProgressDialog(this);

        binding.emaileditaccount.setText(mUser.getEmail().toString());
        binding.emaileditaccount.setEnabled(false);
        
        setupAction();
    }

    private void setupAction() {
        binding.backID.setOnClickListener(view -> {
            Intent intent = new Intent(Editaccount.this,PrimaryMenu.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
            ///
        });
        binding.btnokeditacc.setOnClickListener(view -> {
            if(binding.usernameedit.getText().toString().isEmpty()) {
                Toast.makeText(this,"All Field Must Fill", Toast.LENGTH_LONG).show();
            } else  {
                editname(mUser.getUid().toString(), binding.usernameedit.getText().toString());
            }
            ///
        });
        binding.btncgpass.setOnClickListener(view -> {
            Intent intent = new Intent(Editaccount.this,PassChange.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
            //
        });
    }

//    public static class User {
//
//        public String full_name;
//
//        public User(String full_Name) {
//            this.full_name = full_Name;
//        }
//        public String getFull_name() {
//            return full_name;
//        }
//        public void setFull_name(String full_name) {
//            this.full_name = full_name;
//        }
//
//    }

    private void editname(String uid, String Nname) {
        progressDialog.setMessage("Editing Name...");
        progressDialog.setTitle("Edit Name");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://the-ticket-3f573-default-rtdb.asia-southeast1.firebasedatabase.app");

        dbref = db.getReference("/user_data/"+uid+"/name/full_name");
        dbref.setValue(Nname);

//        dbref = db.getReference("user_data");
//        DatabaseReference usersRef = dbref.child(uid);
//        Map<String, String> map = new HashMap<>();
//        map.put("full_name", Nname);
//        usersRef.setValue(map);

        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(),"Name Changed", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Editaccount.this,PrimaryMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }


}