package com.example.braguia.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

@Entity(tableName = "trail",indices = @Index(value = {"id"},unique = true))
public class Trail {
    @PrimaryKey
    @NonNull
    //@SerializedName("id")
    @ColumnInfo(name = "id")
    String id;

    @SerializedName("trail_img")
    @ColumnInfo(name = "trail_img")
    String image_url;

    @SerializedName("trail_name")
    @ColumnInfo(name = "trail_name")
    String name;

    @SerializedName("trail_desc")
    @ColumnInfo(name = "trail_desc")
    String trail_desc;

    @SerializedName("trail_duration")
    @ColumnInfo(name = "trail_duration")
    String trail_duration;

    @SerializedName("trail_difficulty")
    @ColumnInfo(name = "trail_diff")
    String trail_diff;

    @SerializedName("rel_trail")
    private List<RelTrail> relTrails;

    @SerializedName("edges")
    private List<Edge> edges;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUrl() {
        return image_url;
    }

    public void setUrl(String url) {
        this.image_url = url;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getTrail_desc() {
        return trail_desc;
    }

    public void setTrail_desc(String trail_desc) {
        this.trail_desc = trail_desc;
    }

    public String getTrail_duration() {
        return trail_duration;
    }

    public void setTrail_duration(String trail_duration) {
        this.trail_duration = trail_duration;
    }

    public String getTrail_diff() {
        return trail_diff;
    }

    public void setTrail_diff(String trail_diff) {
        this.trail_diff = trail_diff;
    }

    public List<RelTrail> getRelTrails() {
        return relTrails;
    }

    public void setRelTrails(List<RelTrail> relTrails) {
        this.relTrails = relTrails;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trail trail = (Trail) o;
        return id.equals(trail.id) &&
                Objects.equals(image_url, trail.image_url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image_url);
    }
}
