package com.lifecapable.vehicledriver.Driver.services;

import android.Manifest;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.lifecapable.vehicledriver.R;
import static com.lifecapable.vehicledriver.Driver.App.CHANNEL_ID;

public class LocationService extends Service {

/*  SharedPreferences sharedPreferences;
    String phone_no;*/

    private static final String TAG = "LocationServiceDriver";
    private FusedLocationProviderClient mFusedLocationClient;
    private final static long UPDATE_INTERVAL = 5 * 1000;  /* 5 secs */
    private final static long FASTEST_INTERVAL = 2000; /* 2 sec */

    SharedPreferences status;
    Double lat,lon;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: called.");

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
/*        sharedPreferences = getSharedPreferences("phone",MODE_PRIVATE);
        phone_no = sharedPreferences.getString("phone","");*/
        status = getSharedPreferences("statePreference",MODE_PRIVATE);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Location service")
                .setContentText("getting location")
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
                        Log.d(TAG, "onLocationResult: got location result.");
                        Location location = locationResult.getLastLocation();
                        if (location != null) {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                            if(status.getBoolean("state",false)) {
                                updateLocation(lat,lon);
                            }else{
                                stopSelf();
                            }
                            updateLocation(lat,lon);
                        }
                    }
                },
                Looper.myLooper());
    }

    public void updateLocation(Double lat,Double lon){
        Log.e("Locatin Data    ","Lat "+lat+", long "+ lon);

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
