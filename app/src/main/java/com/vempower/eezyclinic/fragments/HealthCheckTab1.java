package com.vempower.eezyclinic.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.vempower.eezyclinic.APICore.PatientData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.utils.Utils;

public class HealthCheckTab1 extends  AbstractHealthChecksTabFragment {


    public static final String TITLE = "Sugar";
    private View fragmentView;
    private String url;
    private  WebView wv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.activity_checks_tab1, container, false);

        myInit();
        return fragmentView;
    }

    protected void myInit() {
        super.myInit();

    }

    @Override
    int getHealthCheckType() {
        return SUGAR_TYPE;
    }

    @Override
    String getHealthCheckTypeName() {
        return "Sugar Levels";
    }


    @Override
    View getFragemtView() {
        return fragmentView;
    }


}
