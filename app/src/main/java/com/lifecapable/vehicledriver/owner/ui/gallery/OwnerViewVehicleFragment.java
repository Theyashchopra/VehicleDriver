package com.lifecapable.vehicledriver.owner.ui.gallery;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleDetailsOwnerData;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OwnerViewVehicleFragment extends Fragment {
    View root;
    EditText plateEt, modelnumEt, madeinEt, kmscompletedEt, rentperday, rentperhour;
    int vehicleid;
    Button editbt, removebt;
    OwnerJsonPlaceHolder vehicleDataPlaceHolder;
    Retrofit retrofit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_fragment_view_vehicle, container, false);

        vehicleid = getArguments().getInt("Vehicleid");
        plateEt = root.findViewById(R.id.vvplatenumber);
        modelnumEt = root.findViewById(R.id.vvmodelnumber);
        madeinEt = root.findViewById(R.id.vvmadein);
        kmscompletedEt = root.findViewById(R.id.vvkmscompleted);
        rentperday = root.findViewById(R.id.vvrentperday);
        rentperhour = root.findViewById(R.id.vvrentperhour);
        removebt = root.findViewById(R.id.vvremove);

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        editbt = root.findViewById(R.id.vvedit);
        return root;
    }
    public void getData(int vid){
        vehicleDataPlaceHolder = retrofit.create(OwnerJsonPlaceHolder.class);
        Call<VehicleDetailsOwnerData> call = vehicleDataPlaceHolder.ogetVehicleDetails(vid);
        call.enqueue(new Callback<VehicleDetailsOwnerData>() {
            @Override
            public void onResponse(Call<VehicleDetailsOwnerData> call, Response<VehicleDetailsOwnerData> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Somethings Wrong I can feel it"+response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }

                VehicleDetailsOwnerData res = response.body();
                initviews(res);

            }
            @Override
            public void onFailure(Call<VehicleDetailsOwnerData> call, Throwable t) {
                Toast.makeText(getContext(), "Somethings Wrong I can feel it"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void initviews(VehicleDetailsOwnerData allData){
/*
        plateEt.setText(allData.getPlate());
        modelnumEt.setText(allData.getModel_name());
        madeinEt.setText(allData.getYear_of_manufacture());
        rentperhour.setText(allData.getRent_per_hour());
        rentperday.setText(allData.getRent_per_day());
        editbt.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putInt("vehicleid",allData.getId());
            Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_nav_viewvehicle_owner_to_nav_EditVehicle_owner,args);
        });*/

        removebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }
}