package com.example.braguia.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.braguia.data.App;
import com.example.braguia.data.AppDao;
import com.example.braguia.data.RoomDb;
import com.example.braguia.utils.ApiService;

import java.util.List;

public class AppRepository {
    private static AppRepository instance;
    public AppDao appDao;
    public MediatorLiveData<List<App>> allApp;

    public AppRepository(Application application){
        RoomDb database = RoomDb.getInstance(application);
        appDao = database.appDao();
        allApp = new MediatorLiveData<>();
        allApp.addSource(
                appDao.getApp(), localApp -> {
                    // TODO: ADD cache validation logic
                    if (localApp != null && !localApp.isEmpty()) {
                        allApp.setValue(localApp);
                    } else {
                        ApiService.getInstance(application).fetchAppInfo(application);
                    }
                }
        );
    }

    public static synchronized AppRepository getInstance(Application app) {
        if (instance == null) {
            instance = new AppRepository(app);
        }
        return instance;
    }

    public void insert(List<App> app){
        new AppRepository.InsertAsyncTask(appDao).execute(app);
    }

    public LiveData<List<App>> getApp(){
        return allApp;
    }

    private static class InsertAsyncTask extends AsyncTask<List<App>,Void,Void> {
        private AppDao appDao;

        public InsertAsyncTask(AppDao catDao) {
            this.appDao=catDao;
        }

        @Override
        protected Void doInBackground(List<App>... lists) {
            appDao.insert(lists[0]);
            return null;
        }
    }

    public void deleteAppInfo() {
        new AppRepository.DeleteAllAsyncTask(appDao).execute();
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private AppDao appDao;

        public DeleteAllAsyncTask(AppDao trailDao) {
            this.appDao = trailDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            appDao.deleteAll();
            return null;
        }
    }
}
