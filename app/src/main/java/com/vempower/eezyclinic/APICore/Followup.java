package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by satish on 7/12/17.
 */

public class Followup {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("appointmentId")
    @Expose
    private String appointmentId;
    @SerializedName("patientId")
    @Expose
    private String patientId;
    @SerializedName("doctorId")
    @Expose
    private String doctorId;
    @SerializedName("clinicId")
    @Expose
    private String clinicId;
    @SerializedName("factsheetId")
    @Expose
    private String factsheetId;
    @SerializedName("followUpAction")
    @Expose
    private Object followUpAction;
    @SerializedName("upcomingVist")
    @Expose
    private String upcomingVist;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("doctorName")
    @Expose
    private String doctorName;
    @SerializedName("specalities")
    @Expose
    private String specalities;
    @SerializedName("branchId")
    @Expose
    private String branchId;


    @SerializedName("doctorLogo")
    @Expose
    private String doctorLogo;

    //@SerializedName("branchId")
    //@Expose
    private String branchName;

    @SerializedName("clinicName")
    @Expose
    private String clinicName;

    @SerializedName("address")
    @Expose
    private String address;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }

    public String getFactsheetId() {
        return factsheetId;
    }

    public void setFactsheetId(String factsheetId) {
        this.factsheetId = factsheetId;
    }

    public Object getFollowUpAction() {
        return followUpAction;
    }

    public void setFollowUpAction(Object followUpAction) {
        this.followUpAction = followUpAction;
    }

    public String getUpcomingVist() {
        return upcomingVist;
    }

    public void setUpcomingVist(String upcomingVist) {
        this.upcomingVist = upcomingVist;
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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpecalities() {
        return specalities;
    }

    public void setSpecalities(String specalities) {
        this.specalities = specalities;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }





    public String getDoctorLogo() {
        return doctorLogo;
    }


    public String getBranchName() {
        return branchName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public String getAddress() {
        return address;
    }
}
