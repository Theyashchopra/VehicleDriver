package com.lifecapable.vehicledriver.owner.datamodel;

public class HomeOwnerData {
    String address,contact,name,vehicle;

    public HomeOwnerData(String address, String contact, String name, String vehicle) {
        this.address = address;
        this.contact = contact;
        this.name = name;
        this.vehicle = vehicle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }
}
