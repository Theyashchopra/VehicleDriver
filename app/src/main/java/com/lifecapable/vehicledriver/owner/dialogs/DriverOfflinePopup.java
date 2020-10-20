package com.lifecapable.vehicledriver.owner.dialogs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.activities.OwnerLeftNavActivity;

public class DriverOfflinePopup extends DialogFragment {

    Fragment fragment;
    Context context;
    Button ok;
    public DriverOfflinePopup(Fragment fragment, Context context) {
        this.fragment = fragment;
        this.context = context;
    }

    View view;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.driver_offline_popup, container, false);
        ok = view.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(fragment).popBackStack();
                getDialog().dismiss();
            }
        });
        return view;
    }
}
