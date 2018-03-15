
package com.vempower.eezyclinic.APICore;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewHomeData {

    @SerializedName("doctors_list")
    @Expose
    private List<NewHomeDoctorsList> doctorsList = null;
    @SerializedName("specialities")
    @Expose
    private List<NewHomeSpeciality> specialities = null;
    @SerializedName("features")
    @Expose
    private List<String> features = null;
    @SerializedName("health Tips")
    @Expose
    private List<String> healthTips = null;

    public List<NewHomeDoctorsList> getDoctorsList() {
        return doctorsList;
    }

    public void setDoctorsList(List<NewHomeDoctorsList> doctorsList) {
        this.doctorsList = doctorsList;
    }

    public List<NewHomeSpeciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<NewHomeSpeciality> specialities) {
        this.specialities = specialities;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public List<String> getHealthTips() {
        return healthTips;
    }

    public void setHealthTips(List<String> healthTips) {
        this.healthTips = healthTips;
    }

}
