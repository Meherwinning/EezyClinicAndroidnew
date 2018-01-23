
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CasesheetPrescriptionDetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("factsheetId")
    @Expose
    private String factsheetId;
    @SerializedName("medicine_name")
    @Expose
    private String medicineName;
    @SerializedName("strength")
    @Expose
    private String strength;
    @SerializedName("dosage")
    @Expose
    private String dosage;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("refills")
    @Expose
    private String refills;
    @SerializedName("timings")
    @Expose
    private String timings;
    @SerializedName("food")
    @Expose
    private String food;
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

    public String getFactsheetId() {
        return factsheetId;
    }

    public void setFactsheetId(String factsheetId) {
        this.factsheetId = factsheetId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRefills() {
        return refills;
    }

    public void setRefills(String refills) {
        this.refills = refills;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
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
