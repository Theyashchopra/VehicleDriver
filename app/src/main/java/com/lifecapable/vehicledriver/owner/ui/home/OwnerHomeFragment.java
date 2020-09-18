package com.lifecapable.vehicledriver.owner.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.HomeOwnerAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.HomeOwnerData;

import java.util.ArrayList;
import java.util.List;


public class OwnerHomeFragment extends Fragment {

    View root;
    RecyclerView homeRecycle;
    TextView homeenquirycount;
    HomeOwnerAdapter homeadapter;
    List<HomeOwnerData> homeList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_fragment_home, container, false);
        homeRecycle = root.findViewById(R.id.ohomerecycle);
        homeenquirycount = root.findViewById(R.id.ohomeenquirycount);
        inithome();
        inithomeRecycle();
        return root;
    }
    private void inithome(){
        homeenquirycount.setText("0");
    }
    private void inithomeRecycle(){
        homeList = new ArrayList<>();
        homeList.add(new HomeOwnerData("address dlaksjdkadlasdoajsdajsn;jasnjas","8806627745","XYZ","MH31A1234"));
        homeList.add(new HomeOwnerData("address dlaksjdkadlasdoajsdajsn;jasnjas","8806627745","XYZ","MH31A1234"));
        homeList.add(new HomeOwnerData("address dlaksjdkadlasdoajsdajsn;jasnjas","8806627745","XYZ","MH31A1234"));
        homeList.add(new HomeOwnerData("address dlaksjdkadlasdoajsdajsn;jasnjas","8806627745","XYZ","MH31A1234"));
        homeList.add(new HomeOwnerData("address dlaksjdkadlasdoajsdajsn;jasnjas","8806627745","XYZ","MH31A1234"));
        homeList.add(new HomeOwnerData("address dlaksjdkadlasdoajsdajsn;jasnjas","8806627745","XYZ","MH31A1234"));
        homeList.add(new HomeOwnerData("address dlaksjdkadlasdoajsdajsn;jasnjas","8806627745","XYZ","MH31A1234"));
        homeList.add(new HomeOwnerData("address dlaksjdkadlasdoajsdajsn;jasnjas","8806627745","XYZ","MH31A1234"));
        homeList.add(new HomeOwnerData("address dlaksjdkadlasdoajsdajsn;jasnjas","8806627745","XYZ","MH31A1234"));
        homeList.add(new HomeOwnerData("address dlaksjdkadlasdoajsdajsn;jasnjas","8806627745","XYZ","MH31A1234"));
        homeList.add(new HomeOwnerData("address dlaksjdkadlasdoajsdajsn;jasnjas","8806627745","XYZ","MH31A1234"));
        homeList.add(new HomeOwnerData("address dlaksjdkadlasdoajsdajsn;jasnjas","8806627745","XYZ","MH31A1234"));
        homeList.add(new HomeOwnerData("address dlaksjdkadlasdoajsdajsn;jasnjas","8806627745","XYZ","MH31A1234"));
        homeadapter = new HomeOwnerAdapter(homeList,getContext(),OwnerHomeFragment.this);
        homeRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        homeRecycle.setAdapter(homeadapter);
        homeenquirycount.setText(homeList.size()+"");
    }
}