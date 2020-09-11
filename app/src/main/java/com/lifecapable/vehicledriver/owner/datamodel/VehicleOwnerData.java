package com.lifecapable.vehicledriver.owner.datamodel;

public class VehicleOwnerData {
    String vehiclename, vehiclenumber;

    public VehicleOwnerData(String vehiclename, String vehiclenumber) {
        this.vehiclename = vehiclename;
        this.vehiclenumber = vehiclenumber;
    }

    public String getVehiclename() {
        return vehiclename;
    }

    public void setVehiclename(String vehiclename) {
        this.vehiclename = vehiclename;
    }

    public String getVehiclenumber() {
        return vehiclenumber;
    }

    public void setVehiclenumber(String vehiclenumber) {
        this.vehiclenumber = vehiclenumber;
    }
}
