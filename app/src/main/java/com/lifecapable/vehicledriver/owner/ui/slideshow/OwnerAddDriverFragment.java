package com.lifecapable.vehicledriver.owner.ui.slideshow;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lifecapable.vehicledriver.R;

public class OwnerAddDriverFragment extends Fragment {

    View root;
    Button btdone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.owner_fragment_add_driver, container, false);
        btdone = root.findViewById(R.id.addone);
        inithome();
        return root;
    }

    private void inithome(){
        btdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigateUp();
            }
        });
    }
}