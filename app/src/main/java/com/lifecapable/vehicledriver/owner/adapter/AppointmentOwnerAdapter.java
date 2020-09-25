package com.lifecapable.vehicledriver.owner.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.datamodel.AppointmentOwnerData;

import java.util.List;

public class AppointmentOwnerAdapter extends RecyclerView.Adapter<AppointmentOwnerAdapter.AppointmentOwnerViewHolder> {

    List<AppointmentOwnerData> list;
    Context context;
    Fragment fragment;

    public AppointmentOwnerAdapter(List<AppointmentOwnerData> list, Context context, Fragment fragment) {
        this.list = list;
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public AppointmentOwnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.owner_card_appointment,parent, false);
        return new AppointmentOwnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentOwnerViewHolder holder, int position) {
        AppointmentOwnerData curr = list.get(position);
        holder.name.setText(curr.getCustomer_name());
        holder.address.setText(curr.getAddress());
        holder.call.setOnClickListener(view -> {
            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData(Uri.parse("tel:"+curr.getCustomer_mobile()));
            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            context.startActivity(phoneIntent);
        });
        holder.relativeLayout.setOnClickListener(view -> {
            Bundle args = new Bundle();
            args.putInt("id",curr.getId());
            args.putString("address",curr.getAddress());
            args.putString("cname",curr.getCustomer_name());
            args.putString("mobile",curr.getCustomer_mobile());
            args.putString("alternatemobile",curr.getAlternate_mobile());
            args.putString("startdate",curr.getStart_day());
            args.putString("enddate",curr.getEnd_day());
            args.putString("time",curr.getTime());
            args.putInt("vid",curr.getVehicle_id());
            NavHostFragment.findNavController(fragment).navigate(R.id.action_nav_appointments_owner_to_nav_viewAppointment_owner, args);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class AppointmentOwnerViewHolder extends RecyclerView.ViewHolder{
        TextView name, address;
        Button call;
        RelativeLayout relativeLayout;

        public AppointmentOwnerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.apcardcname);
            address = itemView.findViewById(R.id.apcardaddress);
            call = itemView.findViewById(R.id.apcardcall);
            relativeLayout = itemView.findViewById(R.id.apcardrl);
        }
    }
}
