package com.dev.salvus.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dev.salvus.MainActivity;
import com.dev.salvus.R;
import com.dev.salvus.databinding.ActivityRegisterBinding;
import com.dev.salvus.util.Preferences;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    Preferences preferences;
    String name,email,password,phone,address,number1,number2,bloodgroup;
    private static final String TAG = "`RegisterActivity`";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        preferences = new Preferences(this);
        if(!preferences.getPhone().equals("")) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        
    }
}