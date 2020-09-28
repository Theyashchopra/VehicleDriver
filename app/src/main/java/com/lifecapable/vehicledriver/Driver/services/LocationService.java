package com.lifecapable.vehicledriver.Driver.services;

import android.Manifest;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.util.Printer;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.lifecapable.vehicledriver.Driver.adapters.RestAdapter;
import com.lifecapable.vehicledriver.Driver.datamodels.ReturnMessage;
import com.lifecapable.vehicledriver.R;

import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lifecapable.vehicledriver.Driver.App.CHANNEL_ID;

public class LocationService extends Service {

/*  SharedPreferences sharedPreferences;
    String phone_no;*/

    private static final String TAG = "LocationServiceDriver";
    private FusedLocationProviderClient mFusedLocationClient;
    private final static long UPDATE_INTERVAL = 5 * 1000;  /* 5 secs */
    private final static long FASTEST_INTERVAL = 2000; /* 2 sec */

    SharedPreferences status, sharedPreferences;
    int vid, did;
    float lat, lon;
    Timer timer;

    Boolean service;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(!service){
            stopForeground(true);
            stopSelf();
            Log.e("Location service","Stopping service");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: called.");
        service = intent.getExtras().getBoolean("service");
        if(!service){
            stopForeground(true);
            stopSelfResult(startId);
            stopSelf();
            stopService(intent);
            Log.e("Location service","Stopping service");
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
/*        sharedPreferences = getSharedPreferences("phone",MODE_PRIVATE);
        phone_no = sharedPreferences.getString("phone","");*/
        sharedPreferences = getSharedPreferences("driver", MODE_PRIVATE);
        vid = sharedPreferences.getInt("vehicleid", 0);
        did = sharedPreferences.getInt("driverid", 0);

        timer = new Timer();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Location service")
                .setContentText("Updating location")
                .setSmallIcon(R.drawable.ic_location)
                .build();
        startForeground(1, notification);
        getLocation();
        return START_NOT_STICKY;
    }

    private void getLocation() {
        LocationRequest mLocationRequestHighAccuracy = new LocationRequest();
        mLocationRequestHighAccuracy.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequestHighAccuracy.setInterval(UPDATE_INTERVAL);
        mLocationRequestHighAccuracy.setFastestInterval(FASTEST_INTERVAL);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "getLocation: stopping the location service.");
            stopSelf();
            return;
        }
        Log.d(TAG, "getLocation: getting location information.");
        mFusedLocationClient.requestLocationUpdates(mLocationRequestHighAccuracy,
                new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        status = getSharedPreferences("statePreference", MODE_PRIVATE);
                        if(status.getBoolean("state",false)) {
                            Log.d(TAG, "onLocationResult: got location result.");
                            Location location = locationResult.getLastLocation();
                            if (location != null) {
                                lat = (float)location.getLatitude();
                                lon = (float)location.getLongitude();
                                updateLocation(lat,lon);
                            }
                        }else{
                            stopSelf();
                        }
                    }
                },
                getMainLooper());
/*        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (ActivityCompat.checkSelfPermission(LocationService.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(LocationService.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                status = getSharedPreferences("statePreference", MODE_PRIVATE);
                if(status.getBoolean("state",false)) {
                    Task location = mFusedLocationClient.getLastLocation();
                    location.addOnCompleteListener(task -> {
                        Location currLocation =(Location)task.getResult();
                        try {
                            if(currLocation != null){
                                lat = (float)currLocation.getLatitude();
                                lon = (float)currLocation.getLongitude();
                                    updateLocation(lat,lon);
                                }
                        }
                        catch (Exception e){
                            Log.e("Exception in location", "Location is null");
                        }
                    });
                }else{
                    stopSelf();
                }
           }
       },0,5000);*/
    }

    public void updateLocation(float lat,float lon){
        Log.e("Location Data    ","Lat "+lat+", long "+ lon);
        Call<ReturnMessage> call = RestAdapter.createAPI().oputlocation(vid,lat,lon,did);
        call.enqueue(new Callback<ReturnMessage>() {
            @Override
            public void onResponse(@NotNull Call<ReturnMessage> call, @NotNull Response<ReturnMessage> response) {
                if(!response.isSuccessful()){
                    Log.e("yeah","Naah");
                    return;
                }
                ReturnMessage res = response.body();
                if(res != null){
                    Log.e("Location status",res.getMessage());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ReturnMessage> call, @NotNull Throwable t) {
                Toast.makeText(LocationService.this, "Location is not updated"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

/*    private void create_notifications(String id,String number){

        Intent intent = new Intent(this, DriverBottomActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("data", "notify");
        intent.putExtra("ID", id);
        intent.putExtra("number",number);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("1","1", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"1")
                .setContentText("Fetching your")
                .setSmallIcon(R.drawable.ic_location)
                .setAutoCancel(true)
                .setContentText("Updating the data")
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1,builder.build());
        Notification notification = builder.build();
        startForeground(1,notification);
    }*/
}
