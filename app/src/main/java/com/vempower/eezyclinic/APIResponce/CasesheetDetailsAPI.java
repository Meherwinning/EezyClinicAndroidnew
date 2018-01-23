
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.CasesheetData;

public class CasesheetDetailsAPI extends AbstractResponse {

    @SerializedName("data")
    @Expose
    private CasesheetData data;

    public CasesheetData getData() {
        return data;
    }

    public void setData(CasesheetData data) {
        this.data = data;
    }

}
