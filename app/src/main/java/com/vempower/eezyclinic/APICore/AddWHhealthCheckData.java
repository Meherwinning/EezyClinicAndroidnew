
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddWHhealthCheckData {

    @SerializedName("patientId")
    @Expose
    private String patientId;
    @SerializedName("checkupgroupid")
    @Expose
    private String checkupgroupid;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("height")
    @Expose
    private Float height;
    @SerializedName("checkupTime")
    @Expose
    private String checkupTime;

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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public String getCheckupTime() {
        return checkupTime;
    }

    public void setCheckupTime(String checkupTime) {
        this.checkupTime = checkupTime;
    }

}
