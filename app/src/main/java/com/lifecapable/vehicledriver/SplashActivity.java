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
import android.util.Log;
import android.widget.Toast;

import com.lifecapable.vehicledriver.Driver.activities.DriverBottomActivity;
import com.lifecapable.vehicledriver.owner.activities.ExpiredActivity;
import com.lifecapable.vehicledriver.owner.activities.OwnerLeftNavActivity;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences owner,driver;
    boolean isOwner,isDriver;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        owner = getSharedPreferences("owner",MODE_PRIVATE);
        id = owner.getInt("id",0);
        driver = getSharedPreferences("driver",MODE_PRIVATE);

        isOwner = owner.getBoolean("login",false);
        isDriver = driver.getBoolean("login",false);

        try {
            validate();
        } catch (Exception e) {
            Toast.makeText(SplashActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void init(){
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
        },1000);
    }

    private void validate() throws Exception{
        if(id == 0){
            init();
            return;
        }
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<Map> call = o.checkValidity(id);
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if(response.isSuccessful()){
                    Map<String,Boolean> map = response.body();
                    boolean isValid = map.get("message");
                    if(!isValid){
                        Toast.makeText(SplashActivity.this, "Subscription Expired", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SplashActivity.this, ExpiredActivity.class));
                        return;
                    }else{
                        init();
                    }
                    Log.i("Valid",String.valueOf(map.get("message")));
                }else{
                    Toast.makeText(SplashActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}