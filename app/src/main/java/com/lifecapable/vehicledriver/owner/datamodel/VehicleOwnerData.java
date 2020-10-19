package com.lifecapable.vehicledriver.owner.datamodel;

import com.google.gson.annotations.SerializedName;

public class VehicleOwnerData {
    String name, plate_no,model_name;
    int v_id;
    boolean isDocument,isImage;
    Double lat;
    @SerializedName("long")
    Double lon;

    public VehicleOwnerData(String name, String plate_no, String model_name, int v_id, boolean isDocument, boolean isImage, Double lat, Double lon) {
        this.name = name;
        this.plate_no = plate_no;
        this.model_name = model_name;
        this.v_id = v_id;
        this.isDocument = isDocument;
        this.isImage = isImage;
        this.lat = lat;
        this.lon = lon;
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

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public int getV_id() {
        return v_id;
    }

    public void setV_id(int v_id) {
        this.v_id = v_id;
    }

    public boolean isDocument() {
        return isDocument;
    }

    public void setDocument(boolean document) {
        isDocument = document;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setImage(boolean image) {
        isImage = image;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
