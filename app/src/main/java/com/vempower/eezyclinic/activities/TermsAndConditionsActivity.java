package com.vempower.eezyclinic.activities;

import android.os.Bundle;
import android.webkit.WebView;

import com.vempower.eezyclinic.R;
import com.vempower.stashdealcustomer.activities.AbstractActivity;

import org.jetbrains.annotations.Nullable;

/**
 * Created by satish on 30/11/17.
 */

public class TermsAndConditionsActivity  extends AbstractActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_and_conditions_layout);
        init();
    }


    private void init() {
        WebView wv;
        wv =  findViewById(R.id.terms_webView);
        String url="file:///android_asset/EezyClinic_T_C.html";
        wv.loadUrl(url);
    }

    /*public void onTitleMenuClick(View view) {
        finish();
    }*/
}
