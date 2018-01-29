
package com.vempower.eezyclinic.APICore;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpcomingFollowupsData {

    @SerializedName("followups")
    @Expose
    private List<Followup> followups = null;

    public List<Followup> getFollowups() {
        return followups;
    }

    public void setFollowups(List<Followup> followups) {
        this.followups = followups;
    }

}
