package com.example.braguia.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MediaDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Media media);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Media> medias);


    @Query("DELETE FROM mediaTable")
    void deleteAll();

    @Query("SELECT DISTINCT * FROM mediaTable")
    LiveData<List<Media>> getMedia();
}