package com.lifecapable.vehicledriver.Driver.datamodels;

import java.util.List;

public class ListHomeDriverData {
    List<HomeDriverData> appointments;

    public ListHomeDriverData(List<HomeDriverData> appointments) {
        this.appointments = appointments;
    }

    public List<HomeDriverData> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<HomeDriverData> appointments) {
        this.appointments = appointments;
    }
}
