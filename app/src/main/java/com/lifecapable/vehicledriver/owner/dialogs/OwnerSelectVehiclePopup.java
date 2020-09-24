package com.lifecapable.vehicledriver.owner.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.adapter.SelectVehicleOwnerAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.ListVehicleOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.SelectVehicleOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleOwnerData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerSelectVehiclePopup extends DialogFragment implements SelectVehicleOwnerAdapter.OnItemClick {

    public interface OnClosePassData{
        void getAssigned(int pos, VehicleOwnerData curr);
    }
    View root;
    List<VehicleOwnerData> list;
    RecyclerView recyclerView;
    SelectVehicleOwnerAdapter selectVehicleOwnerAdapter;
    int ownerid;
    ProgressBar selectprogress;
    int clickpos;
    VehicleOwnerData clickitem;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root =  inflater.inflate(R.layout.owner_dialog_select_vehicle, container, false);
        recyclerView = root.findViewById(R.id.selectvehiclerecycle);
        selectprogress = root.findViewById(R.id.selectvehicleprogress);
        if(getArguments() != null){
            ownerid = getArguments().getInt("oid");
        }
        initRecycle();
        return root;
    }

    private void initRecycle(){
        list = new ArrayList<>();
        Call<ListVehicleOwnerData> call = RestAdapter.createAPI().ogetVehicleList(ownerid);
        call.enqueue(new Callback<ListVehicleOwnerData>() {
            @Override
            public void onResponse(Call<ListVehicleOwnerData> call, Response<ListVehicleOwnerData> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getContext(), "Something went wrong"+ response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body() != null){
                    ListVehicleOwnerData res = response.body();
                    list.addAll(res.getVehicles());
                }
                selectVehicleOwnerAdapter = new SelectVehicleOwnerAdapter(list,getContext(),getDialog(), OwnerSelectVehiclePopup.this);
                recyclerView.setAdapter(selectVehicleOwnerAdapter);
                selectprogress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ListVehicleOwnerData> call, Throwable t) {

            }
        });

    }

    @Override
    public void getPosition(int pos, VehicleOwnerData curr) {
        clickpos = pos;
        clickitem = curr;
        getDialog().dismiss();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Fragment f1 = getTargetFragment();
        if (f1 instanceof OnClosePassData)
            ((OnClosePassData)f1).getAssigned(clickpos,clickitem);
    }
}
