package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.TeleConsultation;

import java.util.List;

public class TeleConsultationListAPI extends  AbstractResponse {
    @SerializedName("data")
    @Expose
    private List<TeleConsultation> data = null;

 /*   public Integer getStatusCode() {
        return statusCode;
    }*/

    /*public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }*/

    public List<TeleConsultation> getData() {
        return data;
    }

    public void setData(List<TeleConsultation> data) {
        this.data = data;
    }
}
