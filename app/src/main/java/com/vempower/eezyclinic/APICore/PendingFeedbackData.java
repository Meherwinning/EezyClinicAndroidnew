
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PendingFeedbackData {

    @SerializedName("clinicId")
    @Expose
    private String clinicId;
    @SerializedName("appointmentid")
    @Expose
    private String appointmentid;
    @SerializedName("id")
    @Expose
    private Object id;
    @SerializedName("patientId")
    @Expose
    private String patientId;
    @SerializedName("doctorId")
    @Expose
    private String doctorId;
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
    @SerializedName("clinic_name")
    @Expose
    private String clinicName;
    @SerializedName("localityandcity")
    @Expose
    private String localityandcity;
    @SerializedName("doclogo")
    @Expose
    private String doclogo;

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }

    public String getAppointmentid() {
        return appointmentid;
    }

    public void setAppointmentid(String appointmentid) {
        this.appointmentid = appointmentid;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
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

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getLocalityandcity() {
        return localityandcity;
    }

    public void setLocalityandcity(String localityandcity) {
        this.localityandcity = localityandcity;
    }

    public String getDoclogo() {
        return doclogo;
    }

    public void setDoclogo(String doclogo) {
        this.doclogo = doclogo;
    }

}
