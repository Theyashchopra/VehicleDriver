package com.lifecapable.vehicledriver.owner.ui.slideshow;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.DriverDetailsOwnerData;
import com.lifecapable.vehicledriver.owner.dialogs.OwnerDialogGetImageFragment;
import com.lifecapable.vehicledriver.owner.dialogs.OwnerSelectVehiclePopup;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;

import java.io.ByteArrayOutputStream;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OwnerAddDriverFragment extends Fragment implements OwnerDialogGetImageFragment.MyDialogCloseListener, OwnerDialogGetImageFragment.onPhotoSelectedListener {

    View root;
    TextInputEditText nameet, emailet, contact1, contact2, addharet, passwordet;
    ImageView licenceiv,profileiv;
    Button btdone;
    Uri profileuri, licenceuri, imageuri;
    OwnerDialogGetImageFragment dgi;
    Bitmap imagebitmap;
    CardView cardView;
    TextView assignedText;
    OwnerSelectVehiclePopup ownerSelectVehiclePopup;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.owner_fragment_add_driver, container, false);
        btdone = root.findViewById(R.id.addone);
        nameet = root.findViewById(R.id.adnameet);
        emailet = root.findViewById(R.id.ademailet);
        contact1 = root.findViewById(R.id.adcontact1et);
        contact2 = root.findViewById(R.id.adcontact2et);
        addharet = root.findViewById(R.id.adaadharet);
        passwordet = root.findViewById(R.id.adpasswordet);
        licenceiv = root.findViewById(R.id.adlicence);
        profileiv = root.findViewById(R.id.adimage);
        cardView = root.findViewById(R.id.adassignedvehicle);
        assignedText = root.findViewById(R.id.adassignedvehicletext);


        inithome();
        return root;
    }

    private void inithome(){

        cardView.setOnClickListener(view -> {
            Bundle args = new Bundle();
            args.putInt("oid",10);
            ownerSelectVehiclePopup = new OwnerSelectVehiclePopup();
            ownerSelectVehiclePopup.setArguments(args);
            ownerSelectVehiclePopup.show(getFragmentManager(),"Dialog Select Vehicle");
            Log.e("Add Vehicle","Dialog launched");

        });
        btdone.setOnClickListener(v -> {
                try {
                    addDriver();

                } catch (Exception e) {
                    e.printStackTrace();
                }

        });

        licenceiv.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putInt("curr",1);
            dgi = new OwnerDialogGetImageFragment();
            dgi.setArguments(args);
            dgi.setTargetFragment(OwnerAddDriverFragment.this,1);
            dgi.show(getFragmentManager(),"Dialog get fragment");
            Log.e("Add Driver","Dialog launched");
        });
        profileiv.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putInt("curr",2);
            dgi = new OwnerDialogGetImageFragment();
            dgi.setArguments(args);
            dgi.setTargetFragment(OwnerAddDriverFragment.this,2);
            dgi.show(getFragmentManager(),"Dialog get fragment");
            Log.e("Add Driver","Dialog launched");
        });
    }

    private boolean checkempty(){

        return false;
    }

    private void addDriver(){
        if(emailet.getText() == null || passwordet.getText() == null || nameet.getText() == null || contact1.getText() == null ||
            addharet.getText() == null || contact2.getText() == null ){
            Toast.makeText(getActivity(), "You left something empty!!", Toast.LENGTH_SHORT).show();
            return;
        }
/*        File file = FileUtil.from(getContext(),profileuri);
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/*"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("pic", file.getName(), requestFile);

        File file1 = FileUtil.from(getContext(),licenceuri);
        RequestBody requestFile1 = RequestBody.create(file1,MediaType.parse("image/*") );
        MultipartBody.Part body1 = MultipartBody.Part.createFormData("licence_image", file1.getName(), requestFile1);*/

        Call<DriverDetailsOwnerData> call = RestAdapter.createAPI().addDriver(
                emailet.getText().toString(),
                passwordet.getText().toString(),
                nameet.getText().toString(),
                contact1.getText().toString(),
                addharet.getText().toString(),
                contact2.getText().toString(),
                getWifiMacAddress(),
                10,
                13);

        call.enqueue(new Callback<DriverDetailsOwnerData>() {
            @Override
            public void onResponse(Call<DriverDetailsOwnerData> call, Response<DriverDetailsOwnerData> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Somethings Wrong I can feel it"+response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                DriverDetailsOwnerData res = response.body();
                if (res!= null) {
                    Log.e("Yoooooooooo", res.getName());
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigateUp();
                }
            }

            @Override
            public void onFailure(Call<DriverDetailsOwnerData> call, Throwable t) {
                Toast.makeText(getContext(), "Somethings Wrong I can feel it"+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public static String getWifiMacAddress() {
        try {
            String interfaceName = "wlan0";
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (!intf.getName().equalsIgnoreCase(interfaceName)){
                    continue;
                }

                byte[] mac = intf.getHardwareAddress();
                if (mac==null){
                    return "";
                }

                StringBuilder buf = new StringBuilder();
                for (byte aMac : mac) {
                    buf.append(String.format("%02X:", aMac));
                }
                if (buf.length()>0) {
                    buf.deleteCharAt(buf.length() - 1);
                }
                return buf.toString();
            }
        } catch (Exception ex) {

        } // for now eat exceptions
        return "";
    }

    @Override
    public void getImagePath(Uri imagePath) {
        imageuri = imagePath;
        imagebitmap = null;
    }

    @Override
    public void getImageBitmap(Bitmap bitmap) {
        imagebitmap = bitmap;
        imageuri = null;
    }

    @Override
    public void handleDialogClose(int num) {
        if(num == 1){
            if(imagebitmap != null){
                licenceiv.setImageBitmap(imagebitmap);
                licenceuri = getImageUri(getContext(), imagebitmap);
            }else if(imageuri != null){
                licenceiv.setImageURI(imageuri);
                licenceuri = imageuri;
            }
            imagebitmap = null;
            imageuri = null;
        }
        else if(num == 2) {
            if(imagebitmap != null){
                profileiv.setImageBitmap(imagebitmap);
                profileuri = getImageUri(getContext(),imagebitmap);
            }else if(imageuri != null){
                profileiv.setImageURI(imageuri);
                profileuri = imageuri;
            }
            imagebitmap = null;
            imageuri = null;
        }
        else{
            Toast.makeText(getActivity(), "Something Went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    //get uri of bitmap
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}