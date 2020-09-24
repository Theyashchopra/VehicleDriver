package com.lifecapable.vehicledriver.owner.ui.home;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.HomeOwnerAdapter;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.HomeOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.RootEnquiry;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OwnerHomeFragment extends Fragment {

    ProgressBar progressBar;
    View root;
    RecyclerView homeRecycle;
    TextView homeenquirycount;
    HomeOwnerAdapter homeadapter;
    List<HomeOwnerData> homeList;
    SharedPreferences sharedPreferences;
    int id;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_fragment_home, container, false);
        homeRecycle = root.findViewById(R.id.ohomerecycle);
        progressBar = root.findViewById(R.id.home_progress);
        homeenquirycount = root.findViewById(R.id.ohomeenquirycount);
        sharedPreferences = this.getActivity().getSharedPreferences("owner", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id",0);
        inithome();
        inithomeRecycle();
        return root;
    }
    private void inithome(){
        homeenquirycount.setText("0");
    }

    private void inithomeRecycle(){
        if(id == 0){
            Toast.makeText(getContext(), "Something went wrong, please login again", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i("ID",String.valueOf(id));
        homeList = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        OwnerJsonPlaceHolder ownerJsonPlaceHolder = RestAdapter.createAPI();
        Call<RootEnquiry> call = ownerJsonPlaceHolder.getEnquiries(id);
        call.enqueue(new Callback<RootEnquiry>() {
            @Override
            public void onResponse(Call<RootEnquiry> call, Response<RootEnquiry> response) {
                if(response.isSuccessful()){
                    RootEnquiry rootEnquiry = response.body();
                    homeList = rootEnquiry.getEnquiries();
                    homeadapter = new HomeOwnerAdapter(homeList,getContext(),OwnerHomeFragment.this);
                    homeRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
                    homeRecycle.setAdapter(homeadapter);
                    homeRecycle.scheduleLayoutAnimation();
                    progressBar.setVisibility(View.INVISIBLE);
                    ValueAnimator animator = new ValueAnimator();
                    animator.setObjectValues(0, homeList.size());
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            homeenquirycount.setText(String.valueOf(animation.getAnimatedValue()));
                        }
                    });
                    animator.setDuration(500);
                    animator.start();
                }
            }

            @Override
            public void onFailure(Call<RootEnquiry> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
}