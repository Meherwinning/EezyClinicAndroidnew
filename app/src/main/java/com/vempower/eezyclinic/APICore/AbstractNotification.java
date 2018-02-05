package com.vempower.eezyclinic.APICore;

public abstract class AbstractNotification {

    public static final int SEND_REQUEST_TYPE=1;
    public static final int COMING_APPOINTMENT_TYPE=2;


    public abstract int getNotificationType();
}
