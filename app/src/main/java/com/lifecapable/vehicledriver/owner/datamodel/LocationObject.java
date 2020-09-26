package com.lifecapable.vehicledriver.owner.datamodel;

public class LocationObject {

    public int driver_id;
    public double lat;
    public double lon;
    public int v_id;

    LocationObject(){ }

    public int getDriver_id() {
        return driver_id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public int getV_id() {
        return v_id;
    }
}
