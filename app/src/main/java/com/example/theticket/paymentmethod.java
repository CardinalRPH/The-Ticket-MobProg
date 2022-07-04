package com.example.theticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.theticket.databinding.ActivityPaymentmethodBinding;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class paymentmethod extends AppCompatActivity {

    private ActivityPaymentmethodBinding binding;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ticketdatasetter tcd = new ticketdatasetter();
    String movcodex, loccodex, datecodex, timecodex, allar, selarr;
    Integer price;
    List<String> myal;
    List<String> mysel;
    String tmpxxx="";
    String tmpccc="";
    String shtime, dur, dateis, seatno, loct;
    private DatabaseReference dbref;
    String ticketis;
    FirebaseDatabase db = FirebaseDatabase.getInstance("https://the-ticket-3f573-default-rtdb.asia-southeast1.firebasedatabase.app");
    StorageReference storageReference;
    RadioGroup rg1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentmethodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rg1 = binding.radiogrp;


        storageReference = FirebaseStorage.getInstance().getReference();

        binding.radiogrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int idx) {
                switch (idx) {
                    case R.id.radioButton3:
                        break;
                    case R.id.radioButton4:
                        break;
                    case R.id.radioButton5:
                        break;
                    case R.id.radioButton6:
                        break;
                }
            }
        });


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                movcodex = null;
                loccodex = null;
                datecodex = null;
                timecodex = null;
                allar = null;
                selarr = null;
                price = null;
            } else {
                movcodex = extras.getString("moviecode");
                loccodex = extras.getString("loccode");
                datecodex = extras.getString("datecode");
                timecodex = extras.getString("timecode");
                allar = extras.getString("allarr");
                selarr = extras.getString("selarr");
                price = extras.getInt("price");
            }
        } else {
            movcodex = (String) savedInstanceState.getSerializable("moviecode");
            loccodex = (String) savedInstanceState.getSerializable("loccode");
            datecodex = (String) savedInstanceState.getSerializable("datecode");
            timecodex = (String) savedInstanceState.getSerializable("timecode");
            allar = (String) savedInstanceState.getSerializable("allarr");
            selarr = (String) savedInstanceState.getSerializable("selarr");
            price = (Integer) savedInstanceState.getSerializable("price");
        }

        String ticketisv = selarr.concat(timecodex).concat(datecodex).concat(loccodex).concat(movcodex).concat(mUser.getUid());
        ticketis = ticketisv;

        setupAction();
        Log.d("check user", mUser.getUid().toString());
    }


    private void setupAction() {
        binding.btnokeditaboutus.setOnClickListener(view -> {
            if(rg1.getCheckedRadioButtonId()==-1) {
                Toast.makeText(getApplicationContext(),"Please Select Payment", Toast.LENGTH_SHORT).show();
            } else {
                convtiket();
                Intent intent = new Intent(paymentmethod.this,donepayment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
                Log.d("new cat", selarr);
                Log.d("new cat", allar);
            }

        });
        binding.btnokeditaboutus2.setOnClickListener(view -> {
            Intent intent = new Intent(paymentmethod.this,NowShow.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        });
    }

    public static class codetc {

        public String tck;
        public String selseat;
        public String titlex;
        public String shtimesx;
        public String dur;
        public String daate;
        public String locx;
        public String datepch;

        public codetc(String tck, String selseat, String titlex, String shtimesx, String dur, String daate, String locx, String datepch) {
            this.tck = tck;
            this.selseat = selseat;
            this.titlex = titlex;
            this.shtimesx = shtimesx;
            this.dur = dur;
            this.daate = daate;
            this.locx = locx;
            this.datepch = datepch;
        }

        public String getTck() {
            return tck;
        }

        public void setTck(String tck) {
            this.tck = tck;
        }

        public String getSelseat() {
            return selseat;
        }

        public void setSelseat(String selseat) {
            this.selseat = selseat;
        }

        public String getTitlex() {
            return titlex;
        }

        public void setTitlex(String titlex) {
            this.titlex = titlex;
        }

        public String getShtimesx() {
            return shtimesx;
        }

        public void setShtimesx(String shtimesx) {
            this.shtimesx = shtimesx;
        }

        public String getDur() {
            return dur;
        }

        public void setDur(String dur) {
            this.dur = dur;
        }

        public String getDaate() {
            return daate;
        }

        public void setDaate(String daate) {
            this.daate = daate;
        }

        public String getLocx() {
            return locx;
        }

        public void setLocx(String locx) {
            this.locx = locx;
        }

        public String getDatepch() {
            return datepch;
        }

        public void setDatepch(String datepch) {
            this.datepch = datepch;
        }
    }

    private void convtiket() {
        Map<String, codetc> userstck = new HashMap<>();

        dbref = db.getReference("movie_data/"+movcodex+"/Title");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String titleis = snapshot.getValue(String.class).toString();
                dbref = db.getReference("movie_data/"+movcodex+"/location/"+loccodex+"/date/"+datecodex+"/time/"+timecodex+"/tm/");
                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String shtimeis = snapshot.getValue(String.class).toString();
                        dbref = db.getReference("movie_data/"+movcodex+"/Dur");
                        dbref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String duris = snapshot.getValue(String.class).toString();
                                dbref = db.getReference("movie_data/"+movcodex+"/location/"+loccodex+"/date/"+datecodex+"/dtd/");
                                dbref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String dateis = snapshot.getValue(String.class).toString();
                                        dbref = db.getReference("movie_data/"+movcodex+"/location/"+loccodex+"/loc/");
                                        dbref.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String locis = snapshot.getValue(String.class).toString();
                                                Date datenow = new Date();

                                                SimpleDateFormat formatternow = new SimpleDateFormat("dd-MM-yyyy");
                                                String datecal = formatternow.format(datenow);

                                                MultiFormatWriter writer = new MultiFormatWriter();
                                                try {
                                                    BitMatrix matrix = writer.encode(ticketis, BarcodeFormat.QR_CODE, 350, 350);
                                                    BarcodeEncoder encoder = new BarcodeEncoder();
                                                    Bitmap bitmap = encoder.createBitmap(matrix);//// this is bitmap

                                                    StorageReference fileref = storageReference.child("users/"+mUser.getUid()+"/ticket/"+ticketis+".jpg");
                                                    fileref.putFile(getImageUri(getApplicationContext(), bitmap)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                            Toast.makeText(getApplicationContext(),"Generate Successfully", Toast.LENGTH_LONG).show();
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(getApplicationContext(),"Generate Failed", Toast.LENGTH_LONG).show();
                                                        }
                                                    });

                                                } catch (WriterException e) {
                                                    e.printStackTrace();
                                                }


                                                userstck.put("ticket", new codetc(ticketis,selarr,titleis,shtimeis,duris,dateis,locis,datecal));
                                                dbref = db.getReference("user_data");
                                                DatabaseReference refuid = dbref.child(mUser.getUid()).child("theticket").child(ticketis);
                                                refuid.setValue(userstck);
                                                updateseat();
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void updateseat() {
        String alltmp = allar.replaceAll("\\s","");
        String selltmp = selarr.replaceAll("\\s","");
        myal = new ArrayList<String>(Arrays.asList(alltmp.split(",")));
        mysel = new ArrayList<String>(Arrays.asList(selltmp.split(",")));
        myal.removeAll(mysel);
        for (String fix : myal) {
            tmpxxx+= fix+",";
        }

        for (String fixx : mysel) {
            tmpccc+= fixx+",";
        }


        dbref = db.getReference("/movie_data/"+movcodex+"/location/"+loccodex+"/date/"+datecodex+"/time/"+timecodex+"/seat/st");
        dbref.setValue(tmpxxx);

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}