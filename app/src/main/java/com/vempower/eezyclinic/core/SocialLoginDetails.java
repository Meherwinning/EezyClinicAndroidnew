package com.vempower.eezyclinic.core;

/**
 * Created by satish on 23/11/17.
 */

public class SocialLoginDetails {
    //access_token(1), media_type(1), media_id(1), email(1), device_id(1)
    public  final  String ACCESS_TOKEN;
    public  final String MEDIA_TYPE;
    public  final String MEDIA_ID;
    public  final String EMAIL;
    public  final String DEVICE_ID;

    public SocialLoginDetails(String ACCESS_TOKEN,String MEDIA_TYPE,String MEDIA_ID,String EMAIL,String DEVICE_ID) {
        this.ACCESS_TOKEN=ACCESS_TOKEN;
        this.MEDIA_TYPE=MEDIA_TYPE;
        this.MEDIA_ID=MEDIA_ID;
        this.EMAIL=EMAIL;
        this.DEVICE_ID=DEVICE_ID;
    }

    @Override
    public String toString() {
        return "SocialLoginDetails{" +
                "ACCESS_TOKEN='" + ACCESS_TOKEN + '\'' +
                ", MEDIA_TYPE='" + MEDIA_TYPE + '\'' +
                ", MEDIA_ID='" + MEDIA_ID + '\'' +
                ", EMAIL='" + EMAIL + '\'' +
                ", DEVICE_ID='" + DEVICE_ID + '\'' +
                '}';
    }
}
