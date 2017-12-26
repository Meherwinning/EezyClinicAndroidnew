
package com.vempower.eezyclinic.APICore;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardData {

    @SerializedName("patient_profile")
    @Expose
    private PatientData patientProfile;
    @SerializedName("healthtips")
    @Expose
    private String healthtips;
    @SerializedName("healthgoals")
    @Expose
    private String healthgoals;

    @SerializedName("followups")
    @Expose
    private List<Followup> followups = null;


    public PatientData getPatientProfile() {
        return patientProfile;
    }

    public void setPatientProfile(PatientData patientProfile) {
        this.patientProfile = patientProfile;
    }

    public String getHealthtips() {
        return healthtips;
    }

    public void setHealthtips(String healthtips) {
        this.healthtips = healthtips;
    }

    public String getHealthgoals() {
        return healthgoals;
    }

    public void setHealthgoals(String healthgoals) {
        this.healthgoals = healthgoals;
    }



    public List<Followup> getFollowups() {
        return followups;
    }

    public void setFollowups(List<Followup> followups) {
        this.followups = followups;
    }

   /* public List<Appointment> getHealthcheckuplist() {
        return healthcheckuplist;
    }

    public void setHealthcheckuplist(List<Appointment> healthcheckuplist) {
        this.healthcheckuplist = healthcheckuplist;
    }*/

}
