package com.example.braguia.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.braguia.R;
import com.example.braguia.data.Edge;
import com.example.braguia.ui.adapters.TrailEdgeRecyclerViewAdapter;
import com.example.braguia.ui.adapters.TrailRelRecyclerViewAdapter;
import com.example.braguia.data.HistoryTrailDao;
import com.example.braguia.data.HistoryTrails;
import com.example.braguia.utils.SessionManager;
import com.example.braguia.viewmodel.TrailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TrailDetailActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trail_details);

        sessionManager = SessionManager.getInstance(getApplication());

        TextView nameView = findViewById(R.id.trail_detail_name);
        TextView descView = findViewById(R.id.trail_detail_desc);
        TextView durationView = findViewById(R.id.trail_detail_duration);
        TextView diffView = findViewById(R.id.trail_detail_diff);
        FloatingActionButton floatingActionButton = findViewById(R.id.trail_detail_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrailDetailActivity.this, ContactsActivity.class);
                startActivity(intent);
            }
        });


        Intent intent = getIntent();
        String trailId = "";
        if (intent != null) {
            trailId = intent.getStringExtra("trail_id");
        }

        TrailViewModel trailViewModel = new ViewModelProvider(this).get(TrailViewModel.class);
        trailViewModel.getTrail(trailId).observe(this, trail -> {
            nameView.setText(trail.getName());
            descView.setText(trail.getTrail_desc());
            durationView.setText(String.format("Duration: %s minutes", trail.getTrail_duration()));
            diffView.setText(String.format("Difficulty: %s", trail.getTrail_diff()));

            int trailRels = trail.getRelTrails().size();
            if (trailRels > 0) {
                RecyclerView recyclerView = findViewById(R.id.trail_rel_fragment);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(new TrailRelRecyclerViewAdapter(trail.getRelTrails()));
            }

            int edge_no = trail.getEdges().size();
            if (edge_no > 0) {
                RecyclerView recyclerView2 = findViewById(R.id.trail_edge_recycler);
                recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView2.setAdapter(new TrailEdgeRecyclerViewAdapter(trail.getEdges()));
            }

            List<Edge> edges = trail.getEdges();
            Button button = findViewById(R.id.edge_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!sessionManager.getSharedPreferences().getString("isLoggedIn", "false").equalsIgnoreCase("true")) {
                        Toast.makeText(getApplicationContext(), "You are not logged in and you must be a premium user!", Toast.LENGTH_SHORT).show();
                    }
                    else if (!sessionManager.getSharedPreferences().getString("usertype", "normal").equalsIgnoreCase("premium")) {
                        Toast.makeText(getApplicationContext(), "You must be a premium user", Toast.LENGTH_SHORT).show();
                    } else {
                        List<Coordinate> coordinates = new ArrayList<>();

                        for(Edge e: edges) {
                            if (e == edges.get(edges.size() - 1)) {
                                coordinates.add(new Coordinate(e.getStart_pin().getLatitude(), e.getStart_pin().getLongitude()));
                                coordinates.add(new Coordinate(e.getEnd_pin().getLatitude(), e.getEnd_pin().getLongitude()));
                            }
                            else {
                                coordinates.add(new Coordinate(e.getStart_pin().getLatitude(), e.getStart_pin().getLongitude()));
                            }
                        }

                        Uri.Builder mapUri = new Uri.Builder();
                        mapUri.scheme("https")
                                .encodedAuthority("www.google.com")
                                .appendEncodedPath("maps")
                                .appendEncodedPath("dir/")
                                .appendQueryParameter("api", "1");

                        StringBuilder stringCoords = new StringBuilder();
                        for (int i=0; i < coordinates.size() - 1 ; i++) {
                            stringCoords.append(coordinates.get(i).getLatitude())
                                    .append(",")
                                    .append(coordinates.get(i).getLongitude())
                                    .append("|");
                        }
                        String destination = String.format("%s,%s", coordinates.get(coordinates.size() - 1).getLatitude(), coordinates.get(coordinates.size() - 1).getLongitude());

                        mapUri.appendQueryParameter("waypoints", stringCoords.substring(0, stringCoords.length() - 1))
                                .appendQueryParameter("destination", destination);

                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri.build());
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }

                }
            });
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

    public static class Coordinate {
        private final String lat;
        private final String lng;

        public Coordinate(String lat, String lng) {
            assert lat != null;
            assert lng != null;

            this.lat = lat;
            this.lng = lng;
        }

        public String getLatitude() { return this.lat; }
        public String getLongitude() { return this.lng; }
    }
}
