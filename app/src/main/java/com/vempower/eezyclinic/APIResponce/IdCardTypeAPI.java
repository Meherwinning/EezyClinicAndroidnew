
package com.vempower.eezyclinic.APIResponce;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdCardTypeAPI extends AbstractResponse {


    @SerializedName("data")
    @Expose
    private List<IdCardTypeData> data = null;


    public List<IdCardTypeData> getData() {
        return data;
    }

    public void setData(List<IdCardTypeData> data) {
        this.data = data;
    }

}
