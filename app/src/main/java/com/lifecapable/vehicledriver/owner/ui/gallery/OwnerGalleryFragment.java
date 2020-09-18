package com.lifecapable.vehicledriver.owner.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.VehicleOwnerAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleOwnerData;

import java.util.ArrayList;
import java.util.List;

public class OwnerGalleryFragment extends Fragment {
    View root;
    RecyclerView vehicleRecycleview;
    VehicleOwnerAdapter vehicleOwnerAdapter;
    List<VehicleOwnerData> vehicleList;
    TextView vehiclecounttv;
    Button vehicleaddbutton;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_fragment_gallery, container, false);
        vehicleRecycleview = root.findViewById(R.id.ogalleryrecycle);
        vehiclecounttv = root.findViewById(R.id.ogalleryvehiclecount);
        vehicleaddbutton = root.findViewById(R.id.ogalleryaddvehicle);
        initothers();
        initVehicleRecycle();
        vehicleaddbutton.setOnClickListener(v -> {

            NavHostFragment.findNavController(OwnerGalleryFragment.this).navigate(R.id.action_nav_gallery_owner_to_nav_AddNewVehicle_owner);
        });
        return root;
    }

    private void initothers(){

    }

    public void initVehicleRecycle(){
        vehicleList =new ArrayList<>();
        vehicleList.add(new VehicleOwnerData("TATA","MH31A1234"));
        vehicleList.add(new VehicleOwnerData("TATA","MH31A1234"));
        vehicleList.add(new VehicleOwnerData("TATA","MH31A1234"));
        vehicleList.add(new VehicleOwnerData("TATA","MH31A1234"));
        vehicleList.add(new VehicleOwnerData("TATA","MH31A1234"));
        vehicleList.add(new VehicleOwnerData("TATA","MH31A1234"));
        vehicleList.add(new VehicleOwnerData("TATA","MH31A1234"));
        vehicleOwnerAdapter = new VehicleOwnerAdapter(vehicleList, getContext());
        vehicleRecycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        vehicleRecycleview.setAdapter(vehicleOwnerAdapter);
    }
}