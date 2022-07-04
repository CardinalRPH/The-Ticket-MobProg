package com.example.theticket;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.theticket.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
//    private GoogleMap mMap;
    String movcodex, loccodex;
    private DatabaseReference dbref;
    FirebaseDatabase db = FirebaseDatabase.getInstance("https://the-ticket-3f573-default-rtdb.asia-southeast1.firebasedatabase.app");
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                movcodex = null;
                loccodex = null;
            } else {
                movcodex = extras.getString("moviecode");
                loccodex = extras.getString("loccode");
            }
        } else {
            movcodex = (String) savedInstanceState.getSerializable("moviecode");
            loccodex = (String) savedInstanceState.getSerializable("loccode");
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        dbref = db.getReference("movie_data/"+movcodex+"/location/"+loccodex+"/lat");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double latx = (double) snapshot.getValue();
                dbref = db.getReference("movie_data/"+movcodex+"/location/"+loccodex+"/long");
                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        double longx = (double) snapshot.getValue();
                        dbref = db.getReference("movie_data/"+movcodex+"/location/"+loccodex+"/loc");
                        dbref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String locx = snapshot.getValue(String.class).toString();
                                map = googleMap;

                                // Tambahin titik di mallnya :
                                LatLng LippoMallKarawaci = new LatLng(latx, longx);
                                map.addMarker(new MarkerOptions().position(LippoMallKarawaci).title("Marker in "+locx));
                                float zoomLevel = 16.0f; //This goes up to 21
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(LippoMallKarawaci, zoomLevel));

//        map.animateCamera(CameraUpdateFactory.newLatLng(LippoMallKarawaci),5000,null);

                                map.getUiSettings().setZoomControlsEnabled(true);
                                map.getUiSettings().setCompassEnabled(true);
                                map.getUiSettings().setZoomGesturesEnabled(true);
                                map.getUiSettings().setScrollGesturesEnabled(true);
                                map.getUiSettings().setRotateGesturesEnabled(true);
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

    //Summarecon Mall Serpong -6.240548866454592, 106.62831561338864
    //Lippo Mall Karawaci -6.225874025031999, 106.6069498272683
    //The Breeze -6.302757083089676, 106.65402056822293
    //Mall Tangerang City -6.192547789897515, 106.63373210135302

}