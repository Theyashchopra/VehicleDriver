package com.lifecapable.vehicledriver.owner.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleOwnerData;

import java.util.List;

public class LocateOwnerAdapter extends RecyclerView.Adapter<LocateOwnerAdapter.LocateOwnerViewHolder> {

    List<VehicleOwnerData> list;
    Context context;
    Fragment fragment;

    public LocateOwnerAdapter(List<VehicleOwnerData> list, Context context, Fragment fragment) {
        this.list = list;
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public LocateOwnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.owner_card_locate,parent,false);
        return new LocateOwnerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LocateOwnerViewHolder holder, int position) {
        VehicleOwnerData curr = list.get(position);
        holder.textView.setText(curr.getPlate_no());
        holder.relativeLayout.setOnClickListener(view -> {
            Bundle args = new Bundle();
            args.putInt("Vehicleid",curr.getV_id());
            NavHostFragment.findNavController(fragment).navigate(R.id.action_nav_locate_owner_to_nav_vehicle_map);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class LocateOwnerViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        RelativeLayout relativeLayout;
        public LocateOwnerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.locatename);
            relativeLayout = itemView.findViewById(R.id.locaterelative);
        }
    }
}
