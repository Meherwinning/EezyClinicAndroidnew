
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APIResponce.InsuranceData;

public class EditProfileViewInsurance {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("countryId")
    @Expose
    private String countryId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_date")
    @Expose
    private String createdDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof InsuranceData)
        {
            InsuranceData data= (InsuranceData) obj;

            if(data.getId().equalsIgnoreCase(getId()))
            {
                return true;
            }
        }

        return super.equals(obj);
    }

    @Override
    public String toString() {
        return  companyName;
    }
}
