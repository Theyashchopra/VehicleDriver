package com.lifecapable.vehicledriver.owner.ui.locate;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.ListVehicleOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleOwnerData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerAllVehicleMapFragment extends Fragment implements OnMapReadyCallback {

    View root;
    List<VehicleOwnerData> vehicleList;
    SharedPreferences sharedPreferences;
    int id;
    GoogleMap googleMap;
    MapView mapView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_fragment_all_vehicle_map, container, false);
        sharedPreferences = this.requireActivity().getSharedPreferences("owner", Context.MODE_PRIVATE);
        mapView = root.findViewById(R.id.allmapView);
        id = sharedPreferences.getInt("id", 0);
        mapView.getMapAsync(OwnerAllVehicleMapFragment.this);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        loadVehicles();
        try {
            MapsInitializer.initialize(requireActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

    private void loadVehicles() {
        if (id == 0) {
            Toast.makeText(getContext(), "Please login again", Toast.LENGTH_SHORT).show();
            return;
        }
        vehicleList = new ArrayList<>();
        Call<ListVehicleOwnerData> call = RestAdapter.createAPI().ogetVehicleList(id);

        call.enqueue(new Callback<ListVehicleOwnerData>() {
            @Override
            public void onResponse(@NotNull Call<ListVehicleOwnerData> call, @NotNull Response<ListVehicleOwnerData> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Somethings Wrong I can feel it" + response.message(), Toast.LENGTH_SHORT).show();
//                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                ListVehicleOwnerData res = response.body();
                if (res != null) {
                    vehicleList = res.getVehicles();
                    addMarkersOnMap(vehicleList);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ListVehicleOwnerData> call, @NotNull Throwable t) {
                Toast.makeText(getContext(), "Somethings Wrong I can feel it" + t.getMessage(), Toast.LENGTH_SHORT).show();
                //progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void addMarkersOnMap(List<VehicleOwnerData> vehiclesList){
        for(VehicleOwnerData vehicle : vehiclesList){
            try {
                LatLng latLng = new LatLng(vehicle.getLat(),vehicle.getLon());
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(vehicle.getPlate_no()).icon(bitmapDescriptorFromVector(getContext()));
                googleMap.addMarker(markerOptions);
                Log.e("Marker Position", vehicle.getLat()+" ," +vehicle.getLon());
                //map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(21.146002, 79.089984),13));
            }
            catch (Exception e){
                Log.e("Exception ",e.getMessage());
            }
        }
    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context){
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_car_top_view);
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng latLng = new LatLng(21.1529, 79.0820);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
    }
}