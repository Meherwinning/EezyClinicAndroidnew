
package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicalHistoryData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("change_date")
    @Expose
    private String changeDate;
    @SerializedName("edited_by")
    @Expose
    private String editedBy;
    @SerializedName("content")
    @Expose
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MedicalHistoryData{" +
                "id='" + id + '\'' +
                ", changeDate='" + changeDate + '\'' +
                ", editedBy='" + editedBy + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
