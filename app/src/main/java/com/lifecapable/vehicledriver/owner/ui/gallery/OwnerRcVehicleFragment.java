package com.lifecapable.vehicledriver.owner.ui.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.Message;
import com.lifecapable.vehicledriver.owner.dialogs.OwnerDialogGetImageFragment;
import com.lifecapable.vehicledriver.owner.imagepickers.FileUtil;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;
import com.lifecapable.vehicledriver.owner.ui.slideshow.OwnerAddImageDriverFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        buttonClickListeners();
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
            if(imagebitmap != null){
                rcimage.setImageBitmap(imagebitmap);
                rcUri = getImageUri(getContext(),imagebitmap);
            }else if(imageUri != null){
                rcimage.setImageURI(imageUri);
                rcUri = imageUri;
            }
            imagebitmap = null;
            imageUri = null;
        }else if(num == 2){
            if(imagebitmap != null){
                invoice.setImageBitmap(imagebitmap);
                invoiceUri = getImageUri(getContext(),imagebitmap);
            }else if(imageUri != null){
                invoice.setImageURI(imageUri);
                invoiceUri = imageUri;
            }
            imagebitmap = null;
            imageUri = null;
        }else if(num == 3){
            if(imagebitmap != null){
                insurance.setImageBitmap(imagebitmap);
                insuranceUri = getImageUri(getContext(),imagebitmap);
            }else if(imageUri != null){
                insurance.setImageURI(imageUri);
                insuranceUri = imageUri;
            }
            imagebitmap = null;
            imageUri = null;
        }else if(num == 4){
            if(imagebitmap != null){
                vfront.setImageBitmap(imagebitmap);
                vfrontUri = getImageUri(getContext(),imagebitmap);
            }else if(imageUri != null){
                vfront.setImageURI(imageUri);
                vfrontUri = imageUri;
            }
            imagebitmap = null;
            imageUri = null;
        }else if(num == 5){
            if(imagebitmap != null){
                vback.setImageBitmap(imagebitmap);
                vbackUri = getImageUri(getContext(),imagebitmap);
            }else if(imageUri != null){
                vback.setImageURI(imageUri);
                vbackUri = imageUri;
            }
            imagebitmap = null;
            imageUri = null;
        }else if(num == 6){
            if(imagebitmap != null){
                vside.setImageBitmap(imagebitmap);
                vsideUri = getImageUri(getContext(),imagebitmap);
            }else if(imageUri != null){
                vside.setImageURI(imageUri);
                vsideUri = imageUri;
            }
            imagebitmap = null;
            imageUri = null;
        }
    }

    public void buttonClickListeners(){
        rcSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveRc();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Something went south", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        invoiceSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveInvoice();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Something went south", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        insuranceSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveInsurance();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Something went south", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        vfrontSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveVfront();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Something went south", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        vbackSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveVback();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Something went south", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        vsideSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveVside();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Something went south", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void saveRc() throws Exception{
        if(rcUri == null){
            Toast.makeText(getContext(), "Please add image first", Toast.LENGTH_SHORT).show();
            return;
        }
        if(plate.isEmpty()){
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        rcProgress.setVisibility(View.VISIBLE);
        File file = FileUtil.from(this.getActivity(),rcUri);
        RequestBody requestFile1 = RequestBody.create(file, MediaType.parse("multipart/form-data") );
        MultipartBody.Part body1 = MultipartBody.Part.createFormData("rc", file.getName(), requestFile1);
        RequestBody s = RequestBody.create(plate,MediaType.parse("multipart/form-data"));
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<Message> call = o.uploadRC(s,body1);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Message message = response.body();
                if(message != null){
                    Toast.makeText(getContext(), message.getMessage(), Toast.LENGTH_SHORT).show();
                    rcProgress.setVisibility(View.INVISIBLE);
                }else{
                    Toast.makeText(getContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                    rcProgress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(getContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                rcProgress.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void saveInvoice() throws Exception{
        if(invoiceUri == null){
            Toast.makeText(getContext(), "Please add image first", Toast.LENGTH_SHORT).show();
            return;
        }
        if(plate.isEmpty()){
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        invoiceProgress.setVisibility(View.VISIBLE);
        File file = FileUtil.from(this.getActivity(),invoiceUri);
        RequestBody requestFile1 = RequestBody.create(file, MediaType.parse("multipart/form-data") );
        MultipartBody.Part body1 = MultipartBody.Part.createFormData("invoice", file.getName(), requestFile1);
        RequestBody s = RequestBody.create(plate,MediaType.parse("multipart/form-data"));
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<Message> call = o.uploadInvoice(s,body1);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Message message = response.body();
                if(message != null){
                    Toast.makeText(getContext(), message.getMessage(), Toast.LENGTH_SHORT).show();
                    invoiceProgress.setVisibility(View.INVISIBLE);
                }else{
                    Toast.makeText(getContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                    invoiceProgress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(getContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                invoiceProgress.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void saveInsurance() throws Exception{
        if(insuranceUri == null){
            Toast.makeText(getContext(), "Please add image first", Toast.LENGTH_SHORT).show();
            return;
        }
        if(plate.isEmpty()){
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        insuranceProgress.setVisibility(View.VISIBLE);
        File file = FileUtil.from(this.getActivity(),insuranceUri);
        RequestBody requestFile1 = RequestBody.create(file, MediaType.parse("multipart/form-data") );
        MultipartBody.Part body1 = MultipartBody.Part.createFormData("insurance", file.getName(), requestFile1);
        RequestBody s = RequestBody.create(plate,MediaType.parse("multipart/form-data"));
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<Message> call = o.uploadInsurance(s,body1);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Message message = response.body();
                if(message != null){
                    Toast.makeText(getContext(), message.getMessage(), Toast.LENGTH_SHORT).show();
                    insuranceProgress.setVisibility(View.INVISIBLE);
                }else{
                    Toast.makeText(getContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                    insuranceProgress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(getContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                insuranceProgress.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void saveVfront() throws Exception{
        if(vfrontUri == null){
            Toast.makeText(getContext(), "Please add image first", Toast.LENGTH_SHORT).show();
            return;
        }
        if(plate.isEmpty()){
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        vfrontProgress.setVisibility(View.VISIBLE); //here
        File file = FileUtil.from(this.getActivity(),vfrontUri); //here
        RequestBody requestFile1 = RequestBody.create(file, MediaType.parse("multipart/form-data") );
        MultipartBody.Part body1 = MultipartBody.Part.createFormData("vfront", file.getName(), requestFile1); //here
        RequestBody s = RequestBody.create(plate,MediaType.parse("multipart/form-data"));
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<Message> call = o.uploadVfront(s,body1); //here
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Message message = response.body();
                if(message != null){
                    Toast.makeText(getContext(), message.getMessage(), Toast.LENGTH_SHORT).show();
                    vfrontProgress.setVisibility(View.INVISIBLE); //here
                }else{
                    Toast.makeText(getContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                    vfrontProgress.setVisibility(View.INVISIBLE); //here
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(getContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                vfrontProgress.setVisibility(View.INVISIBLE); //here
            }
        });
    }

    public void saveVback() throws Exception{
        if(vbackUri == null){
            Toast.makeText(getContext(), "Please add image first", Toast.LENGTH_SHORT).show();
            return;
        }
        if(plate.isEmpty()){
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        vbackProgress.setVisibility(View.VISIBLE); //here
        File file = FileUtil.from(this.getActivity(),vbackUri); //here
        RequestBody requestFile1 = RequestBody.create(file, MediaType.parse("multipart/form-data") );
        MultipartBody.Part body1 = MultipartBody.Part.createFormData("vback", file.getName(), requestFile1); //here
        RequestBody s = RequestBody.create(plate,MediaType.parse("multipart/form-data"));
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<Message> call = o.uploadVback(s,body1); //here
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Message message = response.body();
                if(message != null){
                    Toast.makeText(getContext(), message.getMessage(), Toast.LENGTH_SHORT).show();
                    vbackProgress.setVisibility(View.INVISIBLE); //here
                }else{
                    Toast.makeText(getContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                    vbackProgress.setVisibility(View.INVISIBLE); //here
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(getContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                vbackProgress.setVisibility(View.INVISIBLE);  //here
            }
        });
    }

    public void saveVside() throws Exception{
        if(vsideUri == null){
            Toast.makeText(getContext(), "Please add image first", Toast.LENGTH_SHORT).show();
            return;
        }
        if(plate.isEmpty()){
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }
        vsideProgress.setVisibility(View.VISIBLE); //here
        File file = FileUtil.from(this.getActivity(),vsideUri); //here
        RequestBody requestFile1 = RequestBody.create(file, MediaType.parse("multipart/form-data") );
        MultipartBody.Part body1 = MultipartBody.Part.createFormData("vside", file.getName(), requestFile1); //here
        RequestBody s = RequestBody.create(plate,MediaType.parse("multipart/form-data"));
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<Message> call = o.uploadVside(s,body1); //here
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Message message = response.body();
                if(message != null){
                    Toast.makeText(getContext(), message.getMessage(), Toast.LENGTH_SHORT).show();
                    vsideProgress.setVisibility(View.INVISIBLE); //here
                }else{
                    Toast.makeText(getContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                    vsideProgress.setVisibility(View.INVISIBLE); //here
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(getContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                vsideProgress.setVisibility(View.INVISIBLE); //here
            }
        });
    }
}