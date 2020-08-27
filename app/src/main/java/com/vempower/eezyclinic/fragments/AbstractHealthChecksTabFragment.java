package com.vempower.eezyclinic.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.vempower.eezyclinic.APICore.PatientData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.GraphExpandViewActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.HealthChecksRefreshListener;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.utils.Utils;
 ;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

abstract class AbstractHealthChecksTabFragment extends AbstractFragment {
    //on Thursday
    //08-03-2018, 08.41 AM
    //String DISPLAY_DATE_FORMAT = "'on' EEEE dd-MM-yyyy, h:mm a";//4th Dec 2017
    String DISPLAY_DATE_FORMAT123 = "'on' EEE dd-MM-yyyy, h:mm a";//4th Dec 2017
    String DISPLAY_DATE_FORMAT1 = "EEE d MMM yyyy, h:mm a";//Thu 14 Jun 2018, 12:13 PM

    protected final int SUGAR_TYPE = 1, BLOOD_PRESSURE_TYPE = 2, WEIGHT_AND_HEIGHT_TYPE = 3, CHOLESTEROL_TYPE = 4;

    public static final String TITLE = "Sugar";
    private String url;
    private WebView wv;
    private ProgressBar progressBar;
    private  TextView graph_type_title_tv;
    protected TextView new_date_tv;
    protected String selectedDateStr;
    private ImageView graph_expand_iv;
    protected HealthChecksRefreshListener refreshListener;


    protected void myInit() {
        url = null;
        new_date_tv = getFragemtView().findViewById(R.id.date_tv);
        selectedDateStr = "";
        graph_expand_iv = getFragemtView().findViewById(R.id.graph_expand_iv);

        new_date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateOfBirthTextviewClick(mFragmentDOBCallback, selectedDOBObj, true);

            }
        });

        wv = getFragemtView().findViewById(R.id.terms_webView);

        progressBar = getFragemtView().findViewById(R.id.progress_bar_view);
        graph_type_title_tv = getFragemtView().findViewById(R.id.graph_type_title_tv);
        graph_type_title_tv.setText(getHealthCheckTypeName());

        progressBar.setVisibility(View.GONE);
        String access_key = SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY, null);
        if (TextUtils.isEmpty(access_key)) {
            showAlertDialog("Alert", Utils.getStringFromResources(R.string.unauthorized_user_lbl), true);
            return;
        }
        MyApplication.showTransparentDialog();
        PatientData patientData = MyApplication.getInstance().getLoggedUserDetailsFromSharedPref();
        MyApplication.hideTransaprentDialog();
        if (patientData == null) {
            showAlertDialog("Alert", Utils.getStringFromResources(R.string.unauthorized_user_lbl), true);
            return;
        }
