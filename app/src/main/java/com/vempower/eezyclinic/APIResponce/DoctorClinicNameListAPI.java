
package com.vempower.eezyclinic.APIResponce;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorClinicNameListAPI extends AbstractResponse{


    @SerializedName("data")
    @Expose
    private List<DoctorClinicNameData> data = null;


    public List<DoctorClinicNameData> getData() {
        return data;
    }

    public void setData(List<DoctorClinicNameData> data) {
        this.data = data;
    }

}
