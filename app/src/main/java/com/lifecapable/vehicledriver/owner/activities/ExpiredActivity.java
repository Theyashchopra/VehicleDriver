package com.lifecapable.vehicledriver.owner.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.dialogs.LogoutPopup;

public class ExpiredActivity extends AppCompatActivity {

    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expired);

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogoutPopup lg = new LogoutPopup();
                lg.show(getSupportFragmentManager(),"logout");
            }
        });
    }
}