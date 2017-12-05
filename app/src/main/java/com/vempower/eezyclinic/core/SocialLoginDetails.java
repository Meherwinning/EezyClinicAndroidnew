package com.vempower.eezyclinic.core;

import java.io.Serializable;

/**
 * Created by satish on 23/11/17.
 */

public class SocialLoginDetails implements Serializable {
    //access_token(1), media_type(1), media_id(1), email(1), device_id(1)
    public  final  String ACCESS_TOKEN;
    public  final String MEDIA_TYPE;
    public  final String MEDIA_ID;
    public  final String EMAIL;
    public  final String DEVICE_ID;
    public final String NAME,AVATAR;


    public SocialLoginDetails(String ACCESS_TOKEN,String MEDIA_TYPE,String MEDIA_ID,String EMAIL,String DEVICE_ID,String NAME,String AVATAR) {
        this.ACCESS_TOKEN=ACCESS_TOKEN;
        this.MEDIA_TYPE=MEDIA_TYPE;
        this.MEDIA_ID=MEDIA_ID;
        this.EMAIL=EMAIL;
        this.DEVICE_ID=DEVICE_ID;
        this.NAME=NAME;
        this.AVATAR=AVATAR;
    }

   /* public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }*/

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
