package com.example.braguia.utils;

import static android.app.NotificationManager.IMPORTANCE_HIGH;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.braguia.R;
import com.example.braguia.ui.PinDetailActivity;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;
import java.util.Random;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equalsIgnoreCase("ACTION_GEOFENCE_TRANSITION")) {
            GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
            if (geofencingEvent.hasError()) {
                String errorMessage = GeofenceStatusCodes.getStatusCodeString(geofencingEvent.getErrorCode());
                Log.e("Siu", errorMessage);
            } else {
                List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
                assert triggeringGeofences != null;

                for (Geofence geofence : triggeringGeofences) {
                    String pinId = geofence.getRequestId();

                    int transitionType = geofencingEvent.getGeofenceTransition();
                    switch (transitionType) {
                        case Geofence.GEOFENCE_TRANSITION_ENTER:
                            sendNotification(context, "Entered geofence", pinId);
                            break;
                        case Geofence.GEOFENCE_TRANSITION_DWELL:
                            sendNotification(context, "Dwelling in geofence", pinId);
                            break;
                        case Geofence.GEOFENCE_TRANSITION_EXIT:
                            sendNotification(context, "Exited geofence", pinId);
                            break;
                        default:
                            Log.e("Siu", "Unknown transition type");
                            break;
                    }
                }
            }
        }
    }

    private void sendNotification(Context context, String message, String pinId) {
        Log.d("Siu", String.format("Message: %s, id: %s", message, pinId));

        Intent notificationIntent = new Intent(context, PinDetailActivity.class);
        notificationIntent.putExtra("pin_id", pinId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, Integer.parseInt(pinId), notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "geofence-channel")
                .setContentTitle("Geofence notification")
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel("geofence-channel") == null) {
                NotificationChannel notificationChannel = new NotificationChannel("geofence-channel", "Geofence receiver", IMPORTANCE_HIGH);
                notificationChannel.setDescription("Channel being used by the geofence broadcast receiver");

                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(new Random().nextInt(), builder.build());
    }
}
