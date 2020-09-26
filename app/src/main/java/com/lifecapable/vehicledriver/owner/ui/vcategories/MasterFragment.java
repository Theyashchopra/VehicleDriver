package com.lifecapable.vehicledriver.owner.ui.vcategories;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.categories.MasterRoot;
import com.lifecapable.vehicledriver.owner.categories.MasterVehicle;
import com.lifecapable.vehicledriver.owner.categories.VehicleModel;
import com.lifecapable.vehicledriver.owner.categories.VehicleModelRoot;
import com.lifecapable.vehicledriver.owner.categories.VehicleType;
import com.lifecapable.vehicledriver.owner.categories.VehicleTypeRoot;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MasterFragment extends Fragment {

    int id = 0;
    Button next;
    List<MasterVehicle> masterVehicleList;
    List<VehicleModel> vehicleModelList;
    List<VehicleType> vehicleTypeList;
    Spinner spinner1,spinner2,spinner3;
    List<String> master,type,models;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_master, container, false);
        initialise();
        listeners();
        return view;
    }
    private void initialise(){
        master = new ArrayList<String>();
        type = new ArrayList<String>();
        models = new ArrayList<String>();
        spinner1 = view.findViewById(R.id.avcategory);
        spinner2 = view.findViewById(R.id.avsubcategoery1);
        spinner3 = view.findViewById(R.id.avsubcategoery2);
        next = view.findViewById(R.id.next);

        masterVehicleList = new ArrayList<>();
        vehicleModelList = new ArrayList<>();
        vehicleTypeList = new ArrayList<>();

        getMasters();
    }

    public void getModels(int id){
        models.clear();
        OwnerJsonPlaceHolder api = RestAdapter.createAPI();
        Call<VehicleModelRoot> call = api.getVModels(id);
        List<String> spinnerList = new ArrayList<>();
        call.enqueue(new Callback<VehicleModelRoot>() {
            @Override
            public void onResponse(Call<VehicleModelRoot> call, Response<VehicleModelRoot> response) {
                if(response.isSuccessful()){
                    VehicleModelRoot vehicleModelRoot = response.body();
                    vehicleModelList = vehicleModelRoot.getModels();
                    if(vehicleModelRoot != null){
                        for(VehicleModel v : vehicleModelRoot.getModels()){
                            models.add(v.getModel_name());
                        }
                        try {
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, models);
                            spinner3.setAdapter(adapter);
                        }catch (Exception e){

                        }
                    }
                }else{
                    Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VehicleModelRoot> call, Throwable t) {
                Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getMasters(){
        master.clear();
        OwnerJsonPlaceHolder api = RestAdapter.createAPI();
        Call<MasterRoot> call = api.getMaster();
        call.enqueue(new Callback<MasterRoot>() {
            @Override
            public void onResponse(Call<MasterRoot> call, Response<MasterRoot> response) {
                if (response.isSuccessful()){
                    MasterRoot masterRoot = response.body();
                    masterVehicleList = masterRoot.getMasters();
                    for(MasterVehicle m : masterRoot.getMasters()){
                        master.add(m.getName());
                    }
                    try {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, master);
                        spinner1.setAdapter(adapter);
                    }catch (Exception e){}
                }else{
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<MasterRoot> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getTypes(int id){
        type.clear();
        OwnerJsonPlaceHolder api = RestAdapter.createAPI();
        Call<VehicleTypeRoot> call = api.getVtypes(id);
        call.enqueue(new Callback<VehicleTypeRoot>() {
            @Override
            public void onResponse(Call<VehicleTypeRoot> call, Response<VehicleTypeRoot> response) {
                if(response.isSuccessful()){
                    VehicleTypeRoot vehicleTypeRoot = response.body();
                    vehicleTypeList = vehicleTypeRoot.getMasters();
                    for(VehicleType v : vehicleTypeRoot.getMasters()){
                        type.add(v.getVehicle_type_name());
                    }
                    try {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,type);
                        spinner2.setAdapter(adapter);
                    }catch (Exception e){

                    }

                }else{
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VehicleTypeRoot> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void listeners(){
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                for(MasterVehicle m : masterVehicleList){
                    if(m.getName().equals(master.get(i))){
                        getTypes(m.getId());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                for(VehicleType v : vehicleTypeList){
                    if(v.getVehicle_type_name().equals(type.get(i))){
                        getModels(v.getId());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                for(VehicleModel v : vehicleModelList){
                    if(v.getModel_name().equals(models.get(i))){
                        id = v.getId();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id == 0){
                    Toast.makeText(getContext(), "Please Select a model first", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bundle args = new Bundle();
                args.putInt("model_id",id);
                NavController navController = NavHostFragment.findNavController(MasterFragment.this);
                navController.navigate(R.id.action_nav_master_to_nav_AddNewVehicle_owner,args);
            }
        });
    }


}