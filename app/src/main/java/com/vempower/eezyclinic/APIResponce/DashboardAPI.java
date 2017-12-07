
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.DashboardData;

public class DashboardAPI extends AbstractResponse {


    @SerializedName("data")
    @Expose
    private DashboardData data;


    public DashboardData getData() {
        return data;
    }



}
