package com.example.braguia.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.braguia.R;
import com.example.braguia.data.Pin;
import com.example.braguia.utils.LocationService;
import com.example.braguia.viewmodel.PinViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap myMap;
    private SearchView searchView;
    private Marker locationMarker = null;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private BroadcastReceiver locationReceiver;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_maps);

        searchView = findViewById(R.id.map_search);
        int searchEditTextId = searchView.getContext().getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        EditText searchEditText = searchView.findViewById(searchEditTextId);
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setHintTextColor(getResources().getColor(R.color.black));

        FloatingActionButton mapFab = findViewById(R.id.map_fab);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        createMarker();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Code for the Location Service and Receiver
        locationReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() != null) {
                    Location location = intent.getParcelableExtra("location");
                    if (location != null) {
                        Log.d("inMaps", location.getLatitude() + ", " + location.getLongitude());

                        updateLocation(location);
                    }
                }
                else {
                    Log.e("mapsError", "Broadcast Receiver message not correct");
                }
            }
        };

        IntentFilter filter = new IntentFilter("location-update");
        registerReceiver(locationReceiver, filter, Context.RECEIVER_NOT_EXPORTED);

        startService(new Intent(this, LocationService.class));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                String searchLocation = searchView.getQuery().toString();
                List<Address> addressList = null;

                Geocoder geocoder = new Geocoder(MapsActivity.this);

                try {
                    addressList = geocoder.getFromLocationName(searchLocation, 1);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                assert addressList != null;
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                myMap.addMarker(new MarkerOptions().position(latLng).title(searchLocation));
                myMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                myMap.animateCamera(CameraUpdateFactory.zoomBy(7.5f));

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMapTypeDialog();
            }
        });

        PinViewModel pinViewModel = new ViewModelProvider(this).get(PinViewModel.class);
        pinViewModel.getAllPins().observe(this, pins -> {
            for (Pin pin: pins) {
                LatLng latLng = new LatLng(Double.parseDouble(pin.getLatitude()), Double.parseDouble(pin.getLongitude()));
                myMap.addMarker(new MarkerOptions().position(latLng).title(pin.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        myMap.getUiSettings().setZoomControlsEnabled(true);
        myMap.getUiSettings().setCompassEnabled(true);
        myMap.setPadding(20,150,20,150);

        myMap.animateCamera(CameraUpdateFactory.zoomBy(7.5f));

        createMarker();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createMarker();
            }
            else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(locationReceiver);
    }

    private void createMarker() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    if (locationMarker == null) {
                        BitmapDescriptor bitmapDescriptor = getDescriptorFromVector(MapsActivity.this, R.drawable.my_location_icon);
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                        locationMarker = myMap.addMarker(new MarkerOptions().position(latLng).title("My location").icon(bitmapDescriptor));
                        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7.5f));

                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                        mapFragment.getMapAsync(MapsActivity.this);
                    }
                }
            }
        });
    }

    private void updateLocation (Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        if (locationMarker != null) {
            locationMarker.setPosition(latLng);
        }
    }

    private BitmapDescriptor getDescriptorFromVector (Context context, int vectorId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorId);

        if(vectorDrawable == null) {
            Log.d("VectorDrawable", "Vector resource not found");
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void showMapTypeDialog() {
        final String[] mapTypes = {"Normal", "Satellite", "Hybrid", "Terrain"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
        builder.setTitle("Select Map Type")
                .setItems(mapTypes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                break;
                            case 1:
                                myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                                break;
                            case 2:
                                myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                                break;
                            case 3:
                                myMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                                break;
                        }
                    }
                });
        builder.create().show();
    }
}