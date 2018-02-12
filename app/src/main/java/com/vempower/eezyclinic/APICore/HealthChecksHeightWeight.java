
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthChecksHeightWeight extends AbstractChecksHeightWeight {


    private String heightCheckupValue;
    private String weightCheckupValue;

    public HealthChecksHeightWeight() {
        setCheckupName("WH");
    }

    public HealthChecksHeightWeight(HealthChecksHeight height) {
        setCheckupName("WH");
        if (height == null) {
            return;
        }
        setCheckupgroupid(height.getCheckupgroupid());
        setCheckupTime(height.getCheckupTime());
        //setCheckupgroupid(height.getCheckupgroupid());
       setCreatedOn(height.getCreatedOn());
       setHeightCheckupValue(height.getHeightCheckupValue());
    }

    public String getWeightCheckupValue() {
        return weightCheckupValue;
    }

    public void setWeightCheckupValue(String weightCheckupValue) {
        this.weightCheckupValue = weightCheckupValue;
    }


    public void setHeightCheckupValue(String heightCheckupValue) {
        this.heightCheckupValue = heightCheckupValue;
    }

    public String getHeightCheckupValue() {
        return heightCheckupValue;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj != null && obj instanceof HealthChecksHeightWeight) {
            HealthChecksHeightWeight height = (HealthChecksHeightWeight) obj;
            if (height.getId() != null && getId() != null) {
                if (height.getId().equalsIgnoreCase(getId())) {
                    return true;
                }
            }

        }

        return super.equals(obj);
    }

}
