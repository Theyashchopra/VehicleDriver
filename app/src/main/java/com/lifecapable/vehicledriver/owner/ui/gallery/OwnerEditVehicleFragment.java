package com.lifecapable.vehicledriver.owner.ui.gallery;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.widget.ViewUtils;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerEditVehicleFragment extends Fragment {
    View root;
    Button donebt;
    TextInputEditText name, platenumber, yearofman, totalhoursrun, kmperhour, fuelconsumptionrate, avgfuelconsumption, rentperhourwithfuel, rentperdaywithfuel, rentperhourwithoutfuel, rentperdaywithoutfuel;
    RadioGroup available;
    ProgressBar progressBar;
    Map<String,Object> map;
    TextView busy_start,busy_end;
    CardView start_card,end_card;
    boolean isAvailable;
    int avail_bit;
    String startstring,endstring;
    int vid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_fragment_edit_vehicle, container, false);
        map = new HashMap<>();
        vid = 0;
        if(getArguments() != null){
            vid = getArguments().getInt("vid");
            map.put("id",vid);
            Log.i("VID",String.valueOf(vid));
        }
        donebt = root.findViewById(R.id.vedit_donebt);
        init();
        initviews();
        return root;
    }
    private void init(){
        busy_start = root.findViewById(R.id.vedit_startdaytv);
        busy_end = root.findViewById(R.id.vedit_enddaytv);
        start_card = root.findViewById(R.id.vedit_startday);
        end_card = root.findViewById(R.id.vedit_endday);
        name = root.findViewById(R.id.vedit_nameet);
        platenumber = root.findViewById(R.id.vedit_platenumberet);
        yearofman = root.findViewById(R.id.vedit_madeinet);
        totalhoursrun = root.findViewById(R.id.vedit_ktotalrunhourset);
        kmperhour = root.findViewById(R.id.vedit_runkmhret);
        fuelconsumptionrate = root.findViewById(R.id.vedit_fuelconsumptionet);
        avgfuelconsumption = root.findViewById(R.id.vedit_avgfuelconsumptionet);
        rentperhourwithfuel = root.findViewById(R.id.vedit_rentperhourwfet);
        rentperdaywithfuel = root.findViewById(R.id.vedit_rentperdaywfet);
        rentperhourwithoutfuel = root.findViewById(R.id.vedit_rentperhourwofet);
        rentperdaywithoutfuel = root.findViewById(R.id.vedit_rentperdaywofet);
        available = root.findViewById(R.id.avavailblenowbg);
        progressBar = root.findViewById(R.id.vedit_progress);
        listeners();
    }

    private void listeners(){
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    map.put("name",editable.toString().trim());
                }
            }
        });
        yearofman.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    map.put("yom",editable.toString().trim());
                }
            }
        });
        platenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    map.put("plate_no",editable.toString().toLowerCase().trim());
                }
            }
        });
        totalhoursrun.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    map.put("total_run_hrs",Integer.parseInt(editable.toString().trim()));
                }
            }
        });
        kmperhour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    map.put("run_km_hr",Integer.parseInt(editable.toString().trim()));
                }
            }
        });
        fuelconsumptionrate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    map.put("fuel_consumption_rate",Integer.parseInt(editable.toString().trim()));
                }
            }
        });
        avgfuelconsumption.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    map.put("fuel_average_consumption_rate",Integer.parseInt(editable.toString().trim()));
                }
            }
        });
        rentperhourwithfuel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    map.put("rent_per_hour_with_fuel",Integer.parseInt(editable.toString().trim()));
                }
            }
        });
        rentperhourwithoutfuel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    map.put("rent_per_hour_without_fuel",Integer.parseInt(editable.toString().trim()));
                }
            }
        });
        rentperdaywithfuel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    map.put("rent_per_day_with_fuel",Integer.parseInt(editable.toString().trim()));
                }
            }
        });
        rentperdaywithoutfuel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    map.put("rent_per_day_without_fuel",Integer.parseInt(editable.toString().trim()));
                }
            }
        });
        start_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        (datePicker, i, i1, i2) -> {
                            startstring =  i2 + "/" + i1 + "/" + i;
                            busy_start.setText(startstring);
                            map.put("busy_start",startstring);
                        },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        end_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        (datePicker, i, i1, i2) -> {
                            endstring =  i2 + "/" + i1 + "/" + i;
                            busy_end.setText(endstring);
                            map.put("busy_end",endstring);
                        },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        available.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                isAvailable = root.findViewById(checkedId) == root.findViewById(R.id.yes);
                if(isAvailable){
                    map.put("availibility",1);
                }else{
                    map.put("availibility",0);
                }
            }
        });
    }

    private void initviews(){
        donebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(getActivity(),R.id.nav_host_fragment).popBackStack();
                try {
                    updateVehicleInfo();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateVehicleInfo() throws Exception{
        if(vid == 0){
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<Map> call = o.updateVehicleData(map);
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if(response.isSuccessful()){
                    Map map = response.body();

                    if(!map.get("name").toString().isEmpty()){
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "Changes wil reflect shortly", Toast.LENGTH_SHORT).show();
                        NavHostFragment.findNavController(OwnerEditVehicleFragment.this).popBackStack();
                    }
                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Changes wil reflect shortly", Toast.LENGTH_SHORT).show();
            }
        });
    }
}