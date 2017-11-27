
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.PatientData;

public class LoginAPI extends  AbstractResponce {



    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("access_token_valid_till")
    @Expose
    private String accessTokenValidTill;



    @SerializedName("user_type")
    @Expose
    private String user_type;

    @SerializedName("data")
    @Expose
    private PatientData data;



    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessTokenValidTill() {
        return accessTokenValidTill;
    }

    public void setAccessTokenValidTill(String accessTokenValidTill) {
        this.accessTokenValidTill = accessTokenValidTill;
    }

    public PatientData getPatientData() {
        return data;
    }

    public void setPatientData(PatientData data) {
        this.data = data;
    }

    public String getUser_type() {
        return user_type;
    }


}
