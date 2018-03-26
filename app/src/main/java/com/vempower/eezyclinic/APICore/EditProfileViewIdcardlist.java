
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APIResponce.IdCardTypeData;

public class EditProfileViewIdcardlist {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("idCardName")
    @Expose
    private String idCardName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCardName() {
        return idCardName;
    }

    public void setIdCardName(String idCardName) {
        this.idCardName = idCardName;
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
        if(obj instanceof IdCardTypeData)
        {
            IdCardTypeData data= (IdCardTypeData) obj;

            if(data.getId().equalsIgnoreCase(getId()))
            {
                return true;
            }
        }

        return super.equals(obj);
    }

    @Override
    public String toString() {
        return idCardName ;
    }
}
