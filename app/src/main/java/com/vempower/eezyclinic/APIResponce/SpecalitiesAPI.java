
package com.vempower.eezyclinic.APIResponce;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecalitiesAPI extends AbstractResponse{

   /* @SerializedName("status_code")
    @Expose
    private Integer statusCode;*/
    @SerializedName("data")
    @Expose
    private List<SpecalitiyData> data = null;

   /* public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
*/
    public List<SpecalitiyData> getData() {
        return data;
    }

    public void setData(List<SpecalitiyData> data) {
        this.data = data;
    }

}
