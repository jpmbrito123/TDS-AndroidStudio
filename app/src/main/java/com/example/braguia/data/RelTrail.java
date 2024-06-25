package com.example.braguia.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "trail_relation", foreignKeys = @ForeignKey(entity = Trail.class, parentColumns = "id", childColumns = "trail_id", onDelete = ForeignKey.CASCADE))
public class RelTrail {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    String id;

    @SerializedName("value")
    @ColumnInfo(name = "value")
    private String value;

    @SerializedName("attrib")
    @ColumnInfo(name = "attrib")
    private String attrib;

    @SerializedName("trail")
    @ColumnInfo(name = "trail_id")
    private String trailId;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAttrib() {
        return attrib;
    }

    public void setAttrib(String attrib) {
        this.attrib = attrib;
    }

    public String getTrailId() {
        return trailId;
    }

    public void setTrailId(String trailId) {
        this.trailId = trailId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RelTrail relTrail = (RelTrail) o;

        if (!id.equals(relTrail.id)) return false;
        if (!value.equals(relTrail.value)) return false;
        if (!attrib.equals(relTrail.attrib)) return false;
        return trailId.equals(relTrail.trailId);
    }
}
