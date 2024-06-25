package com.example.braguia.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Pin> cats);

    @Query("SELECT DISTINCT * FROM pin")
    LiveData<List<Pin>> getPins();

    @Query("SELECT * FROM pin WHERE id=:id")
    LiveData<Pin> getPin(String id);

    @Query("SELECT * FROM pin_relation WHERE pin_id = :pin_id")
    LiveData<List<RelPin>> getRelPinsFromPin(String pin_id);

    @Query("SELECT * FROM mediaTable WHERE media_pin = :media_pin")
    LiveData<List<Media>> getMediaFromPin(String media_pin);

    @Query("DELETE FROM pin")
    void deleteAll();
}
