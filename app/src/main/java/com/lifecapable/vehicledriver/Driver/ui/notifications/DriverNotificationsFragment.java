package com.lifecapable.vehicledriver.Driver.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowId;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.lifecapable.vehicledriver.Driver.adapters.RestAdapter;
import com.lifecapable.vehicledriver.Driver.datamodels.DriverData;
import com.lifecapable.vehicledriver.Driver.datamodels.ListHomeDriverData;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.Driver.dialogs.DriverLogoutPopup;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverNotificationsFragment extends Fragment {
    int driver_id;
    View root;
    TextInputEditText emailet, contactet, alternatecontactet, aadharet, nameet;
    String email,contact,alternatecontact, aadhar, name;
    Button dlogout;
    Button deditbt, ddonebt;
    DriverLogoutPopup logoutPopup;
    SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.driver_fragment_notifications, container, false);

        emailet = root.findViewById(R.id.dpemailet);
        contactet = root.findViewById(R.id.dpcontactet);
        alternatecontactet = root.findViewById(R.id.dpaltcontactet);
        aadharet = root.findViewById(R.id.dpaadharet);
        nameet = root.findViewById(R.id.dpnameet);

        dlogout = root.findViewById(R.id.dlogout);
        deditbt = root.findViewById(R.id.dpedit);
        ddonebt = root.findViewById(R.id.dpdone);

        sharedPreferences = requireActivity().getSharedPreferences("driver", Context.MODE_PRIVATE);
        driver_id = sharedPreferences.getInt("driverid",0);
        logoutPopup = new DriverLogoutPopup();
        initviews();
        fetchData();

        return root;
    }

    private void initviews(){

        nameet.setEnabled(false);
        contactet.setEnabled(false);
        alternatecontactet.setEnabled(false);
        emailet.setEnabled(false);
        aadharet.setEnabled(false);

        deditbt.setOnClickListener(v -> {
            nameet.setEnabled(true);
            contactet.setEnabled(true);
            alternatecontactet.setEnabled(true);
            emailet.setEnabled(true);
            aadharet.setEnabled(true);

            ddonebt.setVisibility(View.VISIBLE);
            deditbt.setVisibility(View.GONE);


        });
        ddonebt.setOnClickListener(v -> {
            nameet.setEnabled(false);
            contactet.setEnabled(false);
            alternatecontactet.setEnabled(false);
            emailet.setEnabled(false);
            aadharet.setEnabled(false);
            ddonebt.setVisibility(View.GONE);
            deditbt.setVisibility(View.VISIBLE);

        });
        dlogout.setOnClickListener(v -> {
            logoutPopup.show(requireActivity().getSupportFragmentManager(),"logout");
            logout();
        });

    }

    private void fetchData(){
        Call<DriverData> call = RestAdapter.createAPI().ogetProfile(driver_id);
        call.enqueue(new Callback<DriverData>() {
            @Override
            public void onResponse(@NotNull Call<DriverData> call, @NotNull Response<DriverData> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Something went wrong"+response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                DriverData res = response.body();
                if (res != null){
                    name = res.getName();
                    email = res.getEmail();
                    aadhar = res.getAdhaar();
                    contact = res.getMobile();
                    alternatecontact = res.getMobile2();

                    nameet.setText(name);
                    emailet.setText(email);
                    aadharet.setText(aadhar);
                    contactet.setText(contact);
                    alternatecontactet.setText(alternatecontact);

                }
            }

            @Override
            public void onFailure(@NotNull Call<DriverData> call, @NotNull Throwable t) {

            }
        });
    }

    private void logout(){

    }
}