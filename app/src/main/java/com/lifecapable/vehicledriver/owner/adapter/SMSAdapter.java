package com.lifecapable.vehicledriver.owner.adapter;

import com.lifecapable.vehicledriver.BuildConfig;
import com.lifecapable.vehicledriver.owner.placeholders.OwnerJsonPlaceHolder;
import com.lifecapable.vehicledriver.owner.placeholders.SMSPlaceholder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SMSAdapter {
    public static SMSPlaceholder createAPI() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        if(BuildConfig.DEBUG){
            builder.addInterceptor(logging);
        }
        builder.cache(null);
        OkHttpClient okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://103.16.142.249/api/mt/")
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(SMSPlaceholder.class);
    }
}
