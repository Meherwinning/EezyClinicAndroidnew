
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.AddhealthCheckData;

public class AddhealthCheckAPI extends AbstractResponse {


    @SerializedName("data")
    @Expose
    private AddhealthCheckData data;

      public AddhealthCheckData getData() {
        return data;
    }

    public void setData(AddhealthCheckData data) {
        this.data = data;
    }

}
