package com.example.braguia.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionManager {
    private static PermissionManager instance;

    public PermissionManager(Context context) {
    }

    public static PermissionManager getInstance(Application app) {
        if (instance == null) {
            instance = new PermissionManager(app);
        }
        return instance;
    }

    public void requestPermissions (Activity activity, Context context) {
        List<String> permissionsToRequest = new ArrayList<>();

        if (!hasFineLocationPermission(context)) {
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!hasPostNotificationsPermission(context)) {
                permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS);
            }
        }

        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissionsToRequest.toArray(new String[0]), 0);
        }

        List<String> permissionsToRequest2 = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (!hasBackgroundLocationPermission(context)) {
                permissionsToRequest2.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
            }
        }

        if(!permissionsToRequest2.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissionsToRequest2.toArray(new String[0]), 1);
        }
    }

    public boolean hasFineLocationPermission (Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public boolean hasPostNotificationsPermission (Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public boolean hasBackgroundLocationPermission (Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkGoogleMapsInstallation(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo("com.google.android.apps.maps", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void promptToInstallGoogleMaps(Activity activity, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("This app requires Google Maps. Do you want to install it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.apps.maps"));
                        activity.startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        activity.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
