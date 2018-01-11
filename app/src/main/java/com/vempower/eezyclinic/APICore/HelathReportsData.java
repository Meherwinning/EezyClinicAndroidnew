
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HelathReportsData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("patientId")
    @Expose
    private String patientId;
    @SerializedName("doctorName")
    @Expose
    private String doctorName;
    @SerializedName("clinicName")
    @Expose
    private String clinicName;
    @SerializedName("documentFile")
    @Expose
    private HelathReportDocumentFile documentFile;
    @SerializedName("other_images")
    @Expose
    private Object otherImages;
    @SerializedName("documentDate")
    @Expose
    private String documentDate;
    @SerializedName("otherDetails")
    @Expose
    private String otherDetails;
    @SerializedName("documentName")
    @Expose
    private String documentName;
    @SerializedName("documentType")
    @Expose
    private String documentType;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("dowloadzip")
    @Expose
    private String dowloadzip;

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

    public HelathReportDocumentFile getDocumentFile() {
        return documentFile;
    }

    public void setDocumentFile(HelathReportDocumentFile documentFile) {
        this.documentFile = documentFile;
    }

    public Object getOtherImages() {
        return otherImages;
    }

    public void setOtherImages(Object otherImages) {
        this.otherImages = otherImages;
    }

    public String getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(String documentDate) {
        this.documentDate = documentDate;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
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

    public String getDowloadzip() {
        return dowloadzip;
    }

    public void setDowloadzip(String dowloadzip) {
        this.dowloadzip = dowloadzip;
    }

}
