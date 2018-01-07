
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileSettingReport {

    @SerializedName("onlyappointmentdoctor")
    @Expose
    private String onlyappointmentdoctor;
    @SerializedName("doctorswithinsameclinic")
    @Expose
    private String doctorswithinsameclinic;
    @SerializedName("family")
    @Expose
    private String family;

    public String getOnlyappointmentdoctor() {
        return onlyappointmentdoctor;
    }

    public void setOnlyappointmentdoctor(String onlyappointmentdoctor) {
        this.onlyappointmentdoctor = onlyappointmentdoctor;
    }

    public String getDoctorswithinsameclinic() {
        return doctorswithinsameclinic;
    }

    public void setDoctorswithinsameclinic(String doctorswithinsameclinic) {
        this.doctorswithinsameclinic = doctorswithinsameclinic;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

}
