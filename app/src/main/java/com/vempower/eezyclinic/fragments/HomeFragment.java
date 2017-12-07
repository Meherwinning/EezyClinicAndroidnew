package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.DashboardData;
import com.vempower.eezyclinic.APICore.PatientData;
import com.vempower.eezyclinic.APIResponce.DashboardAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.mappers.DashboardMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.views.MyTextViewRM;
import com.vempower.eezyclinic.views.MyTextViewRR;

/**
 * Created by satish on 4/12/17.
 */

public class HomeFragment extends AbstractFragment {

    private View fragmentView;
    //private TextView welcome_tv;
    private MyTextViewRM patient_name_tv,patient_id_tv;
    private MyTextViewRR health_tips_tv,health_goal_tv,upcoming_appointment_tv,upcoming_followups_tv;
    private ImageView profile_iv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.home_layout, container, false);

        //showProgressView();

        init();
        return fragmentView;
    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }

    private void init() {
        patient_name_tv = fragmentView.findViewById(R.id.patient_name_tv);
        patient_id_tv = fragmentView.findViewById(R.id.patient_id_tv);
        profile_iv = fragmentView.findViewById(R.id.profile_iv);
        health_tips_tv = fragmentView.findViewById(R.id.health_tips_tv);
        health_goal_tv = fragmentView.findViewById(R.id. health_goal_tv);
        upcoming_appointment_tv  = fragmentView.findViewById(R.id.upcoming_appointment_tv);
        upcoming_followups_tv = fragmentView.findViewById(R.id.upcoming_followups_tv);

       // String str = "Welcome to Eezyclinic\n";
        refreshPatientData(null);
        callDashboardMapper();
       // welcome_tv.setText(str);

    }

    private void callDashboardMapper()
    {
        String access_key= SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,null);
        if (TextUtils.isEmpty(access_key) ) {
            //TODO something
            return;
        }
        DashboardMapper mapper= new DashboardMapper();
        mapper.setOnDashboardListener(new DashboardMapper.DashboardListener() {
            @Override
            public void getDashboardAPI(DashboardAPI dashboardAPI, String errorMessage) {

                if(!isValidResponse(dashboardAPI,errorMessage))
                {
                    return;
                }
                if(dashboardAPI.getData()!=null)
                {
                    if(dashboardAPI.getData().getPatientProfile()!=null)
                    {

                        MyApplication.getInstance().setLoggedUserDetailsToSharedPref(dashboardAPI.getData().getPatientProfile());
                        refreshPatientData(dashboardAPI.getData().getPatientProfile());

                        refreshDashBoard(dashboardAPI.getData());

                    }
                }


            }
        });

    }

    private void refreshDashBoard(DashboardData dashboardData)
    {
        if(dashboardData==null)
        {
            return;
        }
        health_tips_tv.setText(dashboardData.getHealthtips());
        health_goal_tv.setText(dashboardData.getHealthgoals());
       // upcoming_appointment_tv  = fragmentView.findViewById(R.id.upcoming_appointment_tv);
       // upcoming_followups_tv = fragmentView.findViewById(R.id.upcoming_followups_tv);
    }

    private void refreshPatientData(PatientData patientData) {
        if(patientData==null)
        {
            patientData = MyApplication.getInstance().getLoggedUserDetailsFromSharedPref();

        }
         if(patientData!=null)
        {

            patient_name_tv.setText(patientData.getPatientName());
            patient_id_tv.setText(patientData.getPatientUniqueId());
            if (profile_iv != null) {
                //int defaultImageId, ImageView imageView,String imageUrl
                MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.profile_icon, profile_iv, patientData.getPatientLogo());
            }

        }


    }

}
