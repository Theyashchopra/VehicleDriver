package com.lifecapable.vehicledriver.owner.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.DriverOwnerAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.DriverOwnerData;
import java.util.ArrayList;
import java.util.List;

public class OwnerSlideshowFragment extends Fragment {
    View root;
    RecyclerView driverRecycleview;
    DriverOwnerAdapter driverOwnerAdapter;
    List<DriverOwnerData> driverList;
    TextView drivercounttv;
    Button driveraddbutton;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_fragment_slideshow, container, false);
        driverRecycleview = root.findViewById(R.id.odriverrecycle);
        drivercounttv = root.findViewById(R.id.oslidedrivercount);
        driveraddbutton = root.findViewById(R.id.oslideadddriver);
        initdriverRecycle();

        return root;
    }

    private void initdriverRecycle() {
        driverList = new ArrayList<>();

        driverList.add(new DriverOwnerData("My Name is","1234567890"));
        driverList.add(new DriverOwnerData("My Name is","1234567890"));
        driverList.add(new DriverOwnerData("My Name is","1234567890"));
        driverList.add(new DriverOwnerData("My Name is","1234567890"));
        driverList.add(new DriverOwnerData("My Name is","1234567890"));
        driverList.add(new DriverOwnerData("My Name is","1234567890"));
        driverList.add(new DriverOwnerData("My Name is","1234567890"));
        driverList.add(new DriverOwnerData("My Name is","1234567890"));
        driverList.add(new DriverOwnerData("My Name is","1234567890"));

        driverOwnerAdapter = new DriverOwnerAdapter(driverList,getContext());
        driverRecycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        driverRecycleview.setAdapter(driverOwnerAdapter);
    }
}