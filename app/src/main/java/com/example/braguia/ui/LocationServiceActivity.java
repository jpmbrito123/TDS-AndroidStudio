package com.example.braguia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.braguia.R;
import com.example.braguia.utils.LocationService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LocationServiceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_location_service);

        FloatingActionButton floatingActionButton = findViewById(R.id.location_service_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationServiceActivity.this, ContactsActivity.class);
                startActivity(intent);
            }
        });

        Button button_start = findViewById(R.id.button_start);
        Button button_stop = findViewById(R.id.button_stop);

        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(LocationServiceActivity.this, LocationService.class));

                Toast.makeText(getApplicationContext(), "Started location service", Toast.LENGTH_SHORT).show();
            }
        });

        button_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(LocationServiceActivity.this, LocationService.class));

                Toast.makeText(getApplicationContext(), "Stopped location service", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
