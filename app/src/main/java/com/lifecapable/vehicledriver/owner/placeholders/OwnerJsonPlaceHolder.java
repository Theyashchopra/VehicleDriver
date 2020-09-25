package com.lifecapable.vehicledriver.owner.placeholders;

import com.lifecapable.vehicledriver.owner.categories.MasterRoot;
import com.lifecapable.vehicledriver.owner.categories.VehicleModelRoot;
import com.lifecapable.vehicledriver.owner.categories.VehicleTypeRoot;
import com.lifecapable.vehicledriver.owner.datamodel.AppointmentOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.DriverDetailsOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.DriverOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.ListAppointmentOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.ListVehicleOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.LoginOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.Messages;
import com.lifecapable.vehicledriver.owner.datamodel.ProfileOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.RootEnquiry;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleDetailsOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleIds;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @Multipart
    @POST("/dimages")
    Call<Messages> addDriverImage(@Part("id")RequestBody id, @Part MultipartBody.Part pic);

    @Multipart
    @PUT
    Call<Messages> addDriverLicence(@Part("id") RequestBody id, @Part MultipartBody.Part licence);

    @GET("driver")
    Call<DriverOwnerData> ogetDriverList(@Query("owner_id") int id);

    @POST("vehicle")
    Call<VehicleIds> addVehicle(@Body VehicleDetailsOwnerData v);

    @GET("vehicle")
    Call<ListVehicleOwnerData> ogetVehicleList(@Query("owner_id") int owner_id);

    @GET("vehicle")
    Call<VehicleDetailsOwnerData> ogetVehicleDetails(@Query("id") int id);

    @GET("vmaster")
    Call<MasterRoot> getMaster();

    @GET("vtype")
    Call<VehicleTypeRoot> getVtypes(@Query("master_id")int id);

    @GET("vmodel")
    Call<VehicleModelRoot> getVModels(@Query("vehicle_type_id") int id);

    @GET("enquiry")
    Call<RootEnquiry> getEnquiries(@Query("owner_id")int id);

    @GET("appt")
    Call<ListAppointmentOwnerData> ogetListAppointment(@Query("owner_id") int owner_id);

    @POST("appt")
    Call<AppointmentOwnerData> oaddAppointment(@Query("customer_name") String customer_name, @Query("address") String address, @Query("customer_mobile") String customer_mobile, @Query("alternate_mobile") String alternate_mobile, @Query("owner_id") int owner_id, @Query("vehicle_id") int vehicle_id, @Query("start") String start, @Query("end") String end, @Query("time") String time);

    @GET("regowner")
    Call<ProfileOwnerData> ogetProfileData(@Query("id") int id);

}
