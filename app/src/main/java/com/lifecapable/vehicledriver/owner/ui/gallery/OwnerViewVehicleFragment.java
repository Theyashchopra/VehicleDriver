package com.lifecapable.vehicledriver.owner.ui.gallery;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lifecapable.vehicledriver.R;

public class OwnerViewVehicleFragment extends Fragment {
    View root;
    Button editbt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_fragment_view_vehicle, container, false);
        editbt = root.findViewById(R.id.vvedit);
        initviews();
        return root;
    }
    public void initviews(){
        editbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_nav_viewvehicle_owner_to_nav_EditVehicle_owner);
            }
        });
    }
}