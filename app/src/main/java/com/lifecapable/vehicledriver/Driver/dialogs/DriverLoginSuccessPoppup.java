package com.lifecapable.vehicledriver.Driver.dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.lifecapable.vehicledriver.Driver.activities.DriverBottomActivity;
import com.lifecapable.vehicledriver.R;

public class DriverLoginSuccessPoppup extends DialogFragment {
    View view;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.driver_login_success, container, false);
        new Handler(Looper.myLooper(), message -> {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            return false;
        }).postDelayed(() -> startActivity(new Intent(getContext(), DriverBottomActivity.class)),2500);
        return view;
    }
}
