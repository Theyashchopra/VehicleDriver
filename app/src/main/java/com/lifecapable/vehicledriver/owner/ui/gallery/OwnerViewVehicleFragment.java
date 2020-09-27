package com.lifecapable.vehicledriver.owner.ui.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;
import com.google.gson.JsonStreamParser;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleDetailsOwnerData;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OwnerViewVehicleFragment extends Fragment {
    View root;
    ImageView rcimage,invoice,insurance,vfront,vside,vback;
    TextView plateEt, modelnumEt, madeinEt, kmscompletedEt, rentperday, rentperhour;
    ProgressBar rcProgress,invoiceProgress,insuranceProgress,vfrontProgress,vsideProgress,vbackProgress;
    int vehicleid;
    Button editbt, removebt;
    OwnerJsonPlaceHolder vehicleDataPlaceHolder;
    Retrofit retrofit;
    Button location;
    VehicleDetailsOwnerData vehicleDetailsOwnerData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.owner_fragment_view_vehicle, container, false);
        vehicleid = getArguments().getInt("Vehicleid");
        plateEt = root.findViewById(R.id.vvplatenumber);
        modelnumEt = root.findViewById(R.id.vvmodelnumber);
        madeinEt = root.findViewById(R.id.vvmadein);
        kmscompletedEt = root.findViewById(R.id.vvkmscompleted);
        rentperday = root.findViewById(R.id.vvrentperday);
        rentperhour = root.findViewById(R.id.vvrentperhour);
        removebt = root.findViewById(R.id.vvremove);
        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.i("VID",String.valueOf(vehicleid));
        getData(vehicleid);
        editbt = root.findViewById(R.id.vvedit);
        init();
        return root;
    }
    private  void init(){
        rcimage = root.findViewById(R.id.rc_imageV);
        invoice = root.findViewById(R.id.invoice_imageV);
        insurance = root.findViewById(R.id.insurance_imageV);
        vfront = root.findViewById(R.id.vfront_imageV);
        vside = root.findViewById(R.id.vside_imageV);
        vback = root.findViewById(R.id.vback_imageV);

        location = root.findViewById(R.id.navigate);

        rcProgress = root.findViewById(R.id.rc_progressV);
        invoiceProgress = root.findViewById(R.id.invoice_progressV);
        insuranceProgress = root.findViewById(R.id.insurance_progressV);
        vfrontProgress = root.findViewById(R.id.vfront_progressV);
        vbackProgress = root.findViewById(R.id.vback_progressV);
        vsideProgress = root.findViewById(R.id.vside_progressV);
        fetchRc();
        fetchInsurance();
        fetchInvoice();
        fetchVside();
        fetchVfront();
        fetchVback();
    }
    public void getData(int vid){
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<VehicleDetailsOwnerData> call = o.ogetVehicleDetails(vid);
        call.enqueue(new Callback<VehicleDetailsOwnerData>() {
            @Override
            public void onResponse(Call<VehicleDetailsOwnerData> call, Response<VehicleDetailsOwnerData> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Somethings Wrong I can feel it"+response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }

                VehicleDetailsOwnerData res = response.body();
                vehicleDetailsOwnerData = response.body();
                if(res != null) {
                    try {
                        initviews(res);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            @Override
            public void onFailure(Call<VehicleDetailsOwnerData> call, Throwable t) {
                Toast.makeText(getContext(), "Somethings Wrong I can feel it"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void initviews(VehicleDetailsOwnerData allData) throws Exception{

        plateEt.setText(allData.getPlate_no().toUpperCase());
        modelnumEt.setText(allData.getModel_name());
        madeinEt.setText(allData.getYom());
        kmscompletedEt.setText(String.valueOf(allData.getTotal_run_hrs()));
        rentperhour.setText(String.valueOf(allData.getRent_per_hour_with_fuel()));
        rentperday.setText(String.valueOf(allData.getRent_per_day_with_fuel()));
        editbt.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("plate",allData.getPlate_no());
            Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_nav_AddNewVehicle_owner_to_nav_RcVehicle_owner,args);
        });

        removebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //got to edit page
                Bundle args = new Bundle();
                args.putInt("vid",vehicleid);
                NavController navController = NavHostFragment.findNavController(OwnerViewVehicleFragment.this);
                navController.navigate(R.id.action_nav_viewvehicle_owner_to_nav_EditVehicle_owner,args);
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putInt("driver_id",vehicleDetailsOwnerData.getDriver_id());
                args.putString("vname",vehicleDetailsOwnerData.getName());
                args.putInt("vid",vehicleid);
                NavController nav = NavHostFragment.findNavController(OwnerViewVehicleFragment.this);
                nav.navigate(R.id.action_nav_viewvehicle_owner_to_nav_vehicle_map,args);

            }
        });
    }

    private void fetchRc(){
        rcProgress.setVisibility(View.VISIBLE);
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<ResponseBody> call = o.getRC(vehicleid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                    if (bmp != null){
                        rcimage.setImageBitmap(bmp);
                        rcProgress.setVisibility(View.INVISIBLE);
                    }else {
                        rcimage.setImageResource(R.drawable.ic_error_404);
                        rcProgress.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();
                rcProgress.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void fetchInsurance(){
        insuranceProgress.setVisibility(View.VISIBLE);
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<ResponseBody> call = o.getInsurance(vehicleid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                    if (bmp != null){
                        insurance.setImageBitmap(bmp);
                        insuranceProgress.setVisibility(View.INVISIBLE);
                    }else {
                        insurance.setImageResource(R.drawable.ic_error_404);
                        insuranceProgress.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();
                insuranceProgress.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void fetchInvoice(){
        invoiceProgress.setVisibility(View.VISIBLE);
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<ResponseBody> call = o.getInvoice(vehicleid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                    if (bmp != null){
                        invoiceProgress.setVisibility(View.INVISIBLE);
                        invoice.setImageBitmap(bmp);
                    }else {
                        invoiceProgress.setVisibility(View.INVISIBLE);
                        invoice.setImageResource(R.drawable.ic_error_404);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                invoiceProgress.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void  fetchVfront(){
        vfrontProgress.setVisibility(View.VISIBLE);
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<ResponseBody> call = o.getVfront(vehicleid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                    if (bmp != null){
                        vfrontProgress.setVisibility(View.INVISIBLE);
                        vfront.setImageBitmap(bmp);
                    }else {
                        vfrontProgress.setVisibility(View.INVISIBLE);
                        vfront.setImageResource(R.drawable.ic_error_404);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                vfrontProgress.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchVside(){
        vsideProgress.setVisibility(View.VISIBLE);
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<ResponseBody> call = o.getVside(vehicleid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                    if (bmp != null){
                        vsideProgress.setVisibility(View.INVISIBLE);
                        vside.setImageBitmap(bmp);
                    }else {
                        vsideProgress.setVisibility(View.INVISIBLE);
                        vside.setImageResource(R.drawable.ic_error_404);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                vsideProgress.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchVback(){
        vbackProgress.setVisibility(View.VISIBLE);
        OwnerJsonPlaceHolder o = RestAdapter.createAPI();
        Call<ResponseBody> call = o.getVback(vehicleid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                    if (bmp != null){
                        vbackProgress.setVisibility(View.INVISIBLE);
                        vback.setImageBitmap(bmp);
                    }else {
                        vbackProgress.setVisibility(View.INVISIBLE);
                        vback.setImageResource(R.drawable.ic_error_404);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                vbackProgress.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}