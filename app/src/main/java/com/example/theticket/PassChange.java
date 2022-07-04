package com.example.theticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.theticket.databinding.ActivityEditaccountBinding;
import com.example.theticket.databinding.ActivityPassChangeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PassChange extends AppCompatActivity {
    private ActivityPassChangeBinding binding;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPassChangeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        progressDialog=new ProgressDialog(this);

        binding.emaileditaccount.setText(mUser.getEmail().toString());
        binding.emaileditaccount.setEnabled(false);

        setupAction();
    }

    private void setupAction() {
        binding.backPass.setOnClickListener(view -> {
            Intent intent = new Intent(PassChange.this,Editaccount.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        });
        binding.btnokeditacc.setOnClickListener(view -> {
            if(binding.curPass.getText().toString().isEmpty() || binding.newPass.getText().toString().isEmpty()) {
                Toast.makeText(this,"All Field Must Fill", Toast.LENGTH_LONG).show();
            } else  {
                chgpass(mUser.getEmail().toString(), binding.curPass.getText().toString(), binding.newPass.getText().toString());
            }
        });
    }

    private void chgpass(String theemail, String thecurpass, String thenewpass) {
        progressDialog.setMessage("Changing Password...");
        progressDialog.setTitle("Change Password");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        AuthCredential credential = EmailAuthProvider.getCredential(theemail, thecurpass);
        mUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    mUser.updatePassword(thenewpass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Password Updated", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(PassChange.this,PrimaryMenu.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                finish();
                                startActivity(intent);
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Password Not Updated", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else  {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Failed Authenticate", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}