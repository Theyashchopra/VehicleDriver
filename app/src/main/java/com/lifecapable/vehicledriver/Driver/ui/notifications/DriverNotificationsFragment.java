package com.lifecapable.vehicledriver.Driver.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.Driver.dialogs.DriverLogoutPopup;

public class DriverNotificationsFragment extends Fragment {
    View root;
    Button dlogout;
    Button deditbt, ddonebt;
    DriverLogoutPopup logoutPopup;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.driver_fragment_notifications, container, false);
        dlogout = root.findViewById(R.id.dlogout);
        deditbt = root.findViewById(R.id.dpedit);
        ddonebt = root.findViewById(R.id.dpdone);
        logoutPopup = new DriverLogoutPopup();

        deditbt.setOnClickListener(v -> {
            ddonebt.setVisibility(View.VISIBLE);
            deditbt.setVisibility(View.GONE);


        });
        ddonebt.setOnClickListener(v -> {
            ddonebt.setVisibility(View.GONE);
            deditbt.setVisibility(View.VISIBLE);

        });
        dlogout.setOnClickListener(v -> {
                    logoutPopup.show(getActivity().getSupportFragmentManager(),"logout");
                    logout();
        });
        return root;
    }
    private void logout(){

    }
}