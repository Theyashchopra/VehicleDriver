package com.lifecapable.vehicledriver.owner.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.DriverOwnerAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.DriverOwnerData;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;

import java.util.ArrayList;
import java.util.List;

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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_fragment_slideshow, container, false);
        driverRecycleview = root.findViewById(R.id.odriverrecycle);
        drivercounttv = root.findViewById(R.id.oslidedrivercount);
        driveraddbutton = root.findViewById(R.id.oslideadddriver);


        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        inithome();
        initdriverRecycle();

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

    private void initdriverRecycle() {
        driverList = new ArrayList<>();

        /*driverList.add(new DriverOwnerData("My Name is","1234567890"));
        driverList.add(new DriverOwnerData("My Name is","1234567890"));
        driverList.add(new DriverOwnerData("My Name is","1234567890"));
        driverList.add(new DriverOwnerData("My Name is","1234567890"));
        driverList.add(new DriverOwnerData("My Name is","1234567890"));
        driverList.add(new DriverOwnerData("My Name is","1234567890"));
        driverList.add(new DriverOwnerData("My Name is","1234567890"));
        driverList.add(new DriverOwnerData("My Name is","1234567890"));
        driverList.add(new DriverOwnerData("My Name is","1234567890"));
*/
        listDriverPlaceHolder = retrofit.create(OwnerJsonPlaceHolder.class);
 /*       Call<List<DriverOwnerData>> call = listVehiclePlaceHolder.ogetVehicleList(123);

        call.enqueue(new Callback<List<DriverOwnerData>>() {
            @Override
            public void onResponse(Call<List<DriverOwnerData>> call, Response<List<DriverOwnerData>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Somethings Wrong I can feel it"+response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                for (DriverOwnerData v: response.body()){

                }
            }

            @Override
            public void onFailure(Call<List<DriverOwnerData>> call, Throwable t) {

            }
        });*/
        driverOwnerAdapter = new DriverOwnerAdapter(driverList,getContext());
        driverRecycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        driverRecycleview.setAdapter(driverOwnerAdapter);
    }
}