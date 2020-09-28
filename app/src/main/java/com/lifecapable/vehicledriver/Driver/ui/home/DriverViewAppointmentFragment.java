package com.lifecapable.vehicledriver.Driver.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lifecapable.vehicledriver.R;


public class DriverViewAppointmentFragment extends Fragment {

    TextView nametv, addresstv,customermobiletv, alternatemobiletv, startdatetv, enddatetv, timetv;
    Button contact1bt, contact2bt;

    String name, address, customermobile,alternatemobile, startdate, enddate, time;
    int id;
    Bundle args;

    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.driver_fragment_view_appointment, container, false);

        args = getArguments();
        nametv = root.findViewById(R.id.dvacustomername);
        addresstv = root.findViewById(R.id.dvaaddress);
        customermobiletv = root.findViewById(R.id.dvacustomercontact);
        alternatemobiletv = root.findViewById(R.id.dvacustomeralternatecontact);
        startdatetv = root.findViewById(R.id.dvastartdate);
        enddatetv = root.findViewById(R.id.dvaenddate);
        timetv = root.findViewById(R.id.dvatime);
        contact1bt = root.findViewById(R.id.dvacustomercontactbt);
        contact2bt = root.findViewById(R.id.dvacustomeraltcontactbt);

        if(args != null) {
            name = args.getString("customername");
            address = args.getString("address");
            customermobile = args.getString("customermobile");
            alternatemobile = args.getString("alternatemobile");
            startdate = args.getString("startdate");
            enddate = args.getString("enddate");
            time = args.getString("time");
            id = args.getInt("id");

            nametv.setText(name);
            addresstv.setText(address);
            customermobiletv.setText(customermobile);
            alternatemobiletv.setText(alternatemobile);
            startdatetv.setText(startdate);
            enddatetv.setText(enddate);
            timetv.setText(time);
        }

        contact1bt.setOnClickListener(view -> {
            if(customermobile != null){
                Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                phoneIntent.setData(Uri.parse("tel:"+customermobile));
                if (ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                getContext().startActivity(phoneIntent);
            }
        });

        contact2bt.setOnClickListener(view -> {
            if(alternatemobile != null){
                Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                phoneIntent.setData(Uri.parse("tel:"+alternatemobile));
                if (ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                getContext().startActivity(phoneIntent);
            }
        });

        return root;
    }
}