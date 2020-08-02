package com.dev.salvus.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import com.dev.salvus.databinding.ActivityHomeBinding;
import com.dev.salvus.util.MyService;
import com.dev.salvus.util.Preferences;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    Preferences preferences;
    FusedLocationProviderClient mFusedLocationClient;
    private String latitude,longitude;
    RequestQueue requestQueue;
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

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        requestQueue = Volley.newRequestQueue(this);


        //Runtime Permissions
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                getLastLocation();
            }
            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Log.e(TAG, "onError: DexterPermissionError occured: "+error.toString() );
            }
        }).onSameThread()
                .check();


        service();

        binding.policecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://team-ajax.herokuapp.com/emergency";
                Map<String, String> jsonParams = new HashMap<String, String>();

                jsonParams.put("user", preferences.getId());
                jsonParams.put("lat", latitude);
                jsonParams.put("lon", longitude);
                jsonParams.put("emergency", "police");

                JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, url,

                        new JSONObject(jsonParams),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Snackbar.make(binding.parentview,"Your report has been filed. "+latitude+":"+longitude, BaseTransientBottomBar.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //   Handle Error
                                Snackbar.make(binding.parentview,"Error", BaseTransientBottomBar.LENGTH_LONG).show();
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

        binding.ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://team-ajax.herokuapp.com/emergency";
                Map<String, String> jsonParams = new HashMap<String, String>();

                jsonParams.put("user", preferences.getId());
                jsonParams.put("lat", latitude);
                jsonParams.put("lon", longitude);
                jsonParams.put("emergency", "medical");

                JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, url,

                        new JSONObject(jsonParams),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Snackbar.make(binding.parentview,"Help is on the way. "+latitude+":"+longitude, BaseTransientBottomBar.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //   Handle Error
                                Snackbar.make(binding.parentview,"Error", BaseTransientBottomBar.LENGTH_LONG).show();
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


        binding.firetruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://team-ajax.herokuapp.com/emergency";
                Map<String, String> jsonParams = new HashMap<String, String>();

                jsonParams.put("user", preferences.getId());
                jsonParams.put("lat", latitude);
                jsonParams.put("lon", longitude);
                jsonParams.put("emergency", "fire");

                JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, url,

                        new JSONObject(jsonParams),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Snackbar.make(binding.parentview,"Authorities are informed. Help will reach you soon."+latitude+":"+longitude, BaseTransientBottomBar.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //   Handle Error
                                Snackbar.make(binding.parentview,"Error", BaseTransientBottomBar.LENGTH_LONG).show();
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

                    String url = "https://team-ajax.herokuapp.com/emergency";
                    Map<String, String> jsonParams = new HashMap<String, String>();

                    jsonParams.put("user", preferences.getId());
                    jsonParams.put("lat", latitude);
                    jsonParams.put("lon", longitude);
                    jsonParams.put("emergency", "unsafe");

                    JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, url,

                            new JSONObject(jsonParams),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Snackbar.make(dialog.findViewById(R.id.parent_dialogview),"Please fill the form sent to your registered email address. "+latitude+":"+longitude, BaseTransientBottomBar.LENGTH_LONG).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //   Handle Error
                                    Snackbar.make(dialog.findViewById(R.id.parent_dialogview),"Error", BaseTransientBottomBar.LENGTH_LONG).show();
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

                });

                dialog.findViewById(R.id.reportlater_button).setOnClickListener(v1 -> {

                    String url = "https://team-ajax.herokuapp.com/emergency";
                    Map<String, String> jsonParams = new HashMap<String, String>();

                    jsonParams.put("user", preferences.getId());
                    jsonParams.put("lat", latitude);
                    jsonParams.put("lon", longitude);
                    jsonParams.put("emergency", "unsafe");

                    JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, url,

                            new JSONObject(jsonParams),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Snackbar.make(dialog.findViewById(R.id.parent_dialogview),"Please fill the form sent to your registered email address. "+latitude+":"+longitude, BaseTransientBottomBar.LENGTH_LONG).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //   Handle Error
                                    Snackbar.make(dialog.findViewById(R.id.parent_dialogview),"Error", BaseTransientBottomBar.LENGTH_LONG).show();
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
                    preferences.setId("");
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


    //Location
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    latitude = location.getLatitude()+"";
                                    longitude = location.getLongitude()+"";
                                    preferences.setLatitude(latitude);
                                    preferences.setLongitude(longitude);
                                    Snackbar.make(binding.parentview,"Latitude:"+latitude+"\nLongitude:"+longitude, BaseTransientBottomBar.LENGTH_LONG).show();

                                }
                            }
                        }
                );
            } else {
                Snackbar.make(binding.parentview,"Turn on your GPS.", BaseTransientBottomBar.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitude = mLastLocation.getLatitude()+"";
            longitude = mLastLocation.getLongitude()+"";
        }
    };

}