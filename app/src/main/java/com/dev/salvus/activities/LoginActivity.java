package com.dev.salvus.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dev.salvus.MainActivity;
import com.dev.salvus.R;
import com.dev.salvus.databinding.ActivityLoginBinding;
import com.dev.salvus.util.Preferences;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    Preferences preferences;
    String phone,password,id;
    RequestQueue requestQueue;
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

        requestQueue = Volley.newRequestQueue(this);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    phone = binding.inputPhone.getText().toString();
                    password = binding.inputPassword.getText().toString();

                    String url = "https://team-ajax.herokuapp.com/login";
                    Map<String, String> jsonParams = new HashMap<String, String>();

                    jsonParams.put("username", phone);
                    jsonParams.put("password", password);
                    JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, url,

                            new JSONObject(jsonParams),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try{
                                            id = response.getJSONObject("success").get("_id").toString();
                                            preferences.setId(id);
                                            preferences.setPhone(phone);
                                            preferences.setPassword(password);
                                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                            finish();

                                    }
                                    catch(Exception e)
                                    {
                                        //Toast.makeText(LoginActivity.this, "Error : "+e, Toast.LENGTH_SHORT).show();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //Toast.makeText(LoginActivity.this, "ErrorResponse : "+error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            headers.put("X-Requested-With","XMLHttpRequest");
                            return headers;
                        }
                    };
                    requestQueue.add(postRequest);
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
                finish();
            }
        });

    }
}