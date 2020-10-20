package com.lifecapable.vehicledriver.owner.ui.appointment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleOwnerData;
import com.lifecapable.vehicledriver.owner.dialogs.OwnerSelectVehiclePopup;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class EditAppointmentFragment extends Fragment implements OwnerSelectVehiclePopup.OnClosePassData {

    SharedPreferences sharedPreferences;
    String customernamestring, addressstring, mobilestring, alternatemobilestring, startdatestring, enddatestring;
    TextInputEditText cname, address, customer_mobile, alternate_mobile;
    CardView start,end,time,vehicle;
    TextView starttv,endtv,timetv,vehicletv;
    String startstring, endstring, timestring;
    Button done;
    SharedPreferences owner;
    int oid;
    OwnerSelectVehiclePopup ownerSelectVehiclePopup;
    VehicleOwnerData assignedVehicle;
    View view;
    Map<String,Object> map;
    int id,vid;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_appointment, container, false);
        map = new HashMap<>();
        map.put("owner_id",owner);
        id = 0;
        vid = 0;
        if(getArguments() != null){
            id = getArguments().getInt("id");
            vid = getArguments().getInt("vid");
            map.put("id",id);
            map.put("vehicle_id",vid);
            Log.i("ID",String.valueOf(id));
        }
        cname = view.findViewById(R.id.eacustomernameet);
        address = view.findViewById(R.id.eaaddresset);
        customer_mobile = view.findViewById(R.id.eacustomermobileet);
        alternate_mobile = view.findViewById(R.id.eacustomeralternateet);
        progressBar = view.findViewById(R.id.edit_progress);
        start = view.findViewById(R.id.eastartday);
        end = view.findViewById(R.id.eaendday);
        time = view.findViewById(R.id.eatime);
        vehicle = view.findViewById(R.id.easelectvehicle);
        starttv = view.findViewById(R.id.eastartdaytv);
        endtv = view.findViewById(R.id.eaenddaytv);
        timetv = view.findViewById(R.id.eatimetv);
        vehicletv = view.findViewById(R.id.easelectvehicletv);
        done = view.findViewById(R.id.eadone);
        owner = this.getActivity().getSharedPreferences("owner",MODE_PRIVATE);
        oid = owner.getInt("id",-1);
        map.put("owner_id",oid);
        setFields();
        init();
        return view;
    }
    private void setFields(){
        getDatafromArgument();
        cname.setText(customernamestring);
        address.setText(addressstring);
        customer_mobile.setText(mobilestring);
        alternate_mobile.setText(alternatemobilestring);
    }
    private void getDatafromArgument(){
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
    private void init(){
        listeners();
        start.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (datePicker, i, i1, i2) -> {
                        startstring =  i2 + "/" + (i1+1) + "/" + i;
                        Log.i("start",startstring);
                        starttv.setText(startstring);
                        map.put("start",startstring);
                    },
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
        end.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (datePicker, i, i1, i2) -> {
                        endstring =  i2 + "/" + (i1+1) + "/" + i;
                        endtv.setText(endstring);
                        map.put("end",endstring);
                    },
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
        time.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog =new TimePickerDialog(
                    getContext(),
                    (timePicker, i, i1) -> {
                        timestring = i+":"+i1;
                        timetv.setText(timestring);
                        map.put("time",timestring);
                    },
                    Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                    Calendar.getInstance().get(Calendar.MINUTE),
                    true
            );
            timePickerDialog.show();
        });
        vehicle.setOnClickListener(view -> {
            Bundle args = new Bundle();
            args.putInt("oid", oid);
            ownerSelectVehiclePopup = new OwnerSelectVehiclePopup();
            ownerSelectVehiclePopup.setArguments(args);
            ownerSelectVehiclePopup.setTargetFragment(EditAppointmentFragment.this,1);
            ownerSelectVehiclePopup.show(getFragmentManager(),"Dialog Select Vehicle");
            Log.e("Add Vehicle","Dialog launched");

        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    updateData();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    private void listeners(){
        cname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()) {
                    map.put("customer_name", editable.toString().trim());
                }
            }
        });
        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()) {
                    map.put("address", editable.toString().trim());
                }
            }
        });
        customer_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()) {
                    map.put("customer_mobile", editable.toString().trim());
                }
            }
        });
        alternate_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    map.put("alternate_mobile",editable.toString());
                }
            }
        });

    }
    @Override
    public void getAssigned(int pos, VehicleOwnerData curr) {
        assignedVehicle = curr;
        try {
            vehicletv.setText(curr.getName());
            map.put("vehicle_id",curr.getV_id());
        }catch (Exception e){
            Toast.makeText(getContext(), "You didn't choose any vehicle", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateData()  throws Exception{
        if(id == 0){
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<Map> call = o.editAppointment(map);
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if(response.isSuccessful()){
                    Map newMap = response.body();
                    if(!newMap.get("customer_name").toString().isEmpty()){
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "Updated Successfully, Changes will be reflected shortly", Toast.LENGTH_SHORT).show();
                        NavHostFragment.findNavController(EditAppointmentFragment.this).popBackStack();
                    }else{
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}