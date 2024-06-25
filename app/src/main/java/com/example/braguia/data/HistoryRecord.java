package com.example.braguia.data;

import androidx.room.TypeConverters;

public class HistoryRecord {
    private Trail trail;
    private String date;
    public HistoryRecord() {
    }
    public HistoryRecord(Trail trailName, String date) {
        this.trail = trailName;
        this.date = date;
    }
    public Trail getTrail() {
        return trail;
    }
    public void setTrail(Trail trailName) {
        this.trail = trailName;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
