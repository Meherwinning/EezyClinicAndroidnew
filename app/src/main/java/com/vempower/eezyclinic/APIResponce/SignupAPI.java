
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupAPI extends AbstractResponce {


    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("data")
    @Expose
    private SignupData data;

    public String getOtp() {
        return otp;
    }

    public SignupData getData() {
        return data;
    }
}
