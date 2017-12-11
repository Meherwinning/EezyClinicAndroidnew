
package com.vempower.eezyclinic.APIResponce;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsuranceListAPI extends  AbstractResponse {


    @SerializedName("data")
    @Expose
    private List<InsuranceData> data = null;


    public List<InsuranceData> getData() {
        return data;
    }

    public void setData(List<InsuranceData> data) {
        this.data = data;
    }

}
