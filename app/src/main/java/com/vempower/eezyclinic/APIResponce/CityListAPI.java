
package com.vempower.eezyclinic.APIResponce;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityListAPI  extends  AbstractResponse{


    @SerializedName("data")
    @Expose
    private List<CityData> data = null;



    public List<CityData> getData() {
        return data;
    }

    public void setData(List<CityData> data) {
        this.data = data;
    }

}
