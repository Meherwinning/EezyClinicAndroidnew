
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorClinicdetails {

    @SerializedName("Clinic 1")
    @Expose
    private String clinic1;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("clinictimings")
    @Expose
    private String clinictimings;
    @SerializedName("doctortimings")
    @Expose
    private String doctortimings;

    public String getClinic1() {
        return clinic1;
    }

    public void setClinic1(String clinic1) {
        this.clinic1 = clinic1;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getClinictimings() {
        return clinictimings;
    }

    public void setClinictimings(String clinictimings) {
        this.clinictimings = clinictimings;
    }

    public String getDoctortimings() {
        return doctortimings;
    }

    public void setDoctortimings(String doctortimings) {
        this.doctortimings = doctortimings;
    }

}
