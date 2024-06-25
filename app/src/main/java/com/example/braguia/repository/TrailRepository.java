package com.example.braguia.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.braguia.data.Edge;
import com.example.braguia.data.RelTrail;
import com.example.braguia.data.RoomDb;
import com.example.braguia.data.Trail;
import com.example.braguia.data.TrailDao;
import com.example.braguia.utils.ApiService;

import java.util.List;

public class TrailRepository {
    private static TrailRepository instance;
    public TrailDao trailDao;
    public MediatorLiveData<List<Trail>> allTrails;

    public TrailRepository(Application application){
        RoomDb database = RoomDb.getInstance(application);
        trailDao = database.trailDao();
        allTrails = new MediatorLiveData<>();
        allTrails.addSource(
                trailDao.getTrails(), localTrails -> {
                    if (localTrails != null && !localTrails.isEmpty()) {
                        allTrails.setValue(localTrails);
                    } else {
                        ApiService.getInstance(application).fetchTrails(application);
                    }
                }
        );
    }

    public static synchronized TrailRepository getInstance(Application app) {
        if (instance == null) {
            instance = new TrailRepository(app);
        }
        return instance;
    }

    public void insert(List<Trail> trails){
        new InsertAsyncTask(trailDao).execute(trails);
    }

    public LiveData<List<Trail>> getAllTrails(){
        return allTrails;
    }

    public LiveData<Trail> getTrail(String id) {
        return trailDao.getTrail(id);
    }

    public LiveData<List<RelTrail>> getRelTrailsFromTrail(String trail_id) {
        return trailDao.getRelTrailsFromTrail(trail_id);
    }

    public LiveData<List<Edge>> getEdgesFromTrail(String trail_id) {
        return trailDao.getEdgesFromTrail(trail_id);
    }

    private static class InsertAsyncTask extends AsyncTask<List<Trail>,Void,Void> {
        private TrailDao trailDao;

        public InsertAsyncTask(TrailDao catDao) {
            this.trailDao=catDao;
        }

        @Override
        protected Void doInBackground(List<Trail>... lists) {
            trailDao.insert(lists[0]);
            return null;
        }
    }

    public void deleteAllTrails() {
        new DeleteAllAsyncTask(trailDao).execute();
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private TrailDao trailDao;

        public DeleteAllAsyncTask(TrailDao trailDao) {
            this.trailDao = trailDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            trailDao.deleteAll();
            return null;
        }
    }
}
