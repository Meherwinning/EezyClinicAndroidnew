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

import com.vempower.eezyclinic.APICore.HealthChecksSugar;
import com.vempower.eezyclinic.APICore.PatientData;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.mappers.AddSugarHealthCheckMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyEditTextBlackCursorRR;
import com.vempower.eezyclinic.views.MyTextViewRR;

import java.util.List;

public class HealthCheckTab1 extends AbstractHealthChecksTabFragment {


    public static final String TITLE = "Sugar";
    private View fragmentView;
    private LinearLayout sugar_levels_linear, add_new_reocrd_linear, new_sugar_record_view_linear;
    private LayoutInflater inflater;
    private List<HealthChecksSugar> sugarLevelsList;
    private MyEditTextBlackCursorRR new_fasting_et, new_hba1c_et, new_post_meal_et;
    private MyTextViewRR new_date_tv;
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
                ImageView delete_iv = new_sugar_record_view_linear.findViewById(R.id.delete_iv);


            }
        });

    }

    private void initForAddNewRecord() {
        new_fasting_et = getFragemtView().findViewById(R.id.fasting_et);
        new_post_meal_et = getFragemtView().findViewById(R.id.post_meal_et);
        new_hba1c_et = getFragemtView().findViewById(R.id.hba1c_et);
        new_date_tv = getFragemtView().findViewById(R.id.date_tv);

        new_ok_iv = getFragemtView().findViewById(R.id.ok_iv);
        new_delete_iv = getFragemtView().findViewById(R.id.delete_iv);

        new_ok_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HealthChecksSugar sugar = getFromNewView();


                new_sugar_record_view_linear.setVisibility(View.GONE);
                add_new_reocrd_linear.setVisibility(View.VISIBLE);
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

    public HealthChecksSugar getFromNewView() {
        new_fasting_et.getText().toString();
        new_post_meal_et = getFragemtView().findViewById(R.id.post_meal_et);
        new_hba1c_et = getFragemtView().findViewById(R.id.hba1c_et);
        new_date_tv = getFragemtView().findViewById(R.id.date_tv);

        return null;
    }

    private void resetAddNewRecord() {
        new_fasting_et.setText(null);
        new_post_meal_et.setText(null);
        new_hba1c_et.setText(null);
        new_date_tv.setText(null);
    }

    private void callAddNewSugarHealthcheckRecord(final String fasting, final String lunch, final String hba1c, String checkuptime) {
        AddSugarHealthCheckMapper mapper = new AddSugarHealthCheckMapper(fasting, lunch, hba1c, checkuptime);
        mapper.setOnAddSugarHealthCheckListener(new AddSugarHealthCheckMapper.AddSugarHealthCheckListener() {
            @Override
            public void addSugar(AbstractResponse response, String errorMessage) {
                if (!isValidResponse(response, errorMessage, true, false)) {
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
                newSugar.setCheckupTime("2018-01-07");
                newSugar.setCheckupValue(fasting + "," + lunch + "," + hba1c);
                calAddViewRecord(newSugar, true);
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

    private void calAddViewRecord(HealthChecksSugar sugar, boolean isfromAddNew) {

        final View convertView = inflater
                .inflate(R.layout.sugar_single_item, null, false);

        MyEditTextBlackCursorRR fasting_et = convertView.findViewById(R.id.fasting_et);
        MyEditTextBlackCursorRR post_meal_et = convertView.findViewById(R.id.post_meal_et);
        MyEditTextBlackCursorRR hba1c_et = convertView.findViewById(R.id.hba1c_et);
        MyTextViewRR date_tv = convertView.findViewById(R.id.date_tv);

        ImageView ok_iv = convertView.findViewById(R.id.ok_iv);
        ImageView edit_iv = convertView.findViewById(R.id.edit_iv);
        ImageView delete_iv = convertView.findViewById(R.id.delete_iv);

        edit_iv.setVisibility(View.VISIBLE);
        ok_iv.setVisibility(View.GONE);
        delete_iv.setVisibility(View.VISIBLE);
        String[] splits = sugar.getCheckupValue().split(",");
        String fasting = splits.length > 0 ? splits[0] : null;
        String postFasting = splits.length > 1 ? splits[1] : null;
        String hba1c = splits.length > 2 ? splits[2] : null;

        fasting_et.setText(fasting);
        post_meal_et.setText(postFasting);
        hba1c_et.setText(hba1c);
        date_tv.setText(sugar.getCheckupTime());


        ok_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_new_reocrd_linear.setVisibility(View.VISIBLE);
            }
        });
        delete_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_new_reocrd_linear.setVisibility(View.VISIBLE);
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
