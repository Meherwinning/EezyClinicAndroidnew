
package com.vempower.eezyclinic.APICore;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationsListData {

    @SerializedName("sendrequest")
    @Expose
    private List<NotificationsSendrequest> sendrequest = null;
    @SerializedName("comingappointments")
    @Expose
    private List<NotificationsComingappointment> comingappointments = null;

    public List<NotificationsSendrequest> getSendrequest() {
        return sendrequest;
    }

    public void setSendrequest(List<NotificationsSendrequest> sendrequest) {
        this.sendrequest = sendrequest;
    }

    public List<NotificationsComingappointment> getComingappointments() {
        return comingappointments;
    }

    public void setComingappointments(List<NotificationsComingappointment> comingappointments) {
        this.comingappointments = comingappointments;
    }

}
