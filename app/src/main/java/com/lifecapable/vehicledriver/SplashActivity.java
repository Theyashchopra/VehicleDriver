package com.lifecapable.vehicledriver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Toast;

import com.lifecapable.vehicledriver.Driver.activities.DriverBottomActivity;
import com.lifecapable.vehicledriver.owner.activities.OwnerLeftNavActivity;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences owner,driver;
    boolean isOwner,isDriver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        owner = getSharedPreferences("owner",MODE_PRIVATE);
        driver = getSharedPreferences("driver",MODE_PRIVATE);

        isOwner = owner.getBoolean("login",false);
        isDriver = driver.getBoolean("login",false);


        new Handler(Looper.myLooper(), message -> {
            Toast.makeText(SplashActivity.this,message.toString(),Toast.LENGTH_SHORT).show();
            return false;
        }).postDelayed(() -> {
            if(isOwner){
                startActivity(new Intent(SplashActivity.this, OwnerLeftNavActivity.class));
            }else if(isDriver){
                startActivity(new Intent(SplashActivity.this, DriverBottomActivity.class));
            }else{
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }
        },2500);
    }
}