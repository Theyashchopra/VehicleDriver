package com.lifecapable.vehicledriver.owner.ui.slideshow;

import android.content.Context;
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
import com.lifecapable.vehicledriver.owner.datamodel.DriverOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleOwnerData;
import com.lifecapable.vehicledriver.owner.dialogs.OwnerSelectVehiclePopup;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditDriverFragment extends Fragment implements  OwnerSelectVehiclePopup.OnClosePassData{

    TextInputEditText name,adhaar,mobile,password;
    TextView textView;
    CardView cardView;
    Button save;
    View view;
    int id,oid;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    OwnerSelectVehiclePopup ownerSelectVehiclePopup;
    Map<String,Object> map;
    DriverOwnerData driverOwnerData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_driver, container, false);
        driverOwnerData = OwnerViewDriverFragment.driverOwnerData;
        sharedPreferences = this.getActivity().getSharedPreferences("owner", Context.MODE_PRIVATE);
        map = new HashMap<>();
        id = 0;
        oid = sharedPreferences.getInt("id",0);
        if(getArguments() != null){
            id = getArguments().getInt("did");
            Log.i("DID",String.valueOf(id));
        }
        map.put("id",id);
        init();
        return view;
    }

    private void init(){
        name = view.findViewById(R.id.ednameet);
        name.setText(driverOwnerData.getName());

        adhaar = view.findViewById(R.id.edadhaaret);
        adhaar.setText(driverOwnerData.getAdhaar());

        mobile = view.findViewById(R.id.edmobileet);
        mobile.setText(driverOwnerData.getMobile2());

        password = view.findViewById(R.id.edpasswordet);

        cardView = view.findViewById(R.id.adassignedvehicle);
        textView = view.findViewById(R.id.adassignedvehicletext);
        textView.setText(driverOwnerData.getVehicle_plate());
        
        progressBar = view.findViewById(R.id.edit_driver_progress);
        save = view.findViewById(R.id.update_driver);
        listeners();
    }

    private void listeners(){
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putInt("oid",oid);
                ownerSelectVehiclePopup = new OwnerSelectVehiclePopup();
                ownerSelectVehiclePopup.setArguments(args);
                ownerSelectVehiclePopup.setTargetFragment(EditDriverFragment.this,1);
                ownerSelectVehiclePopup.show(getFragmentManager(),"Dialog Select Vehicle");
                Log.e("Add Vehicle","Dialog launched");
            }
        });
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
        adhaar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    map.put("adhaar",editable.toString().trim());
                }
            }
        });
        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    map.put("mobile2",editable.toString());
                }
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    map.put("password",editable.toString());
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    updateDriverFields();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void getAssigned(int pos, VehicleOwnerData curr) {
        //assignedVehicle = curr;
        try {
            textView.setText(curr.getName());
            map.put("vehicle_id",curr.getV_id());
        }catch (Exception e){
            Toast.makeText(getContext(), "You did not chose any vehicle", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDriverFields() throws Exception{
        if(id == 0){
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<Map> call = o.updateDriverData(map);
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                if(response.isSuccessful()){
                    Map map = response.body();
                    if(!map.get("name").toString().isEmpty()){
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                        NavHostFragment.findNavController(EditDriverFragment.this).popBackStack();
                    }
                }else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
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