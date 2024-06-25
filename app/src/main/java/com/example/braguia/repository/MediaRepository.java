package com.example.braguia.repository;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.braguia.data.Media;
import com.example.braguia.data.MediaDao;
import com.example.braguia.data.RoomDb;
import com.example.braguia.utils.ApiService;

import java.util.List;

public class MediaRepository {
    private static MediaRepository instance;
    public MediaDao mediaDao;
    public MediatorLiveData<List<Media>> allMedias;

    public MediaRepository(Application application) {
        RoomDb database = RoomDb.getInstance(application);
        mediaDao = database.mediaDao();
        allMedias = new MediatorLiveData<>();
        allMedias.addSource(
                mediaDao.getMedia(), localMedias -> {
                    if (localMedias != null && !localMedias.isEmpty()) {
                        allMedias.setValue(localMedias);
                    } else {
                        ApiService.getInstance(application).fetchMedia(application);
                    }
                }
        );
    }

    public static synchronized MediaRepository getInstance(Application app) {
        if (instance == null) {
            instance = new MediaRepository(app);
        }
        return instance;
    }

    public void insert(List<Media> pins) {
        new InsertAsyncTask(mediaDao).execute(pins);
    }

    public LiveData<List<Media>> getAllMedias(){
        return allMedias;
    }

    private static class InsertAsyncTask extends AsyncTask<List<Media>,Void,Void> {
        private MediaDao mediaDao;

        public InsertAsyncTask(MediaDao catDao) {
            this.mediaDao=catDao;
        }

        @Override
        protected Void doInBackground(List<Media>... lists) {
            mediaDao.insert(lists[0]);
            return null;
        }
    }

    public void deleteAllTrails() {
        new MediaRepository.DeleteAllAsyncTask(mediaDao).execute();
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private MediaDao mediaDao;

        public DeleteAllAsyncTask(MediaDao mediaDao) {
            this.mediaDao = mediaDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mediaDao.deleteAll();
            return null;
        }
    }
}

