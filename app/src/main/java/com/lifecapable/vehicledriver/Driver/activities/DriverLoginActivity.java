package com.lifecapable.vehicledriver.Driver.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.lifecapable.vehicledriver.Driver.adapters.RestAdapter;
import com.lifecapable.vehicledriver.Driver.datamodels.DriverData;
import com.lifecapable.vehicledriver.Driver.dialogs.DriverLoginSuccessPoppup;
import com.lifecapable.vehicledriver.R;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverLoginActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button loginbt;
    EditText emailet,passet;
    String email,password;
    SharedPreferences driver;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_activity_login);
        progressBar = findViewById(R.id.progress_logind);
        emailet = findViewById(R.id.dcontactet);
        passet = findViewById(R.id.dpasswordet);
        loginbt = findViewById(R.id.logindriverbt);
        driver = getSharedPreferences("driver",MODE_PRIVATE);
        editor = driver.edit();
        loginbt.setOnClickListener(v ->{
                    login();
                });
    }

    private void login(){
        email = emailet.getText().toString();
        password = passet.getText().toString();
        if(email.isEmpty()){
            YoYo.with(Techniques.Shake)
                    .duration(2000)
                    .playOn(emailet);
            emailet.setError("Cannot be empty");
            emailet.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(emailet, InputMethodManager.SHOW_IMPLICIT);
            return;
        }else if(password.isEmpty()){
            YoYo.with(Techniques.Shake)
                    .duration(2000)
                    .playOn(passet);
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            passet.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(passet, InputMethodManager.SHOW_IMPLICIT);
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        Call<DriverData> call = RestAdapter.createAPI().dgetLogin(email, password);
        call.enqueue(new Callback<DriverData>() {
            @Override
            public void onResponse(@NotNull Call<DriverData> call, @NotNull Response<DriverData> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(DriverLoginActivity.this, "Somethings Wrong I can feel it"+response.message(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                DriverData res = response.body();
                if(res != null){
                    if(res.getEmail() != null) {
                        editor.putBoolean("login", true);
                        editor.apply();
                        editor.putInt("driverid", res.getId());
                        editor.apply();
                        editor.putInt("vehicleid", res.getVehicle_id());
                        editor.apply();
                        Toast.makeText(DriverLoginActivity.this, "Email: " + res.getEmail() + "\n full name: " + res.getName() + "\n phone: " + res.getMobile(), Toast.LENGTH_LONG).show();
                        DriverLoginSuccessPoppup lg = new DriverLoginSuccessPoppup();
                        lg.show(getSupportFragmentManager(), "login");
                    }
                }
                else {
                    Toast.makeText(DriverLoginActivity.this, "Somethings Wrong I can feel it"+response.message(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onFailure(@NotNull Call<DriverData> call, @NotNull Throwable t) {
                Toast.makeText(DriverLoginActivity.this, "Somethings Wrong I can feel it"+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}