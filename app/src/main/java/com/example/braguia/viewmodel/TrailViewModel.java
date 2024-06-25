package com.example.braguia.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.braguia.data.Edge;
import com.example.braguia.data.RelTrail;
import com.example.braguia.data.Trail;
import com.example.braguia.repository.TrailRepository;

import java.util.List;

public class TrailViewModel extends AndroidViewModel {
    private TrailRepository trailRepository;
    public LiveData<List<Trail>> trails;

    public TrailViewModel(@NonNull Application application) {
        super(application);
        trailRepository = new TrailRepository(application);
        trails = trailRepository.getAllTrails();
    }

    public LiveData<List<Trail>> getAllTrails() {
        return trails;
    }

    public LiveData<Trail> getTrail(String id) {
        return trailRepository.getTrail(id);
    }

    public LiveData<List<RelTrail>> getRelTrailsFromPin(String trailId) {
        return trailRepository.getRelTrailsFromTrail(trailId);
    }

    public LiveData<List<Edge>> getEdgesFromTrail(String trail_id) {
        return trailRepository.getEdgesFromTrail(trail_id);
    }
}
