package com.lifecapable.vehicledriver.owner.datamodel;

public class SelectVehicleOwnerData {

    int v_id;
    String name, plate_no;

    public SelectVehicleOwnerData(int v_id, String name, String plate_no) {
        this.v_id = v_id;
        this.name = name;
        this.plate_no = plate_no;
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

    public void setPlate_no(String plate_no) {
        this.plate_no = plate_no;
    }
}
