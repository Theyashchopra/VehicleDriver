package com.lifecapable.vehicledriver.owner.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.lifecapable.vehicledriver.R;

public class OwnerProfileFragment extends Fragment {

    View root;
    TextInputEditText nameEt, addressEt, pincodeEt, panEt, tanEt, gstEt, gumastaEt, ownernameEt, emailEt, contactEt, contact2Et;
    Button editbt, donebt, logoutbt;
    Boolean state;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_fragment_profile, container, false);
        state = false;
        nameEt = root.findViewById(R.id.opnameet);
        addressEt = root.findViewById(R.id.opfulladdresset);
        panEt = root.findViewById(R.id.oppannumberet);
        pincodeEt = root.findViewById(R.id.oppincodeet);
        tanEt = root.findViewById(R.id.optannumberet);
        gumastaEt = root.findViewById(R.id.opregisternumberet);
        gstEt = root.findViewById(R.id.opgstnumberet);
        ownernameEt = root.findViewById(R.id.opownernameet);
        emailEt = root.findViewById(R.id.opemailet);
        contactEt = root.findViewById(R.id.opcontact1et);
        contact2Et = root.findViewById(R.id.opcontact2et);
        editbt = root.findViewById(R.id.opedit);
        donebt = root.findViewById(R.id.opdone);
        logoutbt = root.findViewById(R.id.oplogout);
        inithome();

        return root;
    }
    private void inithome(){
        editbt.setOnClickListener(v -> {
            if(state){
                nameEt.setActivated(true);
                addressEt.setActivated(true);
                panEt.setActivated(true);
                pincodeEt.setActivated(true);
                tanEt.setActivated(true);
                gumastaEt.setActivated(true);
                gstEt.setActivated(true);
                ownernameEt.setActivated(true);
                emailEt.setActivated(true);
                contactEt.setActivated(true);
                contact2Et.setActivated(true);
                donebt.setVisibility(View.VISIBLE);
                editbt.setVisibility(View.GONE);
                logoutbt.setVisibility(View.GONE);
            }
        });
        donebt.setOnClickListener(v -> {
            if(!state){
                nameEt.setActivated(false);
                addressEt.setActivated(false);
                panEt.setActivated(false);
                pincodeEt.setActivated(false);
                tanEt.setActivated(false);
                gumastaEt.setActivated(false);
                gstEt.setActivated(false);
                ownernameEt.setActivated(false);
                emailEt.setActivated(false);
                contactEt.setActivated(false);
                contact2Et.setActivated(false);
                donebt.setVisibility(View.GONE);
                editbt.setVisibility(View.VISIBLE);
                logoutbt.setVisibility(View.VISIBLE);
            }
        });


    }
}