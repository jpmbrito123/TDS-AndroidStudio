package com.example.braguia.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.braguia.R;
import com.example.braguia.data.App;
import com.example.braguia.ui.adapters.ContactRecyclerViewAdapter;
import com.example.braguia.ui.adapters.PartnerRecyclerViewAdapter;
import com.example.braguia.ui.adapters.SocialRecyclerViewAdapter;
import com.example.braguia.viewmodel.AppViewModel;

public class ContactsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contacts);

        AppViewModel appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        appViewModel.getAppInfo().observe(this, app -> {
            App appInfo = app.get(0);

            int socials = appInfo.getSocials().size();
            if (socials > 0) {
                RecyclerView recyclerView = findViewById(R.id.app_socials);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(new SocialRecyclerViewAdapter(appInfo.getSocials()));
            }

            int contacts = appInfo.getContacts().size();
            if (contacts > 0) {
                RecyclerView recyclerView = findViewById(R.id.app_contacts);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(new ContactRecyclerViewAdapter(appInfo.getContacts(), getApplicationContext()));
            }

            int partners = appInfo.getPartners().size();
            if (partners > 0) {
                RecyclerView recyclerView = findViewById(R.id.app_partners);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(new PartnerRecyclerViewAdapter(appInfo.getPartners(), getApplicationContext()));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
