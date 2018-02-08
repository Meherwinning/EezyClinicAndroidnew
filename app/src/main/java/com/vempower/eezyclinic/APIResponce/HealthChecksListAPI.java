
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.HealthChecksData;

public class HealthChecksListAPI {

    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("data")
    @Expose
    private HealthChecksData data;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public HealthChecksData getData() {
        return data;
    }

    public void setData(HealthChecksData data) {
        this.data = data;
    }

}
