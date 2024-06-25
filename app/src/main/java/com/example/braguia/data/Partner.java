package com.example.braguia.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "partner", foreignKeys = @ForeignKey(entity = App.class, parentColumns = "app_name", childColumns = "partner_app", onDelete = ForeignKey.CASCADE))
public class Partner {
    @PrimaryKey
    @NonNull
    @SerializedName("partner_name")
    @ColumnInfo(name = "partner_name")
    String partnerName;

    @SerializedName("partner_phone")
    @ColumnInfo(name = "partner_phone")
    String partnerPhone;

    @SerializedName("partner_url")
    @ColumnInfo(name = "partner_url")
    String url;

    @SerializedName("partner_mail")
    @ColumnInfo(name = "partner_mail")
    String email;

    @SerializedName("partner_desc")
    @ColumnInfo(name = "partner_desc")
    String description;

    @SerializedName("partner_app")
    @ColumnInfo(name = "partner_app")
    String app;

    @NonNull
    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(@NonNull String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerPhone() {
        return partnerPhone;
    }

    public void setPartnerPhone(String partnerPhone) {
        this.partnerPhone = partnerPhone;
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

        Partner partner = (Partner) o;

        if (!partnerName.equals(partner.partnerName)) return false;
        if (!Objects.equals(partnerPhone, partner.partnerPhone))
            return false;
        if (!Objects.equals(url, partner.url)) return false;
        if (!Objects.equals(email, partner.email)) return false;
        if (!Objects.equals(description, partner.description))
            return false;
        return Objects.equals(app, partner.app);
    }
}
