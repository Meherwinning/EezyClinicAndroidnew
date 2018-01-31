
package com.vempower.eezyclinic.APICore;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMyNotesData {

    @SerializedName("patientId")
    @Expose
    private String patientId;
    @SerializedName("mynotes")
    @Expose
    private String mynotes;

    public String getPatientId() {

        if(TextUtils.isEmpty(patientId))
        {
            patientId="-1";
        }
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getMynotes() {
        return mynotes;
    }

    public void setMynotes(String mynotes) {
        this.mynotes = mynotes;
    }

}
