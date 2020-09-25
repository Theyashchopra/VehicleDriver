package com.lifecapable.vehicledriver.owner.datamodel;

public class VehicleOwnerData {
    String name, plate_no,model_name;
    int v_id;
    boolean isDocument,isImage;

    public VehicleOwnerData(String name, String plate_no, int v_id) {
        this.name = name;
        this.plate_no = plate_no;
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

    public int getV_id() {
        return v_id;
    }

    public void setV_id(int v_id) {
        this.v_id = v_id;
    }

    public String getModel_name() {
        return model_name;
    }

    public boolean isDocument() {
        return isDocument;
    }

    public boolean isImage() {
        return isImage;
    }
}
