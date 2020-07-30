package com.dev.salvus.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.dev.salvus.MainActivity;
import com.dev.salvus.R;
import com.dev.salvus.databinding.ActivityHomeBinding;
import com.dev.salvus.util.Preferences;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        preferences = new Preferences(this);
        if(preferences.getPhone().equals("")) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        binding.policecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(binding.parentview,"Your report has been filed.", BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });

        binding.ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(binding.parentview,"Help is on the way.", BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });

        binding.firetruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(binding.parentview,"Authorities are informed. Help will reach you soon.", BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                preferences.setPhone("");
                preferences.setPassword("");
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();

            }
        });

        binding.feelingUnsafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(HomeActivity.this);
                dialog.setContentView(R.layout.dialog_report_this_place);
                Window window = dialog.getWindow();
                window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                dialog.setTitle("Report this place");

                dialog.findViewById(R.id.reportcancel_button).setOnClickListener(v1 -> {
                    dialog.cancel();
                });

                dialog.findViewById(R.id.reportnow_button).setOnClickListener(v1 -> {
                    Snackbar.make(dialog.findViewById(R.id.parent_dialogview),"Please fill the form sent to your registered email address.", BaseTransientBottomBar.LENGTH_LONG).show();
                });

                dialog.findViewById(R.id.reportlater_button).setOnClickListener(v1 -> {
                    Snackbar.make(dialog.findViewById(R.id.parent_dialogview),"Please fill the form sent to your registered email address.", BaseTransientBottomBar.LENGTH_LONG).show();
                });

                dialog.show();
            }
        });


    }
}