package com.example.braguia.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "social", foreignKeys = @ForeignKey(entity = App.class, parentColumns = "app_name", childColumns = "social_app", onDelete = ForeignKey.CASCADE))
public class Social {
    @PrimaryKey
    @NonNull
    @SerializedName("social_name")
    @ColumnInfo(name = "social_name")
    String socialName;

    @SerializedName("social_url")
    @ColumnInfo(name = "social_url")
    String url;

    @SerializedName("social_share_link")
    @ColumnInfo(name = "share_link")
    String shareLink;

    @SerializedName("social_app")
    @ColumnInfo(name = "social_app")
    String app;

    @NonNull
    public String getSocialName() {
        return socialName;
    }

    public void setSocialName(@NonNull String socialName) {
        this.socialName = socialName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
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

        Social social = (Social) o;

        if (!socialName.equals(social.socialName)) return false;
        if (!Objects.equals(url, social.url)) return false;
        if (!Objects.equals(shareLink, social.shareLink)) return false;
        return Objects.equals(app, social.app);
    }
}
