
package com.vempower.eezyclinic.APIResponce;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.HelathReportsData;

public class HelathReportsListAPI extends  AbstractResponse {


    @SerializedName("data")
    @Expose
    private List<HelathReportsData> data = null;


    public List<HelathReportsData> getData() {
        return data;
    }

    public void setData(List<HelathReportsData> data) {
        this.data = data;
    }

}
