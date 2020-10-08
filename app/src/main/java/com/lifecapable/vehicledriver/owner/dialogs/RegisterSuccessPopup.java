package com.lifecapable.vehicledriver.owner.dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.activities.OwnerLeftNavActivity;
import com.lifecapable.vehicledriver.owner.activities.OwnerLoginActivity;

public class RegisterSuccessPopup extends DialogFragment {

    View view;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.register_success_popup, container, false);
        new Handler(Looper.myLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                return false;
            }
        }).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getActivity(), OwnerLoginActivity.class));
            }
        },2500);
        return view;
    }
}
