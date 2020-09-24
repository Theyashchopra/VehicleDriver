package com.lifecapable.vehicledriver.owner.ui.gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleDetailsOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleIds;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class OwnerAddNewVehicleFragment extends Fragment {
    TextInputEditText name, platenumber, yearofman, totalhoursrun, kmperhour, fuelconsumptionrate, avgfuelconsumption, rentperhourwithfuel, rentperdaywithfuel, rentperhourwithoutfuel, rentperdaywithoutfuel;
    RadioGroup available;
    boolean isAvailable;
    Button donebt;
    View root;
    SharedPreferences sharedPreferences;
    int id,model_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_fragment_add_new_vehicle, container, false);
        name = root.findViewById(R.id.avnameet);
        platenumber = root.findViewById(R.id.avplatenumberet);
        yearofman = root.findViewById(R.id.avmadeinet);
        totalhoursrun = root.findViewById(R.id.avktotalrunhourset);
        kmperhour = root.findViewById(R.id.avrunkmhret);
        fuelconsumptionrate = root.findViewById(R.id.avfuelconsumptionet);
        avgfuelconsumption = root.findViewById(R.id.avavgfuelconsumptionet);
        rentperhourwithfuel = root.findViewById(R.id.avrentperhourwfet);
        rentperdaywithfuel = root.findViewById(R.id.avrentperdaywfet);
        rentperhourwithoutfuel = root.findViewById(R.id.avrentperhourwofet);
        rentperdaywithoutfuel = root.findViewById(R.id.avrentperdaywofet);
        available = root.findViewById(R.id.avavailblenowbg);
        isAvailable = true;
        sharedPreferences = this.getActivity().getSharedPreferences("owner", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id",0);
        model_id = getArguments().getInt("model_id");
        donebt = root.findViewById(R.id.avdonebt);
        initviews();
        return root;
    }
    private void initviews(){

        available.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                isAvailable = root.findViewById(checkedId) == root.findViewById(R.id.yes);
            }
        });

        donebt.setOnClickListener(v -> {
            savedata();
        });
    }

    private void savedata(){
        if(yearofman.getText().toString().isEmpty() || totalhoursrun.getText().toString().isEmpty() ||
                kmperhour.getText().toString().isEmpty() || fuelconsumptionrate.getText().toString().isEmpty() ||
                avgfuelconsumption.getText().toString().isEmpty() || rentperhourwithfuel.getText().toString().isEmpty() ||
                rentperdaywithfuel.getText().toString().isEmpty() || rentperhourwithoutfuel.getText().toString().isEmpty() ||
                rentperdaywithoutfuel.getText().toString().isEmpty() || platenumber.getText().toString().isEmpty()
        ) {
            Toast.makeText(getContext(), "You left something empty!! ", Toast.LENGTH_SHORT).show();
            return;
        }
        Call<VehicleIds> call = RestAdapter.createAPI().addVehicle(new VehicleDetailsOwnerData(name.getText().toString(),
                id,
                yearofman.getText().toString(),
                Integer.parseInt(totalhoursrun.getText().toString()),
                Integer.parseInt(kmperhour.getText().toString()),
                Integer.parseInt(fuelconsumptionrate.getText().toString()),
                Integer.parseInt(avgfuelconsumption.getText().toString()),
                Integer.parseInt(rentperdaywithfuel.getText().toString()),
                Integer.parseInt(rentperhourwithfuel.getText().toString()),
                Integer.parseInt(rentperhourwithoutfuel.getText().toString()),
                Integer.parseInt(rentperdaywithoutfuel.getText().toString()),
                getWifiMacAddress(),
                platenumber.getText().toString(),
                isAvailable,
                model_id));
        call.enqueue(new Callback<VehicleIds>() {
            @Override
            public void onResponse(Call<VehicleIds> call, Response<VehicleIds> response) {
                if(response.isSuccessful()){
                    Bundle args = new Bundle();
                    if (response.body().getName() != null) {
                        args.putInt("vehicleid", response.body().getV_id());
                        args.putString("vname",response.body().getName());
                        Log.e("Add new vehicle", response.body().getName()+"\n");
                        findNavController(OwnerAddNewVehicleFragment.this).navigate(R.id.action_nav_AddNewVehicle_owner_to_nav_RcVehicle_owner, args);
                    }
                    else {
                        Toast.makeText(getContext(), "Somethings Wrong I can feel it"+response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<VehicleIds> call, Throwable t) {
                Toast.makeText(getContext(), "Somethings Wrong I can feel it"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static String getWifiMacAddress() {
        try {
            String interfaceName = "wlan0";
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (!intf.getName().equalsIgnoreCase(interfaceName)){
                    continue;
                }

                byte[] mac = intf.getHardwareAddress();
                if (mac==null){
                    return "";
                }

                StringBuilder buf = new StringBuilder();
                for (byte aMac : mac) {
                    buf.append(String.format("%02X:", aMac));
                }
                if (buf.length()>0) {
                    buf.deleteCharAt(buf.length() - 1);
                }
                return buf.toString();
            }
        } catch (Exception ex) {

        } // for now eat exceptions
        return "";
    }

}