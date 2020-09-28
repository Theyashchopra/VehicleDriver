package com.lifecapable.vehicledriver.Driver.adapters;

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
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.lifecapable.vehicledriver.Driver.datamodels.HomeDriverData;
import com.lifecapable.vehicledriver.R;

import java.util.List;

public class HomeDriverAdapter extends RecyclerView.Adapter<HomeDriverAdapter.HomeDriverViewHolder> {
    List<HomeDriverData> mList;
    Context mContext;
    Fragment fragment;

    public HomeDriverAdapter(List<HomeDriverData> mList, Context mContext, Fragment fragment) {
        this.mList = mList;
        this.mContext = mContext;
        this.fragment = fragment;
    }
    @NonNull
    @Override
    public HomeDriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.driver_card_home,parent,false);
        return new HomeDriverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeDriverViewHolder holder, int position) {
        HomeDriverData curr = mList.get(position);
        holder.directionsbt.setOnClickListener(v -> {
/*            String uri = "http://maps.google.com/maps?daddr=" + curr.getLat() + "," + curr.getLon() + " (" + "Job location" + ")";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            mContext.startActivity(intent);*/
            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData(Uri.parse("tel:"+curr.getCustomer_mobile()));
            if (ActivityCompat.checkSelfPermission(mContext,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mContext.startActivity(phoneIntent);
        });
        holder.drelative.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putInt("id",curr.getId());
            args.putString("customername",curr.getCustomer_name());
            args.putString("customermobile",curr.getCustomer_mobile());
            args.putString("alternatemobile",curr.getAlternate_mobile());
            args.putString("address",curr.getAddress());
            args.putString("time",curr.getTime());
            args.putString("startdate",curr.getStart_day());
            args.putString("enddate",curr.getEnd_day());
            NavHostFragment.findNavController(fragment).navigate(R.id.action_navigation_home_driver_to_navigation_ViewAppointment_driver, args);
        });
        holder.name.setText(curr.getCustomer_name());
        holder.time.setText(curr.getStart_day());
        holder.contact.setText(curr.getCustomer_mobile());
        holder.address.setText(curr.getAddress());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static public class HomeDriverViewHolder extends RecyclerView.ViewHolder{
        TextView name,time, address, contact;
        Button directionsbt;
        RelativeLayout drelative;
        public HomeDriverViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.dnamecard) ;
            time = itemView.findViewById(R.id.dtimecard);
            address = itemView.findViewById(R.id.daddresscard);
            contact = itemView.findViewById(R.id.dcontactcard);
            directionsbt = itemView.findViewById(R.id.ddirectionscard);
            drelative = itemView.findViewById(R.id.dhcard);
        }
    }
}
