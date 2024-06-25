package com.example.braguia.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.braguia.data.Media;
import com.example.braguia.data.Pin;
import com.example.braguia.data.RelPin;
import com.example.braguia.repository.PinRepository;

import java.util.List;

public class PinViewModel extends AndroidViewModel {
    private PinRepository pinRepository;
    public LiveData<List<Pin>> pins;

    public PinViewModel(@NonNull Application application) {
        super(application);
        pinRepository = new PinRepository(application);
        pins = pinRepository.getAllPins();
    }

    public LiveData<List<Pin>> getAllPins() {
        return pins;
    }

    public LiveData<Pin> getPin(String id) {
        return pinRepository.getPin(id);
    }
    public LiveData<List<Media>> getMedia(String id){
        return pinRepository.getMedia(id);
    }


    public LiveData<List<RelPin>> getRelPinsFromPin(String pinId) {
        return pinRepository.getRelPinsFromPin(pinId);
    }
}
