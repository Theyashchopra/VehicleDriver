package com.lifecapable.vehicledriver.owner.ui.gallery;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textfield.TextInputEditText;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleDetailsOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleIds;

import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class OwnerAddNewVehicleFragment extends Fragment {
    TextInputEditText name, platenumber, yearofman, avgfuelconsumption,rentEt;
    //totalhoursrun, kmperhour, fuelconsumptionrate , rentperhourwithfuel, rentperdaywithfuel, rentperhourwithoutfuel, rentperdaywithoutfuel
    RadioGroup available;
    boolean isAvailable;
    Button donebt;
    View root;
    SharedPreferences sharedPreferences;
    int id,model_id;
    ProgressBar progressBar;
    Spinner spinner;
    String totalrentString,unit;
    CardView enddatecv;
    TextView enddatetv;
    String endString;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_fragment_add_new_vehicle, container, false);
        rentEt = root.findViewById(R.id.rentet);
        name = root.findViewById(R.id.avnameet);
        spinner = root.findViewById(R.id.rentspinner);
        platenumber = root.findViewById(R.id.avplatenumberet);
        yearofman = root.findViewById(R.id.avmadeinet);
        avgfuelconsumption = root.findViewById(R.id.avavgfuelconsumptionet);
        enddatecv = root.findViewById(R.id.avenddatecard);
        enddatetv = root.findViewById(R.id.avenddatetext);
/*        totalhoursrun = root.findViewById(R.id.avktotalrunhourset);
        kmperhour = root.findViewById(R.id.avrunkmhret);
        fuelconsumptionrate = root.findViewById(R.id.avfuelconsumptionet);
        rentperhourwithfuel = root.findViewById(R.id.avrentperhourwfet);
        rentperdaywithfuel = root.findViewById(R.id.avrentperdaywfet);
        rentperhourwithoutfuel = root.findViewById(R.id.avrentperhourwofet);
        rentperdaywithoutfuel = root.findViewById(R.id.avrentperdaywofet);*/
        available = root.findViewById(R.id.avavailblenowbg);
        progressBar = root.findViewById(R.id.progress);
        isAvailable = true;
        sharedPreferences = this.getActivity().getSharedPreferences("owner", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id",0);
        Log.i("OWNER",String.valueOf(id));
        model_id = getArguments().getInt("model_id");
        donebt = root.findViewById(R.id.avdonebt);
        initviews();
        setRentSpinner();
        spinnerListener();
        return root;
    }
    private void initviews(){

        available.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                isAvailable = root.findViewById(checkedId) == root.findViewById(R.id.yes);
                if(isAvailable){
                    enddatecv.setVisibility(View.GONE);
                }
                else {
                    enddatecv.setVisibility(View.VISIBLE);
                }
            }
        });

        donebt.setOnClickListener(v -> {
            savedata();
        });
        enddatecv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        (datePicker, i, i1, i2) -> {
                            endString =  i2 + "/" + (i1+1) + "/" + i;
                            enddatetv.setText(endString);
                            //map.put("busy_start",startstring);
                        },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
    }

    private void savedata(){
   /*     totalhoursrun.getText().toString().isEmpty() ||
                kmperhour.getText().toString().isEmpty() || fuelconsumptionrate.getText().toString().isEmpty() ||
                 || rentperhourwithfuel.getText().toString().isEmpty() ||
                rentperdaywithfuel.getText().toString().isEmpty() || rentperhourwithoutfuel.getText().toString().isEmpty() ||
                rentperdaywithoutfuel.getText().toString().isEmpty()*/
        String vehicle_plate = platenumber.getText().toString().trim();
        totalrentString = rentEt.getText().toString().trim() +" "+ unit;
        if(endString == null){
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            endString = df.format(c);
        }
        if(yearofman.getText().toString().isEmpty() || avgfuelconsumption.getText().toString().isEmpty() || platenumber.getText().toString().isEmpty() || rentEt.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "You left something empty!! ", Toast.LENGTH_SHORT).show();
            return;
        }else if(!platenumber.getText().toString().isEmpty()){
            if(patterMatcher(platenumber.getText().toString())) {
                platenumber.setError("No blank spaces allowed");
                YoYo.with(Techniques.Shake)
                        .duration(2000)
                        .playOn(platenumber);
                return;
            }
        }
        progressBar.setVisibility(View.VISIBLE);
        Call<VehicleIds> call = RestAdapter.createAPI().addVehicle(new VehicleDetailsOwnerData(name.getText().toString(),
                model_id,
                yearofman.getText().toString(),
                Integer.parseInt(avgfuelconsumption.getText().toString()),
                0,0,
                0,0,
                0,0,0,
                getWifiMacAddress(),
                vehicle_plate,
                isAvailable,
                id,
                totalrentString,
                endString));
        /*Integer.parseInt(totalhoursrun.getText().toString()),
                Integer.parseInt(kmperhour.getText().toString()),
                Integer.parseInt(fuelconsumptionrate.getText().toString()),
                Integer.parseInt(rentperdaywithfuel.getText().toString()),
                Integer.parseInt(rentperhourwithfuel.getText().toString()),
                Integer.parseInt(rentperhourwithoutfuel.getText().toString()),
                Integer.parseInt(rentperdaywithoutfuel.getText().toString()),*/
        call.enqueue(new Callback<VehicleIds>() {
            @Override
            public void onResponse(Call<VehicleIds> call, Response<VehicleIds> response) {
                if(response.isSuccessful()){
                    Bundle args = new Bundle();
                    if (response.body().getName() != null) {
                        args.putInt("vehicleid", response.body().getV_id());
                        args.putString("vname",response.body().getName());
                        args.putString("plate",response.body().getPlate_no());
                        Log.e("Add new vehicle", response.body().getName()+"\n");
                        progressBar.setVisibility(View.INVISIBLE);
                        findNavController(OwnerAddNewVehicleFragment.this).navigate(R.id.action_nav_AddNewVehicle_owner_to_nav_RcVehicle_owner, args);
                    }
                    else {
                        Toast.makeText(getContext(), "Somethings Wrong I can feel it"+response.message(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<VehicleIds> call, Throwable t) {
                Toast.makeText(getContext(), "Somethings Wrong I can feel it"+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
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

    public boolean patterMatcher(String s){
        if(s.isEmpty() || s == null){
            return true;
        }
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
        Matcher matcher = pattern.matcher(s);
        if(!matcher.matches()){
            return true;
        }else{
            return false;
        }
    }

    private void setRentSpinner(){

        List<String> strings = new ArrayList<>();
        strings.add("Per Hour");
        strings.add("Per Day");
        strings.add("Per Month");
        strings.add("Per Km");
        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, strings);
            spinner.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void spinnerListener(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = adapterView.getItemAtPosition(i);
                unit = o.toString();
                Log.i("UNIT",unit);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}