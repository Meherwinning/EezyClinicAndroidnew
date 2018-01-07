
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.ProfileSettingsData;

public class ProfileSettingsAPI extends AbstractResponse {


    @SerializedName("data")
    @Expose
    private ProfileSettingsData data;



    public ProfileSettingsData getData() {
        return data;
    }

    public void setData(ProfileSettingsData data) {
        this.data = data;
    }

}
