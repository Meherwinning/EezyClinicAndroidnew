
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.DashboardData;

public class NotificationsAPI extends AbstractResponse {


    @SerializedName("totalcount")
    @Expose
    private String totalcount;


    public String getTotalcount() {
        return totalcount;
    }
}
