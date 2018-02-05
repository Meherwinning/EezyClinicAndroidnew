
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationsComingappointment extends AbstractNotification {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("bookingType")
    @Expose
    private String bookingType;
    @SerializedName("patenetRandomId")
    @Expose
    private String patenetRandomId;
    @SerializedName("patientId")
    @Expose
    private String patientId;
    @SerializedName("clinicId")
    @Expose
    private String clinicId;
    @SerializedName("doctorId")
    @Expose
    private String doctorId;
    @SerializedName("branchId")
    @Expose
    private String branchId;
    @SerializedName("appointmentDateTime")
    @Expose
    private String appointmentDateTime;
    @SerializedName("appointmentDate")
    @Expose
    private String appointmentDate;
    @SerializedName("patientName")
    @Expose
    private String patientName;
    @SerializedName("selectFamilyMember")
    @Expose
    private String selectFamilyMember;
    @SerializedName("reasonsForAppoinment")
    @Expose
    private String reasonsForAppoinment;
    @SerializedName("appointmentFor")
    @Expose
    private String appointmentFor;
    @SerializedName("appointmentbookingemail")
    @Expose
    private String appointmentbookingemail;
    @SerializedName("appointmentbookindmobile")
    @Expose
    private String appointmentbookindmobile;
    @SerializedName("mobileotp")
    @Expose
    private String mobileotp;
    @SerializedName("cancelReason")
    @Expose
    private Object cancelReason;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("procedureStatus")
    @Expose
    private String procedureStatus;
    @SerializedName("patientInTime")
    @Expose
    private Object patientInTime;
    @SerializedName("proceedToDoctorTime")
    @Expose
    private Object proceedToDoctorTime;
    @SerializedName("patientOutTime")
    @Expose
    private Object patientOutTime;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("doctorName")
    @Expose
    private String doctorName;
    @SerializedName("clinicName")
    @Expose
    private String clinicName;
    @SerializedName("locality")
    @Expose
    private String locality;
    @SerializedName("city")
    @Expose
    private String city;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public String getPatenetRandomId() {
        return patenetRandomId;
    }

    public void setPatenetRandomId(String patenetRandomId) {
        this.patenetRandomId = patenetRandomId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(String appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getSelectFamilyMember() {
        return selectFamilyMember;
    }

    public void setSelectFamilyMember(String selectFamilyMember) {
        this.selectFamilyMember = selectFamilyMember;
    }

    public String getReasonsForAppoinment() {
        return reasonsForAppoinment;
    }

    public void setReasonsForAppoinment(String reasonsForAppoinment) {
        this.reasonsForAppoinment = reasonsForAppoinment;
    }

    public String getAppointmentFor() {
        return appointmentFor;
    }

    public void setAppointmentFor(String appointmentFor) {
        this.appointmentFor = appointmentFor;
    }

    public String getAppointmentbookingemail() {
        return appointmentbookingemail;
    }

    public void setAppointmentbookingemail(String appointmentbookingemail) {
        this.appointmentbookingemail = appointmentbookingemail;
    }

    public String getAppointmentbookindmobile() {
        return appointmentbookindmobile;
    }

    public void setAppointmentbookindmobile(String appointmentbookindmobile) {
        this.appointmentbookindmobile = appointmentbookindmobile;
    }

    public String getMobileotp() {
        return mobileotp;
    }

    public void setMobileotp(String mobileotp) {
        this.mobileotp = mobileotp;
    }

    public Object getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(Object cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProcedureStatus() {
        return procedureStatus;
    }

    public void setProcedureStatus(String procedureStatus) {
        this.procedureStatus = procedureStatus;
    }

    public Object getPatientInTime() {
        return patientInTime;
    }

    public void setPatientInTime(Object patientInTime) {
        this.patientInTime = patientInTime;
    }

    public Object getProceedToDoctorTime() {
        return proceedToDoctorTime;
    }

    public void setProceedToDoctorTime(Object proceedToDoctorTime) {
        this.proceedToDoctorTime = proceedToDoctorTime;
    }

    public Object getPatientOutTime() {
        return patientOutTime;
    }

    public void setPatientOutTime(Object patientOutTime) {
        this.patientOutTime = patientOutTime;
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

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int getNotificationType() {
        return AbstractNotification.COMING_APPOINTMENT_TYPE;
    }

}
