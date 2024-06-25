package com.example.braguia.utils;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.List;

public class SessionManager {
    private static SessionManager instance;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String user_type = "usertype";
    private String isLogged = "isLoggedIn";
    private String user = "username";
    private String pass = "password";
    private String session_id = "sessionid";
    private String csrftoken = "csrftoken";

    public SessionManager(Context context) {
        preferences = context.getSharedPreferences("shared preferences", MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static synchronized SessionManager getInstance(Application app) {

        if (instance == null) {
            instance = new SessionManager(app);
        }
        return instance;
    }

    public void addCookies(String username, String password, List<String> cookies) {
        editor.putString(user_type, "");

        editor.putString(isLogged, "true");
        editor.putString(user, username);
        editor.putString(pass, password);

        if (cookies.size() == 2) {
            editor.putString(csrftoken, cookies.get(0));
            editor.putString(session_id, cookies.get(1));
        }

        editor.commit();
    }
  
    public SharedPreferences getSharedPreferences() {
        return preferences;
    }

    public SharedPreferences.Editor getEditor () {
        return editor;
    }

    public void logCookies () {
        Log.d("Siuuu", String.format("user_type: %s\nisLogged: %s\nuser: %s\npass: %s\nsessionid: %s\ncsrftoken: %s",
                preferences.getString(user_type, "null"),
                preferences.getString(isLogged, "false"),
                preferences.getString(user, "null"),
                preferences.getString(pass, "null"),
                preferences.getString(session_id, "null"),
                preferences.getString(csrftoken, "null")));
    }

    public String getUsername() {
        return preferences.getString(user, "null");
    }
}
