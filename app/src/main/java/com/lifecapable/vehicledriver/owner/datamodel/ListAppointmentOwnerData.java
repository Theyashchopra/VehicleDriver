package com.lifecapable.vehicledriver.owner.datamodel;

import java.util.List;

public class ListAppointmentOwnerData {
    List<AppointmentOwnerData> appointments;

    public ListAppointmentOwnerData(List<AppointmentOwnerData> appointments) {
        this.appointments = appointments;
    }

    public List<AppointmentOwnerData> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentOwnerData> appointments) {
        this.appointments = appointments;
    }
}
