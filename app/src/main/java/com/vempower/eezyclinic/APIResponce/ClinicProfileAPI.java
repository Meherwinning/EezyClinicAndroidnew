
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.ClinicProfileData;

public class ClinicProfileAPI extends AbstractResponse {


    @SerializedName("data")
    @Expose
    private ClinicProfileData data;


    public ClinicProfileData getData() {
        return data;
    }

    public void setData(ClinicProfileData data) {
        this.data = data;
    }

}
