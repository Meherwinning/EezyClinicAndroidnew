
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.AppointmentTimeSlotsData;

public class AppointmentTimeSlotsAPI extends  AbstractResponse{


    @SerializedName("data")
    @Expose
    private AppointmentTimeSlotsData data;


    public AppointmentTimeSlotsData getData() {
        return data;
    }

    public void setData(AppointmentTimeSlotsData data) {
        this.data = data;
    }

}
