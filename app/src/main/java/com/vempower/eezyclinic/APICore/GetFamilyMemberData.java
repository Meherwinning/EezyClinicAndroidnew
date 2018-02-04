
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetFamilyMemberData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("patientId")
    @Expose
    private String patientId;
    @SerializedName("familyMemberpatientId")
    @Expose
    private String familyMemberpatientId;
    @SerializedName("relation")
    @Expose
    private String relation;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("patientName")
    @Expose
    private String patientName;
    @SerializedName("patientUniqueId")
    @Expose
    private String patientUniqueId;
    @SerializedName("viewprofilestatus")
    @Expose
    private Integer viewprofilestatus;

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

    public String getFamilyMemberpatientId() {
        return familyMemberpatientId;
    }

    public void setFamilyMemberpatientId(String familyMemberpatientId) {
        this.familyMemberpatientId = familyMemberpatientId;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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

    public Integer getViewprofilestatus() {
        return viewprofilestatus;
    }

    public void setViewprofilestatus(Integer viewprofilestatus) {
        this.viewprofilestatus = viewprofilestatus;
    }

}
