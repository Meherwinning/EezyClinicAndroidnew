
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.ChangeMobileNumberData;

public class ChangeMobileNumberAPI extends  AbstractResponse {


    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("data")
    @Expose
    private ChangeMobileNumberData data;


    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public ChangeMobileNumberData getData() {
        return data;
    }

    public void setData(ChangeMobileNumberData data) {
        this.data = data;
    }

}
