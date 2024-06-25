package com.example.braguia.data;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "history_Trails",foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = "username", childColumns = "username", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Trail.class, parentColumns = "id", childColumns = "trail_id", onDelete = ForeignKey.CASCADE)
},indices = @Index(value = {"id"},unique = true))
public class HistoryTrails {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    int id;
    @ColumnInfo(name = "username")
    String username;
    @ColumnInfo(name = "trail_id")
    String trail_id;
    @ColumnInfo(name = "date")
    String date;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTrail_id() {
        return trail_id;
    }

    public void setTrail_id(String trail_id) {
        this.trail_id = trail_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
