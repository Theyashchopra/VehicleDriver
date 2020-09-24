package com.lifecapable.vehicledriver.owner.datamodel;

public class HomeOwnerData {

    public int eid;
    public int uid;
    public String user_name;
    public String user_mobile;
    public String user_email;
    public int owner_id;
    public String owner_name;
    public String owner_email;
    public int vehicle_id;
    public String vehicle_name;
    public String vehicle_plate;
    public String date_of_enquiry;
    public String location;
    public float lat;
    public float lon;

    HomeOwnerData(){
    }

    public int getEid() {
        return eid;
    }

    public int getUid() {
        return uid;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public String getUser_email() {
        return user_email;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public String getOwner_email() {
        return owner_email;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public String getVehicle_plate() {
        return vehicle_plate;
    }

    public String getDate_of_enquiry() {
        return date_of_enquiry;
    }

    public String getLocation() {
        return location;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }
}
