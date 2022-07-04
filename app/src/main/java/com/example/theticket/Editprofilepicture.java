package com.example.theticket;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.theticket.databinding.ActivityEditprofilepictureBinding;
import com.example.theticket.databinding.ActivityUploadPhotoUpBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Editprofilepicture extends AppCompatActivity {
    private ActivityEditprofilepictureBinding binding;

    StorageReference storageReference;

    Uri imgUri;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditprofilepictureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Glide.with(this).load(R.drawable.prof).circleCrop().into(binding.imgEd);

        storageReference = FirebaseStorage.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        progressDialog=new ProgressDialog(this);

        picload(mUser.getUid());

        setupAction();
    }

    private void setupAction() {
        binding.backEdPt.setOnClickListener(view -> {
            Intent intent = new Intent(Editprofilepicture.this,PrimaryMenu.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
            ///
        });
        binding.chsPhtEd.setOnClickListener(view -> {
            Intent openglr = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(openglr,1000);
            ///
        });
        binding.btnokeditacc.setOnClickListener(view -> {
            upimgttofirebase(imgUri, mAuth.getCurrentUser().getUid());
            ///
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode  == 1000) {
            if(resultCode== Activity.RESULT_OK) {
                imgUri = data.getData();
                Glide.with(this).load(imgUri).circleCrop().into(binding.imgEd);
//                upimgttofirebase(imgUri);

            }
        }
    }

    private void upimgttofirebase(Uri imguri, String iduser) {
        progressDialog.setMessage("Changing...");
        progressDialog.setTitle("Change Photo");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
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
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Changing Successfully", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Editprofilepicture.this, PrimaryMenu.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Changing Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void picload(String uid) {
        StorageReference fileref = storageReference.child("users/"+uid+"/profile.jpg");
        fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri).circleCrop().into(binding.imgEd);
            }
        });

//        Glide.with(this).load(R.drawable.prof).circleCrop().into(binding.photoU);
    }

}