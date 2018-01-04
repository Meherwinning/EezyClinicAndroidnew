
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.PatientProfileData;

public class GetPatientProfileAPI extends  AbstractResponse {



    @SerializedName("data")
    @Expose
    private PatientProfileData data;


    public PatientProfileData getData() {
        return data;
    }

    public void setData(PatientProfileData data) {
        this.data = data;
    }

}
