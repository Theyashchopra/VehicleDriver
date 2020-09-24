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

    List<VehicleOwnerData> list;
    Context context;
    Dialog dialogFragment;

    public SelectVehicleOwnerAdapter(List<VehicleOwnerData> list, Context context, Dialog dialogFragment) {
        this.list = list;
        this.context = context;
        this.dialogFragment = dialogFragment;
    }

    @NonNull
    @Override
    public SelectVehicleOwnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.owner_dialog_select_vehicle,parent, false);

        return new SelectVehicleOwnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectVehicleOwnerViewHolder holder, int position) {
        VehicleOwnerData curr = list.get(position);

        holder.name.setText(curr.getName());
        holder.plate.setText(curr.getPlate_no());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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
        public SelectVehicleOwnerViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.svname);
            plate = itemView.findViewById(R.id.svplate);
            linearLayout = itemView.findViewById(R.id.svll);

        }
    }
}
