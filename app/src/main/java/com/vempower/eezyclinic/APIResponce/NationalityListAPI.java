
package com.vempower.eezyclinic.APIResponce;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NationalityListAPI extends  AbstractResponse {


    @SerializedName("data")
    @Expose
    private List<NationalityData> data = null;



    public List<NationalityData> getData() {
        return data;
    }

    public void setData(List<NationalityData> data) {
        this.data = data;
    }

}
