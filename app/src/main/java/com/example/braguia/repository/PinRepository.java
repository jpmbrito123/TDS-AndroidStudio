package com.example.braguia.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.LinearLayout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.braguia.data.Media;
import com.example.braguia.data.Pin;
import com.example.braguia.data.PinDao;
import com.example.braguia.data.RelPin;
import com.example.braguia.data.RoomDb;
import com.example.braguia.utils.ApiService;

import java.util.List;

public class PinRepository {
    private static PinRepository instance;
    public PinDao pinDao;
    public MediatorLiveData<List<Pin>> allPins;

    public PinRepository(Application application) {
        RoomDb database = RoomDb.getInstance(application);
        pinDao = database.pinDao();
        allPins = new MediatorLiveData<>();
        allPins.addSource(
                pinDao.getPins(), localPins -> {
                    if (localPins != null && !localPins.isEmpty()) {
                        allPins.setValue(localPins);
                    }
                    else {
                        ApiService.getInstance(application).fetchPins(application);
                    }
                }
        );
    }

    public static synchronized PinRepository getInstance(Application app) {
        if (instance == null) {
            instance = new PinRepository(app);
        }
        return instance;
    }

    public void insert(List<Pin> pins) {
        new InsertAsyncTask(pinDao).execute(pins);
    }

    public LiveData<List<Pin>> getAllPins(){
        return allPins;
    }

    public LiveData<Pin> getPin(String id) {
        return pinDao.getPin(id);
    }

    public LiveData<List<Media>> getMedia (String id) {return pinDao.getMediaFromPin(id);}

    public LiveData<List<RelPin>> getRelPinsFromPin(String pin_id) {
        return pinDao.getRelPinsFromPin(pin_id);
    }

    private static class InsertAsyncTask extends AsyncTask<List<Pin>,Void,Void> {
        private PinDao pinDao;

        public InsertAsyncTask(PinDao catDao) {
            this.pinDao=catDao;
        }

        @Override
        protected Void doInBackground(List<Pin>... lists) {
            pinDao.insert(lists[0]);
            return null;
        }
    }

    public void deleteAllTrails() {
        new PinRepository.DeleteAllAsyncTask(pinDao).execute();
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private PinDao pinDao;

        public DeleteAllAsyncTask(PinDao pinDao) {
            this.pinDao = pinDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            pinDao.deleteAll();
            return null;
        }
    }
}
