
package com.vempower.eezyclinic.APICore;

import android.text.TextUtils;

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
        if(TextUtils.isEmpty(onlyappointmentdoctor))
        {
            onlyappointmentdoctor="0";
        }
        return onlyappointmentdoctor;
    }

    public void setOnlyappointmentdoctor(String onlyappointmentdoctor) {
        this.onlyappointmentdoctor = onlyappointmentdoctor;
    }

    public String getDoctorswithinsameclinic() {
        if(TextUtils.isEmpty(doctorswithinsameclinic))
        {
            doctorswithinsameclinic="0";
        }
        return doctorswithinsameclinic;
    }

    public void setDoctorswithinsameclinic(String doctorswithinsameclinic) {
        this.doctorswithinsameclinic = doctorswithinsameclinic;
    }

    public String getFamily() {
        if(TextUtils.isEmpty(family))
        {
            family="0";
        }
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

}
