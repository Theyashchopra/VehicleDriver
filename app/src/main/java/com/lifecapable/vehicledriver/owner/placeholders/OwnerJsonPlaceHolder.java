package com.lifecapable.vehicledriver.owner.placeholders;

import com.lifecapable.vehicledriver.owner.categories.MasterRoot;
import com.lifecapable.vehicledriver.owner.categories.VehicleModelRoot;
import com.lifecapable.vehicledriver.owner.categories.VehicleTypeRoot;
import com.lifecapable.vehicledriver.owner.datamodel.AppointmentOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.CityModelRoot;
import com.lifecapable.vehicledriver.owner.datamodel.DriverDetailsOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.DriverOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.DriverRoot;
import com.lifecapable.vehicledriver.owner.datamodel.ListAppointmentOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.ListVehicleOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.LocationObject;
import com.lifecapable.vehicledriver.owner.datamodel.LoginOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.Message;
import com.lifecapable.vehicledriver.owner.datamodel.Messages;
import com.lifecapable.vehicledriver.owner.datamodel.ProfileOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.RootEnquiry;
import com.lifecapable.vehicledriver.owner.datamodel.StateModel;
import com.lifecapable.vehicledriver.owner.datamodel.StateModelRoot;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleDetailsOwnerData;
import com.lifecapable.vehicledriver.owner.datamodel.VehicleIds;

import java.lang.reflect.Parameter;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @GET("regowner")
    Call<ProfileOwnerData> ogetProfileData(@Query("id") int id);

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

    //get rc image
    @GET("vdocs")
    Call<ResponseBody> getRC(@Query("rc")int id);

    //get invoice image
    @GET("vdocs")
    Call<ResponseBody> getInvoice(@Query("invoice")int id);

    //get insurance
    @GET("vdocs")
    Call<ResponseBody> getInsurance(@Query("insurance")int id);

    //get vfront
    @GET("vdocs")
    Call<ResponseBody> getVfront(@Query("vfront")int id);

    //get vback
    @GET("vdocs")
    Call<ResponseBody> getVback(@Query("vback")int id);

    //get vside
    @GET("vdocs")
    Call<ResponseBody> getVside(@Query("vside")int id);

    //get location of driver
    @GET("vgps")
    Call<LocationObject> getLocation(@Query("id") int vehicle_id);

    //get driver info
    @GET("driver")
    Call<DriverDetailsOwnerData> getDriverDetails(@Query("id")int id);

    //edit your appointment
    @PUT("appt")
    Call<Map> editAppointment(@Body Map map);

    //delete your appointment
    @DELETE("appt")
    Call<Map> deleteAppointment(@Query("id")int id);

    //update vehicle
    @PUT("vehicle")
    Call<Map> updateVehicleData(@Body Map map);

    //update driver
    @PUT("driver")
    Call<Map> updateDriverData(@Body Map map);

    //check validity
    @GET("subs")
    Call<Map> checkValidity(@Query("validity") int id);

    @GET("states")
    Call<StateModelRoot> getStateList();

    @GET("cities")
    Call<CityModelRoot> getCities(@Query("state_id") int id);

    @POST("regowner")
    Call<Map> registerOwner(@Body Map map);

    @Multipart
    @PUT("ownerkyc")
    Call<Message> uploadAddressImage(@Part("email") RequestBody email, @Part MultipartBody.Part address);

    @Multipart
    @POST("ownerkyc")
    Call<Message> uploadPanImage(@Part("email") RequestBody email, @Part MultipartBody.Part pan);

    @DELETE("driver")
    Call<Message> deleteDriver(@Query("id") int id);

    @PUT("regowner")
    Call<ProfileOwnerData> updateAgencyProfile(@Query("mobile2") String mobile2, @Query("full_address") String full_address,@Query("pin_code")String pin_code,@Query("id") int id, @Query("ip_address") String ip_address);
}
