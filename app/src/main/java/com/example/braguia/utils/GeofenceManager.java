package com.example.braguia.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.braguia.data.Pin;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.stream.Collectors;

public class GeofenceManager {
    private static GeofenceManager instance;
    private Activity activity;
    private GeofencingClient geofencingClient;

    public GeofenceManager(Activity activity) {
        this.activity = activity;
        geofencingClient = LocationServices.getGeofencingClient(activity);

    }

    public static synchronized GeofenceManager getInstance(Activity activity) {

        if (instance == null) {
            instance = new GeofenceManager(activity);
        } else if (!instance.getActivity().equals(activity)) {
            instance = new GeofenceManager(activity);
        }
        return instance;
    }

    private Activity getActivity() {
        return activity;
    }

    public void createGeofences(List<Pin> pins) {
        List<Geofence> geofences = pins.stream()
                .map(pin -> new Geofence.Builder()
                        .setRequestId(pin.getId())
                        .setExpirationDuration(Geofence.NEVER_EXPIRE)
                        .setCircularRegion(Double.parseDouble(pin.getLatitude()), Double.parseDouble(pin.getLongitude()), 100)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT)
                        .setLoiteringDelay(1000)
                        .build())
                .collect(Collectors.toList());

        Log.d("Siu", String.format("Geofence count: %d", geofences.size()));

        GeofencingRequest geofencingRequest = new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofences(geofences)
                .build();

        Intent intent = new Intent(activity.getApplicationContext(), GeofenceBroadcastReceiver.class);
        intent.setAction("ACTION_GEOFENCE_TRANSITION");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity.getApplicationContext(), 99, intent, PendingIntent.FLAG_MUTABLE);

        if (ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        geofencingClient.addGeofences(geofencingRequest, pendingIntent)
                .addOnSuccessListener(activity, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Siu", "Geofences added successfully");
                    }
                })
                .addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Siu", "Geofences not added");
                    }
                });
    }

    public void removeGeofences (Context context) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 99, new Intent(context, GeofenceBroadcastReceiver.class), PendingIntent.FLAG_MUTABLE);
        geofencingClient.removeGeofences(pendingIntent).addOnSuccessListener(unused -> {
            Log.d("Siu", "Removed all geofences");
        }).addOnFailureListener(unused -> {
            Log.e("Siu", "Failure to remove geofences");
        });
    }
}
