package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by satish on 21/11/17.
 */

public class AbstractResponse {
    @SerializedName("status_code")
    @Expose
    private String statusCode;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;


    public final String getStatusCode() {
        return statusCode;
    }

    public final void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public final String getStatusMessage() {
        return statusMessage;
    }

    public final void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }



}
