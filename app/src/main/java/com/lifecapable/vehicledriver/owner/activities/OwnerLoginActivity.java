package com.lifecapable.vehicledriver.owner.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.LoginOwnerData;
import com.lifecapable.vehicledriver.owner.dialogs.LoginSuccessPopup;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OwnerLoginActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button loginbt,register;
    EditText emailet,passet;
    String email,pass;
    SharedPreferences owner;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_activity_login);
        loginbt = findViewById(R.id.loginownerbt);
        register = findViewById(R.id.redirect);
        emailet = findViewById(R.id.oemailet);
        passet = findViewById(R.id.opasswordet);
        progressBar = findViewById(R.id.progress_logino);
        owner = getSharedPreferences("owner",MODE_PRIVATE);
        editor = owner.edit();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OwnerLoginActivity.this,MobileNumberActivity.class));
            }
        });

        loginbt.setOnClickListener(v -> {
            try {
                login();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void login() {
        email = emailet.getText().toString();
        pass = passet.getText().toString();
        if(email.isEmpty()){
            YoYo.with(Techniques.Shake)
                    .duration(2000)
                    .playOn(emailet);
            emailet.setError("Cannot be empty");
            emailet.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(emailet, InputMethodManager.SHOW_IMPLICIT);
            return;
        }else if(pass.isEmpty()){
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
        Call<LoginOwnerData> call = RestAdapter.createAPI().ogetLogin(email,pass);

        call.enqueue(new Callback<LoginOwnerData>() {
            @Override
            public void onResponse(@NotNull Call<LoginOwnerData> call, @NotNull Response<LoginOwnerData> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(OwnerLoginActivity.this, "Somethings Wrong I can feel it"+response.message(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                LoginOwnerData res = response.body();
                progressBar.setVisibility(View.INVISIBLE);
                try {
                    if (res.getEmail() != null) {
                        editor.putBoolean("login",true);
                        editor.apply();
                        editor.putInt("id",res.getId());
                        editor.apply();
                        //Toast.makeText(OwnerLoginActivity.this, "Email: " + res.getEmail() + "\n full name: " + res.getName() + "\n phone: " + res.getMobile(), Toast.LENGTH_LONG).show();
                        LoginSuccessPopup lg = new LoginSuccessPopup();
                        lg.show(getSupportFragmentManager(),"login");
                    }else{
                        try{
                            Toast.makeText(OwnerLoginActivity.this,res.getMessage(), Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Toast.makeText(OwnerLoginActivity.this, "Somethings Wrong I can feel it"+response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(OwnerLoginActivity.this, "Somethings Wrong I can feel it"+response.message(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<LoginOwnerData> call, Throwable t) {
                Toast.makeText(OwnerLoginActivity.this, "Somethings Wrong I can feel it"+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}