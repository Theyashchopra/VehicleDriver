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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.Messages;
import com.lifecapable.vehicledriver.owner.dialogs.OwnerDialogGetImageFragment;
import com.lifecapable.vehicledriver.owner.imagepickers.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerAddImageDriverFragment extends Fragment implements OwnerDialogGetImageFragment.MyDialogCloseListener, OwnerDialogGetImageFragment.onPhotoSelectedListener {

    ProgressBar progressBar;
    View root;
    int vehicleId;
    ImageView pic,licence;
    Button done;
    String email;
    Uri profileuri, licenceuri, imageuri;
    OwnerDialogGetImageFragment dgi;
    Bitmap imagebitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.owner_fragment_add_image_drivers, container, false);
        progressBar = root.findViewById(R.id.image_progress);
        if(getArguments() != null){
            email = getArguments().getString("email");
        }
        pic = root.findViewById(R.id.driverimage);
        licence = root.findViewById(R.id.driverlicence);
        done = root.findViewById(R.id.imagedone);
        initlisteners();

        return root;
    }
    private void initlisteners(){
        pic.setOnClickListener(view -> {
            Bundle args = new Bundle();
            args.putInt("curr",2);
            dgi = new OwnerDialogGetImageFragment();
            dgi.setArguments(args);
            dgi.setTargetFragment(OwnerAddImageDriverFragment.this,2);
            dgi.show(getFragmentManager(),"Dialog get fragment");
            Log.e("Add Driver","Dialog launched");
        });

        licence.setOnClickListener(view -> {
            Bundle args = new Bundle();
            args.putInt("curr",1);
            dgi = new OwnerDialogGetImageFragment();
            dgi.setArguments(args);
            dgi.setTargetFragment(OwnerAddImageDriverFragment.this,1);
            dgi.show(getFragmentManager(),"Dialog get fragment");
            Log.e("Add Driver","Dialog launched");
        });

        done.setOnClickListener(view -> {
            try {
                postImageData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    private void postImageData() throws Exception {
        progressBar.setVisibility(View.VISIBLE);
        if(profileuri == null){
            Log.i("URI","NULL");
            return;
        }
        File file = FileUtil.from(this.getActivity(),profileuri);
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        RequestBody s = RequestBody.create(email,MediaType.parse("multipart/form-data"));

        Call<Messages> call = RestAdapter.createAPI().addDriverImage(s,body);

        call.enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Somethings Wrong I can feel it"+response.message(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                Messages res = response.body();
                if(res != null){
                    Log.e("Yoooooooooo", res.getMessage());
                    progressBar.setVisibility(View.INVISIBLE);
                    try {
                        postLicenceData();
                    } catch (IOException e) {
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Messages> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void postLicenceData() throws IOException {
        progressBar.setVisibility(View.VISIBLE);
        File file1 = FileUtil.from(this.getActivity(),licenceuri);
        RequestBody requestFile1 = RequestBody.create(file1,MediaType.parse("multipart/form-data") );
        MultipartBody.Part body1 = MultipartBody.Part.createFormData("license", file1.getName(), requestFile1);
        RequestBody s = RequestBody.create(email,MediaType.parse("multipart/form-data"));
        Call<Messages> call = RestAdapter.createAPI().addDriverLicence(s,body1);
        call.enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Somethings Wrong I can feel it"+response.message(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }
                Messages res = response.body();
                if(res != null){
                    Log.e("Yoooooooooo1111", res.getMessage());
                    progressBar.setVisibility(View.INVISIBLE);
                    Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_nav_AddImageDriver_owner_to_nav_slideshow_owner);
                }
            }

            @Override
            public void onFailure(Call<Messages> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
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
                licence.setImageBitmap(imagebitmap);
                licenceuri = getImageUri(getContext(), imagebitmap);
            }else if(imageuri != null){
                licence.setImageURI(imageuri);
                licenceuri = imageuri;
            }
            imagebitmap = null;
            imageuri = null;
        }
        else if(num == 2) {
            if(imagebitmap != null){
                pic.setImageBitmap(imagebitmap);
                profileuri = getImageUri(getContext(),imagebitmap);
            }else if(imageuri != null){
                pic.setImageURI(imageuri);
                profileuri = imageuri;
            }
            imagebitmap = null;
            imageuri = null;
        }
        else{
            Toast.makeText(getActivity(), "Something Went wrong", Toast.LENGTH_SHORT).show();
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}