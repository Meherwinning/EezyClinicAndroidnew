package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by satish on 21/11/17.
 */

public class AbstractResponce {
    @SerializedName("status_code")
    @Expose
    private String statusCode;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;


    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }



}
