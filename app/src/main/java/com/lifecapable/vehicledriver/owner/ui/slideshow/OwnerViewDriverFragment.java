package com.lifecapable.vehicledriver.owner.ui.slideshow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.dialogs.OwnerImageViewPopup;

import java.security.acl.Owner;

public class OwnerViewDriverFragment extends Fragment {
    View root;
    TextView clickviewlicence;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.owner_fragment_view_driver, container, false);
        clickviewlicence = root.findViewById(R.id.vdlicence);
        clickviewlicence.setOnClickListener(v -> {

            OwnerImageViewPopup ownerImageViewPopup = new OwnerImageViewPopup();
            ownerImageViewPopup.show(getActivity().getSupportFragmentManager(),"View Image");

        });
        return root;
    }
}