package com.lifecapable.vehicledriver.owner.datamodel;

public class VehicleOwnerData {
    String name, plate;
    int id;

    public VehicleOwnerData(String name, String plate, int id) {
        this.name = name;
        this.plate = plate;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
