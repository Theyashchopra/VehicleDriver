package com.lifecapable.vehicledriver.owner.ui.appointment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.AppointmentOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleDetailsOwnerData;
import com.lifecapable.vehicledriver.owner.dialogs.DeleteAppointmentPopup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewAppointmentOwnerFragment extends Fragment {

    View root;
    int id,vid;
    TextView customername,address, mobile, alternatemobile, startdate, enddate, time,vehiclename, vehicleplate;
    String customernamestring, addressstring, mobilestring, alternatemobilestring, startdatestring, enddatestring, timestring, vehiclenamestring, vehicleplatestring;
    ProgressBar progressBar;
    Button edit, delete;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.owner_fragment_view_appointment, container, false);
        if(getArguments() != null){
            id = getArguments().getInt("id");
            vid = getArguments().getInt(("vid"));
            customernamestring = getArguments().getString("cname");
            addressstring = getArguments().getString("address");
            mobilestring = getArguments().getString("mobile");
            alternatemobilestring = getArguments().getString("alternatemobile");
            startdatestring = getArguments().getString("startdate");
            enddatestring = getArguments().getString("enddate");
            timestring = getArguments().getString("time");

        }
        else {
            Log.e("yoyoyoyoyoyoyoyooyo","yoyoyoyoyoyoyoyoyoy");
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
        progressBar = root.findViewById(R.id.vaprog);
        edit = root.findViewById(R.id.vaedit);
        delete = root.findViewById(R.id.vadone);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putInt("id",id);
                args.putInt("vid",vid);
                NavHostFragment.findNavController(ViewAppointmentOwnerFragment.this).navigate(R.id.action_nav_viewAppointment_owner_to_nav_edit_appointment,args);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteAppointmentPopup dp = new DeleteAppointmentPopup(id,ViewAppointmentOwnerFragment.this);
                dp.show(getActivity().getSupportFragmentManager(),"delete");
            }
        });
        initviews();
        getVehicleData(vid);

        return root;
    }

    private void initviews(){
        customername.setText(customernamestring);
        address.setText(addressstring);
        mobile.setText(mobilestring);
        alternatemobile.setText(mobilestring);
        startdate.setText(startdatestring);
        enddate.setText(enddatestring);
        time.setText(timestring);
    }

    private void getVehicleData(int vid){
        Call<VehicleDetailsOwnerData> call = RestAdapter.createAPI().ogetVehicleDetails(vid);
        call.enqueue(new Callback<VehicleDetailsOwnerData>() {
            @Override
            public void onResponse(Call<VehicleDetailsOwnerData> call, Response<VehicleDetailsOwnerData> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Something went wrong" + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                VehicleDetailsOwnerData res = response.body();
                if(res != null){
                    vehiclename.setText(res.getName());
                    vehicleplate.setText(res.getPlate_no());
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<VehicleDetailsOwnerData> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}