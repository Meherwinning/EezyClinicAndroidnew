
package com.vempower.eezyclinic.APICore;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CasesheetData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("appointmentId")
    @Expose
    private String appointmentId;
    @SerializedName("factsheetUniqueId")
    @Expose
    private String factsheetUniqueId;
    @SerializedName("refferedBy")
    @Expose
    private String refferedBy;
    @SerializedName("complaint")
    @Expose
    private String complaint;
    @SerializedName("historyofpresentillness")
    @Expose
    private String historyofpresentillness;
    @SerializedName("observartions")
    @Expose
    private String observartions;
    @SerializedName("investigations")
    @Expose
    private String investigations;
    @SerializedName("treatment")
    @Expose
    private String treatment;
    @SerializedName("reviewadvise")
    @Expose
    private String reviewadvise;
    @SerializedName("temparature")
    @Expose
    private String temparature;
    @SerializedName("pulse")
    @Expose
    private String pulse;
    @SerializedName("rr")
    @Expose
    private String rr;
    @SerializedName("bp")
    @Expose
    private String bp;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("instructions")
    @Expose
    private String instructions;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("doctorFullName")
    @Expose
    private String doctorFullName;
    @SerializedName("specalities")
    @Expose
    private String specalities;
    @SerializedName("clinic_name")
    @Expose
    private String clinicName;
    @SerializedName("medicalhistory")
    @Expose
    private String medicalhistory;
    @SerializedName("patientName")
    @Expose
    private String patientName;
    @SerializedName("patientUniqueId")
    @Expose
    private String patientUniqueId;
    @SerializedName("prescription_details")
    @Expose
    private List<CasesheetPrescriptionDetail> prescriptionDetails = null;
    @SerializedName("complaint_details")
    @Expose
    private CasesheetComplaintDetails complaintDetails;
    @SerializedName("historyofpresentillness_details")
    @Expose
    private CasesheetHistoryofpresentillnessDetails historyofpresentillnessDetails;
    @SerializedName("investigation_details")
    @Expose
    private CasesheetInvestigationDetails investigationDetails;
    @SerializedName("treatment_details")
    @Expose
    private CasesheetTreatmentDetails treatmentDetails;
    @SerializedName("followup_days")
    @Expose
    private String followupDays;
    @SerializedName("print_copy")
    @Expose
    private String printCopy;

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

    public String getFactsheetUniqueId() {
        return factsheetUniqueId;
    }

    public void setFactsheetUniqueId(String factsheetUniqueId) {
        this.factsheetUniqueId = factsheetUniqueId;
    }

    public String getRefferedBy() {
        return refferedBy;
    }

    public void setRefferedBy(String refferedBy) {
        this.refferedBy = refferedBy;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getHistoryofpresentillness() {
        return historyofpresentillness;
    }

    public void setHistoryofpresentillness(String historyofpresentillness) {
        this.historyofpresentillness = historyofpresentillness;
    }

    public String getObservartions() {
        return observartions;
    }

    public void setObservartions(String observartions) {
        this.observartions = observartions;
    }

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

    public String getReviewadvise() {
        return reviewadvise;
    }

    public void setReviewadvise(String reviewadvise) {
        this.reviewadvise = reviewadvise;
    }

    public String getTemparature() {
        return temparature;
    }

    public void setTemparature(String temparature) {
        this.temparature = temparature;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getRr() {
        return rr;
    }

    public void setRr(String rr) {
        this.rr = rr;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
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

    public String getDoctorFullName() {
        return doctorFullName;
    }

    public void setDoctorFullName(String doctorFullName) {
        this.doctorFullName = doctorFullName;
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

    public String getMedicalhistory() {
        return medicalhistory;
    }

    public void setMedicalhistory(String medicalhistory) {
        this.medicalhistory = medicalhistory;
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

    public List<CasesheetPrescriptionDetail> getPrescriptionDetails() {
        return prescriptionDetails;
    }

    public void setPrescriptionDetails(List<CasesheetPrescriptionDetail> prescriptionDetails) {
        this.prescriptionDetails = prescriptionDetails;
    }

    public CasesheetComplaintDetails getComplaintDetails() {
        return complaintDetails;
    }

    public void setComplaintDetails(CasesheetComplaintDetails complaintDetails) {
        this.complaintDetails = complaintDetails;
    }

    public CasesheetHistoryofpresentillnessDetails getHistoryofpresentillnessDetails() {
        return historyofpresentillnessDetails;
    }

    public void setHistoryofpresentillnessDetails(CasesheetHistoryofpresentillnessDetails historyofpresentillnessDetails) {
        this.historyofpresentillnessDetails = historyofpresentillnessDetails;
    }

    public CasesheetInvestigationDetails getInvestigationDetails() {
        return investigationDetails;
    }

    public void setInvestigationDetails(CasesheetInvestigationDetails investigationDetails) {
        this.investigationDetails = investigationDetails;
    }

    public CasesheetTreatmentDetails getTreatmentDetails() {
        return treatmentDetails;
    }

    public void setTreatmentDetails(CasesheetTreatmentDetails treatmentDetails) {
        this.treatmentDetails = treatmentDetails;
    }

    public String getFollowupDays() {
        return followupDays;
    }

    public void setFollowupDays(String followupDays) {
        this.followupDays = followupDays;
    }

    public String getPrintCopy() {
        return printCopy;
    }

    public void setPrintCopy(String printCopy) {
        this.printCopy = printCopy;
    }

}
