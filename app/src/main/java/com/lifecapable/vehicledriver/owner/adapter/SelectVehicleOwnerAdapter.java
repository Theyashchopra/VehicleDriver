package com.lifecapable.vehicledriver.owner.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleOwnerData;

import java.util.List;

public class SelectVehicleOwnerAdapter extends RecyclerView.Adapter<SelectVehicleOwnerAdapter.SelectVehicleOwnerViewHolder> {

    public interface OnItemClick {
        void getPosition(int pos,VehicleOwnerData curr); //pass any things
    }

    List<VehicleOwnerData> list;
    Context context;
    Dialog dialogFragment;
    OnItemClick onItemClick;


    public SelectVehicleOwnerAdapter(List<VehicleOwnerData> list, Context context, Dialog dialogFragment, OnItemClick onItemClick) {
        this.list = list;
        this.context = context;
        this.dialogFragment = dialogFragment;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public SelectVehicleOwnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.owner_select_vehicle_card,parent, false);

        return new SelectVehicleOwnerViewHolder(view,onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectVehicleOwnerViewHolder holder, int position) {
        VehicleOwnerData curr = list.get(position);

        holder.name.setText(curr.getName());
        holder.plate.setText(curr.getPlate_no());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.getPosition(position,curr);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class SelectVehicleOwnerViewHolder extends RecyclerView.ViewHolder{
        TextView name, plate;
        LinearLayout linearLayout;
        OnItemClick onItemClick;
        public SelectVehicleOwnerViewHolder(@NonNull View itemView,OnItemClick onItemClick) {
            super(itemView);
            name = itemView.findViewById(R.id.svname);
            plate = itemView.findViewById(R.id.svplate);
            linearLayout = itemView.findViewById(R.id.svll);
            this.onItemClick = onItemClick;
        }
    }
}
