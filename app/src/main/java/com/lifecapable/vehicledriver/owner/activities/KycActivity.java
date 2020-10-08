package com.lifecapable.vehicledriver.owner.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lifecapable.vehicledriver.R;
import com.lifecapable.vehicledriver.owner.adapter.RestAdapter;
import com.lifecapable.vehicledriver.owner.datamodel.Message;
import com.lifecapable.vehicledriver.owner.imagepickers.Dialog_Get_ImageActivity;
import com.lifecapable.vehicledriver.owner.imagepickers.FileUtil;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;

import java.io.ByteArrayOutputStream;
import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KycActivity extends AppCompatActivity implements Dialog_Get_ImageActivity.onPhotoSelectedListener, Dialog_Get_ImageActivity.MyDialogCloseListener {

    Dialog_Get_ImageActivity dgi;
    Uri imageUri,panUri,addressUri;
    Bitmap imageBitmap;
    Button savePan,saveAddress,done;
    ImageView pan,address;
    ProgressBar panProgress,addressProgress;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc);
        email = "";
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        Log.i("EMAIL",email);
        init();
    }

    private void init(){
        savePan = findViewById(R.id.savePan);
        saveAddress = findViewById(R.id.saveAddress);
        done = findViewById(R.id.done);
        pan = findViewById(R.id.pan);
        address = findViewById(R.id.address);
        panProgress = findViewById(R.id.panProgress);
        addressProgress = findViewById(R.id.addressProgress);
        onClickListeners();
    }

    private void onClickListeners(){
        pan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle ags=new Bundle();
                ags.putInt("curr",1);
                dgi = new Dialog_Get_ImageActivity();
                dgi.setArguments(ags);
                dgi.show(getSupportFragmentManager(),"Dialog_select_Image");
                Log.e("Image Adder","----"+1);
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle ags=new Bundle();
                ags.putInt("curr",2);
                dgi = new Dialog_Get_ImageActivity();
                dgi.setArguments(ags);
                dgi.show(getSupportFragmentManager(),"Dialog_select_Image");
                Log.e("Image Adder","----"+2);
            }
        });
        savePan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    savePanImage();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(KycActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        saveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveAddressImage();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(KycActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KycActivity.this,OwnerLeftNavActivity.class));
            }
        });
    }

    @Override
    public void getImagePath(Uri imagePath) {
        imageUri = imagePath;
        imageBitmap = null;
    }

    @Override
    public void getImageBitmap(Bitmap bitmap) {
        imageBitmap = bitmap;
        imageUri = null;
    }

    @Override
    public void handleDialogClose(int num) {
        if(num == 1){
            if(imageUri != null){
                pan.setImageURI(imageUri);
                panUri = imageUri;
            }else if(imageBitmap != null){
                pan.setImageBitmap(imageBitmap);
                panUri = getImageUri(this,imageBitmap);
            }
        }else if(num == 2){
            if(imageUri != null){
                address.setImageURI(imageUri);
                addressUri = imageUri;
            }else if(imageBitmap != null){
                address.setImageBitmap(imageBitmap);
                addressUri = getImageUri(this,imageBitmap);
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void savePanImage() throws Exception{
        if(email.isEmpty()){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }else if(panUri == null){
            Toast.makeText(this, "Please add image first", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = FileUtil.from(this,panUri);
        RequestBody requestFile1 = RequestBody.create(file, MediaType.parse("multipart/form-data") );
        MultipartBody.Part body1 = MultipartBody.Part.createFormData("image", file.getName(), requestFile1);
        RequestBody s = RequestBody.create(email,MediaType.parse("multipart/form-data"));
        OwnerJsonPlaceHolder op = RestAdapter.createAPI();
        Call<Message> call = op.uploadPanImage(s,body1);
        panProgress.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.isSuccessful()){
                    try{
                        panProgress.setVisibility(View.INVISIBLE);
                        Toast.makeText(KycActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        panProgress.setVisibility(View.INVISIBLE);
                        e.printStackTrace();
                        Toast.makeText(KycActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                panProgress.setVisibility(View.INVISIBLE);
                Toast.makeText(KycActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveAddressImage() throws Exception{
        if(email.isEmpty()){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            return;
        }else if(addressUri == null){
            Toast.makeText(this, "Please add image first", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = FileUtil.from(this,addressUri);
        RequestBody requestFile1 = RequestBody.create(file, MediaType.parse("multipart/form-data") );
        MultipartBody.Part body1 = MultipartBody.Part.createFormData("address_proof", file.getName(), requestFile1);
        RequestBody s = RequestBody.create(email,MediaType.parse("multipart/form-data"));
        OwnerJsonPlaceHolder op = RestAdapter.createAPI();
        Call<Message> call = op.uploadAddressImage(s,body1);
        addressProgress.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.isSuccessful()){
                    try{
                        addressProgress.setVisibility(View.INVISIBLE);
                        Toast.makeText(KycActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        addressProgress.setVisibility(View.INVISIBLE);
                        e.printStackTrace();
                        Toast.makeText(KycActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                addressProgress.setVisibility(View.INVISIBLE);
                Toast.makeText(KycActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}