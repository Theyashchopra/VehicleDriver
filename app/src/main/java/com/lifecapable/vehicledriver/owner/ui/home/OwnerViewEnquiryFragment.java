package com.lifecapable.vehicledriver.owner.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.HomeOwnerAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.HomeOwnerData;
import com.lifecapable.vehicledriver.owner.dialogs.OwnerConfirmEnquiryPopup;
import com.lifecapable.vehicledriver.owner.ui.gallery.VehicleMapFragment;

import java.util.List;
import java.util.Locale;

public class OwnerViewEnquiryFragment extends Fragment implements OnMapReadyCallback {
    View root;
    Button call;
    OwnerConfirmEnquiryPopup ocEnquirypopup;
    TextView username,address,mobile;
    MapView mapView;
    HomeOwnerData homeOwnerData;
    GoogleMap googleMap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_fragment_view_enquiry, container, false);

        inithome();
        mapView = root.findViewById(R.id.mapView2);
        mapView.getMapAsync(OwnerViewEnquiryFragment.this);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        return root;
    }

    private void inithome(){
        homeOwnerData = HomeOwnerAdapter.homeOwnerData;
        call = root.findViewById(R.id.callUser);
        username = root.findViewById(R.id.header);
        address = root.findViewById(R.id.plateMap);
        mobile = root.findViewById(R.id.driverMap);
        setData();
    }

    private void setData(){
        address.setText(getAddress(homeOwnerData.getLat(),homeOwnerData.getLon()));
        username.setText(homeOwnerData.getUser_name());
        mobile.setText(homeOwnerData.getUser_mobile());
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                phoneIntent.setData(Uri.parse("tel:"+homeOwnerData.getUser_mobile()));
                if (ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                requireActivity().startActivity(phoneIntent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng latLng = new LatLng(homeOwnerData.getLat(),homeOwnerData.getLon());
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));
        googleMap.addMarker(new MarkerOptions().position(latLng));
    }
    private String getAddress(double latitude, double longitude){
        String location_address = "Address not available";
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            location_address = address;
        }catch (Exception e){}

        return location_address;
    }
}