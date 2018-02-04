
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchFamilyMemberData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("patientName")
    @Expose
    private String patientName;
    @SerializedName("patientUniqueId")
    @Expose
    private String patientUniqueId;

    public int getId() {

        try {
            return Integer.parseInt(id);
        } catch (Exception e) {

        }
        return -1;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientUniqueId() {
        return patientUniqueId;
    }

    public void setPatientUniqueId(String patientUniqueId) {
        this.patientUniqueId = patientUniqueId;
    }

    @Override
    public String toString() {
        return "SearchFamilyMemberData{" +
                "id='" + id + '\'' +
                ", patientName='" + patientName + '\'' +
                ", patientUniqueId='" + patientUniqueId + '\'' +
                '}';
    }
}
