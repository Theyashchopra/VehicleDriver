package com.lifecapable.vehicledriver.owner.ui.slideshow;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.DriverDetailsOwnerData;
import com.lifecapable.vehicledriver.owner.dialogs.OwnerDialogGetImageFragment;
import com.lifecapable.vehicledriver.owner.imagepickers.FileUtil;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OwnerAddDriverFragment extends Fragment implements OwnerDialogGetImageFragment.MyDialogCloseListener, OwnerDialogGetImageFragment.onPhotoSelectedListener {

    View root;
    TextInputEditText nameet, emailet, contact1, contact2, addharet, passwordet;
    ImageView licenceiv,profileiv;
    Spinner categorysp,subcategorysp1,subcategorysp2;
    Button btdone;
    OwnerJsonPlaceHolder addDriverPlaceHolder;
    Retrofit retrofit;
    Uri profileuri, licenceuri, imageuri;
    OwnerDialogGetImageFragment dgi;
    Bitmap imagebitmap;


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
        categorysp = root.findViewById(R.id.adcategory);
        subcategorysp1 = root.findViewById(R.id.adsubcategory1);
        subcategorysp2 =root.findViewById(R.id.adsubcategory2);

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        inithome();
        return root;
    }

    private void inithome(){
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

    private void addDriver() throws Exception {
        if(emailet.getText() == null || passwordet.getText() == null || nameet.getText() == null || contact1.getText() == null ||
            addharet.getText() == null || contact2.getText() == null || profileuri == null || licenceuri == null){
            Toast.makeText(getActivity(), "You left something empty!!", Toast.LENGTH_SHORT).show();
            return;
        }
        addDriverPlaceHolder = retrofit.create(OwnerJsonPlaceHolder.class);
        File file = FileUtil.from(getContext(),profileuri);
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("pic", file.getName(), requestFile);

        File file1 = FileUtil.from(getContext(),licenceuri);
        RequestBody requestFile1 = RequestBody.create(file1,MediaType.parse("multipart/form-data") );
        MultipartBody.Part body1 = MultipartBody.Part.createFormData("licence_image", file1.getName(), requestFile1);
        Call<DriverDetailsOwnerData> call = RestAdapter.createAPI().registerDriver(
                RequestBody.create(emailet.getText().toString(), MediaType.parse("multipart/form-data")),
                RequestBody.create(passwordet.getText().toString(),MediaType.parse("multipart/form-data")),
                RequestBody.create(nameet.getText().toString(),MediaType.parse("multipart/form-data")),
                RequestBody.create(contact1.getText().toString(),MediaType.parse("multipart/form-data")),
                RequestBody.create(addharet.getText().toString(),MediaType.parse("multipart/form-data")),
                RequestBody.create(contact2.getText().toString(),MediaType.parse("multipart/form-data")),
                RequestBody.create(getWifiMacAddress(),MediaType.parse("multipart/form-data")),
                RequestBody.create("1",MediaType.parse("multipart/form-data")),
                RequestBody.create("13",MediaType.parse("multipart/form-data")),
                body,
                body1);

        call.enqueue(new Callback<DriverDetailsOwnerData>() {
            @Override
            public void onResponse(Call<DriverDetailsOwnerData> call, Response<DriverDetailsOwnerData> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Somethings Wrong I can feel it"+response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                DriverDetailsOwnerData res = response.body();
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigateUp();

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