package com.dev.salvus.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.dev.salvus.MainActivity;
import com.dev.salvus.R;
import com.dev.salvus.util.Preferences;

public class HomeActivity extends AppCompatActivity {

    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        preferences = new Preferences(this);
        if(!preferences.getPhone().equals("")) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }
}