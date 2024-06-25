package com.example.braguia.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TrailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Trail> cats);

    @Query("SELECT DISTINCT * FROM trail")
    LiveData<List<Trail>> getTrails();

    @Query("SELECT * FROM trail WHERE id=:id")
    LiveData<Trail> getTrail(String id);

    @Query("SELECT * FROM trail_relation WHERE trail_id = :trail_id")
    LiveData<List<RelTrail>> getRelTrailsFromTrail(String trail_id);

    @Query("SELECT * FROM edge WHERE trail_id = :trail_id")
    LiveData<List<Edge>> getEdgesFromTrail(String trail_id);

    @Query("DELETE FROM trail")
    void deleteAll();
}
