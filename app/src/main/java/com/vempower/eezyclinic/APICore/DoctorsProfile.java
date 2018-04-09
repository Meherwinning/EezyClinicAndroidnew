
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorsProfile {

    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("languages_known")
    @Expose
    private String languagesKnown;
    @SerializedName("about_doctor")
    @Expose
    private String aboutDoctor;
    @SerializedName("consultation_fee")
    @Expose
    private String consultationFee;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getLanguagesKnown() {
        return languagesKnown;
    }

    public void setLanguagesKnown(String languagesKnown) {
        this.languagesKnown = languagesKnown;
    }

    public String getAboutDoctor() {
        aboutDoctor=aboutDoctor.replaceAll("<br />","\n");
        return aboutDoctor;
    }

    public void setAboutDoctor(String aboutDoctor) {
        this.aboutDoctor = aboutDoctor;
    }

    public String getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(String consultationFee) {
        this.consultationFee = consultationFee;
    }

}
