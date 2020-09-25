package com.lifecapable.vehicledriver.owner.ui.gallery;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.dialogs.OwnerDialogGetImageFragment;
import com.lifecapable.vehicledriver.owner.ui.slideshow.OwnerAddImageDriverFragment;

public class OwnerRcVehicleFragment extends Fragment implements OwnerDialogGetImageFragment.MyDialogCloseListener, OwnerDialogGetImageFragment.onPhotoSelectedListener{

    OwnerDialogGetImageFragment dgi;
    Uri imageUri;
    Bitmap imagebitmap;
    View root;
    String name;
    int vid;
    ImageView rcimage,invoice,insurance,vfront,vside,vback;
    Uri rcUri,invoiceUri,insuranceUri,vfrontUri,vsideUri,vbackUri;
    Bitmap rcBitmap,invoiceBitmap,insuranceBitmap,vfrontBitmap,vsideBitmap,vbackBitmap;
    Button rcSave,invoiceSave,insuranceSave,vfrontSave,vsideSave,vbackSave;
    ProgressBar rcProgress,invoiceProgress,insuranceProgress,vfrontProgress,vsideProgress,vbackProgress;
    Button rcdone;
    String plate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.owner_fragment_rc_vehicle, container, false);
        rcdone = root.findViewById(R.id.rcdone);
        if(getArguments() != null){
            name = getArguments().getString("vname");
            getArguments().getInt("vehicleid");
            plate = getArguments().getString("plate");
        }
        rcdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(OwnerRcVehicleFragment.this).navigate(R.id.action_nav_RcVehicle_owner_to_nav_gallery_owner);
            }
        });
        init();
        return root;
    }
    public void init(){
        //image views
        rcimage = root.findViewById(R.id.rc_image);
        invoice = root.findViewById(R.id.invoice_image);
        insurance = root.findViewById(R.id.insurance_image);
        vfront = root.findViewById(R.id.vfront_image);
        vside = root.findViewById(R.id.vside_image);
        vback = root.findViewById(R.id.vback_image);
        //buttons
        rcSave = root.findViewById(R.id.save_rc);
        invoiceSave = root.findViewById(R.id.save_invoice);
        insuranceSave = root.findViewById(R.id.save_insurance);
        vfrontSave = root.findViewById(R.id.save_vfront);
        vsideSave = root.findViewById(R.id.save_vside);
        vbackSave = root.findViewById(R.id.save_vback);
        //progress bars
        rcProgress = root.findViewById(R.id.rc_progress);
        invoiceProgress = root.findViewById(R.id.invoice_progress);
        insuranceProgress = root.findViewById(R.id.insurance_progress);
        vfrontProgress = root.findViewById(R.id.vfront_progress);
        vbackProgress = root.findViewById(R.id.vback_progress);
        vsideProgress = root.findViewById(R.id.vside_progress);
        listeners();
    }


    @Override
    public void getImagePath(Uri imagePath) {
        imageUri = imagePath;
        imagebitmap = null;
    }

    @Override
    public void getImageBitmap(Bitmap bitmap) {
        imagebitmap = bitmap;
        imageUri = null;
    }

    @Override
    public void handleDialogClose(int num) {
        if(num == 1){

        }
    }

    private void listeners(){
        rcimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putInt("curr",1);
                dgi = new OwnerDialogGetImageFragment();
                dgi.setArguments(args);
                dgi.setTargetFragment(OwnerRcVehicleFragment.this,1);
                dgi.show(getFragmentManager(),"Dialog get fragment");
                Log.e("Add Driver","Dialog launched");
            }
        });
        insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putInt("curr",3);
                dgi = new OwnerDialogGetImageFragment();
                dgi.setArguments(args);
                dgi.setTargetFragment(OwnerRcVehicleFragment.this,3);
                dgi.show(getFragmentManager(),"Dialog get fragment");
                Log.e("Add Driver","Dialog launched");
            }
        });
        invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putInt("curr",2);
                dgi = new OwnerDialogGetImageFragment();
                dgi.setArguments(args);
                dgi.setTargetFragment(OwnerRcVehicleFragment.this,2);
                dgi.show(getFragmentManager(),"Dialog get fragment");
                Log.e("Add Driver","Dialog launched");
            }
        });
        vfront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putInt("curr",4);
                dgi = new OwnerDialogGetImageFragment();
                dgi.setArguments(args);
                dgi.setTargetFragment(OwnerRcVehicleFragment.this,4);
                dgi.show(getFragmentManager(),"Dialog get fragment");
                Log.e("Add Driver","Dialog launched");
            }
        });
        vside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putInt("curr",6);
                dgi = new OwnerDialogGetImageFragment();
                dgi.setArguments(args);
                dgi.setTargetFragment(OwnerRcVehicleFragment.this,6);
                dgi.show(getFragmentManager(),"Dialog get fragment");
                Log.e("Add Driver","Dialog launched");
            }
        });
        vback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putInt("curr",5);
                dgi = new OwnerDialogGetImageFragment();
                dgi.setArguments(args);
                dgi.setTargetFragment(OwnerRcVehicleFragment.this,5);
                dgi.show(getFragmentManager(),"Dialog get fragment");
                Log.e("Add Driver","Dialog launched");
            }
        });


    }
}