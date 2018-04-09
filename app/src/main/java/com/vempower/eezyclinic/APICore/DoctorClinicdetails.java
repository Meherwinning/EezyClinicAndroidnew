
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorClinicdetails {

    /*
    "clinic_name": "Clinic 1",
            "clinic_address": "213, Hamsa Building, Besides Post Office Al Karama Dubai www.clinic1.com",
            "contact": "501123232",
            "clinictimings": "Saturday to Thursday 8 am - 9 pm",
            "doctortimings": "Monday: 8am-1pm, 4pm-8pm\nThursday: 8 am to 1 pm\n"
     */

    @SerializedName("clinic_address")
    @Expose
    private String clinic_address;
    @SerializedName("clinic_name")
    @Expose
    private String clinic_name;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("clinictimings")
    @Expose
    private String clinictimings;
    @SerializedName("doctortimings")
    @Expose
    private String doctortimings;

    public String getClinic_address() {
        clinic_address=clinic_address.replaceAll("<br />","\n");
        return clinic_address;
    }

    public void setClinic_address(String clinic_address) {
        this.clinic_address = clinic_address;
    }

    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getClinictimings() {
        clinictimings=clinictimings.replaceAll(":",": ");
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

    /* public String getClinic1() {
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
*/
}
