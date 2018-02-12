package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.vempower.eezyclinic.APICore.PatientData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyTextViewRR;

import java.text.SimpleDateFormat;
import java.util.Calendar;

abstract class AbstractHealthChecksTabFragment  extends  AbstractFragment{
    String DISPLAY_DATE_FORMAT="dd-MM-yyyy";

    protected final int SUGAR_TYPE=1, BLOOD_PRESSURE_TYPE =2,WEIGHT_AND_HEIGHT_TYPE=3,CHOLESTEROL_TYPE=4;

    public static final String TITLE = "Sugar";
    private String url;
    private WebView wv;
    private ProgressBar progressBar;
    private MyTextViewRR graph_type_title_tv;
    protected MyTextViewRR new_date_tv;



    protected void myInit() {
        url=null;
        new_date_tv = getFragemtView().findViewById(R.id.date_tv);
        new_date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateOfBirthTextviewClick(mFragmentDOBCallback,selectedDOBObj,true);

            }
        });
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


    public void onDateOfBirthTextviewClick(final SublimePickerFragment.Callback callback, final SelectedDate selectedObj, boolean isDOB) {

        SublimePickerFragment pickerFrag = new SublimePickerFragment();
        pickerFrag.setCallback(callback);

        // Options
        Pair<Boolean, SublimeOptions> optionsPair = getOptions(selectedObj,isDOB);

        if (!optionsPair.first) { // If options are not valid
            // showToastMessage("No pickers activated");
            return;
        }

        // Valid options
        Bundle bundle = new Bundle();
        bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
        pickerFrag.setArguments(bundle);

        pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        pickerFrag.show(getChildFragmentManager(), "SUBLIME_PICKER");


    }


    Pair<Boolean, SublimeOptions> getOptions(SelectedDate selectedDate,boolean isDOB) {
        SublimeOptions options = new SublimeOptions();
        if(isDOB) {
            Calendar endCalendar = Calendar.getInstance();
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.set(Calendar.YEAR, startCalendar.get(Calendar.YEAR) - 120);

            options.setDateRange(startCalendar.getTimeInMillis(), endCalendar.getTimeInMillis());
        }

        if (selectedDate != null) {
            options.setDateParams(selectedDate);
        }
        int displayOptions = SublimeOptions.ACTIVATE_DATE_PICKER;


        // if (rbDatePicker.getVisibility() == View.VISIBLE && rbDatePicker.isChecked()) {
        options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);
        /*} else if (rbTimePicker.getVisibility() == View.VISIBLE && rbTimePicker.isChecked()) {
            options.setPickerToShow(SublimeOptions.Picker.TIME_PICKER);
        } else if (rbRecurrencePicker.getVisibility() == View.VISIBLE && rbRecurrencePicker.isChecked()) {
            options.setPickerToShow(SublimeOptions.Picker.REPEAT_OPTION_PICKER);
        }
*/
        options.setDisplayOptions(displayOptions);

        // Enable/disable the date range selection feature
        //options.setCanPickDateRange(cbAllowDateRangeSelection.isChecked());

        // Example for setting date range:
        // Note that you can pass a date range as the initial date params
        // even if you have date-range selection disabled. In this case,
        // the user WILL be able to change date-range using the header
        // TextViews, but not using long-press.

        /*Calendar startCal = Calendar.getInstance();
        startCal.set(2016, 2, 4);
        Calendar endCal = Calendar.getInstance();
        endCal.set(2016, 2, 17);

        options.setDateParams(startCal, endCal);*/

        // If 'displayOptions' is zero, the chosen options are not valid
        return new Pair<>(displayOptions != 0 ? Boolean.TRUE : Boolean.FALSE, options);
    }


    private SelectedDate selectedDOBObj;
    SublimePickerFragment.Callback mFragmentDOBCallback = new SublimePickerFragment.Callback() {
        @Override
        public void onCancelled() {
            // rlDateTimeRecurrenceInfo.setVisibility(View.GONE);
        }

        @Override
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate1,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {


            if (selectedDate1 == null || selectedDate1.getFirstDate() == null) {
                return;
            }
            selectedDOBObj = selectedDate1;
            Calendar selectedCal = selectedDate1.getFirstDate();

            //  String date = selectedCal.get(Calendar.YEAR) + "-" + (selectedCal.get(Calendar.MONTH) + 1) + "-" + selectedCal.get(Calendar.DAY_OF_MONTH);
            SimpleDateFormat format = new SimpleDateFormat(DISPLAY_DATE_FORMAT);
            //SimpleDateFormat requestFormat = new SimpleDateFormat(Constants.REQUEST_DATE_FORMAT);
            //profileDetails.dateofBirth=requestFormat.format(selectedCal.getTime());
            new_date_tv.setText(format.format(selectedCal.getTime()));

        }
    };

   /* SublimePickerFragment.Callback mFragmentCallback = new SublimePickerFragment.Callback() {
        @Override
        public void onCancelled() {
            // rlDateTimeRecurrenceInfo.setVisibility(View.GONE);
        }

        @Override
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate1,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {


            if (selectedDate1 == null || selectedDate1.getFirstDate() == null) {
                return;
            }
            selectedDOBObj = selectedDate1;
            Calendar selectedCal = selectedDate1.getFirstDate();

            //  String date = selectedCal.get(Calendar.YEAR) + "-" + (selectedCal.get(Calendar.MONTH) + 1) + "-" + selectedCal.get(Calendar.DAY_OF_MONTH);
            SimpleDateFormat format = new SimpleDateFormat(DISPLAY_DATE_FORMAT);
            //SimpleDateFormat requestFormat = new SimpleDateFormat(Constants.REQUEST_DATE_FORMAT);
            //profileDetails.dateofBirth=requestFormat.format(selectedCal.getTime());
            date_tv.setText(format.format(selectedCal.getTime()));

        }
    };
*/
}
