
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.AppointmentTimeSlotsData;

public class AppointmentTimeSlotsAPI {

    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("data")
    @Expose
    private AppointmentTimeSlotsData data;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public AppointmentTimeSlotsData getData() {
        return data;
    }

    public void setData(AppointmentTimeSlotsData data) {
        this.data = data;
    }

}
