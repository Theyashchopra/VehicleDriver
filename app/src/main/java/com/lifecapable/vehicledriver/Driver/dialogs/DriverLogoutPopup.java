package com.lifecapable.vehicledriver.Driver.dialogs;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.lifecapable.vehicledriver.Driver.services.LocationService;
import com.lifecapable.vehicledriver.MainActivity;
import com.lifecapable.vehicledriver.R;

import static android.content.Context.ACTIVITY_SERVICE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class DriverLogoutPopup extends DialogFragment {

    View root;
    Button yesbt,nobt;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.driver_logout_popup, container, false);
        yesbt = root.findViewById(R.id.dyes);
        nobt = root.findViewById(R.id.dno);
        sharedPreferences = this.requireActivity().getSharedPreferences("driver", Context.MODE_PRIVATE);
        Dialog dialog = getDialog();
        try {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        }
        catch (Exception e){

        }

        yesbt.setOnClickListener(v ->{
            logout();
            dialog.dismiss();

        });
        nobt.setOnClickListener(v ->{

            dialog.dismiss();

        });
        return root;
    }

    public void logout(){
        if(isLocationServiceRunning()){
            stopLocationService();
        }
        editor = sharedPreferences.edit();
        editor.putBoolean("login",false);
        editor.apply();
        startActivity(new Intent(getActivity(), MainActivity.class));
        getDialog().dismiss();
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
    private void stopLocationService(){
        try{
            Intent serviceIntent = new Intent(getActivity(), LocationService.class);
            requireActivity().stopService(serviceIntent);
        }catch (NullPointerException e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

}
