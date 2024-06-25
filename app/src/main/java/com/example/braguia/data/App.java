package com.example.braguia.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

@Entity(tableName = "app")
public class App {
    @PrimaryKey
    @NonNull
    @SerializedName("app_name")
    @ColumnInfo(name = "app_name")
    String app_name;

    @SerializedName("app_desc")
    @ColumnInfo(name = "app_desc")
    String app_desc;

    @SerializedName("app_landing_page_text")
    @ColumnInfo(name = "app_landing_page_text")
    String app_landing_page_text;

    @SerializedName("socials")
    private List<Social> socials;

    @SerializedName("contacts")
    private List<Contact> contacts;

    @SerializedName("partners")
    private List<Partner> partners;

    @NonNull
    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(@NonNull String app_name) {
        this.app_name = app_name;
    }

    public String getApp_desc() {
        return app_desc;
    }

    public void setApp_desc(String app_desc) {
        this.app_desc = app_desc;
    }

    public String getApp_landing_page_text() {
        return app_landing_page_text;
    }

    public void setApp_landing_page_text(String app_landing_page_text) {
        this.app_landing_page_text = app_landing_page_text;
    }

    public List<Social> getSocials() {
        return socials;
    }

    public void setSocials(List<Social> socials) {
        this.socials = socials;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Partner> getPartners() {
        return partners;
    }

    public void setPartners(List<Partner> partners) {
        this.partners = partners;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        App app = (App) o;
        return app_name.equals(app.app_name) &&
                app_desc.equals(app.app_desc) &&
                Objects.equals(app_landing_page_text, app.app_landing_page_text) &&
                Objects.equals(socials, app.socials) &&
                Objects.equals(contacts, app.contacts) &&
                Objects.equals(partners, app.partners);
    }
}
