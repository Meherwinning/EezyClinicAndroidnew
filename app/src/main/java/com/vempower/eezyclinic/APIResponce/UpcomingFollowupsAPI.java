
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.UpcomingFollowupsData;

public class UpcomingFollowupsAPI extends AbstractResponse {


    @SerializedName("data")
    @Expose
    private UpcomingFollowupsData data;


    public UpcomingFollowupsData getData() {
        return data;
    }

    public void setData(UpcomingFollowupsData data) {
        this.data = data;
    }

}
