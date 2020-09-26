package com.lifecapable.vehicledriver.owner.ui.gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.adapter.VehicleOwnerAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.ListVehicleOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleOwnerData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerGalleryFragment extends Fragment {
    View root;
    RecyclerView vehicleRecycleview;
    VehicleOwnerAdapter vehicleOwnerAdapter;
    List<VehicleOwnerData> vehicleList;
    TextView vehiclecounttv;
    Button vehicleaddbutton;
    SharedPreferences sharedPreferences;
    int id;
    ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_fragment_gallery, container, false);
        vehicleRecycleview = root.findViewById(R.id.ogalleryrecycle);
        vehiclecounttv = root.findViewById(R.id.ogalleryvehiclecount);
        vehicleaddbutton = root.findViewById(R.id.ogalleryaddvehicle);
        sharedPreferences = this.requireActivity().getSharedPreferences("owner", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id",0);
        progressBar = root.findViewById(R.id.vehicle_progress);

        initothers();
        initVehicleRecycle();
        vehicleaddbutton.setOnClickListener(v -> NavHostFragment.findNavController(OwnerGalleryFragment.this).navigate(R.id.action_nav_gallery_owner_to_nav_master));
        return root;
    }

    private void initothers(){

    }

    public void initVehicleRecycle(){
        if(id == 0){
            Toast.makeText(getContext(), "Please login again", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        vehicleList =new ArrayList<>();
        Call<ListVehicleOwnerData> call = RestAdapter.createAPI().ogetVehicleList(id);

        call.enqueue(new Callback<ListVehicleOwnerData>() {
            @Override
            public void onResponse(@NotNull Call<ListVehicleOwnerData> call, @NotNull Response<ListVehicleOwnerData> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Somethings Wrong I can feel it"+response.message(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                ListVehicleOwnerData res =response.body();
                if(res!=null){
                    vehicleList = res.getVehicles();
                    vehicleOwnerAdapter = new VehicleOwnerAdapter(vehicleList, getContext());
                    vehicleRecycleview.setLayoutManager(new LinearLayoutManager(getContext()));
                    vehicleRecycleview.setAdapter(vehicleOwnerAdapter);
                    vehicleRecycleview.scheduleLayoutAnimation();
                    vehiclecounttv.setText(vehicleList.size()+"");
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onFailure(@NotNull Call<ListVehicleOwnerData> call, @NotNull Throwable t) {
                    Toast.makeText(getContext(), "Somethings Wrong I can feel it"+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}