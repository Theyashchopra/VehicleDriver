package com.lifecapable.vehicledriver.Driver.ui.home;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lifecapable.vehicledriver.Driver.adapters.HomeDriverAdapter;
import com.lifecapable.vehicledriver.Driver.datamodels.HomeDriverData;
import com.lifecapable.vehicledriver.Driver.services.LocationService;
import com.lifecapable.vehicledriver.R;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class DriverHomeFragment extends Fragment {
    View root;
    RecyclerView homerecycle;
    HomeDriverAdapter homeDriverAdapter;
    List<HomeDriverData> appointlist;
    Button busybt, statusbt;
    Boolean busystate, statusstate;
    TextView busytv, statustv;
    View busyvw, statusvw;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.driver_fragment_home, container, false);
        homerecycle = root.findViewById(R.id.dhomerecycle);
        busybt = root.findViewById(R.id.busybutton);
        statusbt = root.findViewById(R.id.statsbutton);
        statustv = root.findViewById(R.id.statustxt);
        busytv = root.findViewById(R.id.busytxt);
        busyvw = root.findViewById(R.id.busyview);
        statusvw =root.findViewById(R.id.statusview);
        busystate = true;
        statusstate = true;
        initHome();
        initRecycle();
        return root;
    }

    private void initHome(){
        statusbt.setOnClickListener(v -> {
            if(statusstate){
                statusstate = false;
                statusbt.setBackgroundResource(R.drawable.background_green);
                statusbt.setText("ONLINE");
                statusvw.setBackgroundColor(Color.rgb(50,205,50));
                statustv.setText("Online");
                statustv.setTextColor(Color.rgb(50,205,50));
                startLocationService();
            }
            else{
                statusstate = true;
                statusbt.setBackgroundResource(R.drawable.background_red);
                statusbt.setText("OFFLINE");
                statusvw.setBackgroundColor(Color.RED);
                statustv.setText("Offline");
                statustv.setTextColor(Color.RED);
                stopLocationService();
            }

        });

        busybt.setOnClickListener(v -> {
            if(busystate){
                busystate = false;
                busybt.setBackgroundResource(R.drawable.background_green);
                busybt.setText("Available");
                busyvw.setBackgroundColor(Color.rgb(50,205,50));
                busytv.setText("Available");
                busytv.setTextColor(Color.rgb(50,205,50));

            }
            else{
                busystate = true;
                busybt.setBackgroundResource(R.drawable.background_orange);
                busybt.setText("BUSY");
                busyvw.setBackgroundColor(Color.rgb(255, 165, 0));
                busytv.setText("Busy");
                busytv.setTextColor(Color.rgb(255, 165, 0));
            }

        });
    }
    private void initRecycle(){
        appointlist = new ArrayList<>();
        appointlist.add(new HomeDriverData("XYZ","11/10/20 - 12/10/20","Sadar","1234567890",21.1480f,79.0936f));
        appointlist.add(new HomeDriverData("XYZ","11/10/20 - 12/10/20","Sadar","1234567890",0.0f,0.0f));
        appointlist.add(new HomeDriverData("XYZ","11/10/20 - 12/10/20","Sadar","1234567890",0.0f,0.0f));
        appointlist.add(new HomeDriverData("XYZ","11/10/20 - 12/10/20","Sadar","1234567890",0.0f,0.0f));
        appointlist.add(new HomeDriverData("XYZ","11/10/20 - 12/10/20","Sadar","1234567890",0.0f,0.0f));
        appointlist.add(new HomeDriverData("XYZ","11/10/20 - 12/10/20","Sadar","1234567890",0.0f,0.0f));
        appointlist.add(new HomeDriverData("XYZ","11/10/20 - 12/10/20","Sadar","1234567890",0.0f,0.0f));
        appointlist.add(new HomeDriverData("XYZ","11/10/20 - 12/10/20","Sadar","1234567890",0.0f,0.0f));
        appointlist.add(new HomeDriverData("XYZ","11/10/20 - 12/10/20","Sadar","1234567890",0.0f,0.0f));
        homeDriverAdapter = new HomeDriverAdapter(appointlist,getContext());
        homerecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        homerecycle.setAdapter(homeDriverAdapter);
    }

    private void startLocationService(){
        if (!isLocationServiceRunning()) {
            Intent serviceIntent = new Intent(getActivity(), LocationService.class);
            ContextCompat.startForegroundService(getActivity(), serviceIntent);
        }
    }
    private void stopLocationService(){
            try{
                Intent serviceIntent = new Intent(getActivity(), LocationService.class);
                getActivity().stopService(serviceIntent);
            }catch (NullPointerException e){
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
    }
    private boolean isLocationServiceRunning() {
        ActivityManager manager = (ActivityManager) getContext().getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if("com.lifecapable.vehicledriver.Driver.services.LocationService".equals(service.service.getClassName())) {
                Log.d(TAG, "isLocationServiceRunning: location service is already running.");
                return true;
            }
        }
        Log.d(TAG, "isLocationServiceRunning: location service is not running.");
        return false;
    }

}