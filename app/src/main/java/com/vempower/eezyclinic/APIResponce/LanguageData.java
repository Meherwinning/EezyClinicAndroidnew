
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LanguageData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("language_name")
    @Expose
    private String languageName;
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

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
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
    public String toString() {
        return languageName ;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  LanguageData)
        {
            LanguageData data= (LanguageData) obj;

            if(data.getId().equalsIgnoreCase(getId()))
            {
                return true;
            }
        }

        return super.equals(obj);
    }
}
