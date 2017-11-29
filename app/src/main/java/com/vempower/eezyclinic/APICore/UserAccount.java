
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.core.AdapterItem;

public class UserAccount implements AdapterItem {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("patientName")
    @Expose
    private String patientName;
    @SerializedName("patientUniqueId")
    @Expose
    private String patientUniqueId;
    @SerializedName("patentEmail")
    @Expose
    private String patentEmail;

    public String getId() {
        return id;
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

    public String getPatentEmail() {
        return patentEmail;
    }

    public void setPatentEmail(String patentEmail) {
        this.patentEmail = patentEmail;
    }

    @Override
    public String getItemName() {
        return patientName+"\n"+patentEmail;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id='" + id + '\'' +
                ", patientName='" + patientName + '\'' +
                ", patientUniqueId='" + patientUniqueId + '\'' +
                ", patentEmail='" + patentEmail + '\'' +
                '}';
    }
}
