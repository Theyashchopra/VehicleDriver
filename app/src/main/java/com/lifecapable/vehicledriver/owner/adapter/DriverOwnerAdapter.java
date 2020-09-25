package com.lifecapable.vehicledriver.owner.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.datamodel.DriverOwnerData;
import java.util.List;

public class DriverOwnerAdapter extends RecyclerView.Adapter<DriverOwnerAdapter.DriverOwnerViewHolder> {
    List<DriverOwnerData> mList;
    Context mContext;
    public static DriverOwnerData driverOwnerData;
    public DriverOwnerAdapter(List<DriverOwnerData> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public DriverOwnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.owner_card_driver,parent,false);
        return new DriverOwnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverOwnerViewHolder holder, int position) {
        DriverOwnerData curr = mList.get(position);
        holder.dname.setText(curr.getName());
        holder.dcontact.setText(curr.getMobile());
        holder.drelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driverOwnerData = mList.get(position);
                Navigation.findNavController(v).navigate(R.id.action_nav_slideshow_owner_to_nav_ViewDriver_owner);
            }
        });
        holder.dcall.setOnClickListener(v -> {
            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData(Uri.parse("tel:"+curr.getMobile()));
            if (ActivityCompat.checkSelfPermission(mContext,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mContext.startActivity(phoneIntent);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class DriverOwnerViewHolder extends RecyclerView.ViewHolder{
        TextView dname, dcontact;
        Button dcall;
        RelativeLayout drelative;
        public DriverOwnerViewHolder(@NonNull View itemView) {
            super(itemView);
            dname = itemView.findViewById(R.id.odcardname);
            dcontact = itemView.findViewById(R.id.odcardcontact);
            dcall = itemView.findViewById(R.id.odcardcall);
            drelative = itemView.findViewById(R.id.odcardrl);
        }
    }
}
