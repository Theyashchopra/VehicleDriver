package com.lifecapable.vehicledriver.owner.placeholders;

import com.lifecapable.vehicledriver.Driver.datamodels.DriverData;
import com.lifecapable.vehicledriver.owner.datamodel.DriverDetailsOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.DriverOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.ListVehicleOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.LoginOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleDetailsOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleIds;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleOwnerData;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface OwnerJsonPlaceHolder {

    @GET("regowner")
    Call<LoginOwnerData> ogetLogin(@Query("email") String email, @Query("password") String password);

    @POST("driver")
    Call<DriverDetailsOwnerData> addDriver(@Query("email") String email, @Query("password") String password,
                                           @Query("name") String name, @Query("mobile") String mobile,
                                           @Query("adhaar") String adhaar, @Query("mobile2") String mobile2,
                                           @Query("ip_address") String ip_address, @Query("vehicle_id") int vehicle_id,
                                           @Query("owner_id") int owner_id);

    @POST("vehicle")
    Call<VehicleIds> addVehicle(@Body VehicleDetailsOwnerData v);


    @GET("driver")
    Call<DriverOwnerData> ogetDriverList(@Query("owner_id") int id);

    @GET("vehicle")
    Call<ListVehicleOwnerData> ogetVehicleList(@Query("owner_id") int owner_id);

    @GET("vehicle")
    Call<VehicleDetailsOwnerData> ogetVehicleDetails(@Query("id") int id);


}
