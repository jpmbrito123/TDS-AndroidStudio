package com.example.braguia.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.braguia.R;
import com.example.braguia.data.HistoryRecord;
import com.example.braguia.data.Trail;
import com.example.braguia.data.TrailDao;
import com.example.braguia.repository.HistoryRepository;
import com.example.braguia.ui.adapters.HistoryRecyclerViewAdapter;
import com.example.braguia.ui.adapters.TrailRecyclerViewAdapter;
import com.example.braguia.utils.SessionManager;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private HistoryRepository historyRepository;
    private List<Trail> allTrails;
    private List<HistoryRecord> allRecords;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        SessionManager sessionManager = SessionManager.getInstance(getApplication());
        historyRepository = HistoryRepository.getInstance(getApplication());

        MediatorLiveData<List<Trail>> trailsLiveData = new MediatorLiveData<>();
        MediatorLiveData<List<HistoryRecord>> recordsLiveData = new MediatorLiveData<>();


        TextView username = findViewById(R.id.username);

        username.setText(sessionManager.getUsername());

        trailsLiveData.addSource(historyRepository.getTrails(), trails -> {
            if (trails != null && !trails.isEmpty()) {
                trailsLiveData.setValue(trails);
            }
        });

        recordsLiveData.addSource(historyRepository.getRecordsFromUser(), records -> {
            if (records != null && !records.isEmpty()) {
                recordsLiveData.setValue(records);
            }
        });

        MediatorLiveData<Pair<List<Trail>, List<HistoryRecord>>> combinedLiveData = new MediatorLiveData<>();
        combinedLiveData.addSource(trailsLiveData, trails -> {
            if (trails != null && !trails.isEmpty() && recordsLiveData.getValue() != null && !recordsLiveData.getValue().isEmpty()) {
                combinedLiveData.setValue(new Pair<>(trails, recordsLiveData.getValue()));
            }
        });
        combinedLiveData.addSource(recordsLiveData, records -> {
            if (records != null && !records.isEmpty() && trailsLiveData.getValue() != null && !trailsLiveData.getValue().isEmpty()) {
                combinedLiveData.setValue(new Pair<>(trailsLiveData.getValue(), records));
            }
        });

// Observe combinedLiveData to perform actions when both trails and records are available
        combinedLiveData.observe(this, pair -> {
            List<Trail> trails = pair.first;
            List<HistoryRecord> records = pair.second;
            for (int i = 0; i < pair.first.size(); i++) {
                records.get(i).setTrail(trails.get(i));
            }

            if (records.size() > 0){
                loadRecyclerView(records);
            }

            // Perform actions with both trails and records
            Log.d("TAG", "Trails: " + trails);
            Log.d("TAG", "Records: " + records);
        });
    }

    private void loadRecyclerView(List<HistoryRecord> records){
            RecyclerView recyclerView = findViewById(R.id.history_fragment);

            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(new HistoryRecyclerViewAdapter(records));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
