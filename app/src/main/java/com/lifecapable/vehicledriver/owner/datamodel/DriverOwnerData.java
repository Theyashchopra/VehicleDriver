package com.lifecapable.vehicledriver.owner.datamodel;

public class DriverOwnerData {
    int id;
    String name;
    String contact;

    public DriverOwnerData(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
