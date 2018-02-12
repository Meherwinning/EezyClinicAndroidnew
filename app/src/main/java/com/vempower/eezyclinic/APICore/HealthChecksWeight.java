
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthChecksWeight extends AbstractChecksHeightWeight {


    @SerializedName("checkupValue")
    @Expose
    private String weightCheckupValue;


    public String getWeightCheckupValue() {
        return weightCheckupValue;
    }

    public void setWeightCheckupValue(String weightCheckupValue) {
        this.weightCheckupValue = weightCheckupValue;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj != null && obj instanceof HealthChecksWeight) {
            HealthChecksWeight weight = (HealthChecksWeight) obj;
            if (weight.getCheckupgroupid() != null && getCheckupgroupid() != null) {
                if (weight.getCheckupgroupid().equalsIgnoreCase(getCheckupgroupid())) {
                    return true;
                }
            }

        }

        return super.equals(obj);
    }
}
