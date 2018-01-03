
package com.vempower.eezyclinic.APIResponce;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TPAListAPI extends AbstractResponse {

    @SerializedName("data")
    @Expose
    private List<TPAListData> data = null;


    public List<TPAListData> getData() {
        return data;
    }

    public void setData(List<TPAListData> data) {
        this.data = data;
    }

}
