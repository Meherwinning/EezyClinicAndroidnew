
package com.vempower.eezyclinic.APIResponce;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LanguageListAPI extends  AbstractResponse{


    @SerializedName("data")
    @Expose
    private List<LanguageData> data = null;



    public List<LanguageData> getData() {
        return data;
    }

    public void setData(List<LanguageData> data) {
        this.data = data;
    }

}
