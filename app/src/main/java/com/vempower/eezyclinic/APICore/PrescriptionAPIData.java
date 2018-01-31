
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrescriptionAPIData extends PDFDetails {
//HARVESTCB
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
    private PrescriptionDocumentFile documentFile;
    @SerializedName("other_images")
    @Expose
    private List<OtherImage> otherImages = null;;
    @SerializedName("documentDate")
    @Expose
    private String documentDate;
    @SerializedName("otherDetails")
    @Expose
    private String otherDetails;
    @SerializedName("documentName")
    @Expose
    private String documentName;

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;


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

    public PrescriptionDocumentFile getDocumentFile() {
        return documentFile;
    }

    public void setDocumentFile(PrescriptionDocumentFile documentFile) {
        this.documentFile = documentFile;
    }

    public List<OtherImage> getOtherImages() {
        return otherImages;
    }

    public void setOtherImages(List<OtherImage> otherImages) {
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


}
