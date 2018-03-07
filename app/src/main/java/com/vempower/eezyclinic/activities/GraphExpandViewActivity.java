package com.vempower.eezyclinic.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;

import org.jetbrains.annotations.Nullable;

/**
 * Created by satish on 9/1/18.
 */

public class GraphExpandViewActivity extends AbstractFragmentActivity {

    private WebView wv;
   // private String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_expand_layout_activity);

        myInit();
    }

    private void myInit() {
        if(getIntent()==null || !getIntent().hasExtra(Constants.Pref.GRAPH_URL_STR))
        {
            showAlertDialog("Alert",Utils.getStringFromResources(R.string.invalid_graph_url_str),true);
            return;
        }
        String url=getIntent().getStringExtra(Constants.Pref.GRAPH_URL_STR);
        if(TextUtils.isEmpty(url))
        {
            showAlertDialog("Alert",Utils.getStringFromResources(R.string.invalid_graph_url_str),true);
            return;

        }

      String titleStr=  getIntent().getStringExtra(Constants.Pref.GRAPH_TYPE);
        TextView titleTv=   findViewById(R.id.graph_title_tv);
       if( titleTv!=null)
       {
           titleTv.setText(titleStr);
       }
       /*
        String access_key= SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,null);
        if (TextUtils.isEmpty(access_key) ) {
            showAlertDialog("Alert", Utils.getStringFromResources(R.string.unauthorized_user_lbl),true);
            return ;
        }

        url= Constants.BASIC_URL+"/patient/healthcheckreports?access_key="+access_key+"&type=1";
*/

        findViewById(R.id.close_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        wv =  findViewById(R.id.terms_webView);


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

        wv.loadUrl(url);
    }


}
