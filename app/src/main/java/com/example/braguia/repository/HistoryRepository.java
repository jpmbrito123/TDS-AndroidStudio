package com.example.braguia.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.braguia.data.Edge;
import com.example.braguia.data.HistoryRecord;
import com.example.braguia.data.HistoryTrailDao;
import com.example.braguia.data.RelTrail;
import com.example.braguia.data.RoomDb;
import com.example.braguia.data.Trail;
import com.example.braguia.data.TrailDao;
import com.example.braguia.data.UserDao;
import com.example.braguia.utils.ApiService;
import com.example.braguia.utils.SessionManager;

import java.util.List;

public class HistoryRepository {
    private static HistoryRepository instance;
    public TrailDao trailDao;
    public HistoryTrailDao historyTrailDao;
    public MediatorLiveData<List<Trail>> allTrails;
    public MediatorLiveData<List<HistoryRecord>> allRecords;

    public SessionManager sessionManager;

    public HistoryRepository(Application application){
        RoomDb database = RoomDb.getInstance(application);
        sessionManager = new SessionManager(application);

        trailDao = database.trailDao();
        historyTrailDao = database.HistoryTrailDao();

        allTrails = new MediatorLiveData<>();
        allTrails.addSource(
                historyTrailDao.getUserAllHistory(sessionManager.getUsername()), localTrails -> {
                    if (localTrails != null && !localTrails.isEmpty()) {
                        allTrails.setValue(localTrails);
                    } else {
                        ApiService.getInstance(application).fetchTrails(application);
                    }
                }
        );

        allRecords = new MediatorLiveData<>();
        allRecords.addSource(
                historyTrailDao.getUserRecords(sessionManager.getUsername()), records -> {
                    if (records != null && !records.isEmpty()) {
                        List<Trail> temp = allTrails.getValue();
                        allRecords.setValue(records);
                    } else {
                        ApiService.getInstance(application).fetchTrails(application);
                    }
                }
        );
    }

    public static synchronized HistoryRepository getInstance(Application app) {
        if (instance == null) {
            instance = new HistoryRepository(app);
        }
        return instance;
    }

    public LiveData<List<Trail>> getTrails() {
        return this.allTrails;
    }

    public LiveData<List<HistoryRecord>> getRecordsFromUser() {
        return this.allRecords;
    }

    private static class FetchAsyncTask extends AsyncTask<String,Void,Void> {
        private HistoryTrailDao historyTrailDao;

        public FetchAsyncTask(HistoryTrailDao catDao) {
            this.historyTrailDao=catDao;
        }

        @Override
        protected Void doInBackground(String... username) {
            historyTrailDao.getUserAllHistory(username[0]);
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
