
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.ForgotPasswordData;

public class ForgotPasswordOTPAPI extends AbstractResponce {


    @SerializedName("data")
    @Expose
    private ForgotPasswordData data;


    public ForgotPasswordData getData() {
        return data;
    }

    public void setData(ForgotPasswordData data) {
        this.data = data;
    }

}
