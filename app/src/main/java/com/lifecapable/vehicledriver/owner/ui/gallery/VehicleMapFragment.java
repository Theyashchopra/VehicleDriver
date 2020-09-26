package com.lifecapable.vehicledriver.owner.ui.gallery;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.PicassoMarker.PicassoMarker;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.LocationObject;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleMapFragment extends Fragment implements OnMapReadyCallback {

    OwnerJsonPlaceHolder ownerJsonPlaceHolder;
    Call<LocationObject> call;
    int vehicle_id;
    GoogleMap googleMap;
    View view;
    MapView mapView;
    Timer timer;
    PicassoMarker marker;
    boolean first;
    private float start_rotation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_vehicle_map, container, false);
        timer = new Timer();
        vehicle_id = 0;
        first = true;
        if(getArguments() != null){
            vehicle_id = getArguments().getInt("vid");
            Log.i("VID",String.valueOf(vehicle_id));
        }

        mapView = view.findViewById(R.id.mapView);
        mapView.getMapAsync(VehicleMapFragment.this);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        ownerJsonPlaceHolder = RestAdapter.createAPI();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        perform();
        setHasOptionsMenu(true);
        return  view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public void perform(){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(vehicle_id == 0){
                    return;
                }
                call = ownerJsonPlaceHolder.getLocation(vehicle_id);
                call.enqueue(new Callback<LocationObject>() {
                    @Override
                    public void onResponse(Call<LocationObject> call, Response<LocationObject> response) {
                        LocationObject locationObject = response.body();
                        if(locationObject != null){
                            try {
                                if(first) {
                                    Log.i("LOCATION", locationObject.getLat() + "," + locationObject.getLon());
                                    LatLng latLng = new LatLng(locationObject.getLat(), locationObject.getLon());
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
                                    first = false;
                                }
                                updateLocation(locationObject);
                            }catch (Exception e){/*eat*/}
                        }
                    }

                    @Override
                    public void onFailure(Call<LocationObject> call, Throwable t) {

                    }
                });
            }
        }, 0, 3000);//put here time 1000 milliseconds=1 second
    }

    private void updateLocation(LocationObject locationObject){
        if(marker == null){
            LatLng latLng = new LatLng(locationObject.getLat(),locationObject.getLon());
            marker = new PicassoMarker(googleMap.addMarker(new MarkerOptions().position(latLng)));
            Picasso.with(view.getContext()).load(R.mipmap.car).resize( 70,  70)
                    .into(marker);
        }
        Location location = new Location("");
        location.setLatitude(locationObject.getLon());
        location.setLongitude(locationObject.getLon());
        moveVechile(marker.getmMarker(),location);
        rotateMarker(marker.getmMarker(),location.getBearing(),start_rotation);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                timer.cancel();
                timer.purge();
                NavHostFragment.findNavController(VehicleMapFragment.this).popBackStack();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                timer.cancel();
                timer.purge();
                NavHostFragment.findNavController(VehicleMapFragment.this).popBackStack();
                break;
        }
        return true;
    }

    public void moveVechile(final Marker myMarker, final Location finalPosition) {

        final LatLng startPosition = myMarker.getPosition();

        final Handler handler = new Handler(Looper.getMainLooper());
        final long start = SystemClock.uptimeMillis();
        final Interpolator interpolator = new AccelerateDecelerateInterpolator();
        final float durationInMs = 3000;
        final boolean hideMarker = false;

        handler.post(new Runnable() {
            long elapsed;
            float t;
            float v;

            @Override
            public void run() {

                // Calculate progress using interpolator
                elapsed = SystemClock.uptimeMillis() - start;
                t = elapsed / durationInMs;
                v = interpolator.getInterpolation(t);

                LatLng currentPosition = new LatLng(
                        startPosition.latitude * (1 - t) + (finalPosition.getLatitude()) * t,
                        startPosition.longitude * (1 - t) + (finalPosition.getLongitude()) * t);
                myMarker.setPosition(currentPosition);
                // myMarker.setRotation(finalPosition.getBearing());


                // Repeat till progress is completeelse
                if (t < 1) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                    // handler.postDelayed(this, 100);
                } else {
                    if (hideMarker) {
                        myMarker.setVisible(false);
                    } else {
                        myMarker.setVisible(true);
                    }
                }
            }
        });


    }


    public void rotateMarker(final Marker marker, final float toRotation, final float st) {
        final Handler handler = new Handler(Looper.getMainLooper());
        final long start = SystemClock.uptimeMillis();
        final float startRotation = marker.getRotation();
        final long duration = 1555;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed / duration);

                float rot = t * toRotation + (1 - t) * startRotation;


                marker.setRotation(-rot > 180 ? rot / 2 : rot);
                start_rotation = -rot > 180 ? rot / 2 : rot;
                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });
    }
}