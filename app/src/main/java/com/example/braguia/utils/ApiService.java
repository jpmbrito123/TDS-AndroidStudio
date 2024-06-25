package com.example.braguia.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.braguia.data.App;
import com.example.braguia.data.Media;
import com.example.braguia.data.Pin;
import com.example.braguia.data.Trail;
import com.example.braguia.data.User;
import com.example.braguia.repository.AppRepository;
import com.example.braguia.repository.MediaRepository;
import com.example.braguia.repository.PinRepository;
import com.example.braguia.repository.TrailRepository;
import com.example.braguia.repository.UserRepository;
import com.example.braguia.ui.MainActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private static ApiService instance;
    private SessionManager sessionManager;
    private AppRepository appRepository;
    private MediaRepository mediaRepository;
    private PinRepository pinRepository;
    private TrailRepository trailRepository;
    private static final String BASE_URL = "https://39b6-193-137-92-72.ngrok-free.app";

    public ApiService (Context context) {
    }

    public static ApiService getInstance(Application app) {
        if (instance == null) {
            instance = new ApiService(app);
        }
        return instance;
    }

    private static OkHttpClient client () {
        return new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.SECONDS)
                .readTimeout(10000, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    private static Retrofit retrofit () {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public String getCookies (Application app) {
        sessionManager = SessionManager.getInstance(app);
        SharedPreferences sp = sessionManager.getSharedPreferences();
        String sessionid = sp.getString("sessionid", "");
        String csrftoken = sp.getString("csrftoken", "");
        String cookies = csrftoken + ";" + sessionid;

        return cookies;
    }

    public void fetchAppInfo (Application app) {
        appRepository = AppRepository.getInstance(app);

        Api api = retrofit().create(Api.class);
        Call<List<App>> call = api.getApp();

        call.enqueue(new retrofit2.Callback<List<App>>() {
            @Override
            public void onResponse(Call<List<App>> call, Response<List<App>> response) {
                if(response.isSuccessful()) {
                    Gson gson = new Gson();
                    String responseGson = gson.toJson(response.body());
                    Log.d("Siu", responseGson);
                    appRepository.insert(response.body());
                }
                else{
                    Log.e("main", "onFailure: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<App>> call, Throwable t) {
                Log.e("main", "onFailure: " + t.getMessage());
            }
        });
    }

    public void fetchMedia (Application app) {
        mediaRepository = MediaRepository.getInstance(app);

        Api api = retrofit().create(Api.class);
        Call<List<Media>> call = api.getMedias();
        call.enqueue(new retrofit2.Callback<List<Media>>() {
            @Override
            public void onResponse(Call<List<Media>> call, Response<List<Media>> response) {
                if(response.isSuccessful()) {
                    Gson gson = new Gson();
                    String responseGson = gson.toJson(response.body());
                    Log.d("Siuu", responseGson);
                    mediaRepository.insert(response.body());
                }
                else{
                    Log.e("Siu", "onFailure: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Media>> call, Throwable t) {
                Log.e("Siu", "onFailure: " + t.getMessage());
            }
        });
    }

    public void fetchPins (Application app) {
        pinRepository = PinRepository.getInstance(app);

        Api api = retrofit().create(Api.class);
        Call<List<Pin>> call = api.getPins(getCookies(app));
        call.enqueue(new retrofit2.Callback<List<Pin>>() {
            @Override
            public void onResponse(Call<List<Pin>> call, Response<List<Pin>> response) {
                if(response.isSuccessful()) {
                    Gson gson = new Gson();
                    String responseGson = gson.toJson(response.body());
                    Log.d("Siuu", responseGson);
                    pinRepository.insert(response.body());
                }
                else{
                    Log.e("Siu", "onFailure: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Pin>> call, Throwable t) {
                Log.e("Siu", "onFailure: " + t.getMessage());
            }
        });
    }

    public void fetchTrails (Application app) {
        trailRepository = TrailRepository.getInstance(app);

        Api api = retrofit().create(Api.class);
        Call<List<Trail>> call = api.getTrails();
        call.enqueue(new retrofit2.Callback<List<Trail>>() {
            @Override
            public void onResponse(Call<List<Trail>> call, Response<List<Trail>> response) {
                if(response.isSuccessful()) {
                    Gson gson = new Gson();
                    String responseGson = gson.toJson(response.body());
                    Log.d("Siu", responseGson);
                    trailRepository.insert(response.body());
                }
                else{
                    Log.e("main", "onFailure: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Trail>> call, Throwable t) {
                Log.e("main", "onFailure: " + t.getMessage());
            }
        });
    }

    public void loginRequest (Application app, Activity activity, String user, String pass, String authtoken) {
        sessionManager = SessionManager.getInstance(app);

        Api api = retrofit().create(Api.class);
        Call<Void> call = api.login(new UserData(user, pass), authtoken);

        call.enqueue(new retrofit2.Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if(response.isSuccessful()) {;
                    List<String> cookies = new ArrayList<>();
                    Headers headers = response.headers();

                    for(int i=0; i<headers.size(); i++) {
                        String header_name = headers.name(i);
                        if (header_name.equalsIgnoreCase("set-cookie")) {
                            String cookie = headers.value(i);
                            cookie = cookie.substring(0, cookie.indexOf(";"));
                            cookies.add(cookie);
                        }
                    }

                    sessionManager.addCookies(user, pass, cookies);
                    createLoginSession(app);
                    // sessionManager.logCookies();

                    Toast.makeText(activity, "Sucessfully Logged in.", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                }
                else{
                    Toast.makeText(activity, "Invalid credentials.", Toast.LENGTH_SHORT).show();
                    Log.e("Siu", "onFailure: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(activity, "Failure trying to login", Toast.LENGTH_SHORT).show();
                Log.e("Siu", "onFailure: " + t.getMessage());
            }
        });
    }

    public void createLoginSession(Application app) {
        Api api = retrofit().create(Api.class);
        Call<User> call = api.getUser(getCookies(app));
        call.enqueue(new retrofit2.Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    User user = response.body();
                    UserRepository userRepository = UserRepository.getInstance(app);
                    userRepository.insert(user);
                    userRepository.addUser(user.getUsername());

                    sessionManager.getEditor().putString("usertype", user.getUser_type());
                    sessionManager.getEditor().commit();
                    // sessionManager.logCookies();
                }
                else{
                    Log.e("Siu", "onFailure: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Siu", "onFailure: " + t.getMessage());
            }
        });
    }

    public void logoutRequest(Application app) {
        sessionManager = SessionManager.getInstance(app);

        if (sessionManager.getSharedPreferences().getString("isLoggedIn", "false").equals("true")) {
            Api api = retrofit().create(Api.class);
            Call<Void> call = api.logout(getCookies(app));
            call.enqueue(new retrofit2.Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()) {
                        sessionManager.getEditor().clear().apply();

                    } else {
                        Log.e("SessionManager", "Logout failure");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("SessionManager", "Logout error: " + t.getMessage());
                }
            });
        } else {
            Log.d("SessionManager", "User is not logged in!");
        }
    }

    public String getUrl () {
        return BASE_URL;
    }
}
