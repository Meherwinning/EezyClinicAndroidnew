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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.vempower.eezyclinic.APICore.HealthChecksCholesterol;
import com.vempower.eezyclinic.APICore.HealthChecksHeight;
import com.vempower.eezyclinic.APICore.HealthChecksHeightWeight;
import com.vempower.eezyclinic.APICore.HealthChecksWeight;
import com.vempower.eezyclinic.APICore.PatientData;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.APIResponce.AddWHhealthCheckAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.MyDialogInterface;
import com.vempower.eezyclinic.mappers.AddCholHealthCheckMapper;
import com.vempower.eezyclinic.mappers.AddHeightAndWeightHealthCheckMapper;
import com.vempower.eezyclinic.mappers.DeleteSugarHealthCheckMapper;
import com.vempower.eezyclinic.mappers.DeleteWeightHeightCheckMapper;
import com.vempower.eezyclinic.mappers.UpdateCholHealthCheckMapper;
import com.vempower.eezyclinic.mappers.UpdateWeightHeightCheckMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyEditTextBlackCursorRR;
import com.vempower.eezyclinic.views.MyTextViewRR;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HealthCheckTab3 extends AbstractHealthChecksTabFragment {


    public static final String TITLE = "Weight/Height";
    private View fragmentView;
    private LinearLayout sugar_levels_linear, add_new_reocrd_linear, new_sugar_record_view_linear;
    private LayoutInflater inflater;
    //  private List<HealthChecksSugar> sugarLevelsList;
    private MyEditTextBlackCursorRR new_weight_et, new_height_et;
    private MyTextViewRR new_date_tv;
    private ImageView new_delete_iv, new_ok_iv;
    //private List<HealthChecksCholesterol> chorlList;
    private ArrayList<HealthChecksHeightWeight> heightWeightList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.activity_checks_tab3, container, false);

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

                // ImageView delete_iv = new_sugar_record_view_linear.findViewById(R.id.delete_iv);

            }
        });

    }

    private void initForAddNewRecord() {
        new_weight_et = getFragemtView().findViewById(R.id.weight_et);
        new_height_et = getFragemtView().findViewById(R.id.height_et);

        new_date_tv = getFragemtView().findViewById(R.id.date_tv);

        new_ok_iv = getFragemtView().findViewById(R.id.ok_iv);
        new_delete_iv = getFragemtView().findViewById(R.id.delete_iv);


        new_ok_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String weight = new_weight_et.getText().toString();
                String height = new_height_et.getText().toString();
                String checkuptime = "2018-01-07";// new_date_tv.getText().toString();
                if (TextUtils.isEmpty(weight)) {
                    Utils.showToastMessage(R.string.please_enter_weight_value_lbl);
                    return;
                }
                if (TextUtils.isEmpty(height)) {
                    Utils.showToastMessage(R.string.please_enter_height_value_lbl);
                    return;
                }


                callAddNewSugarHealthcheckRecord(weight,height, checkuptime);
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
        new_weight_et.setText(null);
        new_height_et.setText(null);
        new_date_tv.setText(null);
    }

    private void callAddNewSugarHealthcheckRecord(final String weight, final String height, final String checkuptime) {

        AddHeightAndWeightHealthCheckMapper mapper = new AddHeightAndWeightHealthCheckMapper(weight, height, checkuptime);

        mapper.setOnAddHeightAndWeightHealthCheckListener(new AddHeightAndWeightHealthCheckMapper.AddHeightAndWeightHealthCheckListener() {
            @Override
            public void addHeightAndWeigh(AddWHhealthCheckAPI response, String errorMessage) {
                if (!isValidResponse(response, errorMessage, true, false)) {
                    return;
                }
                HealthChecksHeightWeight  heightWeight= new HealthChecksHeightWeight();

                heightWeight.setCheckupgroupid(response.getData().getCheckupgroupid());
                heightWeight.setCheckupTime(checkuptime);
                //setCheckupgroupid(height.getCheckupgroupid());
                //setCreatedOn(height.getCreatedOn());
                heightWeight.setHeightCheckupValue(height);
                heightWeight.setWeightCheckupValue(weight);

                calAddViewRecord(heightWeight, true);

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
        if (heightWeightList == null || heightWeightList.size() == 0) {
            return;
        }

        for (HealthChecksHeightWeight heightWeight : heightWeightList) {
            if (heightWeight == null) {
                continue;
            }
            calAddViewRecord(heightWeight, false);


        }

    }



    private class RecordHolder {
        MyEditTextBlackCursorRR weight_et, height_et;
        MyTextViewRR date_tv;
        ImageView ok_iv, edit_iv, delete_iv;
        private final HealthChecksHeightWeight heightWeight;
        private SelectedDate selectedObj;

        public RecordHolder(HealthChecksHeightWeight heightWeight, View convertView) {
            this.heightWeight = heightWeight;

            weight_et = convertView.findViewById(R.id.weight_et);
            height_et = convertView.findViewById(R.id.height_et);
           // triglycerides_et = convertView.findViewById(R.id.triglycerides_et);
            //tot_cholesterol_et = convertView.findViewById(R.id.tot_cholesterol_et);
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
            if (heightWeight == null) {
                return;
            }
            //String[] splits = heightWeight.get.split(",");
           // String weight = splits.length > 0 ? splits[0] : null;
            //String height = splits.length > 1 ? splits[1] : null;
           /* String triglycerides = splits.length > 2 ? splits[2] : null;
            String tot_cholesterol = splits.length > 3 ? splits[3] : null;
*/
            weight_et.setText(heightWeight.getWeightCheckupValue());
            height_et.setText(heightWeight.getHeightCheckupValue());
            //triglycerides_et.setText(triglycerides);
            //tot_cholesterol_et.setText(tot_cholesterol);
           // date_tv.setText(heightWeight.getCheckupTime());
            date_tv.setText(getDisplayDateStr(heightWeight.getCheckupTime()));
        }

        public void setViewState(boolean isEnabled) {
            weight_et.setEnabled(isEnabled);
            height_et.setEnabled(isEnabled);
           // triglycerides_et.setEnabled(isEnabled);
           // tot_cholesterol_et.setEnabled(isEnabled);
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

        public void onUpdateButtonClick() {
           /* fasting_et = convertView.findViewById(R.id.fasting_et);
            post_meal_et = convertView.findViewById(R.id.post_meal_et);
            hba1c_et = convertView.findViewById(R.id.hba1c_et);
            date_tv = convertView.findViewById(R.id.date_tv);
*/
            final String weight = weight_et.getText().toString();
            final String height = height_et.getText().toString();
            //final String triglycerides = triglycerides_et.getText().toString();
           // final String tot_cholesterol = tot_cholesterol_et.getText().toString();
            final String checkuptime1 = date_tv.getText().toString();
            if (TextUtils.isEmpty(weight)) {
                Utils.showToastMessage(R.string.please_enter_weight_value_lbl);
                return;
            }
            if (TextUtils.isEmpty(height)) {
                Utils.showToastMessage(R.string.please_enter_height_value_lbl);
                return;
            }
            /*if (TextUtils.isEmpty(triglycerides)) {
                Utils.showToastMessage(R.string.please_enter_triglycerides_value_lbl);
                return;
            }
            if (TextUtils.isEmpty(tot_cholesterol)) {
                Utils.showToastMessage(R.string.please_enter_tot_cholesterol_value_lbl);
                return;
            }*/
            if (TextUtils.isEmpty(checkuptime1)) {
                Utils.showToastMessage(R.string.please_select_date_lbl);
                return;
            }
            final String checkuptime=getServerDateStr(checkuptime1);
            UpdateWeightHeightCheckMapper  mapper= new UpdateWeightHeightCheckMapper( heightWeight.getCheckupgroupid(),  weight,
                     height, checkuptime);

            mapper.setOnUpdateWeightHeightCheckListener(new UpdateWeightHeightCheckMapper.UpdateWeightHeightCheckListener() {
                @Override
                public void updateWeightHeightCheck(AbstractResponse response, String errorMessage) {
                    if (!isValidResponse(response, errorMessage, true, false)) {
                        return;
                    }
                    heightWeight.setCheckupTime(checkuptime);
                    heightWeight.setWeightCheckupValue(weight);
                    heightWeight.setHeightCheckupValue(height);
                    Utils.showToastMessage(response.getStatusMessage());
                    setViewState(false);

                }
            });

        }


    }

    private void calAddViewRecord(final  HealthChecksHeightWeight  heightWeight, final boolean isfromAddNew) {

        final View convertView = inflater
                .inflate(R.layout.hw_single_item, null, false);
        final RecordHolder holder = new RecordHolder(heightWeight, convertView);
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
                    showMyCustomDialog("Alert", Utils.getStringFromResources(R.string.are_you_sure_to_delete_height_and_weight_record_lbl), "Yes", "No", new MyDialogInterface() {
                        @Override
                        public void onPossitiveClick() {
                            int id = -1;
                            try {
                                if (TextUtils.isEmpty(heightWeight.getId())) {
                                    Utils.showToastMessage(R.string.invalid_sugar_record_details_lbl);
                                    return;
                                }
                                id = Integer.parseInt(heightWeight.getCheckupgroupid());
                            } catch (Exception e) {
                                Utils.showToastMessage(R.string.invalid_sugar_record_details_lbl);
                                return;
                            }

                            DeleteWeightHeightCheckMapper mapper= new DeleteWeightHeightCheckMapper(id);

                            mapper.setOnDeleteWeightHeightCheckListener(new DeleteWeightHeightCheckMapper.DeleteWeightHeightCheckListener() {
                                @Override
                                public void deleteWeightHeight(AbstractResponse response, String errorMessage) {
                                    if (!isValidResponse(response, errorMessage, true, false)) {
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
        return WEIGHT_AND_HEIGHT_TYPE;
    }

    @Override
    String getHealthCheckTypeName() {
        return "Weight and Height";
    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }


    public void setHeightAndWeightList(ArrayList<HealthChecksHeightWeight>  heightWeightList) {

        this.heightWeightList=heightWeightList;
       // heightWeightList = new ArrayList<>();

      /*  if (heightList == null || weightList == null) {
            return;
        }
        if (heightList.size() == 0 || weightList.size() == 0) {
            return;
        }

        for (HealthChecksHeight height : heightList) {

            if (height == null) {
                continue;
            }
            HealthChecksWeight newWeight= new HealthChecksWeight();
            newWeight.setCheckupgroupid(height.getCheckupgroupid());

            int index = weightList.indexOf(newWeight);

            if(index!=-1)
            {
                HealthChecksWeight weight = weightList.get(index);
                HealthChecksHeightWeight heightWeight= new HealthChecksHeightWeight(height);
                heightWeight.setWeightCheckupValue(weight.getWeightCheckupValue());
                heightWeightList.add(heightWeight);

            }



        }*/


    }

}



