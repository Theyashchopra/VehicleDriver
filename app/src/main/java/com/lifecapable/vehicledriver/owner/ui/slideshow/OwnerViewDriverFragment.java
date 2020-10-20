package com.lifecapable.vehicledriver.owner.ui.slideshow;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

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
import com.lifecapable.vehicledriver.owner.adapter.DriverOwnerAdapter;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.DriverOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.Message;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleDetailsOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleOwnerData;
import com.lifecapable.vehicledriver.owner.dialogs.OwnerDeleteDriverPopup;
import com.lifecapable.vehicledriver.owner.dialogs.OwnerImageViewPopup;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;
import com.lifecapable.vehicledriver.owner.ui.gallery.OwnerViewVehicleFragment;

import java.security.acl.Owner;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerViewDriverFragment extends Fragment {
    ProgressBar progressBar;
    View root;
    Button edit,upload,delete;
    TextView clickviewlicence;
    public static DriverOwnerData driverOwnerData;
    TextView name,number1,number2,adhaar,license,vname,plate,model,vplate;
    ImageView imageView;
    Bitmap licensebitmap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.owner_fragment_view_driver, container, false);
        delete = root.findViewById(R.id.deletedriver);
        clickviewlicence = root.findViewById(R.id.vdlicence);
        clickviewlicence.setOnClickListener(v -> {

            OwnerImageViewPopup ownerImageViewPopup = new OwnerImageViewPopup(licensebitmap);
            ownerImageViewPopup.show(getActivity().getSupportFragmentManager(),"View Image");

        });
        driverOwnerData = DriverOwnerAdapter.driverOwnerData;
        init();
        return root;
    }

    private void init(){
        progressBar = root.findViewById(R.id.vprogress);
        name = root.findViewById(R.id.vdname);
        number1 = root.findViewById(R.id.vdcontact1);
        number2 = root.findViewById(R.id.vdcontact2);
        adhaar = root.findViewById(R.id.vdaadhar);
        license = root.findViewById(R.id.vdlicence);
        vname = root.findViewById(R.id.vdvmodelname);
        plate = root.findViewById(R.id.vdvnumber);
        model = root.findViewById(R.id.model);
        vplate = root.findViewById(R.id.vdvnumber);
        edit = root.findViewById(R.id.vdedit);
        upload = root.findViewById(R.id.vdremove);
        imageView = root.findViewById(R.id.vdimage);
        imageView.setImageResource(R.drawable.ic_person);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OwnerDeleteDriverPopup od = new OwnerDeleteDriverPopup(driverOwnerData.id,OwnerViewDriverFragment.this);
                od.show(getActivity().getSupportFragmentManager(),"Delete");

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("email",driverOwnerData.getEmail());
                NavController navController = NavHostFragment.findNavController(OwnerViewDriverFragment.this);
                navController.navigate(R.id.action_nav_ViewDriver_owner_to_nav_AddImageDriver_owner,args);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putInt("did",driverOwnerData.getId());
                NavHostFragment.findNavController(OwnerViewDriverFragment.this).navigate(R.id.action_nav_ViewDriver_owner_to_nav_edit_driver,args);
            }
        });
        try {
            setData();
            getVehicleData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setData() throws Exception{
        if(driverOwnerData == null){
            Toast.makeText(getContext(), "No internet access", Toast.LENGTH_SHORT).show();
            return;
        }
        name.setText(driverOwnerData.getName());
        number1.setText(driverOwnerData.getMobile());
        number2.setText(driverOwnerData.getMobile2());
        adhaar.setText(driverOwnerData.getAdhaar());
        plate.setText(driverOwnerData.getVehicle_plate().toUpperCase());
        getImage();
        getLicense();
    }
    /*Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());*/

    private void getImage(){
        progressBar.setVisibility(View.VISIBLE);
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<ResponseBody> call = o.getDriverImage(driverOwnerData.getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                    if (bmp != null){
                        progressBar.setVisibility(View.INVISIBLE);
                        imageView.setImageBitmap(bmp);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void getLicense(){
        progressBar.setVisibility(View.VISIBLE);
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<ResponseBody> call = o.getDriverLicense(driverOwnerData.getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                    if (bmp != null){
                        licensebitmap = bmp;
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void getVehicleData(){
        progressBar.setVisibility(View.VISIBLE);
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<VehicleDetailsOwnerData> call = o.ogetVehicleDetails(driverOwnerData.getVehicle_id());
        call.enqueue(new Callback<VehicleDetailsOwnerData>() {
            @Override
            public void onResponse(Call<VehicleDetailsOwnerData> call, Response<VehicleDetailsOwnerData> response) {
                if(response.isSuccessful()){
                    VehicleDetailsOwnerData v = response.body();
                    vname.setText(v.getName());
                    vplate.setText(v.getPlate_no().toUpperCase());
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<VehicleDetailsOwnerData> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}