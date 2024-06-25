package com.example.braguia.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "contact", foreignKeys = @ForeignKey(entity = App.class, parentColumns = "app_name", childColumns = "contact_app", onDelete = ForeignKey.CASCADE))
public class Contact {
    @PrimaryKey
    @NonNull
    @SerializedName("contact_name")
    @ColumnInfo(name = "contact_name")
    String contactName;

    @SerializedName("contact_phone")
    @ColumnInfo(name = "contact_phone")
    String contactPhone;

    @SerializedName("contact_url")
    @ColumnInfo(name = "contact_url")
    String url;

    @SerializedName("contact_mail")
    @ColumnInfo(name = "contact_mail")
    String email;

    @SerializedName("contact_desc")
    @ColumnInfo(name = "contact_desc")
    String description;

    @SerializedName("contact_app")
    @ColumnInfo(name = "contact_app")
    String app;

    @NonNull
    public String getContactName() {
        return contactName;
    }

    public void setContactName(@NonNull String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (!contactName.equals(contact.contactName)) return false;
        if (!Objects.equals(contactPhone, contact.contactPhone))
            return false;
        if (!Objects.equals(url, contact.url)) return false;
        if (!Objects.equals(email, contact.email)) return false;
        if (!Objects.equals(description, contact.description))
            return false;
        return Objects.equals(app, contact.app);
    }
}
