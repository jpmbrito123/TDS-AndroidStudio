package com.example.braguia.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "pin", indices = @Index(value = {"id"}, unique = true))
public class Pin {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    String id;

    @SerializedName("pin_name")
    @ColumnInfo(name = "name")
    String name;

    @SerializedName("pin_desc")
    @ColumnInfo(name = "description")
    String description;

    @SerializedName("pin_lat")
    @ColumnInfo(name = "latitude")
    String latitude;

    @SerializedName("pin_lng")
    @ColumnInfo(name = "longitude")
    String longitude;

    @SerializedName("pin_alt")
    @ColumnInfo(name = "altitude")
    String altitude;

    @SerializedName("rel_pin")
    private List<RelPin> relPins;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public List<RelPin> getRelPins () {return relPins;}

    public void setRelPins(List<RelPin> relPins) {
        this.relPins = relPins;
    }
}
