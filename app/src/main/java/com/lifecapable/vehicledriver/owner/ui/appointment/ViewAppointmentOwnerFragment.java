package com.lifecapable.vehicledriver.owner.ui.appointment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lifecapable.vehicledriver.R;


public class ViewAppointmentOwnerFragment extends Fragment {

    View root;
    int id;
    TextView customername,address, mobile, alternatemobile, startdate, enddate, time,vehiclename, vehicleplate;
    Button done, cancel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.owner_fragment_view_appointment, container, false);
        if(getArguments() != null){
            id = getArguments().getInt("id");
        }
        customername = root.findViewById(R.id.vacustomername);
        address = root.findViewById(R.id.vaaddress);
        mobile = root.findViewById(R.id.vacustomermobile);
        alternatemobile = root.findViewById(R.id.vacustomermobilealternate);
        startdate = root.findViewById(R.id.vastartDate);
        enddate = root.findViewById(R.id.vaenddate);
        time = root.findViewById(R.id.vatime);
        vehiclename = root.findViewById(R.id.vavehiclename);
        vehicleplate = root.findViewById(R.id.vavehicleplate);

        fetchData();

        return root;
    }
    private void fetchData(){

    }
}