
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationsSendrequest extends AbstractNotification {

    @SerializedName("countryName")
    @Expose
    private Object countryName;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("patientUniqueId")
    @Expose
    private String patientUniqueId;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("familyMemberpatientId")
    @Expose
    private String familyMemberpatientId;
    @SerializedName("patientId")
    @Expose
    private String patientId;
    @SerializedName("patientName")
    @Expose
    private String patientName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("patentEmail")
    @Expose
    private String patentEmail;

    public Object getCountryName() {
        return countryName;
    }

    public void setCountryName(Object countryName) {
        this.countryName = countryName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPatientUniqueId() {
        return patientUniqueId;
    }

    public void setPatientUniqueId(String patientUniqueId) {
        this.patientUniqueId = patientUniqueId;
    }

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

    public String getFamilyMemberpatientId() {
        return familyMemberpatientId;
    }

    public void setFamilyMemberpatientId(String familyMemberpatientId) {
        this.familyMemberpatientId = familyMemberpatientId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPatentEmail() {
        return patentEmail;
    }

    public void setPatentEmail(String patentEmail) {
        this.patentEmail = patentEmail;
    }

    @Override
    public int getNotificationType() {
        return AbstractNotification.SEND_REQUEST_TYPE;
    }
}
