
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.NewHomeData;

public class NewHomeAPI extends AbstractResponse {

   /* @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;*/
    @SerializedName("data")
    @Expose
    private NewHomeData data;


    public NewHomeData getData() {
        return data;
    }

    public void setData(NewHomeData data) {
        this.data = data;
    }

}
