package com.lifecapable.vehicledriver.owner.datamodel;

import java.util.List;

public class DriverRoot {

    List<DriverOwnerData> drivers;

    DriverRoot(){ }

    public List<DriverOwnerData> getDrivers() {
        return drivers;
    }
}
