package com.lifecapable.vehicledriver.owner.ui.gallery;

import android.os.Bundle;

import androidx.appcompat.widget.ViewUtils;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lifecapable.vehicledriver.R;

public class OwnerEditVehicleFragment extends Fragment {
    View root;
    Button donebt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_fragment_edit_vehicle, container, false);
        donebt = root.findViewById(R.id.evdonebt);
        initviews();
        return root;
    }
    private void initviews(){
        donebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigateUp();
            }
        });
    }
}