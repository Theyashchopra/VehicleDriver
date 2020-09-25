package com.lifecapable.vehicledriver.owner.ui.gallery;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;
import com.google.gson.JsonStreamParser;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleDetailsOwnerData;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OwnerViewVehicleFragment extends Fragment {
    View root;
    TextView plateEt, modelnumEt, madeinEt, kmscompletedEt, rentperday, rentperhour;
    int vehicleid;
    Button editbt, removebt;
    OwnerJsonPlaceHolder vehicleDataPlaceHolder;
    Retrofit retrofit;
    VehicleDetailsOwnerData vehicleDetailsOwnerData;
    ImageView insurance,rc,invoice;
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

        insurance = root.findViewById(R.id.insurance);
        rc = root.findViewById(R.id.rc);
        invoice = root.findViewById(R.id.invoice);

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.i("VID",String.valueOf(vehicleid));
        getData(vehicleid);
        editbt = root.findViewById(R.id.vvedit);
        return root;
    }
    public void getData(int vid){
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<VehicleDetailsOwnerData> call = o.ogetVehicleDetails(vid);
        call.enqueue(new Callback<VehicleDetailsOwnerData>() {
            @Override
            public void onResponse(Call<VehicleDetailsOwnerData> call, Response<VehicleDetailsOwnerData> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Somethings Wrong I can feel it"+response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }

                VehicleDetailsOwnerData res = response.body();
                if(res != null) {
                    try {
                        initviews(res);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            @Override
            public void onFailure(Call<VehicleDetailsOwnerData> call, Throwable t) {
                Toast.makeText(getContext(), "Somethings Wrong I can feel it"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void initviews(VehicleDetailsOwnerData allData) throws Exception{

        plateEt.setText(allData.getPlate_no().toUpperCase());
        modelnumEt.setText(allData.getModel_name());
        madeinEt.setText(allData.getYom());
        kmscompletedEt.setText(String.valueOf(allData.getTotal_run_hrs()));
        rentperhour.setText(String.valueOf(allData.getRent_per_hour_with_fuel()));
        rentperday.setText(String.valueOf(allData.getRent_per_day_with_fuel()));
        editbt.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("plate",allData.getPlate_no());
            Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_nav_AddNewVehicle_owner_to_nav_RcVehicle_owner,args);
        });

        removebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //got to edit page
            }
        });
    }
}