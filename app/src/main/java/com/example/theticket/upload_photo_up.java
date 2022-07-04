package com.example.theticket;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.theticket.databinding.ActivityUploadPhotoUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class upload_photo_up extends AppCompatActivity {
    private ActivityUploadPhotoUpBinding binding;
    private DatabaseReference dbref;

    StorageReference storageReference;
    FirebaseDatabase db = FirebaseDatabase.getInstance("https://the-ticket-3f573-default-rtdb.asia-southeast1.firebasedatabase.app");

    String namaU,emailU,passU;

    Uri imgUri;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadPhotoUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Glide.with(this).load(R.drawable.prof).circleCrop().into(binding.imgUp);

        storageReference = FirebaseStorage.getInstance().getReference();


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        progressDialog=new ProgressDialog(this);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                namaU = null;
                emailU = null;
                passU = null;
            } else {
                namaU = extras.getString("namaP");
                emailU = extras.getString("emailP");
                passU = extras.getString("passP");
            }
        } else {
            namaU = (String) savedInstanceState.getSerializable("namaP");
            emailU = (String) savedInstanceState.getSerializable("emailP");
            passU = (String) savedInstanceState.getSerializable("passP");
        }

        setupAction();
    }

    private void setupAction() {
        binding.btnfinishUP.setOnClickListener(view -> {
            perforauth();
        });

        binding.backPht.setOnClickListener(view -> {
            startActivity(new Intent(upload_photo_up.this, Signup.class));
        });

        binding.chsPht.setOnClickListener(view -> {
            Intent openglr = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(openglr,1000);
        });
    }

    private void perforauth() {
        progressDialog.setMessage("Please Wait...");
        progressDialog.setTitle("Sign UP");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(emailU, passU).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
//                    progressDialog.dismiss();
//                    if(imgUri.equals(null)) {
//
//                    }
                    upimgttofirebase(imgUri, mAuth.getCurrentUser().getUid());
                    senddata(namaU, mAuth.getCurrentUser().getUid());
                    Intent i = new Intent(upload_photo_up.this, CreateAccountSuccess.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Registration Successfully", Toast.LENGTH_LONG).show();
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                } else  {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),""+task.getException(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode  == 1000) {
            if(resultCode== Activity.RESULT_OK) {
                imgUri = data.getData();
                Glide.with(this).load(imgUri).circleCrop().into(binding.imgUp);
//                upimgttofirebase(imgUri);

            }
        }
    }

    private void upimgttofirebase(Uri imguri, String iduser) {
        StorageReference fileref = storageReference.child("users/"+iduser+"/profile.jpg");
        fileref.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
////                        Picasso.get().load(uri).into(binding.imgUp);
//                        Glide.with(getApplicationContext()).load(uri).circleCrop().into(binding.imgUp);
//                    }
//                });
                Toast.makeText(getApplicationContext(),"Upload Successfully", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Upload Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static class User {

        public String full_name;

        public User(String full_Name) {
            this.full_name = full_Name;
        }
        public String getFull_name() {
            return full_name;
        }
        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

    }

    private  void senddata (final String name, final String uid) {
        dbref = db.getReference("user_data");
        DatabaseReference refuid = dbref.child(uid);
        DatabaseReference refname = refuid.child("name");
        refname.setValue(new upload_photo_up.User(name));


//        Map<String, User> map = new HashMap<>();
//        map.put(uid, new User(name));
//        dbref.setValue(map);
//        Toast.makeText(getApplicationContext(),"Data Saved", Toast.LENGTH_LONG).show();
    }
}