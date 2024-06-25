package com.example.braguia.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryTrailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HistoryTrails cats);
    @Query("SELECT DISTINCT * FROM history_Trails")
    LiveData<List<HistoryTrails>> getAllHistory();
    @Query("SELECT * FROM trail WHERE id in (SELECT trail_id FROM history_Trails WHERE username =:username)")
    LiveData<List<Trail>> getUserAllHistory(String username);
    @Query("SELECT * FROM trail WHERE id in (SELECT trail_id FROM history_Trails WHERE username = :username and date = :date)")
    LiveData<List<Trail>> getUserDateHistory(String username, String date);
    @Query("SELECT COUNT(*) FROM history_Trails WHERE username = :username and trail_id = :trail_id and date = :date")
    int getInstance(String username, String trail_id, String date);
    @Query("SELECT trail.trail_name, history_Trails.date from history_Trails inner join trail on trail_id = trail.id where username = :username")
    LiveData<List<HistoryRecord>> getUserRecords(String username);
    @Query("DELETE FROM history_Trails")
    void deleteAll();
}
