package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vempower.eezyclinic.APICore.PatientData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyEditTextBlackCursorRR;
import com.vempower.stashdealcustomer.activities.AbstractActivity;

/**
 * Created by satish on 6/12/17.
 */

public class ChangeEmailIdFragment extends AbstractFragment {

    private MyEditTextBlackCursorRR registed_email_id_et,change_email_id_et;
    private View fragmentView;
    private AppCompatButton verify_email_bt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.change_email_id, container, false);


        myInit();

        return fragmentView;
    }

    private void myInit() {

        registed_email_id_et  =getFragemtView().findViewById(R.id.registed_email_id_et);
        change_email_id_et  =getFragemtView().findViewById(R.id.change_email_id_et);

        verify_email_bt =getFragemtView().findViewById(R.id.verify_email_bt);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        compute();
    }

    private void compute() {
        PatientData patientData = MyApplication.getInstance().getLoggedUserDetailsFromSharedPref();
        String access_key= SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,null);

        if(patientData==null || TextUtils.isEmpty(access_key))
        {

            showAlertDialog("Alert", Utils.getStringFromResources(R.string.unautherized_user_details_lbl), false);
            return;
        }
        registed_email_id_et.setText(patientData.getPatentEmail());

        verify_email_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String newEmail=  change_email_id_et.getText().toString();

              if(TextUtils.isEmpty(newEmail))
              {
                  Utils.showToastMsg("Please enter new Email id");
                  return;
              }
                if(!Utils.isValidEmail(newEmail))
                {
                    Utils.showToastMsg("Please enter valid Email id");
                    return;
                }

                Utils.showToastMsg("Email id :"+newEmail+"\nAPI pending");



            }
        });
    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }
}
