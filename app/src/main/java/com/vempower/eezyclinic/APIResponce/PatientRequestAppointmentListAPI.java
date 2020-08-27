package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.PatientRequestAppointment;
import com.vempower.eezyclinic.APICore.TeleConsultation;

import java.util.List;

public class PatientRequestAppointmentListAPI extends AbstractResponse {
    @SerializedName("data")
    @Expose
    private List<PatientRequestAppointment> data = null;

 /*   public Integer getStatusCode() {
        return statusCode;
    }*/

    /*public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }*/

    public List<PatientRequestAppointment> getData() {
        return data;
    }

    public void setData(List<PatientRequestAppointment> data) {
        this.data = data;
    }
}
