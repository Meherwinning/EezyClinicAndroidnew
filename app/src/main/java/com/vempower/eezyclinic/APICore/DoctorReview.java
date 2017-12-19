
package com.vempower.eezyclinic.APICore;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorReview {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("waitingTime")
    @Expose
    private String waitingTime;
    @SerializedName("diagnasis")
    @Expose
    private String diagnasis;
    @SerializedName("customerservice")
    @Expose
    private String customerservice;
    @SerializedName("recommend")
    @Expose
    private String recommend;
    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("created_date")
    @Expose
    private String createdDate;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWaitingTime() {
        if(TextUtils.isEmpty(waitingTime))
        {
            return 0;
        }
        try
        {
            return Integer.parseInt(waitingTime);
        }catch(Exception e)
        {
            return 0;
        }
    }

    public void setWaitingTime(String waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getDiagnasis() {
        if(TextUtils.isEmpty(diagnasis))
        {
            return 0;
        }
        try
        {
            return Integer.parseInt(diagnasis);
        }catch(Exception e)
        {
            return 0;
        }
    }

    public void setDiagnasis(String diagnasis) {
        this.diagnasis = diagnasis;
    }

    public int getCustomerservice() {
        if(TextUtils.isEmpty(customerservice))
        {
            return 0;
        }
        try
        {
            return Integer.parseInt(customerservice);
        }catch(Exception e)
        {
            return 0;
        }
    }

    public void setCustomerservice(String customerservice) {
        this.customerservice = customerservice;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}
