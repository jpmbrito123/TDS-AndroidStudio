package com.example.braguia.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;

import com.example.braguia.data.User;
import com.example.braguia.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private MediatorLiveData<User> currentUser;
    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        currentUser = userRepository.getCurrentUser();
    }

    public MediatorLiveData<User> getCurrentUser(String username) {
        userRepository.addUser(username);
        currentUser = userRepository.getCurrentUser();
        return currentUser;
    }
}
