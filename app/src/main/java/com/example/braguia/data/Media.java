package com.example.braguia.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "mediaTable")
public class Media {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;
    @SerializedName("media_file")
    @ColumnInfo(name = "media_file")
    private String mediaFile;
    @SerializedName("media_type")
    @ColumnInfo(name = "media_type")
    private String mediaType;
    @SerializedName("media_pin")
    @ColumnInfo(name = "media_pin")
    private int mediaPin;


    public Media(int id,String mediaFile, String mediaType, int mediaPin) {
        this.id = id;
        this.mediaFile = mediaFile;
        this.mediaType = mediaType;
        this.mediaPin = mediaPin;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMediaFile() {
        return mediaFile;
    }

    public void setMediaFile(String mediaFile) {
        this.mediaFile = mediaFile;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public int getMediaPin() {
        return mediaPin;
    }

    public void setMediaPin(int mediaPin) {
        this.mediaPin = mediaPin;
    }


}