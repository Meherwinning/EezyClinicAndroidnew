package com.vempower.eezyclinic.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.vempower.eezyclinic.APICore.CasesheetPrescriptionDetail;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.mappers.UploadProfilePicMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyTextViewRR;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by satish on 6/12/17.
 */

public class AddModifyPrescriptionFragment extends ImageProcessFragment {

    private View fragmentView;
    private MyTextViewRR date_of_document_tv;
    private SelectedDate selectedDOBObj;
    private LinearLayout horigental_linear;
    private LayoutInflater inflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.add_modify_prescription_layout, container, false);

        myInit();

        return fragmentView;
    }

    private void myInit() {
        inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        date_of_document_tv =getFragemtView().findViewById(R.id.date_of_document_tv);

        date_of_document_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateOfBirthTextviewClick(mFragmentDOBCallback,selectedDOBObj,true);
            }
        });

        horigental_linear  =getFragemtView().findViewById(R.id.horigental_linear);

        getFragemtView().findViewById(R.id.add_new_image_linear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageSourceDialog(Constants.ImagePic.FROM_ADD_NEW_IMAGE);
            }
        });
    }




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
            SimpleDateFormat format = new SimpleDateFormat(Constants.DISPLAY_DATE_FORMAT);
            SimpleDateFormat requestFormat = new SimpleDateFormat(Constants.REQUEST_DATE_FORMAT);
           // profileDetails.dateofBirth=requestFormat.format(selectedCal.getTime());
            date_of_document_tv.setText(format.format(selectedCal.getTime()));

        }
    };


    public void onDateOfBirthTextviewClick(final SublimePickerFragment.Callback callback,final SelectedDate selectedObj,boolean isDOB) {

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


    @Override
    View getFragemtView() {
        return fragmentView;
    }

    private void setNewImageViewToList(File file) {

        if(file==null)
        {
            return;
        }


        final View convertView = inflater
                .inflate(R.layout.add_image_to_linear_layout, null, false);

        ImageView profile_iv6= convertView.findViewById(R.id.profile_iv6);
        ImageView close_iv = convertView.findViewById(R.id. close_iv);

        MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image,profile_iv6,file);

        close_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horigental_linear.removeView(convertView);
            }
        });
           horigental_linear.addView(convertView);
    }

    @Override
    protected void setImage(File file, int responseId) {

        if(true)
        {

            setNewImageViewToList(file);
            return;
        }
        if(file!=null)
        {
            String keyName=null;
            switch (responseId)
            {
                case Constants.ImagePic.FROM_PROFILE:
                    keyName="image_file";
                    break;
                case Constants.ImagePic.FROM_ID_FRONT:
                    keyName="idcard";
                    break;
                case Constants.ImagePic.FROM_ID_BACK:
                    keyName="idcard_image_back";
                    break;

                case Constants.ImagePic.FROM_INSURANCE_BACK:
                    keyName="insurance_card_2";
                    break;
                case Constants.ImagePic.FROM_INSURANCE_FRONT:
                    keyName="insurance_card_1";
                    break;
            }

            if(TextUtils.isEmpty(keyName))
            {
                Utils.showToastMsg("Invalid image source");
                return;
            }

/*
            UploadProfilePicMapper picMapper= new UploadProfilePicMapper(file,keyName);
            picMapper.setOnUpdateProfilePicListener(new UploadProfilePicMapper.UpdateProfilePicListener() {
                @Override
                public void uploadProfilePic(AbstractResponse response, String errorMessage) {
                    if(!isValidResponse(response,errorMessage))
                    {
                        return;
                    }
                    Utils.showToastMsg(response.getStatusMessage());
                    switch (responseId)
                    {
                        case Constants.ImagePic.FROM_PROFILE:
                            MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.profile_icon, profile_iv, file);

                            break;
                        case Constants.ImagePic.FROM_ID_FRONT:
                            MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, id_front_iv, file);

                            break;
                        case Constants.ImagePic.FROM_ID_BACK:
                            MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, id_back_iv, file);

                            break;


                        case Constants.ImagePic.FROM_INSURANCE_FRONT:
                            MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, insurance_front_iv, file);

                            break;
                        case Constants.ImagePic.FROM_INSURANCE_BACK:
                            MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, insurance_back_iv, file);

                            break;
                    }

                }

            });*/
        }

    }
}
