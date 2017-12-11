
package com.vempower.eezyclinic.APIResponce;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryListAPI extends  AbstractResponse{


    @SerializedName("data")
    @Expose
    private List<CountryData> data = null;

    /*public Integer getStatusCode() {
        return statusCode;
    }*/

   /* public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }*/

    public List<CountryData> getData() {
        return data;
    }

    public void setData(List<CountryData> data) {
        this.data = data;
    }

}
