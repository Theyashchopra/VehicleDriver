package com.lifecapable.vehicledriver.owner.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.lifecapable.vehicledriver.R;

public class OwnerLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_activity_login);
        findViewById(R.id.loginownerbt).setOnClickListener(v ->
                startActivity(new Intent(OwnerLoginActivity.this,OwnerLeftNavActivity.class)));
    }
}