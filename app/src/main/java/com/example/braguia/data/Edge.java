package com.example.braguia.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "edge", foreignKeys = @ForeignKey(entity = Trail.class, parentColumns = "id", childColumns = "trail_id", onDelete = ForeignKey.CASCADE))
public class Edge {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    String id;

    @SerializedName("edge_transport")
    @ColumnInfo(name = "transport")
    String transport;

    @SerializedName("edge_duration")
    @ColumnInfo(name = "duration")
    String duration;

    @SerializedName("edge_desc")
    @ColumnInfo(name = "edge_desc")
    String description;

    @SerializedName("edge_trail")
    @ColumnInfo(name = "trail_id")
    String trail_id;

    @SerializedName("edge_start")
    Pin start_pin;

    @SerializedName("edge_end")
    Pin end_pin;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrail_id() {
        return trail_id;
    }

    public void setTrail_id(String trail_id) {
        this.trail_id = trail_id;
    }

    public Pin getStart_pin() {
        return start_pin;
    }

    public void setStart_pin(Pin start_pin) {
        this.start_pin = start_pin;
    }

    public Pin getEnd_pin() {
        return end_pin;
    }

    public void setEnd_pin(Pin end_pin) {
        this.end_pin = end_pin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return id.equals(edge.id) &&
                Objects.equals(transport, edge.transport) &&
                Objects.equals(duration, edge.duration) &&
                Objects.equals(description, edge.description) &&
                Objects.equals(trail_id, edge.trail_id) &&
                Objects.equals(start_pin, edge.start_pin) &&
                Objects.equals(end_pin, edge.end_pin);
    }
}
