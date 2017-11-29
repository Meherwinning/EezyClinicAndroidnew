
package com.vempower.eezyclinic.APIResponce;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.UserAccount;

public class ForgotPasswordAPI extends AbstractResponce {


    @SerializedName("data")
    @Expose
    private List<UserAccount> data = null;


    public List<UserAccount> getData() {
        return data;
    }

    public void setData(List<UserAccount> data) {
        this.data = data;
    }

}
