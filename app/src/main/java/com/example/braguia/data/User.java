package com.example.braguia.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "user")
public class User {
    @PrimaryKey
    @NonNull
    //@SerializedName("id")
    @ColumnInfo(name = "username")
    String username;

    //@SerializedName("image_url")
    @ColumnInfo(name = "user_type")
    String user_type;
    @ColumnInfo(name = "first_name")
    private String first_name;

    @ColumnInfo(name = "last_name")
    private String last_name;

    @ColumnInfo(name = "email")
    private String email;


    public User(String user_type, @NonNull String username, String first_name, String last_name, String email) {
        this.user_type = user_type;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
    }


    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }


    @NonNull
    public String getUsername() {
        return username;
    }

    public String getUser_type() {
        return user_type;
    }
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) &&
                user_type.equals(user.user_type) &&
                first_name.equals(user.first_name) &&
                last_name.equals(user.last_name) &&
                email.equals(user.email);
    }
}