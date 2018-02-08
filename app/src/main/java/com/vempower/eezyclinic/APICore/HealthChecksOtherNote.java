
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthChecksOtherNote {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("patientId")
    @Expose
    private String patientId;
    @SerializedName("checkupgroupid")
    @Expose
    private String checkupgroupid;
    @SerializedName("checkupName")
    @Expose
    private String checkupName;
    @SerializedName("checkupValue")
    @Expose
    private String checkupValue;
    @SerializedName("checkupTime")
    @Expose
    private String checkupTime;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_on")
    @Expose
    private String createdOn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getCheckupgroupid() {
        return checkupgroupid;
    }

    public void setCheckupgroupid(String checkupgroupid) {
        this.checkupgroupid = checkupgroupid;
    }

    public String getCheckupName() {
        return checkupName;
    }

    public void setCheckupName(String checkupName) {
        this.checkupName = checkupName;
    }

    public String getCheckupValue() {
        return checkupValue;
    }

    public void setCheckupValue(String checkupValue) {
        this.checkupValue = checkupValue;
    }

    public String getCheckupTime() {
        return checkupTime;
    }

    public void setCheckupTime(String checkupTime) {
        this.checkupTime = checkupTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

}
