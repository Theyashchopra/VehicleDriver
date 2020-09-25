package com.lifecapable.vehicledriver.owner.ui.appointment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.AppointmentOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleOwnerData;
import com.lifecapable.vehicledriver.owner.dialogs.OwnerSelectVehiclePopup;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class OwnerAddNewAppointmentFragment extends Fragment implements  OwnerSelectVehiclePopup.OnClosePassData{

    View root;
    TextInputEditText cname, address, customer_mobile, alternate_mobile;
    CardView start,end,time,vehicle;
    TextView starttv,endtv,timetv,vehicletv;
    String startstring, endstring, timestring;
    Button done;
    SharedPreferences owner;
    int oid;
    OwnerSelectVehiclePopup ownerSelectVehiclePopup;
    VehicleOwnerData assignedVehicle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_fragment_add_new_appointment, container, false);
        cname = root.findViewById(R.id.nacustomernameet);
        address = root.findViewById(R.id.naaddresset);
        customer_mobile = root.findViewById(R.id.nacustomermobileet);
        alternate_mobile = root.findViewById(R.id.nacustomeralternateet);

        start = root.findViewById(R.id.nastartday);
        end = root.findViewById(R.id.naendday);
        time = root.findViewById(R.id.natime);
        vehicle = root.findViewById(R.id.naselectvehicle);
        starttv = root.findViewById(R.id.nastartdaytv);
        endtv = root.findViewById(R.id.naenddaytv);
        timetv = root.findViewById(R.id.natimetv);
        vehicletv = root.findViewById(R.id.naselectvehicletv);
        done = root.findViewById(R.id.nadone);
        owner = getActivity().getSharedPreferences("owner",MODE_PRIVATE);
        oid = owner.getInt("id",-1);
        initviews();


        return root;
    }

    private void initviews(){
        start.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (datePicker, i, i1, i2) -> {
                        startstring =  i2 + "/" + i1 + "/" + i;
                        starttv.setText(startstring);
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
                        endstring =  i2 + "/" + i1 + "/" + i;
                        endtv.setText(endstring);
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
                    },
                    Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                    Calendar.getInstance().get(Calendar.MINUTE),
                    true
            );
            timePickerDialog.show();
        });
        vehicletv.setOnClickListener(view -> {
             Bundle args = new Bundle();
             args.putInt("oid", oid);
             ownerSelectVehiclePopup = new OwnerSelectVehiclePopup();
             ownerSelectVehiclePopup.setArguments(args);
             ownerSelectVehiclePopup.setTargetFragment(OwnerAddNewAppointmentFragment.this,1);
             ownerSelectVehiclePopup.show(getFragmentManager(),"Dialog Select Vehicle");
            Log.e("Add Vehicle","Dialog launched");

        });
        done.setOnClickListener(view -> uploadData());

    }

    private void uploadData(){
        if(cname.getText() == null || address.getText() == null || customer_mobile.getText() == null || alternate_mobile.getText() == null || startstring == null || endstring == null || timestring == null || vehicle == null){
            Toast.makeText(getContext(), "You left something empty!!", Toast.LENGTH_SHORT).show();
            return;
        }
        Call<AppointmentOwnerData> call = RestAdapter.createAPI().oaddAppointment(cname.getText().toString(),address.getText().toString(),customer_mobile.getText().toString(), alternate_mobile.getText().toString(),oid,assignedVehicle.getV_id(),startstring,endstring,timestring);
        call.enqueue(new Callback<AppointmentOwnerData>() {
            @Override
            public void onResponse(Call<AppointmentOwnerData> call, Response<AppointmentOwnerData> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Somethings Wrong I can feel it"+response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                AppointmentOwnerData res = response.body();
                if(res!=null){
                    Log.e("Yoooooooooo", res.getCustomer_name());
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigateUp();

                }
            }
            @Override
            public void onFailure(Call<AppointmentOwnerData> call, Throwable t) {
                Toast.makeText(getContext(), "Somethings Wrong I can feel it"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void getAssigned(int pos, VehicleOwnerData curr) {
        assignedVehicle = curr;
        vehicletv.setText(curr.getName());
        Log.e("Yo ", curr.getName());
    }
}