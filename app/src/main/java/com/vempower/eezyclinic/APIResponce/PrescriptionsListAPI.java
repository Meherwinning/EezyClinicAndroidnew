
package com.vempower.eezyclinic.APIResponce;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.PrescriptionAPIData;

public class PrescriptionsListAPI extends  AbstractResponse {


    @SerializedName("data")
    @Expose
    private List<PrescriptionAPIData> data = null;


    public List<PrescriptionAPIData> getData() {
        return data;
    }

    public void setData(List<PrescriptionAPIData> data) {
        this.data = data;
    }

}
