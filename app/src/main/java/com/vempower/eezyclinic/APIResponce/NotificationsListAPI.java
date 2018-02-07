
package com.vempower.eezyclinic.APIResponce;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vempower.eezyclinic.APICore.NotificationsListData;

public class NotificationsListAPI extends AbstractResponse {


    @SerializedName("data")
    @Expose
    private NotificationsListData data;


    public NotificationsListData getData() {
        return data;
    }

    public void setData(NotificationsListData data) {
        this.data = data;
    }

}
