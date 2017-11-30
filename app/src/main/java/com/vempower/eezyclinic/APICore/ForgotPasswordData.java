
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordData {

    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("id")
    @Expose
    private String id;

    public String getOtp() {
        return otp;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
