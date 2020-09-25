package com.lifecapable.vehicledriver.owner.placeholders;

import com.lifecapable.vehicledriver.owner.categories.MasterRoot;
import com.lifecapable.vehicledriver.owner.categories.VehicleModelRoot;
import com.lifecapable.vehicledriver.owner.categories.VehicleTypeRoot;
import com.lifecapable.vehicledriver.owner.datamodel.AppointmentOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.DriverDetailsOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.DriverOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.DriverRoot;
import com.lifecapable.vehicledriver.owner.datamodel.ListAppointmentOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.ListVehicleOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.LoginOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.Message;
import com.lifecapable.vehicledriver.owner.datamodel.Messages;
import com.lifecapable.vehicledriver.owner.datamodel.RootEnquiry;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleDetailsOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleIds;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
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
    //save driver image
    @Multipart
    @POST("dimages")
    Call<Messages> addDriverImage(@Part("email")RequestBody email, @Part MultipartBody.Part pic);

    //save driver license
    @Multipart
    @PUT("dimages")
    Call<Messages> addDriverLicence(@Part("email") RequestBody email, @Part MultipartBody.Part licence);

    @GET("driver")
    Call<DriverRoot> getDriverList(@Query("owner_id") int id);

    @POST("vehicle")
    Call<VehicleIds> addVehicle(@Body VehicleDetailsOwnerData v);

    //get list of vehicles
    @GET("vehicle")
    Call<ListVehicleOwnerData> ogetVehicleList(@Query("owner_id") int owner_id);

    //get vehicle details
    @GET("vehicle")
    Call<VehicleDetailsOwnerData> ogetVehicleDetails(@Query("id") int id);

    //get list of enquiries
    @GET("enquiry")
    Call<RootEnquiry> getEnquiries(@Query("owner_id")int id);

    //get master type
    @GET("vmaster")
    Call<MasterRoot> getMaster();

    //get vehicle type
    @GET("vtype")
    Call<VehicleTypeRoot> getVtypes(@Query("master_id")int id);

    //get model list
    @GET("vmodel")
    Call<VehicleModelRoot> getVModels(@Query("vehicle_type_id") int id);


    @GET("appt")
    Call<ListAppointmentOwnerData> ogetListAppointment(@Query("owner_id") int owner_id);

    @POST("appt")
    Call<AppointmentOwnerData> oaddAppointment(@Query("customer_name") String customer_name, @Query("address") String address, @Query("customer_mobile") String customer_mobile, @Query("alternate_mobile") String alternate_mobile, @Query("owner_id") int owner_id, @Query("vehicle_id") int vehicle_id, @Query("start") String start, @Query("end") String end, @Query("time") String time);

    @GET("appt")
    Call<AppointmentOwnerData> ogetAppointment(@Query("id") int id);

    //get driver image
    @GET("driver")
    Call<ResponseBody> getDriverImage(@Query("driver_image") int id);

    //get driver license
    @GET("driver")
    Call<ResponseBody> getDriverLicense(@Query("driver_license")int id);

    //save Rc
    @Multipart
    @POST("vdocs")
    Call<Message> uploadRC(@Part("plate")RequestBody plate,@Part MultipartBody.Part rc);

    //save Invoice
    @Multipart
    @PUT("vdocs")
    Call<Message> uploadInvoice(@Part("plate")RequestBody plate,@Part MultipartBody.Part invoice);

    //save insurance
    @Multipart
    @PATCH("vdocs")
    Call<Message> uploadInsurance(@Part("plate")RequestBody plate,@Part MultipartBody.Part insurance);

    //save vfront
    @Multipart
    @PATCH("vimages")
    Call<Message> uploadVfront(@Part("plate")RequestBody plate,@Part MultipartBody.Part vfront);

    //save vback
    @Multipart
    @POST("vimages")
    Call<Message> uploadVback(@Part("plate")RequestBody plate,@Part MultipartBody.Part vback);

    //save vside
    @Multipart
    @PUT("vimages")
    Call<Message> uploadVside(@Part("plate")RequestBody plate,@Part MultipartBody.Part vside);

}
