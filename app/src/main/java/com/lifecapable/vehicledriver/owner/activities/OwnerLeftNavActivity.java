package com.lifecapable.vehicledriver.owner.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import com.lifecapable.vehicledriver.MainActivity;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.SplashActivity;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.ProfileOwnerData;
import com.lifecapable.vehicledriver.owner.dialogs.LogoutPopup;
import com.lifecapable.vehicledriver.owner.dialogs.ReminderPopup;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerLeftNavActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    View navHeaderView;
    ImageView headerImage;
    TextView headerTV1,headerTV2;
    NavController navController;
    SharedPreferences sharedPreferences;
    int id;
    public static Double daysLeft;

    String email, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_activity_left_nav);
        name = " ";
        email = " ";
        sharedPreferences = this.getSharedPreferences("owner", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id",0);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navHeaderView = navigationView.getHeaderView(0);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_owner, R.id.nav_gallery_owner, R.id.nav_slideshow_owner, R.id.nav_profile_owner,R.id.nav_appointments_owner)
                .setOpenableLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navHeaderView.setOnClickListener(v -> {
            navController.navigate(R.id.nav_profile_owner);
            //Toast.makeText(OwnerLeftNavActivity.this, "Click Click", Toast.LENGTH_SHORT).show();
            mAppBarConfiguration.getOpenableLayout().close();closeContextMenu();
        });
        try {
            validate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logoutFromNavigationBar(navigationView,drawer);
        getData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void logoutFromNavigationBar(NavigationView navigationView,DrawerLayout drawerLayout){
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.nav_logout){
                LogoutPopup lg = new LogoutPopup();
                lg.show(getSupportFragmentManager(),"logout");
            }
            //This is for maintaining the behavior of the Navigation view
            NavigationUI.onNavDestinationSelected(item,navController);
            //This is for closing the drawer after acting on it
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void getData(){
        Call<ProfileOwnerData> call = RestAdapter.createAPI().ogetProfileData(id);
        call.enqueue(new Callback<ProfileOwnerData>() {
            @Override
            public void onResponse(@NotNull Call<ProfileOwnerData> call, @NotNull Response<ProfileOwnerData> response) {
                if (!response.isSuccessful()){
                    Log.e("Respose error ", response.message());
                    return;
                }
                ProfileOwnerData res = response.body();
                if(res != null){
                    name = res.getName();
                    email = res.getEmail();
                    boolean kyc = res.isKYC();
                    if(!kyc){
                        Log.i("KYC","Pending");
                        Toast.makeText(OwnerLeftNavActivity.this, "KYC Pending", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(OwnerLeftNavActivity.this,KycActivity.class);
                        intent.putExtra("email",email);
                        startActivity(intent);
                    }
                    headerImage = navHeaderView.findViewById(R.id.himageView);
                    headerTV1 = navHeaderView.findViewById(R.id.htv1);
                    headerTV2 = navHeaderView.findViewById(R.id.htv2);
                    headerTV1.setText(res.getName());
                    headerTV2.setText(res.getEmail());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ProfileOwnerData> call, @NotNull Throwable t) {
                Toast.makeText(OwnerLeftNavActivity.this, "Something went wrong" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void validate() throws Exception{
        if(id == 0){
            return;
        }
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<Map> call = o.checkValidity(id);
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if(response.isSuccessful()){
                    Map<String,Object> map = response.body();
                    boolean isValid = (boolean)map.get("message");
                    daysLeft = (Double) map.get("days_left");
                    Log.e("Validate  ",""+isValid+" "+daysLeft);
                    if(!isValid){
                        Toast.makeText(OwnerLeftNavActivity.this, "Subscription Expired", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OwnerLeftNavActivity.this, ExpiredActivity.class));
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("days",String.valueOf(Math.round(daysLeft)));
                    if(Math.round(daysLeft) < 5){
                        ReminderPopup rp = new ReminderPopup();
                        rp.setArguments(bundle);
                        rp.show(getSupportFragmentManager(),"reminder");
                    }
/*                    ReminderPopup rp = new ReminderPopup();
                    rp.setArguments(bundle);
                    rp.show(getSupportFragmentManager(),"reminder");*/
                    Log.i("Valid",String.valueOf(map.get("message")));
                }else{
                    Toast.makeText(OwnerLeftNavActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                Toast.makeText(OwnerLeftNavActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}