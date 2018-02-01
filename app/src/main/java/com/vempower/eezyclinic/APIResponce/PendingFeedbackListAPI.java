
package com.vempower.eezyclinic.APIResponce;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.PendingFeedbackData;

public class PendingFeedbackListAPI extends AbstractResponse {


    @SerializedName("data")
    @Expose
    private List<PendingFeedbackData> data = null;
    public  List<PendingFeedbackData> getData() {
        return data;
    }

}
