
package com.vempower.eezyclinic.APIResponce;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.Appointment;

public class UpcomingAppointmentListAPI extends  AbstractResponse {

   /* @SerializedName("status_code")
    @Expose
    private Integer statusCode;*/
    @SerializedName("data")
    @Expose
    private List<Appointment> data = null;

 /*   public Integer getStatusCode() {
        return statusCode;
    }*/

    /*public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }*/

    public List<Appointment> getData() {
        return data;
    }

    public void setData(List<Appointment> data) {
        this.data = data;
    }

}
