package com.lifecapable.vehicledriver.Driver.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lifecapable.vehicledriver.Driver.datamodels.HomeDriverData;
import com.lifecapable.vehicledriver.R;

import java.util.List;

public class HomeDriverAdapter extends RecyclerView.Adapter<HomeDriverAdapter.HomeDriverViewHolder> {
    List<HomeDriverData> mList;
    Context mContext;

    public HomeDriverAdapter(List<HomeDriverData> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
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


        });
        holder.time.setText(curr.getTime());
        holder.contact.setText(curr.getContact());
        holder.address.setText(curr.getAddress());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static public class HomeDriverViewHolder extends RecyclerView.ViewHolder{
        TextView time, address, contact;
        Button directionsbt;
        public HomeDriverViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.dtimecard);
            address = itemView.findViewById(R.id.daddresscard);
            contact = itemView.findViewById(R.id.dcontactcard);
            directionsbt = itemView.findViewById(R.id.ddirectionscard);
        }
    }
}
