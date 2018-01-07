
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileSettingsData {

    @SerializedName("myuploadfiles")
    @Expose
    private ProfileSettingReport myuploadfiles;
    @SerializedName("doctorreport")
    @Expose
    private ProfileSettingReport doctorreport;
    @SerializedName("diagnosticreport")
    @Expose
    private ProfileSettingReport diagnosticreport;
    @SerializedName("patient_profile")
    @Expose
    private ProfileSettingReport patientProfile;
    @SerializedName("medical_history")
    @Expose
    private ProfileSettingReport medicalHistory;
    @SerializedName("patient_notes")
    @Expose
    private ProfileSettingReport patientNotes;

    public ProfileSettingReport getMyuploadfiles() {
        return myuploadfiles;
    }

    public void setMyuploadfiles(ProfileSettingReport myuploadfiles) {
        this.myuploadfiles = myuploadfiles;
    }

    public ProfileSettingReport getDoctorreport() {
        return doctorreport;
    }

    public void setDoctorreport(ProfileSettingReport doctorreport) {
        this.doctorreport = doctorreport;
    }

    public ProfileSettingReport getDiagnosticreport() {
        return diagnosticreport;
    }

    public void setDiagnosticreport(ProfileSettingReport diagnosticreport) {
        this.diagnosticreport = diagnosticreport;
    }

    public ProfileSettingReport getPatientProfile() {
        return patientProfile;
    }

    public void setPatientProfile(ProfileSettingReport patientProfile) {
        this.patientProfile = patientProfile;
    }

    public ProfileSettingReport getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(ProfileSettingReport medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public ProfileSettingReport getPatientNotes() {
        return patientNotes;
    }

    public void setPatientNotes(ProfileSettingReport patientNotes) {
        this.patientNotes = patientNotes;
    }

}
