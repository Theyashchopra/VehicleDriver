package com.lifecapable.vehicledriver.owner.ui.locate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.LocateOwnerAdapter;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.ListVehicleOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleOwnerData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerLocateFragment extends Fragment {

    View root;
    List<VehicleOwnerData> list;
    LocateOwnerAdapter locateOwnerAdapter;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    int id;
    CardView cardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.owner_fragment_locate, container, false);
        recyclerView = root.findViewById(R.id.locaterecycle);
        cardView = root.findViewById(R.id.lc);
        sharedPreferences = this.requireActivity().getSharedPreferences("owner", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id",0);
        loadRecycle();
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(OwnerLocateFragment.this).navigate(R.id.action_nav_locate_owner_to_nav_AllVehicleMap_owner);
            }
        });
        return root;
    }
    private void loadRecycle(){
        if(id == 0){
            Toast.makeText(getContext(), "Please login again", Toast.LENGTH_SHORT).show();
            return;
        }
        list =new ArrayList<>();
        Call<ListVehicleOwnerData> call = RestAdapter.createAPI().ogetVehicleList(id);

        call.enqueue(new Callback<ListVehicleOwnerData>() {
            @Override
            public void onResponse(@NotNull Call<ListVehicleOwnerData> call, @NotNull Response<ListVehicleOwnerData> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Somethings Wrong I can feel it"+response.message(), Toast.LENGTH_SHORT).show();
          //          progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                ListVehicleOwnerData res =response.body();
                if(res!=null){
                    list = res.getVehicles();
                    locateOwnerAdapter = new LocateOwnerAdapter(list, getContext(),OwnerLocateFragment.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(locateOwnerAdapter);
                    recyclerView.scheduleLayoutAnimation();
        //            progressBar.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onFailure(@NotNull Call<ListVehicleOwnerData> call, @NotNull Throwable t) {
                Toast.makeText(getContext(), "Somethings Wrong I can feel it"+t.getMessage(), Toast.LENGTH_SHORT).show();
             //   progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}