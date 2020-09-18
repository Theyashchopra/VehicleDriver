package com.lifecapable.vehicledriver.owner.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.lifecapable.vehicledriver.R;

public class OwnerConfirmEnquiryPopup extends DialogFragment {

    View root;
    Button yesbt,nobt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_confirm_enquiry_popup, container, false);
        yesbt = root.findViewById(R.id.ceyes);
        nobt = root.findViewById(R.id.ceno);

        Dialog dialog = getDialog();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        yesbt.setOnClickListener(v ->{
            logout();
            dialog.dismiss();

        });
        nobt.setOnClickListener(v ->{

            dialog.dismiss();

        });
        return root;
    }

    public void logout(){

    }
}
