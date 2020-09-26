package com.lifecapable.vehicledriver.Driver.datamodels;

public class HomeDriverData {
    int id, owner_id, vehicle_id;
    String customer_name, address, customer_mobile, alternate_mobile, owner_name, start_day, end_day, time;

    public HomeDriverData(int id, int owner_id, int vehicle_id, String customer_name, String address, String customer_mobile, String alternate_mobile, String owner_name, String start_day, String end_day, String time) {
        this.id = id;
        this.owner_id = owner_id;
        this.vehicle_id = vehicle_id;
        this.customer_name = customer_name;
        this.address = address;
        this.customer_mobile = customer_mobile;
        this.alternate_mobile = alternate_mobile;
        this.owner_name = owner_name;
        this.start_day = start_day;
        this.end_day = end_day;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomer_mobile() {
        return customer_mobile;
    }

    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }

    public String getAlternate_mobile() {
        return alternate_mobile;
    }

    public void setAlternate_mobile(String alternate_mobile) {
        this.alternate_mobile = alternate_mobile;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getStart_day() {
        return start_day;
    }

    public void setStart_day(String start_day) {
        this.start_day = start_day;
    }

    public String getEnd_day() {
        return end_day;
    }

    public void setEnd_day(String end_day) {
        this.end_day = end_day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
