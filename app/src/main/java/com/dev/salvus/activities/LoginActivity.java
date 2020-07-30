package com.dev.salvus.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dev.salvus.MainActivity;
import com.dev.salvus.R;
import com.dev.salvus.databinding.ActivityLoginBinding;
import com.dev.salvus.util.Preferences;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    Preferences preferences;
    String phone,password;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        preferences = new Preferences(this);
        if(!preferences.getPhone().equals("")) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    phone = binding.inputPhone.getText().toString();
                    password = binding.inputPassword.getText().toString();
                    preferences.setPhone(phone);
                    preferences.setPassword(password);
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();

                }
                catch(Exception e) {
                    Log.e(TAG,"Login Button Clicked : "+e);
                }
            }
        });

        binding.tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }
}