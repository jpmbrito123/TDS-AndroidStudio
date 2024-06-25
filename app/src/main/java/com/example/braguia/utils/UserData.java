package com.example.braguia.utils;

import com.google.gson.annotations.SerializedName;

public class UserData {
    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    public UserData(String username, String password) {
        this.username = username;
        this.password = password;
    }
}