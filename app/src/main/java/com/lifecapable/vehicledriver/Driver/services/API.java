package com.lifecapable.vehicledriver.Driver.services;


import com.lifecapable.vehicledriver.Driver.datamodels.DriverData;
import com.lifecapable.vehicledriver.Driver.datamodels.ListHomeDriverData;
import com.lifecapable.vehicledriver.Driver.datamodels.ReturnMessage;
import com.lifecapable.vehicledriver.Driver.datamodels.VehicleDriverData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface API {

    @GET("driver")
    Call<DriverData> dgetLogin(@Query("username") String username, @Query("password") String password);

    @GET("appt")
    Call<ListHomeDriverData> dgetAppointmnets(@Query("vehicle_id") int vehicle_id);

    @GET("driver")
    Call<DriverData> ogetProfile(@Query("id") int id);

    @PUT("vgps")
    Call<ReturnMessage> oputlocation(@Query("id") int id, @Query("lat") float lat, @Query("lon") float lon, @Query("driver_id") int driver_id);

    @PUT("vehicle")
    Call<VehicleDriverData> oputAvailability(@Query("id")int id, @Query("availibility")int availability);
}
