
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.DoctorProfileData;

public class DoctorProfileAPI extends AbstractResponse {

    @SerializedName("data")
    @Expose
    private DoctorProfileData data;


    public DoctorProfileData getData() {
        return data;
    }

    public void setData(DoctorProfileData data) {
        this.data = data;
    }

}
