package com.lifecapable.vehicledriver.owner.ui.slideshow;

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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.DriverOwnerAdapter;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.DriverOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.DriverRoot;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OwnerSlideshowFragment extends Fragment {
    View root;
    RecyclerView driverRecycleview;
    DriverOwnerAdapter driverOwnerAdapter;
    List<DriverOwnerData> driverList;
    TextView drivercounttv;
    Button driveraddbutton;
    OwnerJsonPlaceHolder listDriverPlaceHolder;
    Retrofit retrofit;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    int id;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_fragment_slideshow, container, false);
        driverRecycleview = root.findViewById(R.id.odriverrecycle);
        drivercounttv = root.findViewById(R.id.oslidedrivercount);
        driveraddbutton = root.findViewById(R.id.oslideadddriver);
        sharedPreferences = this.getActivity().getSharedPreferences("owner", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id",0);
        progressBar = root.findViewById(R.id.driver_progress);

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        inithome();
        initdriverRecycle(id);

        return root;
    }
    private void inithome(){
        driveraddbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_nav_slideshow_owner_to_nav_AddDriver_owner);
            }
        });
    }

    private void initdriverRecycle(int id) {
        progressBar.setVisibility(View.VISIBLE);
        driverList = new ArrayList<>();
        if(id == 0){
            return;
        }
        OwnerJsonPlaceHolder ownerJsonPlaceHolder = RestAdapter.createAPI();
        Call<DriverRoot> call = ownerJsonPlaceHolder.getDriverList(id);
        call.enqueue(new Callback<DriverRoot>() {
            @Override
            public void onResponse(Call<DriverRoot> call, Response<DriverRoot> response) {
                DriverRoot driverRoot = response.body();
                if(driverRoot != null){
                    driverOwnerAdapter = new DriverOwnerAdapter(driverRoot.getDrivers(),getContext());
                    driverRecycleview.setLayoutManager(new LinearLayoutManager(getContext()));
                    driverRecycleview.setAdapter(driverOwnerAdapter);
                    driverRecycleview.scheduleLayoutAnimation();
                    progressBar.setVisibility(View.INVISIBLE);
                    drivercounttv.setText(String.valueOf(driverRoot.getDrivers().size()));
                }
            }

            @Override
            public void onFailure(Call<DriverRoot> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
}