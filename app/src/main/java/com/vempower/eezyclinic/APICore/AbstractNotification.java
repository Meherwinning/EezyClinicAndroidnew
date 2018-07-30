package com.vempower.eezyclinic.APICore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class AbstractNotification {

    public static final int SEND_REQUEST_TYPE=1;
    public static final int COMING_APPOINTMENT_TYPE=2;
    public static final int NORMAL_MSZ_TYPE=3;

    @SerializedName("send_date_time")
    @Expose
    private String sendDateTime;


    public String getSendDateTime() {
        return sendDateTime;
    }

    public void setSendDateTime(String sendDateTime) {
        this.sendDateTime = sendDateTime;
    }



    public abstract int getNotificationType();
}
