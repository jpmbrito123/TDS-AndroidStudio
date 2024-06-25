package com.example.braguia.utils;

import com.example.braguia.data.App;
import com.example.braguia.data.Media;
import com.example.braguia.data.Pin;
import com.example.braguia.data.Trail;
import com.example.braguia.data.User;
import com.example.braguia.utils.UserData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {
    @GET("/app")
    Call<List<App>> getApp();
    @GET("/pins")
    Call<List<Pin>> getPins(@Header("Cookie") String cookies);
    @GET("/trails")
    Call<List<Trail>> getTrails();
    @GET("/user")
    Call<User> getUser(@Header("Cookie") String cookies);
    @GET("/content")
    Call<List<Media>> getMedias();
    @POST("/login")
    Call<Void> login(@Body UserData userData, @Header("Authorization") String authtoken);
    @POST("/logout")
    Call<Void> logout(@Header("Authorization") String authToken);
}


