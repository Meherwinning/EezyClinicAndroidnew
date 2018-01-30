
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.GetMyNotesData;

public class GetMyNotesAPI extends AbstractResponse {


    @SerializedName("data")
    @Expose
    private GetMyNotesData data;


    public GetMyNotesData getData() {
        return data;
    }

    public void setData(GetMyNotesData data) {
        this.data = data;
    }

}
