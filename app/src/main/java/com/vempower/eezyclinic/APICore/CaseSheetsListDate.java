
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CaseSheetsListDate {

    @SerializedName("investigations")
    @Expose
    private String investigations;
    @SerializedName("treatment")
    @Expose
    private String treatment;
    @SerializedName("casesheetstatus")
    @Expose
    private String casesheetstatus;
    @SerializedName("appointmentid")
    @Expose
    private String appointmentid;
    @SerializedName("reasonsForAppoinment")
    @Expose
    private Object reasonsForAppoinment;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("patientInTime")
    @Expose
    private Object patientInTime;
    @SerializedName("procedureStatus")
    @Expose
    private String procedureStatus;
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
    @SerializedName("patenetRandomId")
    @Expose
    private String patenetRandomId;
    @SerializedName("clinic_name")
    @Expose
    private String clinicName;
    @SerializedName("waitingTime")
    @Expose
    private Object waitingTime;
    @SerializedName("patientOutTime")
    @Expose
    private String patientOutTime;
    @SerializedName("consultationTime")
    @Expose
    private String consultationTime;
    @SerializedName("appointmentDateTime")
    @Expose
    private String appointmentDateTime;
    @SerializedName("localityandcity")
    @Expose
    private String localityandcity;
    @SerializedName("consultfee")
    @Expose
    private String consultfee;
    @SerializedName("doclogo")
    @Expose
    private String doclogo;
    @SerializedName("gender")
    @Expose
    private String gender;

    public String getInvestigations() {
        return investigations;
    }

    public void setInvestigations(String investigations) {
        this.investigations = investigations;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getCasesheetstatus() {
        return casesheetstatus;
    }

    public void setCasesheetstatus(String casesheetstatus) {
        this.casesheetstatus = casesheetstatus;
    }

    public String getAppointmentid() {
        return appointmentid;
    }

    public void setAppointmentid(String appointmentid) {
        this.appointmentid = appointmentid;
    }

    public Object getReasonsForAppoinment() {
        return reasonsForAppoinment;
    }

    public void setReasonsForAppoinment(Object reasonsForAppoinment) {
        this.reasonsForAppoinment = reasonsForAppoinment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getPatientInTime() {
        return patientInTime;
    }

    public void setPatientInTime(Object patientInTime) {
        this.patientInTime = patientInTime;
    }

    public String getProcedureStatus() {
        return procedureStatus;
    }

    public void setProcedureStatus(String procedureStatus) {
        this.procedureStatus = procedureStatus;
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

    public String getPatenetRandomId() {
        return patenetRandomId;
    }

    public void setPatenetRandomId(String patenetRandomId) {
        this.patenetRandomId = patenetRandomId;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public Object getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Object waitingTime) {
        this.waitingTime = waitingTime;
    }

    public String getPatientOutTime() {
        return patientOutTime;
    }

    public void setPatientOutTime(String patientOutTime) {
        this.patientOutTime = patientOutTime;
    }

    public String getConsultationTime() {
        return consultationTime;
    }

    public void setConsultationTime(String consultationTime) {
        this.consultationTime = consultationTime;
    }

    public String getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(String appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public String getLocalityandcity() {
        return localityandcity;
    }

    public void setLocalityandcity(String localityandcity) {
        this.localityandcity = localityandcity;
    }

    public String getConsultfee() {
        return consultfee;
    }

    public void setConsultfee(String consultfee) {
        this.consultfee = consultfee;
    }

    public String getDoclogo() {
        return doclogo;
    }

    public void setDoclogo(String doclogo) {
        this.doclogo = doclogo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
