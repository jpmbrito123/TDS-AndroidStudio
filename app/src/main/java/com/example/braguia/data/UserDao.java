package com.example.braguia.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User cat);

    @Query("SELECT DISTINCT * FROM user")
    LiveData<List<User>> getUsers();

    @Query("SELECT * FROM user WHERE username=:username")
    LiveData<User> getUser(String username);

    @Query("DELETE FROM user")
    void deleteAll();
}
