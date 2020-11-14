package com.lifecapable.vehicledriver.owner.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.datamodel.HomeOwnerData;
import java.util.List;
import java.util.Locale;

public class HomeOwnerAdapter extends RecyclerView.Adapter<HomeOwnerAdapter.HomeOwnerViewHolder> {
    List<HomeOwnerData> mList;
    Context mContext;
    Fragment fragment;
    public static HomeOwnerData homeOwnerData;

    public HomeOwnerAdapter(List<HomeOwnerData> mList, Context mContext, Fragment fragment) {
        this.mList = mList;
        this.mContext = mContext;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public HomeOwnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.owner_card_home,parent,false);
        return new HomeOwnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeOwnerViewHolder holder, int position) {
        HomeOwnerData curr = mList.get(position);
        //holder.oaddresstv.setText(curr.getAddress());
        holder.ocontacttv.setText(curr.getUser_mobile());
        holder.onametv.setText(curr.getUser_name());
        holder.ovehicletv.setText(curr.getVehicle_plate());
        holder.timetv.setText(curr.getDate_of_enquiry());
        holder.ocontactbt.setOnClickListener(v -> {
            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData(Uri.parse("tel:"+curr.getUser_mobile()));
            if (ActivityCompat.checkSelfPermission(mContext,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mContext.startActivity(phoneIntent);

        });
        holder.ocardrelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeOwnerData = mList.get(position);
                if(homeOwnerData.getLat() == 0.0 || homeOwnerData.getLon() == 0.0){
                    Toast.makeText(mContext, "Location of this enquiry is not available", Toast.LENGTH_SHORT).show();
                    return;
                }
                NavHostFragment.findNavController(fragment).navigate(R.id.action_nav_home_owner_to_nav_ViewEnquiry_owner);
            }
        });

        // Added to remove enquries from menu
        holder.carddelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mList.size());
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class HomeOwnerViewHolder extends RecyclerView.ViewHolder{
        TextView oaddresstv,ocontacttv,onametv,ovehicletv;
        Button ocontactbt;
        AppCompatImageView carddelete;
        TextView timetv;
        RelativeLayout ocardrelative;
        public HomeOwnerViewHolder(@NonNull View itemView) {
            super(itemView);
            //oaddresstv = itemView.findViewById(R.id.ocardaddress);
            ocontacttv = itemView.findViewById(R.id.ocardcontact);
            ocontactbt = itemView.findViewById(R.id.ocardbutton);
            onametv = itemView.findViewById(R.id.ocardname);
            ovehicletv = itemView.findViewById(R.id.ocardvehicle);
            ocardrelative = itemView.findViewById(R.id.ocardrl);
            timetv = itemView.findViewById(R.id.ocardtime);
            carddelete = itemView.findViewById(R.id.carddelete);
        }
    }


}
