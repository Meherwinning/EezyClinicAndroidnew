package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeleConsultation {
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
    @SerializedName("patentEmail")
    @Expose
    private String patentEmail;
    @SerializedName("country")
    @Expose
    private String country;
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
    private Object appointmentbookingemail;
    @SerializedName("appointmentbookindmobile")
    @Expose
    private Object appointmentbookindmobile;
    @SerializedName("mobileotp")
    @Expose
    private Object mobileotp;
    @SerializedName("mobile")
    @Expose
    private String mobile;
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
    private String patientInTime;
    @SerializedName("proceedToDoctorTime")
    @Expose
    private Object proceedToDoctorTime;
    @SerializedName("patientOutTime")
    @Expose
    private String patientOutTime;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("main_display_image")
    @Expose
    private Object mainDisplayImage;
    @SerializedName("clinic_name")
    @Expose
    private String clinicName;
    @SerializedName("doctorLogo")
    @Expose
    private String doctorLogo;
    @SerializedName("doctorName")
    @Expose
    private String doctorName;
    @SerializedName("consultancyFee")
    @Expose
    private String consultancyFee;
    @SerializedName("counsultancyPrice")
    @Expose
    private String counsultancyPrice;
    @SerializedName("consultancyFeeCurrency")
    @Expose
    private String consultancyFeeCurrency;
    @SerializedName("paymentStatus")
    @Expose
    private String paymentStatus;
    @SerializedName("branch_timings")
    @Expose
    private String branchTimings;
    @SerializedName("specalities")
    @Expose
    private String specalities;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("locality")
    @Expose
    private String locality;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("google_map_latitude")
    @Expose
    private String googleMapLatitude;
    @SerializedName("google_map_longitude")
    @Expose
    private String googleMapLongitude;

    @SerializedName("RoomName")
    @Expose
    private String RoomName;

    @SerializedName("waitingTime")
    @Expose
    private String waitingTime;

    @SerializedName("consultationTime")
    @Expose
    private String consultationTime;

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

    public String getpatentEmail() {
        return patentEmail;
    }

    public void setpatentEmail(String patentEmail) {
        this.patentEmail = patentEmail;
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

    public Object getAppointmentbookingemail() {
        return appointmentbookingemail;
    }

    public void setAppointmentbookingemail(Object appointmentbookingemail) {
        this.appointmentbookingemail = appointmentbookingemail;
    }

    public Object getAppointmentbookindmobile() {
        return appointmentbookindmobile;
    }

    public void setAppointmentbookindmobile(Object appointmentbookindmobile) {
        this.appointmentbookindmobile = appointmentbookindmobile;
    }

    public Object getMobileotp() {
        return mobileotp;
    }

    public void setMobileotp(Object mobileotp) {
        this.mobileotp = mobileotp;
    }

    public String getMobile(){
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getPatientInTime() {
        return patientInTime;
    }

    public void setPatientInTime(String patientInTime) {
        this.patientInTime = patientInTime;
    }

    public Object getProceedToDoctorTime() {
        return proceedToDoctorTime;
    }

    public void setProceedToDoctorTime(Object proceedToDoctorTime) {
        this.proceedToDoctorTime = proceedToDoctorTime;
    }

    public String getPatientOutTime() {
        return patientOutTime;
    }

    public void setPatientOutTime(String patientOutTime) {
        this.patientOutTime = patientOutTime;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Object getMainDisplayImage() {
        return mainDisplayImage;
    }

    public void setMainDisplayImage(Object mainDisplayImage) {
        this.mainDisplayImage = mainDisplayImage;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getDoctorLogo() {
        return doctorLogo;
    }

    public void setDoctorLogo(String doctorLogo) {
        this.doctorLogo = doctorLogo;
    }

    public String getDoctorName() {
        return doctorName;
    }
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getconsultancyFee() {
        return consultancyFee;
    }
    public void setconsultancyFee(String consultancyFee) {
        this.consultancyFee = consultancyFee;
    }

    public String getcounsultancyPrice() {
        return counsultancyPrice;
    }
    public void setcounsultancyPrice(String counsultancyPrice) {
        this.counsultancyPrice = counsultancyPrice;
    }

    public String getconsultancyFeeCurrency() {
        return consultancyFeeCurrency;
    }
    public void setconsultancyFeeCurrency(String consultancyFeeCurrency) {
        this.consultancyFeeCurrency = consultancyFeeCurrency;
    }

    public String getpaymentStatus() {
        return paymentStatus;
    }
    public void setpaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getBranchTimings() {
        return branchTimings;
    }

    public void setBranchTimings(String branchTimings) {
        this.branchTimings = branchTimings;
    }

    public String getSpecalities() {
        return specalities;
    }

    public void setSpecalities(String specalities) {
        this.specalities = specalities;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGoogleMapLatitude() {
        return googleMapLatitude;
    }

    public void setGoogleMapLatitude(String googleMapLatitude) {
        this.googleMapLatitude = googleMapLatitude;
    }

    public String getGoogleMapLongitude() {
        return googleMapLongitude;
    }

    public void setGoogleMapLongitude(String googleMapLongitude) {
        this.googleMapLongitude = googleMapLongitude;
    }
    public String getRoom_name() {
        return RoomName;
    }

    public void setRoom_name(String RoomName) {
        this.RoomName = RoomName;
    }
    public String getWaitingTime() {
        return waitingTime;
    }

    public String getConsultationTime() {
        return consultationTime;
    }
}
