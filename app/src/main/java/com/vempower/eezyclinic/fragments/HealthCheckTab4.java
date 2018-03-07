package com.vempower.eezyclinic.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.vempower.eezyclinic.APICore.HealthChecksCholesterol;
import com.vempower.eezyclinic.APICore.HealthChecksSugar;
import com.vempower.eezyclinic.APICore.PatientData;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.APIResponce.AddhealthCheckAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.MyDialogInterface;
import com.vempower.eezyclinic.mappers.AddCholHealthCheckMapper;
import com.vempower.eezyclinic.mappers.AddSugarHealthCheckMapper;
import com.vempower.eezyclinic.mappers.DeleteSugarHealthCheckMapper;
import com.vempower.eezyclinic.mappers.UpdateCholHealthCheckMapper;
import com.vempower.eezyclinic.mappers.UpdateSugarHealthCheckMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyEditTextBlackCursorRR;
 ;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HealthCheckTab4 extends  AbstractHealthChecksTabFragment {


    public static final String TITLE = "Cholesterol";
    private View fragmentView;
    private LinearLayout sugar_levels_linear, add_new_reocrd_linear, new_sugar_record_view_linear;
    private LayoutInflater inflater;
  //  private List<HealthChecksSugar> sugarLevelsList;
    private MyEditTextBlackCursorRR new_tot_cholesterol_et,new_triglycerides_et,new_hdl_et,new_ldl_et;
    private TextView new_date_tv;
    private ImageView new_delete_iv, new_ok_iv;
    private List<HealthChecksCholesterol> chorlList;
    private RelativeLayout no_records_relative;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.activity_checks_tab4, container, false);

        myInit();
        return fragmentView;
    }
    @Override
    int recordsCount() {
        if(sugar_levels_linear!=null)
        {
            return sugar_levels_linear.getChildCount();
        }
        return 0;
    }

    protected  void setNoRecordsViewManage()
    {
        if (recordsCount() == 0) {
            no_records_relative.setVisibility(View.VISIBLE);
            return;
        }else
        {
            no_records_relative.setVisibility(View.GONE);
        }
    }

    protected void myInit() {
        super.myInit();
        inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        no_records_relative  = getFragemtView().findViewById(R.id.no_records_relative);

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
                no_records_relative.setVisibility(View.GONE);

                // ImageView delete_iv = new_sugar_record_view_linear.findViewById(R.id.delete_iv);

            }
        });

    }

    private void initForAddNewRecord() {
        new_ldl_et = getFragemtView().findViewById(R.id.ldl_et);
        new_hdl_et = getFragemtView().findViewById(R.id.hdl_et);
        new_triglycerides_et = getFragemtView().findViewById(R.id.triglycerides_et);
        new_tot_cholesterol_et = getFragemtView().findViewById(R.id.tot_cholesterol_et);

        new_date_tv = getFragemtView().findViewById(R.id.date_tv);

        new_ok_iv = getFragemtView().findViewById(R.id.ok_iv);
        new_delete_iv = getFragemtView().findViewById(R.id.delete_iv);


        new_ok_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ldl = new_ldl_et.getText().toString();
                String hdl = new_hdl_et.getText().toString();
                String triglycerides = new_triglycerides_et.getText().toString();
                String tot_cholesterol = new_tot_cholesterol_et.getText().toString();
               // String checkuptime = "2018-01-07";// new_date_tv.getText().toString();
                if (TextUtils.isEmpty(ldl)) {
                    Utils.showToastMessage(R.string.please_enter_ldl_value_lbl);
                    return;
                }
                if (TextUtils.isEmpty(hdl)) {
                    Utils.showToastMessage(R.string.please_enter_hdl_value_lbl);
                    return;
                }
                if (TextUtils.isEmpty(triglycerides)) {
                    Utils.showToastMessage(R.string.please_enter_triglycerides_value_lbl);
                    return;
                }
                if (TextUtils.isEmpty(tot_cholesterol)) {
                    Utils.showToastMessage(R.string.please_enter_tot_cholesterol_value_lbl);
                    return;
                }


                callAddNewSugarHealthcheckRecord(ldl, hdl, triglycerides,tot_cholesterol);
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
        new_ldl_et.setText(null);
        new_hdl_et.setText(null);
        new_triglycerides_et.setText(null);
        new_tot_cholesterol_et.setText(null);
        new_date_tv.setText(null);
        selectedDateStr="";
    }

    private void callAddNewSugarHealthcheckRecord(final String ldl, final String hdl, final String triglicerides,final String totalcholestral) {

        AddCholHealthCheckMapper mapper= new AddCholHealthCheckMapper( ldl,  hdl,  triglicerides, totalcholestral,  selectedDateStr);

        mapper.setOnAddCholHealthCheckListener(new AddCholHealthCheckMapper.AddCholHealthCheckListener() {
            @Override
            public void addChol(AddhealthCheckAPI response, String errorMessage) {
                if (!isValidResponse(response, errorMessage, true, false)) {
                    return;
                }

                if(response==null || response.getData()==null)
                {
                    return;
                }

                HealthChecksCholesterol chol = new HealthChecksCholesterol();
                chol.setCheckupName("Cholesterol");
                chol.setCheckupValue(ldl + "," + hdl + "," + triglicerides+","+totalcholestral);
                chol.setCheckupTime(selectedDateStr);
                chol.setCheckupgroupid(response.getData().getCheckupgroupid());
                chol.setId(response.getData().getHealthcheckId());
                calAddViewRecord(chol, true);
                refreshGraph();
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
        if (chorlList == null || chorlList.size() == 0) {
            no_records_relative.setVisibility(View.VISIBLE);
            return;
        }else
        {
            no_records_relative.setVisibility(View.GONE);
        }

        for (HealthChecksCholesterol chol : chorlList) {
            if (chol == null) {
                continue;
            }
            calAddViewRecord(chol, false);


        }

    }

    public void setChorlList(List<HealthChecksCholesterol> chorlList) {
        this.chorlList = chorlList;
    }

    private class RecordHolder {
        MyEditTextBlackCursorRR ldl_et, hdl_et, triglycerides_et,tot_cholesterol_et;
         TextView date_tv;
        ImageView ok_iv, edit_iv, delete_iv;
        private final HealthChecksCholesterol chol;
        private SelectedDate selectedObj;

        public RecordHolder(HealthChecksCholesterol chol, View convertView) {
            this.chol = chol;

            ldl_et = convertView.findViewById(R.id.ldl_et);
            hdl_et = convertView.findViewById(R.id.hdl_et);
            triglycerides_et = convertView.findViewById(R.id.triglycerides_et);
            tot_cholesterol_et = convertView.findViewById(R.id.tot_cholesterol_et);
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
            if (chol == null) {
                return;
            }
            String[] splits = chol.getCheckupValue().split(",");
            String ldl = splits.length > 0 ? splits[0] : null;
            String hdl = splits.length > 1 ? splits[1] : null;
            String triglycerides = splits.length > 2 ? splits[2] : null;
            String tot_cholesterol = splits.length > 3 ? splits[3] : null;

            ldl_et.setText(ldl);
            hdl_et.setText(hdl);
            triglycerides_et.setText(triglycerides);
            tot_cholesterol_et.setText(tot_cholesterol);
           // date_tv.setText(chol.getCheckupTime());
            date_tv.setText(getDisplayDateStr(chol.getCheckupTime()));

        }

        public void setViewState(boolean isEnabled) {
            ldl_et.setEnabled(isEnabled);
            hdl_et.setEnabled(isEnabled);
            triglycerides_et.setEnabled(isEnabled);
            tot_cholesterol_et.setEnabled(isEnabled);
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
            final String ldl = ldl_et.getText().toString();
            final String hdl = hdl_et.getText().toString();
            final String triglycerides = triglycerides_et.getText().toString();
            final String tot_cholesterol = tot_cholesterol_et.getText().toString();
            final String checkuptime1 = date_tv.getText().toString();
            if (TextUtils.isEmpty(ldl)) {
                Utils.showToastMessage(R.string.please_enter_ldl_value_lbl);
                return;
            }
            if (TextUtils.isEmpty(hdl)) {
                Utils.showToastMessage(R.string.please_enter_hdl_value_lbl);
                return;
            }
            if (TextUtils.isEmpty(triglycerides)) {
                Utils.showToastMessage(R.string.please_enter_triglycerides_value_lbl);
                return;
            }
            if (TextUtils.isEmpty(tot_cholesterol)) {
                Utils.showToastMessage(R.string.please_enter_tot_cholesterol_value_lbl);
                return;
            }
            if (TextUtils.isEmpty(checkuptime1)) {
                Utils.showToastMessage(R.string.please_select_date_lbl);
                return;
            }
            final String checkuptime=getServerDateStr(checkuptime1);
            UpdateCholHealthCheckMapper mapper= new UpdateCholHealthCheckMapper( chol.getId(),  ldl,
                     hdl,  triglycerides,  tot_cholesterol,
                     checkuptime);

            mapper.setOnUpdateSugarHealthCheckListener(new UpdateCholHealthCheckMapper.UpdateSugarHealthCheckListener() {
                @Override
                public void updateSugar(AbstractResponse response, String errorMessage) {
                    if(!isValidResponse(response,errorMessage,true,false))
                    {
                        return;
                    }

                    chol.setCheckupTime(checkuptime);
                    chol.setCheckupValue(ldl + "," + hdl + "," + triglycerides+","+tot_cholesterol);
                    Utils.showToastMessage(response.getStatusMessage());
                    setViewState(false);
                    refreshGraph();
                }
            });


        }


    }

    private void calAddViewRecord(final HealthChecksCholesterol chol, final boolean isfromAddNew) {

        final View convertView = inflater
                .inflate(R.layout.chol_single_item, null, false);
        final RecordHolder holder = new RecordHolder(chol, convertView);
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
                    showMyCustomDialog("Alert", Utils.getStringFromResources(R.string.are_you_sure_to_delete_chol_record_lbl), "Yes", "No", new MyDialogInterface() {
                        @Override
                        public void onPossitiveClick()
                        {
                            int id=-1;
                            try
                            {
                                if(TextUtils.isEmpty(chol.getId()))
                                {
                                    Utils.showToastMessage(R.string.invalid_sugar_record_details_lbl);
                                    return;
                                }
                                id= Integer.parseInt(chol.getId());
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
                                    refreshGraph();
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
    String getHealthCheckTypeName() {
        return "Cholesterol Levels";
    }

    @Override
    int getHealthCheckType() {
        return CHOLESTEROL_TYPE;
    }
    @Override
    View getFragemtView() {
        return fragmentView;
    }


}



