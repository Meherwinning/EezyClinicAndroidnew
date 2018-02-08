package com.vempower.eezyclinic.fragments;

import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.vempower.eezyclinic.APICore.PatientData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyTextViewRR;

abstract class AbstractHealthChecksTabFragment  extends  AbstractFragment{

    protected final int SUGAR_TYPE=1, BLOOD_PRESSURE_TYPE =2,WEIGHT_AND_HEIGHT_TYPE=3,CHOLESTEROL_TYPE=4;

    public static final String TITLE = "Sugar";
    private String url;
    private WebView wv;
    private ProgressBar progressBar;
    private MyTextViewRR graph_type_title_tv;



    protected void myInit() {
        url=null;
        wv =  getFragemtView().findViewById(R.id.terms_webView);
        progressBar  =  getFragemtView().findViewById(R.id.progress_bar_view);
        graph_type_title_tv  =  getFragemtView().findViewById(R.id.graph_type_title_tv);
        graph_type_title_tv.setText(getHealthCheckTypeName());

        progressBar.setVisibility(View.GONE);
        String access_key= SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,null);
        if (TextUtils.isEmpty(access_key) ) {
            showAlertDialog("Alert", Utils.getStringFromResources(R.string.unauthorized_user_lbl),true);
            return ;
        }
        MyApplication.showTransparentDialog();
        PatientData patientData = MyApplication.getInstance().getLoggedUserDetailsFromSharedPref();
        MyApplication.hideTransaprentDialog();
        if(patientData==null )
        {
            showAlertDialog("Alert", Utils.getStringFromResources(R.string.unauthorized_user_lbl),true);
            return ;
        }

        url= Constants.BASIC_URL+"/patient/healthcheckreports?access_key="+access_key+"&type="+getHealthCheckType();
        //String url="file:///android_asset/"+"chart.html";




        wv.setWebChromeClient(new WebChromeClient());
        // wv.setWebViewClient(new WebChromeClient());
        wv.clearCache(true);
        wv.clearHistory();
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);

        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setDisplayZoomControls(false);

        wv.setScrollbarFadingEnabled(false);
       // wv.setInitialScale(1);

        /*wv.setInitialScale(1);

        wv.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        wv.setScrollbarFadingEnabled(false);*/


    }

    abstract int getHealthCheckType();
    abstract String getHealthCheckTypeName();

    @Override
    public void onResume() {
        super.onResume();

        refreshGraph();
    }

    protected void refreshGraph() {
        if(wv!=null || TextUtils.isEmpty(url)) {
            progressBar.setVisibility(View.VISIBLE);
            wv.loadUrl(url);
            wv.setWebViewClient(new WebViewClient() {

                public void onPageFinished(WebView view, String url) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }else
        {
            showAlertDialog("Alert", Utils.getStringFromResources(R.string.unauthorized_user_lbl),true);
            return ;

        }
    }
}
