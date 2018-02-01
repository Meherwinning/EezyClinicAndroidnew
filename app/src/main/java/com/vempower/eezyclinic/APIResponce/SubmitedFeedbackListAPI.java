
package com.vempower.eezyclinic.APIResponce;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.SubmitedFeedbackListData;

public class SubmitedFeedbackListAPI extends AbstractResponse {


    @SerializedName("data")
    @Expose
    private List<SubmitedFeedbackListData> data = null;

    public List<SubmitedFeedbackListData> getData() {
        return data;
    }

    public void setData(List<SubmitedFeedbackListData> data) {
        this.data = data;
    }

}
