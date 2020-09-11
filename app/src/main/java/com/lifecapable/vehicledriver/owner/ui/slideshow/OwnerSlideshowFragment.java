package com.lifecapable.vehicledriver.owner.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.lifecapable.vehicledriver.R;

public class OwnerSlideshowFragment extends Fragment {
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         root = inflater.inflate(R.layout.owner_fragment_slideshow, container, false);

        return root;
    }
}