package com.lifecapable.vehicledriver.Driver.services;


import com.lifecapable.vehicledriver.Driver.datamodels.DriverData;
import com.lifecapable.vehicledriver.Driver.datamodels.ListHomeDriverData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    @GET("driver")
    Call<DriverData> dgetLogin(@Query("username") String username, @Query("password") String password);

    @GET("appt")
    Call<ListHomeDriverData> dgetAppointmnets(@Query("vehicle_id") int vehicle_id);

    @GET("driver")
    Call<DriverData> ogetProfile(@Query("id") int id);

}
