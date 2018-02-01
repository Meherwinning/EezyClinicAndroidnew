
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitedFeedbackListData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("waitingTime")
    @Expose
    private String waitingTime;
    @SerializedName("diagnasis")
    @Expose
    private String diagnasis;
    @SerializedName("customerservice")
    @Expose
    private String customerservice;
    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("clinic_name")
    @Expose
    private String clinicName;
    @SerializedName("patientInTime")
    @Expose
    private String patientInTime;
    @SerializedName("appointmentDateTime")
    @Expose
    private String appointmentDateTime;
    @SerializedName("appdate")
    @Expose
    private Object appdate;
    @SerializedName("yearday")
    @Expose
    private Object yearday;
    @SerializedName("time")
    @Expose
    private Object time;
    @SerializedName("doctorfullname")
    @Expose
    private String doctorfullname;
    @SerializedName("specalities")
    @Expose
    private String specalities;
    @SerializedName("doclogo")
    @Expose
    private String doclogo;
    @SerializedName("localityandcity")
    @Expose
    private String localityandcity;
    @SerializedName("recommend")
    @Expose
    private String recommend;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(String waitingTime) {
        this.waitingTime = waitingTime;
    }

    public String getDiagnasis() {
        return diagnasis;
    }

    public void setDiagnasis(String diagnasis) {
        this.diagnasis = diagnasis;
    }

    public String getCustomerservice() {
        return customerservice;
    }

    public void setCustomerservice(String customerservice) {
        this.customerservice = customerservice;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getPatientInTime() {
        return patientInTime;
    }

    public void setPatientInTime(String patientInTime) {
        this.patientInTime = patientInTime;
    }

    public String getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(String appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public Object getAppdate() {
        return appdate;
    }

    public void setAppdate(Object appdate) {
        this.appdate = appdate;
    }

    public Object getYearday() {
        return yearday;
    }

    public void setYearday(Object yearday) {
        this.yearday = yearday;
    }

    public Object getTime() {
        return time;
    }

    public void setTime(Object time) {
        this.time = time;
    }

    public String getDoctorfullname() {
        return doctorfullname;
    }

    public void setDoctorfullname(String doctorfullname) {
        this.doctorfullname = doctorfullname;
    }

    public String getSpecalities() {
        return specalities;
    }

    public void setSpecalities(String specalities) {
        this.specalities = specalities;
    }

    public String getDoclogo() {
        return doclogo;
    }

    public void setDoclogo(String doclogo) {
        this.doclogo = doclogo;
    }

    public String getLocalityandcity() {
        return localityandcity;
    }

    public void setLocalityandcity(String localityandcity) {
        this.localityandcity = localityandcity;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

}
