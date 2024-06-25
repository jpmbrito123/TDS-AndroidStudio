package com.example.braguia.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.braguia.data.App;
import com.example.braguia.repository.AppRepository;

import java.util.List;

public class AppViewModel extends AndroidViewModel {
    private AppRepository appRepository;
    public LiveData<List<App>> appInfo;

    public AppViewModel(@NonNull Application application) {
        super(application);
        appRepository = new AppRepository(application);
        appInfo = appRepository.getApp();
    }

    public LiveData<List<App>> getAppInfo() {
        return appInfo;
    }
}
