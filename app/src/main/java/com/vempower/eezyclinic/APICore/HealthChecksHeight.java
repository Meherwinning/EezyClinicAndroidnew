
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthChecksHeight extends AbstractChecksHeightWeight {



    @SerializedName("checkupValue")
    @Expose
    private String heightCheckupValue;

    public void setHeightCheckupValue(String heightCheckupValue) {
        this.heightCheckupValue = heightCheckupValue;
    }

    public String getHeightCheckupValue() {
        return heightCheckupValue;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj != null && obj instanceof HealthChecksHeight) {
            HealthChecksHeight height = (HealthChecksHeight) obj;
            if (height.getCheckupgroupid() != null && getCheckupgroupid() != null) {
                if (height.getCheckupgroupid().equalsIgnoreCase(getCheckupgroupid())) {
                    return true;
                }
            }

        }

        return super.equals(obj);
    }

}
