
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.Followup;
import com.vempower.eezyclinic.APICore.UpcomingFollowupsData;

import java.util.List;

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


   /* @SerializedName("followups")
    @Expose
    private List<Followup> followups = null;

    public List<Followup> getFollowups() {
        return followups;
    }

    public void setFollowups(List<Followup> followups) {
        this.followups = followups;
    }*/

}
