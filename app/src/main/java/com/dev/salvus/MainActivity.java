package com.dev.salvus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.dev.salvus.activities.HomeActivity;
import com.dev.salvus.activities.LoginActivity;
import com.dev.salvus.util.Preferences;

public class MainActivity extends AppCompatActivity {

    Preferences preferences;
    private static int TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportActionBar().hide();

        preferences = new Preferences(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(!preferences.getPhone().equals("")) {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                }
                else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                finish();

            }
        }, TIME_OUT);

    }
}