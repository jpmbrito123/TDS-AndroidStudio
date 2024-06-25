package com.example.braguia.repository;

import static com.example.braguia.data.RoomDb.databaseWriteExecutor;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.braguia.data.RoomDb;
import com.example.braguia.data.User;
import com.example.braguia.data.UserDao;

public class UserRepository {
    private static UserRepository instance;
    public UserDao userDao;
    public MediatorLiveData<User> currentUser;

    public UserRepository(Application application) {
        RoomDb database = RoomDb.getInstance(application);
        userDao = database.userDao();
        currentUser = new MediatorLiveData<>();
    }

    public static synchronized UserRepository getInstance(Application app) {
        if (instance == null) {
            instance = new UserRepository(app);
        }
        return instance;
    }

    public void insert(User user) {
        databaseWriteExecutor.execute(() -> {
            userDao.insert(user);
        });
    }

    public void addUser (String username) {
        LiveData<User> userLiveData = userDao.getUser(username);
        currentUser.addSource(
                userDao.getUser(username), localUser -> {
                    currentUser.setValue(localUser);
                    currentUser.removeSource(userLiveData);
                }
        );
    }

    public MediatorLiveData<User> getCurrentUser() {
        return currentUser;
    }
}
