package com.lifecapable.vehicledriver.owner.datamodel;

public class AppointmentOwnerData {
    int id;
    String customer_name, address, customer_mobile;

    public AppointmentOwnerData(int id, String customer_name, String address, String customer_mobile) {
        this.id = id;
        this.customer_name = customer_name;
        this.address = address;
        this.customer_mobile = customer_mobile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
