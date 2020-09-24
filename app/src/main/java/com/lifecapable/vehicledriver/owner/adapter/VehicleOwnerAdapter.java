package com.lifecapable.vehicledriver.owner.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleOwnerData;
import java.util.List;

public class VehicleOwnerAdapter extends RecyclerView.Adapter<VehicleOwnerAdapter.VehicleOwnerViewHolder> {
    List<VehicleOwnerData> mList;
    Context mContext;

    public VehicleOwnerAdapter(List<VehicleOwnerData> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public VehicleOwnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.owner_card_vehicle,parent,false);
        return new VehicleOwnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleOwnerViewHolder holder, int position) {
        VehicleOwnerData curr = mList.get(position);
        holder.vehname.setText(curr.getName());
        holder.vehnumber.setText(curr.getPlate_no());
        holder.vehview.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putInt("Vehicleid",1);
            Navigation.findNavController(v).navigate(R.id.action_nav_gallery_owner_to_nav_viewvehicle_owner,args);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class VehicleOwnerViewHolder extends RecyclerView.ViewHolder{
        TextView vehname,vehnumber;
        Button vehview;
        public VehicleOwnerViewHolder(@NonNull View itemView) {
            super(itemView);
            vehname = itemView.findViewById(R.id.ovcardmodelname);
            vehnumber = itemView.findViewById(R.id.ovcardnumber);
            vehview = itemView.findViewById(R.id.ovcardview);
        }
    }
}
