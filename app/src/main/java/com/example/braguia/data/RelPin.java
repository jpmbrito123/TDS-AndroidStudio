package com.example.braguia.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "pin_relation", foreignKeys = @ForeignKey(entity = Pin.class, parentColumns = "id", childColumns = "pin_id", onDelete = ForeignKey.CASCADE))
public class RelPin {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @SerializedName("value")
    @ColumnInfo(name = "value")
    private String value;

    @SerializedName("attrib")
    @ColumnInfo(name = "attrib")
    private String attrib;

    @SerializedName("pin")
    @ColumnInfo(name = "pin_id")
    private String pinId;

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String getAttrib() {
        return attrib;
    }

    public String getPinId() {
        return pinId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setAttrib(String attrib) {
        this.attrib = attrib;
    }

    public void setPinId(String pinId) {
        this.pinId = pinId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RelPin relPin = (RelPin) o;

        if (!id.equals(relPin.id)) return false;
        if (!value.equals(relPin.value)) return false;
        if (!attrib.equals(relPin.attrib)) return false;
        return pinId.equals(relPin.pinId);
    }
}
