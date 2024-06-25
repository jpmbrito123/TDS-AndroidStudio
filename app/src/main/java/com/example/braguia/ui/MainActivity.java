package com.example.braguia.ui;

import static android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.braguia.R;
import com.example.braguia.data.App;
import com.example.braguia.utils.ApiService;
import com.example.braguia.utils.GeofenceManager;
import com.example.braguia.utils.PermissionManager;
import com.example.braguia.utils.SessionManager;
import com.example.braguia.viewmodel.AppViewModel;
import com.example.braguia.viewmodel.PinViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    private GeofenceManager geofenceManager;
    private PermissionManager permissionManager;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        sessionManager = SessionManager.getInstance(getApplication());
        permissionManager = PermissionManager.getInstance(getApplication());
        geofenceManager = GeofenceManager.getInstance(this);

        DrawerLayout main = findViewById(R.id.main);
        navigationView = findViewById(R.id.navigation_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        Button appButton = findViewById(R.id.app_button);
        TextView nameView = findViewById(R.id.app_name);
        TextView descView = findViewById(R.id.app_desc);
        TextView landView = findViewById(R.id.app_page_text);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, main, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        main.addDrawerListener(toggle);
        main.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {
                if (newState == DrawerLayout.STATE_SETTLING) {
                    updateNavigationMenu();
                }
            }
        });

        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
                startActivity(intent);
            }
        });

        permissionManager.requestPermissions(this, getApplicationContext());
        if (!permissionManager.checkGoogleMapsInstallation(this)) {
            permissionManager.promptToInstallGoogleMaps(this, getApplicationContext());
        }

        AppViewModel appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        appViewModel.getAppInfo().observe(this, app -> {
            App appInfo = app.get(0);

            nameView.setText(appInfo.getApp_name());
            descView.setText(appInfo.getApp_desc());
            landView.setText(appInfo.getApp_landing_page_text());
        });
        appButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrailActivity.class);
                startActivity(intent);
            }
        });

        PinViewModel pinViewModel = new ViewModelProvider(this).get(PinViewModel.class);
        pinViewModel.getAllPins().observe(this, pins -> {
            geofenceManager.createGeofences(pins);
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        geofenceManager.removeGeofences(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout main = findViewById(R.id.main);
        if (main.isDrawerOpen(GravityCompat.START)) {
            main.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.history) {
            if (sessionManager.getSharedPreferences().getString("isLoggedIn", "false").equals("true")) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "You are not logged in!", Toast.LENGTH_SHORT).show();
            }
        }
        else if (menuItem.getItemId() == R.id.location_settings) {
            Intent intent = new Intent(ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        else if (menuItem.getItemId() == R.id.location_service_settings) {
            Intent intent = new Intent(MainActivity.this, LocationServiceActivity.class);
            startActivity(intent);
        }
        else if (menuItem.getItemId() == R.id.login) {
            if (sessionManager.getSharedPreferences().getString("isLoggedIn", "false").equals("true")) {
                Toast.makeText(this, "You must log out first!", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
        else if (menuItem.getItemId() == R.id.logout) {
            if (sessionManager.getSharedPreferences().getString("isLoggedIn", "false").equals("true")) {
                ApiService.getInstance(getApplication()).logoutRequest(getApplication());
                Toast.makeText(this, "Successfully logged out", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "You are not logged in!", Toast.LENGTH_SHORT).show();
            }
        }
        else if (menuItem.getItemId() == R.id.media) {
            if (!sessionManager.getSharedPreferences().getString("isLoggedIn", "false").equalsIgnoreCase("true")) {
                Toast.makeText(this, "You are not logged in and you must be a premium user!", Toast.LENGTH_SHORT).show();
            }
            else if (!sessionManager.getSharedPreferences().getString("usertype", "normal").equalsIgnoreCase("premium")) {
                Toast.makeText(getApplicationContext(), "You must be a premium user", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, MediaActivity.class);
                startActivity(intent);
            }
        }
        else if (menuItem.getItemId() == R.id.maps) {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
        }
        else if (menuItem.getItemId() == R.id.pins) {
            Intent intent = new Intent(MainActivity.this, PinActivity.class);
            startActivity(intent);
        }
        else if (menuItem.getItemId() == R.id.profile) {
            if (sessionManager.getSharedPreferences().getString("isLoggedIn", "false").equals("true")) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "You are not logged in!", Toast.LENGTH_SHORT).show();
            }
        }
        else if (menuItem.getItemId() == R.id.trails) {
            Intent intent = new Intent(MainActivity.this, TrailActivity.class);
            startActivity(intent);
        }
        return true;
    }

    private void updateNavigationMenu() {
        Menu menu = navigationView.getMenu();
        MenuItem login = menu.findItem(R.id.login);
        MenuItem logout = menu.findItem(R.id.logout);

        String isLoggedIn = sessionManager.getSharedPreferences().getString("isLoggedIn", "false");

        if (isLoggedIn.equalsIgnoreCase("true") && login != null) {
            login.setVisible(false);
            login.setEnabled(false);
            logout.setVisible(true);
            logout.setEnabled(true);
        } else if (!isLoggedIn.equalsIgnoreCase("true") && login != null) {
            login.setVisible(true);
            login.setEnabled(true);
            logout.setVisible(false);
            logout.setEnabled(false);
        }
    }
}