package com.lifecapable.vehicledriver.owner.datamodel;

public class VehicleIds {
    int v_id;
    String name,plate_no;

    public VehicleIds(int v_id, String name) {
        this.v_id = v_id;
        this.name = name;
    }

    public int getV_id() {
        return v_id;
    }

    public void setV_id(int v_id) {
        this.v_id = v_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlate_no() {
        return plate_no;
    }
}
