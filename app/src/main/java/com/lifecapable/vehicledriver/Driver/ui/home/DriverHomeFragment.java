package com.lifecapable.vehicledriver.Driver.ui.home;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.driver_fragment_home, container, false);

        homerecycle = root.findViewById(R.id.dhomerecycle);
        busybt = root.findViewById(R.id.busybutton);
        statusbt = root.findViewById(R.id.statsbutton);
        statustv = root.findViewById(R.id.statustxt);
        busytv = root.findViewById(R.id.busytxt);
        busyvw = root.findViewById(R.id.busyview);
        statusvw =root.findViewById(R.id.statusview);
        try {
            sharedPreferences = requireActivity().getSharedPreferences("statePreference", Context.MODE_PRIVATE);
        }catch (NullPointerException ne){
            Log.e("yo","yo");
        }
        setBusyState(sharedPreferences.getBoolean("busy",false));
        setOnlineStatus(sharedPreferences.getBoolean("state",false));

        initHome();
        initRecycle();

        return root;
    }

    private void initHome(){
        statusbt.setOnClickListener(v -> {
            if (statusstate){
                setOnlineStatus(false);
            }
            else {
                setOnlineStatus(true);
            }
        });

        busybt.setOnClickListener(v -> {
            if(busystate){
                setBusyState(false);
            }
            else{
                setBusyState(true);
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

    private void setBusyState(boolean busystate1){
        editor = sharedPreferences.edit();
        if(busystate1){
            String available = "AVAILABLE";
            busystate = true;
            busybt.setBackgroundResource(R.drawable.background_green);
            busybt.setText(available);
            busyvw.setBackgroundColor(Color.rgb(50,205,50));
            busytv.setText(available);
            busytv.setTextColor(Color.rgb(50,205,50));
            editor.putBoolean("busy",true);
        }
        else{
            String busy = "BUSY";
            busystate = false;
            busybt.setBackgroundResource(R.drawable.background_orange);
            busybt.setText(busy);
            busyvw.setBackgroundColor(Color.rgb(255, 165, 0));
            busytv.setText(busy);
            busytv.setTextColor(Color.rgb(255, 165, 0));
            editor.putBoolean("busy",false);
        }
        editor.apply();
    }

    private void setOnlineStatus(boolean status){
        editor = sharedPreferences.edit();
        if(status){
            String online = "ONLINE";
            statusstate = true;
            statusbt.setBackgroundResource(R.drawable.background_green);
            statusbt.setText(online);
            statusvw.setBackgroundColor(Color.rgb(50,205,50));
            statustv.setText(online);
            statustv.setTextColor(Color.rgb(50,205,50));
            startLocationService();
            editor.putBoolean("state",true);
        }
        else{
            String offline = "OFFLINE";
            statusstate = false;
            statusbt.setBackgroundResource(R.drawable.background_red);
            statusbt.setText(offline);
            statusvw.setBackgroundColor(Color.RED);
            statustv.setText(offline);
            statustv.setTextColor(Color.RED);
            stopLocationService();
            editor.putBoolean("state",false);
        }
        editor.apply();
    }

    private void startLocationService(){
        if (!isLocationServiceRunning()) {
            Intent serviceIntent = new Intent(getActivity(), LocationService.class);
            ContextCompat.startForegroundService(requireActivity(), serviceIntent);
        }
    }
    private void stopLocationService(){
            try{
                Intent serviceIntent = new Intent(getActivity(), LocationService.class);
                requireActivity().stopService(serviceIntent);
            }catch (NullPointerException e){
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
    }
    private boolean isLocationServiceRunning() {
        ActivityManager manager = (ActivityManager) requireContext().getSystemService(ACTIVITY_SERVICE);
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