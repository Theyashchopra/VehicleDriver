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
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.datamodel.DriverOwnerData;
import java.util.List;

public class DriverOwnerAdapter extends RecyclerView.Adapter<DriverOwnerAdapter.DriverOwnerViewHolder> {
    List<DriverOwnerData> mList;
    Context mContext;

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
        holder.dcontact.setText(curr.getContact());
        holder.dcall.setOnClickListener(v -> {
            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData(Uri.parse("tel:"+curr.getContact()));
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
        public DriverOwnerViewHolder(@NonNull View itemView) {
            super(itemView);
            dname = itemView.findViewById(R.id.odcardname);
            dcontact = itemView.findViewById(R.id.odcardcontact);
            dcall = itemView.findViewById(R.id.odcardcall);
        }
    }
}
