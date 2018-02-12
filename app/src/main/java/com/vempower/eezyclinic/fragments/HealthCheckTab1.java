package com.vempower.eezyclinic.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.vempower.eezyclinic.APICore.HealthChecksSugar;
import com.vempower.eezyclinic.APICore.PatientData;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.APIResponce.AddhealthCheckAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.MyDialogInterface;
import com.vempower.eezyclinic.mappers.AddSugarHealthCheckMapper;
import com.vempower.eezyclinic.mappers.DeleteSugarHealthCheckMapper;
import com.vempower.eezyclinic.mappers.UpdateSugarHealthCheckMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyEditTextBlackCursorRR;
import com.vempower.eezyclinic.views.MyTextViewRR;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HealthCheckTab1 extends AbstractHealthChecksTabFragment {


    public static final String TITLE = "Sugar";
    private View fragmentView;
    private LinearLayout sugar_levels_linear, add_new_reocrd_linear, new_sugar_record_view_linear;
    private LayoutInflater inflater;
    private List<HealthChecksSugar> sugarLevelsList;
    private MyEditTextBlackCursorRR new_fasting_et, new_hba1c_et, new_post_meal_et;

    private ImageView new_delete_iv, new_ok_iv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.activity_checks_tab1, container, false);

        myInit();
        return fragmentView;
    }

    protected void myInit() {
        super.myInit();
        inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        sugar_levels_linear = getFragemtView().findViewById(R.id.sugar_levels_linear);
        add_new_reocrd_linear = getFragemtView().findViewById(R.id.add_new_reocrd_linear);
        new_sugar_record_view_linear = getFragemtView().findViewById(R.id.new_sugar_record_view_linear);
        new_sugar_record_view_linear.setVisibility(View.GONE);
        add_new_reocrd_linear.setVisibility(View.VISIBLE);
        initForAddNewRecord();
        add_new_reocrd_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calAddNewViewRecord();
                resetAddNewRecord();
                add_new_reocrd_linear.setVisibility(View.INVISIBLE);

                new_sugar_record_view_linear.setVisibility(View.VISIBLE);
                //ImageView delete_iv = new_sugar_record_view_linear.findViewById(R.id.delete_iv);


            }
        });

    }

    private void initForAddNewRecord() {
        new_fasting_et = getFragemtView().findViewById(R.id.fasting_et);
        new_post_meal_et = getFragemtView().findViewById(R.id.post_meal_et);
        new_hba1c_et = getFragemtView().findViewById(R.id.hba1c_et);


        new_ok_iv = getFragemtView().findViewById(R.id.ok_iv);
        new_delete_iv = getFragemtView().findViewById(R.id.delete_iv);


        new_ok_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fasting = new_fasting_et.getText().toString();
                String lunch = new_post_meal_et.getText().toString();
                String hba1c = new_hba1c_et.getText().toString();
                String checkuptime = new_date_tv.getText().toString();
                if (TextUtils.isEmpty(lunch)) {
                    Utils.showToastMessage(R.string.please_enter_lunch_value_lbl);
                    return;
                }
                if (TextUtils.isEmpty(fasting)) {
                    Utils.showToastMessage(R.string.please_enter_pasting_value_lbl);
                    return;
                }
                if (TextUtils.isEmpty(hba1c)) {
                    Utils.showToastMessage(R.string.please_enter_hba1c_value_lbl);
                    return;
                }
                // new_date_tv.getText().toString();
               /* String checkuptime="2018-01-07";
                HealthChecksSugar  sugar= new HealthChecksSugar();
                sugar.setCheckupValue(fasting+","+lunch+","+hba1c);
                sugar.setCheckupTime(checkuptime);
                sugar.setCheckupName("Sugar Level");

                return sugar;*/

                callAddNewSugarHealthcheckRecord(fasting, lunch, hba1c, checkuptime);
            }
        });

        new_delete_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAddNewRecord();
                new_sugar_record_view_linear.setVisibility(View.GONE);
                add_new_reocrd_linear.setVisibility(View.VISIBLE);
            }
        });
        resetAddNewRecord();
    }

    private void resetAddNewRecord() {
        new_fasting_et.setText(null);
        new_post_meal_et.setText(null);
        new_hba1c_et.setText(null);
        new_date_tv.setText(null);
    }

    private void callAddNewSugarHealthcheckRecord(final String fasting, final String lunch, final String hba1c, final String checkuptime) {
        AddSugarHealthCheckMapper mapper = new AddSugarHealthCheckMapper(fasting, lunch, hba1c, checkuptime);
        mapper.setOnAddSugarHealthCheckListener(new AddSugarHealthCheckMapper.AddSugarHealthCheckListener() {
            @Override
            public void addSugar(AddhealthCheckAPI response, String errorMessage) {
                if (!isValidResponse(response, errorMessage, true, false)) {
                    return;
                }

                if(response==null || response.getData()==null)
                {
                    return;
                }

                /* {
                "id": "403",
                "patientId": "265",
                "checkupgroupid": null,
                "checkupName": "Sugar Level",
                "checkupValue": "40,140,230",
                "checkupTime": "2018-01-07 00:00:00",
                "status": "1",
                "created_on": "2018-02-08 18:49:28"
            }*/

                HealthChecksSugar newSugar = new HealthChecksSugar();
                newSugar.setCheckupName("Sugar Level");
                newSugar.setCheckupValue(fasting + "," + lunch + "," + hba1c);
                newSugar.setCheckupTime(checkuptime);

                newSugar.setCheckupgroupid(response.getData().getCheckupgroupid());
                newSugar.setId(response.getData().getHealthcheckId());
                calAddViewRecord(newSugar, true);

                new_sugar_record_view_linear.setVisibility(View.GONE);
                add_new_reocrd_linear.setVisibility(View.VISIBLE);

            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setSugarlevelViews();
    }

    private void setSugarlevelViews() {
        if (sugarLevelsList == null || sugarLevelsList.size() == 0) {
            return;
        }

        for (HealthChecksSugar sugar : sugarLevelsList) {
            if (sugar == null) {
                continue;
            }
            calAddViewRecord(sugar, false);


        }

    }

    private class RecordHolder {
        MyEditTextBlackCursorRR fasting_et, post_meal_et, hba1c_et;
        MyTextViewRR date_tv;
        ImageView ok_iv, edit_iv, delete_iv;
        private final HealthChecksSugar sugar;
        private SelectedDate selectedObj;

        public RecordHolder(HealthChecksSugar sugar, View convertView) {
            this.sugar = sugar;

            fasting_et = convertView.findViewById(R.id.fasting_et);
            post_meal_et = convertView.findViewById(R.id.post_meal_et);
            hba1c_et = convertView.findViewById(R.id.hba1c_et);
            date_tv = convertView.findViewById(R.id.date_tv);

            ok_iv = convertView.findViewById(R.id.ok_iv);
            edit_iv = convertView.findViewById(R.id.edit_iv);
            delete_iv = convertView.findViewById(R.id.delete_iv);

            init();
            setHealthChecksSugar();
        }

        private String getDisplayDateStr(String dateStr)
        {
            SimpleDateFormat format = new SimpleDateFormat(DISPLAY_DATE_FORMAT);
            SimpleDateFormat requestFormat = new SimpleDateFormat(Constants.SERVER_DATE_FORMAT_NEW);


            //For Date of birth
            if(!TextUtils.isEmpty(dateStr)) {
                try {

                    Date date = requestFormat.parse(dateStr);
                    if (selectedObj == null) {
                        selectedObj = new SelectedDate(Calendar.getInstance());
                    }
                    selectedObj.setTimeInMillis(date.getTime());
                    return format.format(date);
                    //profileDetails.dateofBirth=patientProfileObj.getDateofbirth();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        private String getServerDateStr(String dateStr)
        {
            SimpleDateFormat format = new SimpleDateFormat(DISPLAY_DATE_FORMAT);
            SimpleDateFormat requestFormat = new SimpleDateFormat(Constants.SERVER_DATE_FORMAT_NEW);


            //For Date of birth
            if(!TextUtils.isEmpty(dateStr)) {
                try {

                    Date date = format.parse(dateStr);

                    return requestFormat.format(date);
                    //profileDetails.dateofBirth=patientProfileObj.getDateofbirth();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        private void init() {
            edit_iv.setVisibility(View.VISIBLE);
            ok_iv.setVisibility(View.GONE);
            delete_iv.setVisibility(View.VISIBLE);

            date_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDateOfBirthTextviewClick(mFragmentCallback,selectedObj,true);

                }
            });
        }

        SublimePickerFragment.Callback mFragmentCallback = new SublimePickerFragment.Callback() {
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
                selectedObj = selectedDate1;
                Calendar selectedCal = selectedDate1.getFirstDate();

                //  String date = selectedCal.get(Calendar.YEAR) + "-" + (selectedCal.get(Calendar.MONTH) + 1) + "-" + selectedCal.get(Calendar.DAY_OF_MONTH);
                SimpleDateFormat format = new SimpleDateFormat(DISPLAY_DATE_FORMAT);
                //SimpleDateFormat requestFormat = new SimpleDateFormat(Constants.REQUEST_DATE_FORMAT);
                //profileDetails.dateofBirth=requestFormat.format(selectedCal.getTime());
                date_tv.setText(format.format(selectedCal.getTime()));

            }
        };


        public void setHealthChecksSugar() {
            if (sugar == null) {
                return;
            }
            String[] splits = sugar.getCheckupValue().split(",");
            String fasting = splits.length > 0 ? splits[0] : null;
            String postFasting = splits.length > 1 ? splits[1] : null;
            String hba1c = splits.length > 2 ? splits[2] : null;

            fasting_et.setText(fasting);
            post_meal_et.setText(postFasting);
            hba1c_et.setText(hba1c);
            date_tv.setText(getDisplayDateStr(sugar.getCheckupTime()));
        }

        public void setViewState(boolean isEnabled) {
            fasting_et.setEnabled(isEnabled);
            post_meal_et.setEnabled(isEnabled);
            hba1c_et.setEnabled(isEnabled);
            date_tv.setEnabled(isEnabled);

            if (isEnabled) {
                edit_iv.setVisibility(View.GONE);
                ok_iv.setVisibility(View.VISIBLE);
                delete_iv.setVisibility(View.VISIBLE);
            } else {
                edit_iv.setVisibility(View.VISIBLE);
                ok_iv.setVisibility(View.GONE);
                delete_iv.setVisibility(View.VISIBLE);
            }
        }

        public void onUpdateButtonClick()
        {
           /* fasting_et = convertView.findViewById(R.id.fasting_et);
            post_meal_et = convertView.findViewById(R.id.post_meal_et);
            hba1c_et = convertView.findViewById(R.id.hba1c_et);
            date_tv = convertView.findViewById(R.id.date_tv);
*/
           final String fasting = fasting_et.getText().toString();
            final String lunch = post_meal_et.getText().toString();
            final String hba1c = hba1c_et.getText().toString();
             String checkuptime1 = date_tv.getText().toString();
            if (TextUtils.isEmpty(lunch)) {
                Utils.showToastMessage(R.string.please_enter_lunch_value_lbl);
                return;
            }
            if (TextUtils.isEmpty(fasting)) {
                Utils.showToastMessage(R.string.please_enter_pasting_value_lbl);
                return;
            }
            if (TextUtils.isEmpty(hba1c)) {
                Utils.showToastMessage(R.string.please_enter_hba1c_value_lbl);
                return;
            }
            if (TextUtils.isEmpty(checkuptime1)) {
                Utils.showToastMessage(R.string.please_select_date_lbl);
                return;
            }
           // "YYYY-M-d H:I:S"
            final String checkuptime=getServerDateStr(checkuptime1);
            UpdateSugarHealthCheckMapper mapper= new UpdateSugarHealthCheckMapper(sugar.getId(),  fasting,  lunch,  hba1c,  checkuptime);

            mapper.setOnUpdateSugarHealthCheckListener(new UpdateSugarHealthCheckMapper.UpdateSugarHealthCheckListener() {
                @Override
                public void updateSugar(AbstractResponse response, String errorMessage) {
                    if(!isValidResponse(response,errorMessage,true,false))
                    {
                        return;
                    }
                    sugar.setCheckupTime(checkuptime);
                    sugar.setCheckupValue(fasting + "," + lunch + "," + hba1c);
                    Utils.showToastMessage(response.getStatusMessage());
                    setViewState(false);
                }
            });


           // holder.setViewState(false);

        }


    }

    private void calAddViewRecord(final HealthChecksSugar sugar, final boolean isfromAddNew) {

        final View convertView = inflater
                .inflate(R.layout.sugar_single_item, null, false);
        final RecordHolder holder = new RecordHolder(sugar, convertView);
        //holder.setHealthChecksSugar(sugar);
        holder.setViewState(false);


        holder.edit_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.setViewState(true);
            }
        });


        holder.ok_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // add_new_reocrd_linear.setVisibility(View.VISIBLE);
                holder.onUpdateButtonClick();

            }
        });
        holder.delete_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.edit_iv.getVisibility() == View.GONE) {
                    holder.setViewState(false);
                    holder.setHealthChecksSugar();
                } else {
                    showMyCustomDialog("Alert", Utils.getStringFromResources(R.string.are_you_sure_to_delete_sugar_record_lbl), "Yes", "No", new MyDialogInterface() {
                        @Override
                        public void onPossitiveClick()
                        {
                            int id=-1;
                            try
                            {
                                if(TextUtils.isEmpty(sugar.getId()))
                                {
                                    Utils.showToastMessage(R.string.invalid_sugar_record_details_lbl);
                                    return;
                                }
                                id= Integer.parseInt(sugar.getId());
                            }catch (Exception e)
                            {
                                Utils.showToastMessage(R.string.invalid_sugar_record_details_lbl);
                                return;
                            }
                            DeleteSugarHealthCheckMapper mapper= new DeleteSugarHealthCheckMapper(id);
                            mapper.setOnDeleteSugarHealthCheckListener(new DeleteSugarHealthCheckMapper.DeleteSugarHealthCheckListener() {
                                @Override
                                public void deleteSugar(AbstractResponse response, String errorMessage) {
                                    if(!isValidResponse(response,errorMessage,true,false))
                                    {
                                        return;
                                    }
                                    Utils.showToastMessage(response.getStatusMessage());
                                    sugar_levels_linear.removeView(convertView);
                                }
                            });
                        }

                        @Override
                        public void onNegetiveClick() {

                        }
                    });
                }


            }
        });

        if (isfromAddNew) {
            sugar_levels_linear.addView(convertView, 0);
        } else {
            sugar_levels_linear.addView(convertView);
        }


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


    public void setSugarLevelsList(List<HealthChecksSugar> sugarLevelsList) {
        this.sugarLevelsList = sugarLevelsList;
    }


}