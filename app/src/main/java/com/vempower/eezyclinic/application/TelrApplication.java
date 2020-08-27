package com.vempower.eezyclinic.application;

import android.content.Context;
import android.util.Log;

public class TelrApplication extends com.telr.mobile.sdk.TelrApplication {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        Log.d("Demo","Context Started....");
        TelrApplication.context = getApplicationContext();
    }

    public static Context getContext(){
        return TelrApplication.context;
    }
}