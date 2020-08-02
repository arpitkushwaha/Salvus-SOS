package com.dev.salvus.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import com.dev.salvus.databinding.ActivityRegisterBinding;
import com.dev.salvus.util.Preferences;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    Preferences preferences;
    RequestQueue requestQueue;
    String name,email,password,phone,age,gender,address,number1,number2,email1,email2;
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

        requestQueue = Volley.newRequestQueue(this);
        String[] items = new String[]{"Female", "Male", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        binding.inputGender.setAdapter(adapter);

        binding.inputGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(position==0)
                   gender="Female";
               else if(position==1)
                   gender="Male";
               else
                   gender="Other";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = binding.inputName.getText().toString();
                preferences.setName(name);
                email = binding.inputEmail.getText().toString();
                preferences.setEmail(email);
                password = binding.inputPassword.getText().toString();
                preferences.setPassword(password);
                age = binding.inputAge.getText().toString();
                preferences.setAge(age);
                number1 = binding.inputNumber1.getText().toString();
                preferences.setNumber1(number1);
                number2 = binding.inputNumber2.getText().toString();
                preferences.setNumber2(number2);
                address = binding.inputAddress.getText().toString();
                preferences.setAddress(address);
                email1 = binding.inputEmail1.getText().toString();
                preferences.setEmail1(email1);
                email2 = binding.inputEmail2.getText().toString();
                preferences.setEmail2(email2);
                phone = binding.inputPhone.getText().toString();
                //preferences.setPhone(phone);

                String url = "https://team-ajax.herokuapp.com/register";
                JSONObject contact1,contact2;
                contact1 = new JSONObject();
                contact2 = new JSONObject();
                try{
                    contact1.put("phone", number1);
                    contact1.put("email", email1);
                    contact2.put("phone", number2);
                    contact2.put("email", email2);
                }
                catch(Exception e)
                {
                    Log.e(TAG,""+e);
                }

                Map<String, Object> jsonParams = new HashMap<String, Object>();

                jsonParams.put("username", phone);
                jsonParams.put("password", password);
                jsonParams.put("email", email);
                jsonParams.put("age", age);
                jsonParams.put("gender", gender);
                jsonParams.put("address", address);
                jsonParams.put("email", email);
                jsonParams.put("age", age);
                jsonParams.put("contact1", contact1);
                jsonParams.put("contact2", contact2);
                Snackbar.make(binding.cardView,"Wait...", BaseTransientBottomBar.LENGTH_LONG).show();

                JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, url,

                        new JSONObject(jsonParams),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //   Handle Error
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
        });

//        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                finish();
//            }
//        });



        
    }
}