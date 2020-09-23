package com.lifecapable.vehicledriver.owner.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.lifecapable.vehicledriver.owner.dialogs.LogoutPopup;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OwnerLeftNavActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    View navHeaderView;
    ImageView headerImage;
    TextView headerTV1,headerTV2;
    NavController navController;
    OwnerJsonPlaceHolder userPlaceHolder;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_activity_left_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navHeaderView = navigationView.getHeaderView(0);
        headerImage = navHeaderView.findViewById(R.id.himageView);
        headerTV1 = navHeaderView.findViewById(R.id.htv1);
        headerTV2 = navHeaderView.findViewById(R.id.htv2);
        headerTV1.setText("Name");
        headerTV2.setText("email");

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_owner, R.id.nav_gallery_owner, R.id.nav_slideshow_owner, R.id.nav_profile_owner)
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
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void logoutFromNavigationBar(NavigationView navigationView,DrawerLayout drawerLayout){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
            }
        });
    }
}