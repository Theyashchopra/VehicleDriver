package com.lifecapable.vehicledriver.Driver.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.lifecapable.vehicledriver.R;

public class DriverLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_activity_login);
        findViewById(R.id.logindriverbt).setOnClickListener(v ->
                startActivity(new Intent(DriverLoginActivity.this, DriverBottomActivity.class)));
    }
}