package com.lifecapable.vehicledriver.owner.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.datamodel.LoginOwnerData;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OwnerLoginActivity extends AppCompatActivity {

    Button loginbt;
    EditText emailet,passet;
    String email,pass;
    OwnerJsonPlaceHolder loginPlaceHolder;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_activity_login);
        loginbt = findViewById(R.id.loginownerbt);
        emailet = findViewById(R.id.oemailet);
        passet = findViewById(R.id.opasswordet);

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loginbt.setOnClickListener(v -> login());

    }

    private void login(){
        email = emailet.getText().toString();
        pass = passet.getText().toString();
        loginPlaceHolder = retrofit.create(OwnerJsonPlaceHolder.class);
        Call<LoginOwnerData> call = loginPlaceHolder.ogetLogin(email,pass);

        call.enqueue(new Callback<LoginOwnerData>() {
            @Override
            public void onResponse(Call<LoginOwnerData> call, Response<LoginOwnerData> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(OwnerLoginActivity.this, "Somethings Wrong I can feel it"+response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                LoginOwnerData res = response.body();
                Toast.makeText(OwnerLoginActivity.this, "Email: "+res.getEmail()+"\n full name: "+res.getName()+"\n phone: "+res.getMobile(), Toast.LENGTH_LONG).show();
                Intent i=new Intent(OwnerLoginActivity.this,OwnerLeftNavActivity.class);
                startActivity(i);

            }

            @Override
            public void onFailure(Call<LoginOwnerData> call, Throwable t) {
                Toast.makeText(OwnerLoginActivity.this, "Somethings Wrong I can feel it"+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}