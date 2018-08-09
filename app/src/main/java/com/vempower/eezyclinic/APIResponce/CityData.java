
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("cityName")
    @Expose
    private String cityName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


    @Override
    public boolean equals(Object obj) {

        if (obj != null && obj instanceof CityData) {
            CityData data = (CityData) obj;
            if (data.cityName != null && cityName != null && data.cityName.equalsIgnoreCase(cityName)) {
                return true;
            }
        }

        return super.equals(obj);
    }

    @Override
    public String toString() {
        return cityName;
    }
}
