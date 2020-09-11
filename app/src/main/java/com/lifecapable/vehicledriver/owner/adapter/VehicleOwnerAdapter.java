package com.lifecapable.vehicledriver.owner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
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
        holder.vehname.setText(curr.getVehiclename());
        holder.vehnumber.setText(curr.getVehiclenumber());
        holder.vehremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class VehicleOwnerViewHolder extends RecyclerView.ViewHolder{
        TextView vehname,vehnumber;
        Button vehremove;
        public VehicleOwnerViewHolder(@NonNull View itemView) {
            super(itemView);
            vehname = itemView.findViewById(R.id.ovcardmodelname);
            vehnumber = itemView.findViewById(R.id.ovcardnumber);
            vehremove = itemView.findViewById(R.id.ovcardremove);
        }
    }
}
