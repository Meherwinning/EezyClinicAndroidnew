
package com.vempower.eezyclinic.APIResponce;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.CaseSheetsListDate;
import com.vempower.eezyclinic.APICore.CaseSheetsListMetaDeta;

public class CaseSheetsListAPI extends  AbstractResponse {


    @SerializedName("data")
    @Expose
    private List<CaseSheetsListDate> data = null;
    @SerializedName("meta_deta")
    @Expose
    private CaseSheetsListMetaDeta metaDeta;



    public List<CaseSheetsListDate> getData() {
        return data;
    }

    public void setData(List<CaseSheetsListDate> data) {
        this.data = data;
    }

    public CaseSheetsListMetaDeta getMetaDeta() {
        return metaDeta;
    }

    public void setMetaDeta(CaseSheetsListMetaDeta metaDeta) {
        this.metaDeta = metaDeta;
    }

}
