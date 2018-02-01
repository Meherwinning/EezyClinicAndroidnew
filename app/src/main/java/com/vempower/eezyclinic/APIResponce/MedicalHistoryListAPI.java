
package com.vempower.eezyclinic.APIResponce;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.MedicalHistoryData;

public class MedicalHistoryListAPI extends AbstractResponse {


    @SerializedName("data")
    @Expose
    private List<MedicalHistoryData> data = null;


    public List<MedicalHistoryData> getData() {
        return data;
    }

    public void setData(List<MedicalHistoryData> data) {
        this.data = data;
    }

}