//https://dev.v-empower.com:81/eezyclinic-git/patient/healthcheckreports?access_key=97857cb8d95925c863a56d4d7703f8c8&type=1
        url = Constants.GRAPH_BASE_URL + access_key + "&type=" + getHealthCheckType();

        //String url="file:///android_asset/"+"chart.html";

        if (graph_expand_iv != null) {
            graph_expand_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (recordsCount() == 0) {
                        return;
                    }
                    callGraphExpandActivity();
                }
            });
        }
        wv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                callGraphExpandActivity();
                return true;
            }
        });


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

    public void setRefreshListener(HealthChecksRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    private void callGraphExpandActivity() {
        //Utils.showToastMessage("Now click on Graph expand image");
        Intent intent = ((Activity)MyApplication.getCurrentActivityContext()).getIntent();
        intent.setClass(MyApplication.getCurrentActivityContext(), GraphExpandViewActivity.class);
        intent.putExtra(Constants.Pref.GRAPH_URL_STR, url);
        intent.putExtra(Constants.Pref.GRAPH_TYPE, getHealthCheckTypeName());

        startActivity(intent);
    }

    abstract int getHealthCheckType();

    abstract String getHealthCheckTypeName();

    abstract int recordsCount();

    abstract  void setNoRecordsViewManage();

    @Override
    public void onResume() {
        super.onResume();

        refreshGraph();
    }

    protected void refreshGraph() {
        if (wv != null || TextUtils.isEmpty(url)) {
            progressBar.setVisibility(View.VISIBLE);
            wv.loadUrl(url);
            wv.setWebViewClient(new WebViewClient() {

                public void onPageFinished(WebView view, String url) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else {
            showAlertDialog("Alert", Utils.getStringFromResources(R.string.unauthorized_user_lbl), true);
            return;

        }
        setNoRecordsViewManage();
    }


    public void onDateOfBirthTextviewClick(final SublimePickerFragment.Callback callback, final SelectedDate selectedObj, boolean isDOB) {

        SublimePickerFragment pickerFrag = new SublimePickerFragment();
        pickerFrag.setCallback(callback);

        // Options
        Pair<Boolean, SublimeOptions> optionsPair = getOptions(selectedObj, isDOB);

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


    Pair<Boolean, SublimeOptions> getOptions(SelectedDate selectedDate, boolean isDOB) {
        SublimeOptions options = new SublimeOptions();
        if (isDOB) {
            Calendar endCalendar = Calendar.getInstance();
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.set(Calendar.YEAR, startCalendar.get(Calendar.YEAR) - 120);

            options.setDateRange(startCalendar.getTimeInMillis(), endCalendar.getTimeInMillis());
        }

        if (selectedDate != null) {
            options.setDateParams(selectedDate);
            //int hourOfDay, int minute, boolean is24HourView
           // selectedDate.getFirstDate().
            options.setTimeParams(selectedDate.getFirstDate().get(Calendar.HOUR_OF_DAY),selectedDate.getFirstDate().get(Calendar.MINUTE),false);
        }
        int displayOptions = SublimeOptions.ACTIVATE_DATE_PICKER;
        displayOptions |= SublimeOptions.ACTIVATE_TIME_PICKER;


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
            selectedDate1.set(Calendar.HOUR_OF_DAY,hourOfDay);
            selectedDate1.set(Calendar.MINUTE,minute);
            selectedDOBObj = selectedDate1;
            Calendar selectedCal = selectedDate1.getFirstDate();

            //  String date = selectedCal.get(Calendar.YEAR) + "-" + (selectedCal.get(Calendar.MONTH) + 1) + "-" + selectedCal.get(Calendar.DAY_OF_MONTH);
           // SimpleDateFormat format = new SimpleDateFormat(DISPLAY_DATE_FORMAT);
            SimpleDateFormat serverDateFormat = new SimpleDateFormat(Constants.SERVER_DATE_FORMAT_NEW);
            //profileDetails.dateofBirth=requestFormat.format(selectedCal.getTime());
            selectedDateStr = serverDateFormat.format(selectedCal.getTime());
            new_date_tv.setText(getFormattedDate(selectedCal.getTime()));

          //  onTimePickClick(mFragmentTimeCallback, selectedDOBObj, false);

        }
    };

    protected  String getFormattedDate(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        //2nd of march 2015
        int day=cal.get(Calendar.DATE);

        if(!((day>10) && (day<19)))
            switch (day % 10) {
                case 1:
                    //"'on' EEEE d'st' -MM-yyyy, h:mm a";//4th Dec 2017
                    return new SimpleDateFormat( "'on' EEE d'st' MMM yyyy, h:mm a").format(date);
                case 2:
                    return new SimpleDateFormat("'on' EEE d'nd' MMM yyyy, h:mm a").format(date);
                case 3:
                    return new SimpleDateFormat("'on' EEE d'rd' MMM yyyy, h:mm a").format(date);
                default:
                    return new SimpleDateFormat("'on' EEE d'th' MMM yyyy, h:mm a").format(date);
            }
        return new SimpleDateFormat("'on' EEE d'th' MMM yyyy, h:mm a").format(date);
    }

    protected String filterDateStr(String dateStr) {
        if (TextUtils.isEmpty(dateStr)) {
            return dateStr;
        }
        if (dateStr.contains("st")) {
            dateStr = dateStr.replace("st", "");
            dateStr = dateStr.trim();
        }
        if (dateStr.contains("nd")) {
            dateStr = dateStr.replace("nd", "");
            dateStr = dateStr.trim();
        }
        if (dateStr.contains("rd")) {
            dateStr = dateStr.replace("rd", "");
            dateStr = dateStr.trim();
        }
        if (dateStr.contains("th")) {
            dateStr = dateStr.replace("th", "");
            dateStr = dateStr.trim();
        }
        return dateStr;
    }


}
