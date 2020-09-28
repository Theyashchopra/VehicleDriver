package com.lifecapable.vehicledriver.owner.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.ProfileOwnerData;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerProfileFragment extends Fragment {

    View root;
    TextInputEditText nameEt, addressEt, pincodeEt, panEt, tanEt, gstEt, gumastaEt, emailEt, contactEt, contact2Et;
    Button editbt, donebt, logoutbt;
    Boolean state;
    TextView subnaem,alloted_vehicles;
    SharedPreferences sharedPreferences;
    int id;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_fragment_profile, container, false);
        state = true;

        sharedPreferences = this.requireActivity().getSharedPreferences("owner", Context.MODE_PRIVATE);

        id = sharedPreferences.getInt("id",0);

        nameEt = root.findViewById(R.id.opnameet);
        progressBar = root.findViewById(R.id.opsProgress);
        subnaem = root.findViewById(R.id.opsubscriptionname);
        alloted_vehicles = root.findViewById(R.id.opsallottedvehicles);
        addressEt = root.findViewById(R.id.opfulladdresset);
        panEt = root.findViewById(R.id.oppannumberet);
        pincodeEt = root.findViewById(R.id.oppincodeet);
        tanEt = root.findViewById(R.id.optannumberet);
        gumastaEt = root.findViewById(R.id.opregisternumberet);
        gstEt = root.findViewById(R.id.opgstnumberet);
//        ownernameEt = root.findViewById(R.id.opownernameet);
        emailEt = root.findViewById(R.id.opemailet);
        contactEt = root.findViewById(R.id.opcontact1et);
        contact2Et = root.findViewById(R.id.opcontact2et);
        //editbt = root.findViewById(R.id.opedit);
        //donebt = root.findViewById(R.id.opdone);
        //logoutbt = root.findViewById(R.id.oplogout);
        inithome();

        return root;
    }
    private void inithome(){

        nameEt.setEnabled(false);
        addressEt.setEnabled(false);
        panEt.setEnabled(false);
        pincodeEt.setEnabled(false);
        tanEt.setEnabled(false);
        gumastaEt.setEnabled(false);
        gstEt.setEnabled(false);
  //      ownernameEt.setActivated(false);
        emailEt.setEnabled(false);
        contactEt.setEnabled(false);
        contact2Et.setEnabled(false);

        getData();

       /* editbt.setOnClickListener(v -> {
                state = false;
                nameEt.setEnabled(true);
                addressEt.setEnabled(true);
                panEt.setEnabled(true);
                pincodeEt.setEnabled(true);
                tanEt.setEnabled(true);
                gumastaEt.setEnabled(true);
                gstEt.setEnabled(true);
   //             ownernameEt.setActivated(true);
                emailEt.setEnabled(true);
                contactEt.setEnabled(true);
                contact2Et.setEnabled(true);
                donebt.setVisibility(View.VISIBLE);
                editbt.setVisibility(View.GONE);
                logoutbt.setVisibility(View.GONE);
        });
        donebt.setOnClickListener(v -> {
                state = true;
                nameEt.setEnabled(false);
                addressEt.setEnabled(false);
                panEt.setEnabled(false);
                pincodeEt.setEnabled(false);
                tanEt.setEnabled(false);
                gumastaEt.setEnabled(false);
                gstEt.setEnabled(false);
       //         ownernameEt.setActivated(false);
                emailEt.setEnabled(false);
                contactEt.setEnabled(false);
                contact2Et.setEnabled(false);
                donebt.setVisibility(View.GONE);
                editbt.setVisibility(View.VISIBLE);
                logoutbt.setVisibility(View.VISIBLE);
        });*/
    }
    private void getData(){
        progressBar.setVisibility(View.VISIBLE);
        Call<ProfileOwnerData> call = RestAdapter.createAPI().ogetProfileData(id);
        call.enqueue(new Callback<ProfileOwnerData>() {
            @Override
            public void onResponse(@NotNull Call<ProfileOwnerData> call, @NotNull Response<ProfileOwnerData> response) {
                if (!response.isSuccessful()){
                    Log.e("Respose error ", response.message());
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "No internet access", Toast.LENGTH_SHORT).show();
                    return;
                }
                ProfileOwnerData res = response.body();
                if(res != null){
                    nameEt.setText(res.getName());
                    addressEt.setText(res.getFullAddress());
                    panEt.setText(res.getPan());
                    pincodeEt.setText(res.getPinCode());
                    tanEt.setText(res.getTan());
                    gumastaEt.setText(res.getGumasta());
                    gstEt.setText(res.getGst());
                    emailEt.setText(res.getEmail());
                    contactEt.setText(res.getMobile());
                    contact2Et.setText(res.getMobile2());
                    subnaem.setText(res.getSubscriptionName());
                    alloted_vehicles.setText(String.valueOf(res.getAllotedVehicles()));
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ProfileOwnerData> call, @NotNull Throwable t) {
                Toast.makeText(getContext(), "Something went wrong" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}