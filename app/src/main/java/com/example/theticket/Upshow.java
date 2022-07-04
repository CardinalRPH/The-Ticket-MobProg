package com.example.theticket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.theticket.databinding.ActivityNowShowBinding;
import com.example.theticket.databinding.ActivityUpshowBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Upshow extends AppCompatActivity {

    private ActivityUpshowBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityUpshowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final ArrayList<String> imageList = new ArrayList<>();
        final RecyclerView recyclerView = binding.dataList;
        final List<String> titles = new ArrayList<>();
        final List<String> thecode = new ArrayList<>();
        final List<String> codeimg = new ArrayList<>();
        final ImageAdapter2 adapter = new ImageAdapter2(imageList, titles, thecode, codeimg, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false);

//        titles.add("First Item");
//        titles.add("Second Item");
//        titles.add("Third Item");
//        titles.add("Fourth Item");

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("movie/nowshow");

        storageReference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference fileRef : listResult.getItems()) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageList.add(uri.toString());
                            codeimg.add(fileRef.getName());
                            String fileNoEx = removeExtension(fileRef.getName());
                            titles.add(removeExtension(fileNoEx));
                            thecode.add(getExtension(fileNoEx));
                            Log.d("item", uri.toString());
                        }
                    }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            recyclerView.setLayoutManager(gridLayoutManager);
                            recyclerView.setAdapter(adapter);
                        }
                    });
                }
            }
        });
    }
    private static String removeExtension(String s) {

        String separator = System.getProperty("file.separator");
        String filename;

        // Remove the path upto the filename.
        int lastSeparatorIndex = s.lastIndexOf(separator);
        if (lastSeparatorIndex == -1) {
            filename = s;
        } else {
            filename = s.substring(lastSeparatorIndex + 1);
        }

        // Remove the extension.
        int extensionIndex = filename.lastIndexOf(".");
        if (extensionIndex == -1)
            return filename;

        return filename.substring(0, extensionIndex);
    }

    public static String getExtension(String s) {
        int index = s.lastIndexOf('.');
        if(index > 0) {
            return s.substring(index + 1);
        } else  {
            return "";
        }
    }
}