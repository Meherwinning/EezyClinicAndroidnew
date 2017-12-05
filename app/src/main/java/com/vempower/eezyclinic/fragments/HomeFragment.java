package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.PatientData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;

/**
 * Created by satish on 4/12/17.
 */

public class HomeFragment extends AbstractFragment {

    private View fragmentView;
    private TextView welcome_tv;
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
        welcome_tv = fragmentView.findViewById(R.id.welcome_tv);

        String str = "Welcome to Eezyclinic\n";
        PatientData patientData = MyApplication.getInstance().getLoggedUserDetailsFromSharedPref();
        if(patientData!=null)
        {

            if(!TextUtils.isEmpty(patientData.getPatientName()))
            {
                str=str+patientData.getPatientName()+"\n"+patientData.getPatientUniqueId();
            }

        }
        welcome_tv.setText(str);

    }

}
