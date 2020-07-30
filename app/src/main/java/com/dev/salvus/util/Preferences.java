package com.dev.salvus.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    Context context;
    String name;
    String phone;
    String password;

    public Preferences(Context context) {
        this.context = context;
    }

    public String getName() {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        name = pref.getString("name","");
        return name;
    }

    public void setName(String name) {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString("name",name);
        edit.commit();
        this.name = name;
    }

    public String getPhone() {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        phone = pref.getString("phone","");
        return phone;
    }

    public void setPhone(String phone) {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString("phone",phone);
        edit.commit();
        this.phone = phone;
    }

    public String getPassword() {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        password = pref.getString("password","");
        return password;
    }

    public void setPassword(String password) {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString("password",password);
        edit.commit();
        this.password = password;
    }
}
