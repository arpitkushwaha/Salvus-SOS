package com.dev.salvus.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    Context context;
    String name,id;
    String phone;
    String email,age,gender,number1,number2,email1,email2,address;
    String password;
    String latitude;
    String longitude;

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

    public String getId() {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        id = pref.getString("id","");
        return id;
    }

    public void setId(String id) {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString("id",id);
        edit.commit();
        this.id = id;
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

    public String getLatitude() {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        latitude = pref.getString("latitude","");
        return latitude;
    }

    public void setLatitude(String latitude) {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString("latitude",latitude);
        edit.commit();
        this.latitude = latitude;
    }

    public String getLongitude() {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        longitude = pref.getString("longitude","");
        return longitude;
    }

    public void setLongitude(String longitude) {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString("longitude",longitude);
        edit.commit();
        this.longitude = longitude;
    }

    public String getEmail() {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        email = pref.getString("email","");
        return email;
    }

    public void setEmail(String email) {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString("email",email);
        edit.commit();
        this.email = email;
    }

    public String getAge() {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        age = pref.getString("age","");
        return age;
    }

    public void setAge(String age) {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString("age",age);
        edit.commit();
        this.age = age;
    }

    public String getGender() {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        gender = pref.getString("gender","");
        return gender;
    }

    public void setGender(String gender) {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString("gender",gender);
        edit.commit();
        this.gender = gender;
    }

    public String getAddress() {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        address = pref.getString("address","");
        return address;
    }

    public void setAddress(String address) {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString("address",address);
        edit.commit();
        this.address = address;
    }

    public String getEmail1() {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        email1 = pref.getString("email1","");
        return email1;
    }

    public void setEmail1(String email1) {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString("email1",email1);
        edit.commit();
        this.email1 = email1;
    }

    public String getEmail2() {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        email2 = pref.getString("email2","");
        return email2;
    }

    public void setEmail2(String email2) {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString("email2",email2);
        edit.commit();
        this.email2 = email2;
    }

    public String getNumber1() {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        number1 = pref.getString("number1","");
        return number1;
    }

    public void setNumber1(String number1) {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString("number1",number1);
        edit.commit();
        this.number1 = number1;
    }

    public String getNumber2() {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        number2 = pref.getString("number2","");
        return number2;
    }

    public void setNumber2(String number2) {

        SharedPreferences pref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=pref.edit();
        edit.putString("number2",number2);
        edit.commit();
        this.number2 = number2;
    }
}
