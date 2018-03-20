
package com.vempower.eezyclinic.APICore;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.core.Feature;
import com.vempower.eezyclinic.core.HealthTip;

public class NewHomeData {

    @SerializedName("doctors_list")
    @Expose
    private List<NewHomeDoctorsList> doctorsList = null;
    @SerializedName("specialities")
    @Expose
    private List<NewHomeSpeciality> specialities = null;
    @SerializedName("features")
    @Expose
    private List<String> features = null;
    @SerializedName("health Tips")
    @Expose
    private List<String> healthTips = null;


    private List<HealthTip> tips=null;
    private List<Feature> featuresList=null;

    public List<NewHomeDoctorsList> getDoctorsList() {
        return doctorsList;
    }

    public void setDoctorsList(List<NewHomeDoctorsList> doctorsList) {
        this.doctorsList = doctorsList;
    }

    public List<NewHomeSpeciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<NewHomeSpeciality> specialities) {
        this.specialities = specialities;
    }

    public List<Feature>  getFeatures() {
        if(featuresList==null)
        {
            featuresList= new ArrayList<>();
            if(features!=null)
            {
                for(String fe:features)
                {
                    if(fe==null)
                    {
                        continue;
                    }
                    featuresList.add(new Feature(fe)) ;
                }
            }
        }
        return featuresList;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public List<HealthTip> getHealthTips() {
        if(tips==null)
        {
            tips= new ArrayList<>();
            if(healthTips!=null)
            {
                for(String tip:healthTips)
                {
                    if(tip==null)
                    {
                        continue;
                    }
                    tips.add(new HealthTip(tip)) ;
                }
            }
        }
        return tips;
    }

    public void setHealthTips(List<String> healthTips) {
        this.healthTips = healthTips;
    }

}
