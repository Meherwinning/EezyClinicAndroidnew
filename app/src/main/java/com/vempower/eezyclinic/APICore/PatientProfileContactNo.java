
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientProfileContactNo {

    @SerializedName("primary")
    @Expose
    private String primary;
    @SerializedName("secondarymobile")
    @Expose
    private String secondarymobile;
    @SerializedName("residencemobile")
    @Expose
    private String residencemobile;

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public String getSecondarymobile() {
        return secondarymobile;
    }

    public void setSecondarymobile(String secondarymobile) {
        this.secondarymobile = secondarymobile;
    }

    public String getResidencemobile() {
        return residencemobile;
    }

    public void setResidencemobile(String residencemobile) {
        this.residencemobile = residencemobile;
    }

}
