
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.PatientData;

public class VerifyOTPAPI extends AbstractResponse {


    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("access_token_valid_till")
    @Expose
    private String accessTokenValidTill;
    @SerializedName("data")
    @Expose
    private PatientData data;


    public String getUserType() {
        return userType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getAccessTokenValidTill() {
        return accessTokenValidTill;
    }

    public PatientData getData() {
        return data;
    }
}
