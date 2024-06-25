package com.example.braguia.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.braguia.R;
import com.example.braguia.utils.ApiService;
import com.example.braguia.utils.SessionManager;

import java.io.UnsupportedEncodingException;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUser;
    EditText editTextPassword;
    Button confirmButton;
    TextView textView;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = SessionManager.getInstance(getApplication());
        SharedPreferences sp = sessionManager.getSharedPreferences();

        if (sp.getString("isLoggedIn", "false").equals("true")) {
            // User is already logged in
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            // User is not logged in
            setContentView(R.layout.activity_login);

            confirmButton = findViewById(R.id.ConfirmButton);
            editTextUser = findViewById(R.id.inputUser);
            editTextPassword = findViewById(R.id.inputPass);
            textView = findViewById(R.id.textView);
            confirmButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    String user = editTextUser.getText().toString();
                    String pass = editTextPassword.getText().toString();
                    String authtoken = createAuthToken(user, pass);

                    ApiService.getInstance(getApplication()).loginRequest(getApplication(), LoginActivity.this, user, pass, authtoken);
                }

            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sp = sessionManager.getSharedPreferences();
        if (sp.getString("isLoggedIn", "false").equals("true")) {  // User is already logged in
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private String createAuthToken( String user, String password){
        byte [] data = new byte[0];
        try{
            data = (user + ":" +  password).getBytes("UTF-8");

        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "Basic "+ Base64.encodeToString(data, Base64.NO_WRAP);
    }
}
