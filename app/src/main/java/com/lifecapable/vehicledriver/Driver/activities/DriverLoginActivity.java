package com.lifecapable.vehicledriver.Driver.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lifecapable.vehicledriver.R;

import org.xmlpull.v1.sax2.Driver;

public class DriverLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);
        findViewById(R.id.logindriverbt).setOnClickListener(v ->
                startActivity(new Intent(DriverLoginActivity.this, DriverBottomActivity.class)));
    }
}