package com.lifecapable.vehicledriver.owner.activities;

import android.content.Context;
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
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.ProfileOwnerData;
import com.lifecapable.vehicledriver.owner.dialogs.LogoutPopup;

import org.jetbrains.annotations.NotNull;

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
            Toast.makeText(OwnerLeftNavActivity.this, "Click Click", Toast.LENGTH_SHORT).show();
            mAppBarConfiguration.getOpenableLayout().close();closeContextMenu();
        });
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
}