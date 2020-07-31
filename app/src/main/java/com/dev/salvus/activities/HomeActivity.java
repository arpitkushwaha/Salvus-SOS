package com.dev.salvus.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.dev.salvus.MainActivity;
import com.dev.salvus.R;
import com.dev.salvus.databinding.ActivityHomeBinding;
import com.dev.salvus.util.MyService;
import com.dev.salvus.util.Preferences;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    Preferences preferences;
    private static final String TAG = "HomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        preferences = new Preferences(this);
        if(preferences.getPhone().equals("")) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        service();

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


        binding.feelingUnsafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(HomeActivity.this);
                dialog.setContentView(R.layout.dialog_report_this_place);
                Window window = dialog.getWindow();
                window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
                //dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.getWindow().setBackgroundDrawableResource(R.color.translucent_black);

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


        binding.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(HomeActivity.this);
                dialog.setContentView(R.layout.dialog_more);
                Window window = dialog.getWindow();
                window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
                //dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.getWindow().setBackgroundDrawableResource(R.color.translucent_black);

                dialog.setTitle("More");

                dialog.findViewById(R.id.morecancel_button).setOnClickListener(v1 -> {
                    dialog.cancel();
                });


                Button service = dialog.findViewById(R.id.service_button);
                service.setOnClickListener(v1 -> {
                    if (isMyServiceRunning(MyService.class)) {
                    Snackbar.make(dialog.findViewById(R.id.parent_moreview),"Service Stopped", BaseTransientBottomBar.LENGTH_LONG).show();
                    stopService(new Intent(HomeActivity.this, MyService.class));
                    service.setText("Start Service");
                } else {
                    Snackbar.make(dialog.findViewById(R.id.parent_moreview),"Service Started", BaseTransientBottomBar.LENGTH_LONG).show();
                    startService(new Intent(HomeActivity.this, MyService.class));
                    service.setText("Stop Service");
                }
                });


                dialog.findViewById(R.id.logout_button).setOnClickListener(v1 -> {
                    preferences.setPhone("");
                    preferences.setPassword("");
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    finish();
                });

                dialog.show();
            }
        });


    }

    private void service()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1)
        {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            if(keyguardManager!=null)
                keyguardManager.requestDismissKeyguard(this, null);
        }
        else
        {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }
        startService(new Intent(getApplicationContext(), MyService.class));
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        try {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        catch(Exception e){
            Log.e(TAG,"isMyServiceRunning() Exception : "+e);
        }
        return false;
    }

    private void foregroundService()
    {

        Intent intent = new Intent(HomeActivity.this, MyService.class);
        intent.setAction(MyService.ACTION_START_FOREGROUND_SERVICE);
        startService(intent);
    }

}