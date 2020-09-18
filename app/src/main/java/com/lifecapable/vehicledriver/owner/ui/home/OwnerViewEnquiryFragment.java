package com.lifecapable.vehicledriver.owner.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.dialogs.OwnerConfirmEnquiryPopup;

public class OwnerViewEnquiryFragment extends Fragment {
    View root;
    Button confirmbt;
    OwnerConfirmEnquiryPopup ocEnquirypopup;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_fragment_view_enquiry, container, false);
        ocEnquirypopup = new OwnerConfirmEnquiryPopup();
        confirmbt = root.findViewById(R.id.veconfirm);
        inithome();
        return root;
    }

    private void inithome(){
        confirmbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocEnquirypopup.show(getActivity().getSupportFragmentManager(),"confirm enquiry");
            }
        });
    }
}