package com.lifecapable.vehicledriver.owner.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textfield.TextInputEditText;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.dialogs.RegisterSuccessPopup;

public class OTPActivity extends AppCompatActivity {

    TextInputEditText pin;
    Button done;
    ProgressBar progressBar;
    String mobile,otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_activity_o_t_p);

        init();
        Intent intent = getIntent();
        otp = intent.getStringExtra("OTP");
        mobile = intent.getStringExtra("mobile");
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticate();
            }
        });
    }

    private void init(){
        pin = findViewById(R.id.pinNumberEt);
        done = findViewById(R.id.done);
        progressBar = findViewById(R.id.progressbar);
    }

    private void authenticate(){
        String one = pin.getText().toString().trim();
        if(otp == null || otp.isEmpty() || mobile == null ||mobile.isEmpty()){
            Toast.makeText(OTPActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        if(one.isEmpty()){
            Toast.makeText(this, "Please Enter OTP first", Toast.LENGTH_SHORT).show();
            pin.setError("Cannot be empty");
            YoYo.with(Techniques.Shake)
                    .duration(2000)
                    .playOn(pin);
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        if(one.equals(String.valueOf(otp))){
            //start activity

            Intent intent = new Intent(OTPActivity.this, OwnerRegisterActivity.class);
            intent.putExtra("mobile",mobile);
            startActivity(intent);
        }else{
            progressBar.setVisibility(View.INVISIBLE);
            pin.setError("Invalid OTP");
            YoYo.with(Techniques.Shake)
                    .duration(2000)
                    .playOn(pin);
            Toast.makeText(this, "OTP is Invalid", Toast.LENGTH_SHORT).show();
        }
    }
}