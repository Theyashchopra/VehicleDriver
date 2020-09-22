package com.lifecapable.vehicledriver.owner.placeholders;

import com.lifecapable.vehicledriver.Driver.datamodels.DriverData;
import com.lifecapable.vehicledriver.owner.datamodel.DriverDetailsOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.DriverOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.ListVehicleOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.LoginOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleDetailsOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleOwnerData;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
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

    @Multipart
    @POST("driver")
    Call<DriverDetailsOwnerData> registerDriver(@Part("email") RequestBody email, @Part("password") RequestBody password, @Part("name") RequestBody name, @Part("mobile")RequestBody mobile, @Part("adhaar")RequestBody adhaar, @Part("mobile2")RequestBody mobile2, @Part("ip_address")RequestBody ip_address, @Part("vehicle_id")RequestBody vehicle_id, @Part("owner_id")RequestBody owner_id, @Part MultipartBody.Part pic, @Part MultipartBody.Part license_image);


    @GET("driver")
    Call<DriverOwnerData> ogetDriverList(@Query("owner_id") int id);

    @GET("vehicle")
    Call<ListVehicleOwnerData> ogetVehicleList(@Query("owner_id") int owner_id);

    @GET("vehicle")
    Call<VehicleDetailsOwnerData> ogetVehicleDetails(@Query("id") int id);


}
