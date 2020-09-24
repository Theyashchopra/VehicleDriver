package com.lifecapable.vehicledriver.owner.ui.gallery;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifecapable.vehicledriver.R;

public class OwnerRcVehicleFragment extends Fragment {

    View root;
    String name;
    int vid;
    TextView rcname;
    ImageView rcimage;
    Button rcdone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.owner_fragment_rc_vehicle, container, false);
        rcname = root.findViewById(R.id.rcname);
        rcimage = root.findViewById(R.id.rcimage);
        rcdone = root.findViewById(R.id.rcdone);
        if(getArguments() != null){
            name = getArguments().getString("vname");
            getArguments().getInt("vehicleid");
            rcname.setText(name);
        }
        rcimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rcdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedata();
                NavHostFragment.findNavController(OwnerRcVehicleFragment.this).navigate(R.id.action_nav_RcVehicle_owner_to_nav_gallery_owner);
            }
        });
        return root;
    }

    private void savedata(){

    }
}