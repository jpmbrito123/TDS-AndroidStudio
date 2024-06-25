package com.example.braguia.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<App> cats);

    @Query("SELECT DISTINCT * FROM app")
    LiveData<List<App>> getApp();

    @Query(("SELECT * FROM contact WHERE contact_app = :app_name"))
    LiveData<List<Contact>> getContacts(String app_name);

    @Query(("SELECT * FROM partner WHERE partner_app = :app_name"))
    LiveData<List<Partner>> getPartners(String app_name);

    @Query("SELECT * FROM social WHERE social_app = :app_name")
    LiveData<List<Social>> getSocials(String app_name);

    @Query("DELETE FROM app")
    void deleteAll();
}
