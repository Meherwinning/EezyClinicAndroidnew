
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.EditProfileViewAPIData;

public class EditProfileViewAPI extends AbstractResponse {

   /* @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;*/
    @SerializedName("data")
    @Expose
    private EditProfileViewAPIData data;

    public EditProfileViewAPIData getData() {
        return data;
    }

    public void setData(EditProfileViewAPIData data) {
        this.data = data;
    }

}
