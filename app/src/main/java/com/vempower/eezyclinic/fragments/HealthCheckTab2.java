package com.vempower.eezyclinic.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.vempower.eezyclinic.APICore.HealthChecksBP;
import com.vempower.eezyclinic.APICore.HealthChecksSugar;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.APIResponce.AddhealthCheckAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.MyDialogInterface;
import com.vempower.eezyclinic.mappers.AddBPHealthCheckMapper;
import com.vempower.eezyclinic.mappers.AddSugarHealthCheckMapper;
import com.vempower.eezyclinic.mappers.DeleteSugarHealthCheckMapper;
import com.vempower.eezyclinic.mappers.UpdateBPHealthCheckMapper;
import com.vempower.eezyclinic.mappers.UpdateSugarHealthCheckMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyEditTextBlackCursor;
 ;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HealthCheckTab2 extends  AbstractHealthChecksTabFragment {


    public static final String TITLE = "Blood Pressure";
    private View fragmentView;
    private LinearLayout sugar_levels_linear, add_new_reocrd_linear, new_sugar_record_view_linear;
    private LayoutInflater inflater;
   // private List<HealthChecksSugar> sugarLevelsList;
    private MyEditTextBlackCursor new_diastolic_et,new_systolic_et;
    private TextView new_date_tv;
    private ImageView new_delete_iv, new_ok_iv;
    private List<HealthChecksBP> BPList;
    private RelativeLayout no_records_relative;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.activity_checks_tab2, container, false);

        myInit();
        return fragmentView;
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
                no_records_relative.setVisibility(View.GONE);

                new_sugar_record_view_linear.setVisibility(View.VISIBLE);
              //  ImageView delete_iv = new_sugar_record_view_linear.findViewById(R.id.delete_iv);


            }
        });

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

    private void initForAddNewRecord() {
        new_diastolic_et = getFragemtView().findViewById(R.id.diastolic_et);
        new_systolic_et = getFragemtView().findViewById(R.id.systolic_et);
        //new_hba1c_et = getFragemtView().findViewById(R.id.hba1c_et);
        new_date_tv = getFragemtView().findViewById(R.id.date_tv);

        new_ok_iv = getFragemtView().findViewById(R.id.ok_iv);
        new_delete_iv = getFragemtView().findViewById(R.id.delete_iv);


        new_ok_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String diastolic = new_diastolic_et.getText().toString();
                String systolic = new_systolic_et.getText().toString();
               // String hba1c = new_hba1c_et.getText().toString();
                //String checkuptime = "2018-01-07";// new_date_tv.getText().toString();
                if (TextUtils.isEmpty(diastolic)) {
                    Utils.showToastMessage(R.string.please_enter_diastolic_value_lbl);
                    return;
                }
                if (TextUtils.isEmpty(systolic)) {
                    Utils.showToastMessage(R.string.please_enter_systolic_value_lbl);
                    return;
                }
               /* if (TextUtils.isEmpty(hba1c)) {
                    Utils.showToastMessage(R.string.please_enter_hba1c_value_lbl);
                    return;
                }*/
                // new_date_tv.getText().toString();
               /* String checkuptime="2018-01-07";
                HealthChecksSugar  sugar= new HealthChecksSugar();
                sugar.setCheckupValue(fasting+","+lunch+","+hba1c);
                sugar.setCheckupTime(checkuptime);
                sugar.setCheckupName("Sugar Level");

                return sugar;*/

                callAddNewBPHealthcheckRecord(diastolic, systolic);
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
        new_diastolic_et.setText(null);
        new_systolic_et.setText(null);
       // new_hba1c_et.setText(null);
        new_date_tv.setText(null);
        selectedDateStr="";
    }

    private void callAddNewBPHealthcheckRecord(final String diastolic, final String systolic) {
        AddBPHealthCheckMapper mapper= new AddBPHealthCheckMapper(diastolic,systolic,selectedDateStr);

        mapper.setOnAddBPHealthCheckListener(new AddBPHealthCheckMapper.AddBPHealthCheckListener() {
            @Override
            public void addBP(AddhealthCheckAPI response, String errorMessage) {
                if (!isValidResponse(response, errorMessage, true, false)) {
                    return;
                }

                if(response==null || response.getData()==null)
                {
                    return;
                }

                HealthChecksBP bp = new HealthChecksBP();
                bp.setCheckupName("BP");
                bp.setCheckupValue(diastolic + "," + systolic);
                bp.setCheckupTime(selectedDateStr);
                bp.setCheckupgroupid(response.getData().getCheckupgroupid());
                bp.setId(response.getData().getHealthcheckId());
                calAddViewRecord(bp, true);
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
        if (BPList == null || BPList.size() == 0) {
            no_records_relative.setVisibility(View.VISIBLE);
            return;
        }else
        {
            no_records_relative.setVisibility(View.GONE);
        }

        for (HealthChecksBP bp : BPList) {
            if (bp == null) {
                continue;
            }
            calAddViewRecord(bp, false);


        }

    }

    public void setBPList(List<HealthChecksBP> BPList) {
        this.BPList = BPList;
    }

    private class RecordHolder {
        MyEditTextBlackCursor diastolic_et, systolic_et;
         TextView date_tv;
        ImageView ok_iv, edit_iv, delete_iv;
        private final HealthChecksBP bp;
        private SelectedDate selectedObj;

        public RecordHolder(HealthChecksBP bp, View convertView) {
            this.bp = bp;

            diastolic_et = convertView.findViewById(R.id.diastolic_et);
            systolic_et = convertView.findViewById(R.id.systolic_et);
           // hba1c_et = convertView.findViewById(R.id.hba1c_et);
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
            if (bp == null) {
                return;
            }
            String[] splits = bp.getCheckupValue().split(",");
            String fasting = splits.length > 0 ? splits[0] : null;
            String postFasting = splits.length > 1 ? splits[1] : null;
            String hba1c = splits.length > 2 ? splits[2] : null;

            diastolic_et.setText(fasting);
            systolic_et.setText(postFasting);
           // hba1c_et.setText(hba1c);
           // date_tv.setText(bp.getCheckupTime());
            date_tv.setText(getDisplayDateStr(bp.getCheckupTime()));
        }

        public void setViewState(boolean isEnabled) {
            diastolic_et.setEnabled(isEnabled);
            systolic_et.setEnabled(isEnabled);
           // hba1c_et.setEnabled(isEnabled);
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
            final String diastolic = diastolic_et.getText().toString();
            final String systolic = systolic_et.getText().toString();
            final String checkuptime1 = date_tv.getText().toString();
            if (TextUtils.isEmpty(diastolic)) {
                Utils.showToastMessage(R.string.please_enter_diastolic_value_lbl);
                return;
            }
            if (TextUtils.isEmpty(systolic)) {
                Utils.showToastMessage(R.string.please_enter_systolic_value_lbl);
                return;
            }

            if (TextUtils.isEmpty(checkuptime1)) {
                Utils.showToastMessage(R.string.please_select_date_lbl);
                return;
            }
            final String checkuptime=getServerDateStr(checkuptime1);
            UpdateBPHealthCheckMapper  mapper= new UpdateBPHealthCheckMapper(bp.getId(),  diastolic,  systolic,   checkuptime);

            mapper.setOnUpdateBPHealthCheckListener(new UpdateBPHealthCheckMapper.UpdateBPHealthCheckListener() {
                @Override
                public void updateBP(AbstractResponse response, String errorMessage) {
                    if(!isValidResponse(response,errorMessage,true,false))
                    {
                        return;
                    }
                    bp.setCheckupTime(checkuptime);
                    bp.setCheckupValue(diastolic + "," + systolic );
                    Utils.showToastMessage(response.getStatusMessage());
                    setViewState(false);
                    refreshGraph();
                }
            });
        }


    }

    private void calAddViewRecord(final HealthChecksBP bp, final boolean isfromAddNew) {

        final View convertView = inflater
                .inflate(R.layout.bp_single_item, null, false);
        final RecordHolder holder = new RecordHolder(bp, convertView);
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
                    showMyCustomDialog("Alert", Utils.getStringFromResources(R.string.are_you_sure_to_delete_bp_record_lbl), "Yes", "No", new MyDialogInterface() {
                        @Override
                        public void onPossitiveClick()
                        {
                            int id=-1;
                            try
                            {
                                if(TextUtils.isEmpty(bp.getId()))
                                {
                                    Utils.showToastMessage(R.string.invalid_bp_record_details_lbl);
                                    return;
                                }
                                id= Integer.parseInt(bp.getId());
                            }catch (Exception e)
                            {
                                Utils.showToastMessage(R.string.invalid_bp_record_details_lbl);
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
    int getHealthCheckType() {
        return BLOOD_PRESSURE_TYPE;
    }
    @Override
    String getHealthCheckTypeName() {
        return "Blood Pressure";
    }
    @Override
    View getFragemtView() {
        return fragmentView;
    }





}




