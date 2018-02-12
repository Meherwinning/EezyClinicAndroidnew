
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.AddWHhealthCheckData;

public class AddWHhealthCheckAPI extends  AbstractResponse {


    @SerializedName("data")
    @Expose
    private AddWHhealthCheckData data;


    public AddWHhealthCheckData getData() {
        return data;
    }

    public void setData(AddWHhealthCheckData data) {
        this.data = data;
    }

}
