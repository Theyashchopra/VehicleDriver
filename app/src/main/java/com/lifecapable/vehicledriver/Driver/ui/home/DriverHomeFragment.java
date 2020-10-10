package com.lifecapable.vehicledriver.Driver.ui.home;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lifecapable.vehicledriver.Driver.adapters.HomeDriverAdapter;
import com.lifecapable.vehicledriver.Driver.adapters.RestAdapter;
import com.lifecapable.vehicledriver.Driver.datamodels.HomeDriverData;
import com.lifecapable.vehicledriver.Driver.datamodels.ListHomeDriverData;
import com.lifecapable.vehicledriver.Driver.datamodels.VehicleDriverData;
import com.lifecapable.vehicledriver.Driver.services.LocationService;
import com.lifecapable.vehicledriver.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.ACTIVITY_SERVICE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class DriverHomeFragment extends Fragment {
    View root;
    int vid;
    ProgressBar progressBar;
    RecyclerView homerecycle;
    HomeDriverAdapter homeDriverAdapter;
    List<HomeDriverData> appointlist;
    Button busybt, statusbt;
    Boolean busystate, statusstate;
    TextView busytv, statustv,notice;
    View busyvw, statusvw;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences2;
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
        notice = root.findViewById(R.id.appt_notice);
        progressBar = root.findViewById(R.id.progress);
        try {
            sharedPreferences = requireActivity().getSharedPreferences("statePreference", Context.MODE_PRIVATE);
            sharedPreferences2 = requireActivity().getSharedPreferences("driver", Context.MODE_PRIVATE);
        }catch (NullPointerException ne){
            Log.e("yo","yo");
        }
        vid = sharedPreferences2.getInt("vehicleid",0);
        Log.e("Vid    ", vid+"");
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
        progressBar.setVisibility(View.VISIBLE);
        appointlist = new ArrayList<>();
        Call<ListHomeDriverData> call = RestAdapter.createAPI().dgetAppointmnets(vid);
        call.enqueue(new Callback<ListHomeDriverData>() {
            @Override
            public void onResponse(@NotNull Call<ListHomeDriverData> call, @NotNull Response<ListHomeDriverData> response) {
                if(!response.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    return;
                }
                ListHomeDriverData res = response.body();
                if (res != null){
                    appointlist.addAll(res.getAppointments());
                    if(appointlist.isEmpty()){
                        notice.setText("You don't have any appointments yet");
                    }
                    homeDriverAdapter = new HomeDriverAdapter(appointlist,getContext(),DriverHomeFragment.this);
                    homerecycle.setLayoutManager(new LinearLayoutManager(getContext()));
                    homerecycle.setAdapter(homeDriverAdapter);
                    homerecycle.scheduleLayoutAnimation();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ListHomeDriverData> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Something went wrong"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setBusyState(boolean busystate1){
        editor = sharedPreferences.edit();
        if(busystate1){
            setAvailabilityInApi(1);
            String available = "AVAILABLE";
            busystate = true;
            busybt.setBackgroundResource(R.drawable.background_green);
            busybt.setText(available);
            busyvw.setBackgroundColor(Color.rgb(50,205,50));
            busytv.setText(available);
            busytv.setTextColor(Color.rgb(50,205,50));
            editor.putBoolean("busy",true);
            editor.apply();
        }
        else{
            setAvailabilityInApi(0);
            String busy = "BUSY";
            busystate = false;
            busybt.setBackgroundResource(R.drawable.background_orange);
            busybt.setText(busy);
            busyvw.setBackgroundColor(Color.rgb(255, 165, 0));
            busytv.setText(busy);
            busytv.setTextColor(Color.rgb(255, 165, 0));
            editor.putBoolean("busy",false);
            editor.apply();
        }

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
            editor.apply();
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
            editor.apply();
        }

    }

    private void setAvailabilityInApi(int s){
        Call<VehicleDriverData> call = RestAdapter.createAPI().oputAvailability(vid,s);
        call.enqueue(new Callback<VehicleDriverData>() {
            @Override
            public void onResponse(@NotNull Call<VehicleDriverData> call, @NotNull Response<VehicleDriverData> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Something went wrong"+response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                VehicleDriverData res = response.body();
                if(res != null){
                    if(res.getName() == null){
                        Toast.makeText(getContext(), "Something went wrong"+response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<VehicleDriverData> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void startLocationService(){
        if (!isLocationServiceRunning()) {
            Intent serviceIntent = new Intent(getActivity(), LocationService.class);
            serviceIntent.putExtra("service",true);
            requireActivity().startService( serviceIntent);
            
        }
    }
    private void stopLocationService(){
            try{
                Intent serviceIntent = new Intent(getActivity(), LocationService.class);
                serviceIntent.putExtra("service",false);
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