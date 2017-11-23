package com.vempower.eezyclinic.APIResponce;

/**
 * Created by satish on 21/11/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterAPI {

    @SerializedName("Status")
    @Expose
    public Integer status;
    @SerializedName("success_messages")
    @Expose
    public String successMessages;

}