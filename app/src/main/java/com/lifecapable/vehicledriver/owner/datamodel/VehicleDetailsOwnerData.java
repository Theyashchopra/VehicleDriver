package com.lifecapable.vehicledriver.owner.datamodel;

public class VehicleDetailsOwnerData {

    String name, plate, year_of_manufacture, image, available, status,driver_id, lat, lon, rotation, rc, invoice, insurance, model_name;
    int id, model_id, total_running_hrs, speed, fuel_consumption, rent_per_day, rent_per_hour;

    public VehicleDetailsOwnerData(String name, String plate, String year_of_manufacture, String image, String available, String status, String driver_id, String lat, String lon, String rotation, String rc, String invoice, String insurance, String model_name, int id, int model_id, int total_running_hrs, int speed, int fuel_consumption, int rent_per_day, int rent_per_hour) {
        this.name = name;
        this.plate = plate;
        this.year_of_manufacture = year_of_manufacture;
        this.image = image;
        this.available = available;
        this.status = status;
        this.driver_id = driver_id;
        this.lat = lat;
        this.lon = lon;
        this.rotation = rotation;
        this.rc = rc;
        this.invoice = invoice;
        this.insurance = insurance;
        this.model_name = model_name;
        this.id = id;
        this.model_id = model_id;
        this.total_running_hrs = total_running_hrs;
        this.speed = speed;
        this.fuel_consumption = fuel_consumption;
        this.rent_per_day = rent_per_day;
        this.rent_per_hour = rent_per_hour;
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

    public String getYear_of_manufacture() {
        return year_of_manufacture;
    }

    public void setYear_of_manufacture(String year_of_manufacture) {
        this.year_of_manufacture = year_of_manufacture;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getRotation() {
        return rotation;
    }

    public void setRotation(String rotation) {
        this.rotation = rotation;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModel_id() {
        return model_id;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }

    public int getTotal_running_hrs() {
        return total_running_hrs;
    }

    public void setTotal_running_hrs(int total_running_hrs) {
        this.total_running_hrs = total_running_hrs;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getFuel_consumption() {
        return fuel_consumption;
    }

    public void setFuel_consumption(int fuel_consumption) {
        this.fuel_consumption = fuel_consumption;
    }

    public int getRent_per_day() {
        return rent_per_day;
    }

    public void setRent_per_day(int rent_per_day) {
        this.rent_per_day = rent_per_day;
    }

    public int getRent_per_hour() {
        return rent_per_hour;
    }

    public void setRent_per_hour(int rent_per_hour) {
        this.rent_per_hour = rent_per_hour;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }
}
