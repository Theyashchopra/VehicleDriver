package com.lifecapable.vehicledriver.owner.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.VehicleOwnerAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.ListVehicleOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleOwnerData;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OwnerGalleryFragment extends Fragment {
    View root;
    RecyclerView vehicleRecycleview;
    VehicleOwnerAdapter vehicleOwnerAdapter;
    List<VehicleOwnerData> vehicleList;
    TextView vehiclecounttv;
    Button vehicleaddbutton;

    OwnerJsonPlaceHolder listVehiclePlaceHolder;
    Retrofit retrofit;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_fragment_gallery, container, false);
        vehicleRecycleview = root.findViewById(R.id.ogalleryrecycle);
        vehiclecounttv = root.findViewById(R.id.ogalleryvehiclecount);
        vehicleaddbutton = root.findViewById(R.id.ogalleryaddvehicle);

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        initothers();
        initVehicleRecycle();
        vehicleaddbutton.setOnClickListener(v -> {

            NavHostFragment.findNavController(OwnerGalleryFragment.this).navigate(R.id.action_nav_gallery_owner_to_nav_AddNewVehicle_owner);
        });
        return root;
    }

    private void initothers(){

    }

    public void initVehicleRecycle(){
        vehicleList =new ArrayList<>();
        /*vehicleList.add(new VehicleOwnerData("TATA","MH31A1234"));
        vehicleList.add(new VehicleOwnerData("TATA","MH31A1234"));
        vehicleList.add(new VehicleOwnerData("TATA","MH31A1234"));
        vehicleList.add(new VehicleOwnerData("TATA","MH31A1234"));
        vehicleList.add(new VehicleOwnerData("TATA","MH31A1234"));
        vehicleList.add(new VehicleOwnerData("TATA","MH31A1234"));
        vehicleList.add(new VehicleOwnerData("TATA","MH31A1234"));*/
        listVehiclePlaceHolder = retrofit.create(OwnerJsonPlaceHolder.class);
        Call<ListVehicleOwnerData> call = listVehiclePlaceHolder.ogetVehicleList(123);

        call.enqueue(new Callback<ListVehicleOwnerData>() {
            @Override
            public void onResponse(Call<ListVehicleOwnerData> call, Response<ListVehicleOwnerData> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Somethings Wrong I can feel it"+response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                ListVehicleOwnerData res =response.body();
                if(res!=null){
                    vehicleList.addAll(res.getVehicles());
                }
            }
            @Override
            public void onFailure(Call<ListVehicleOwnerData> call, Throwable t) {
                    Toast.makeText(getContext(), "Somethings Wrong I can feel it"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        vehicleOwnerAdapter = new VehicleOwnerAdapter(vehicleList, getContext());
        vehicleRecycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        vehicleRecycleview.setAdapter(vehicleOwnerAdapter);
        vehiclecounttv.setText(vehicleList.size()+"");
    }
}