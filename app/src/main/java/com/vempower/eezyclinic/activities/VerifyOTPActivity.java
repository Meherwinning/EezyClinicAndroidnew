package com.vempower.eezyclinic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.facebook.LoginActivity;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.views.MyButtonRectangleRM;
import com.vempower.eezyclinic.views.MyEditTextRR;

/**
 * Created by satish on 23/11/17.
 */

public class VerifyOTPActivity extends AbstractFragmentActivity {
    private String otp_str;
    private String patient_id;

    private MyEditTextRR otp_et;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        init();
    }
    private void init() {

        if (getIntent() == null) {
            showMyDialog("Alert", "Invalid user details\nplease try again", true, new Intent(this, SigninActivity.class));
            return;
        }
        otp_et=findViewById(R.id.otp_et);

     //   intent.putExtra(Constants.Pref.OTP_KEY,signupAPI.getOtp());
      //  intent.putExtra(Constants.Pref.PATIENT_ID_KEY,signupAPI.getData().patientId);

        otp_str = getIntent().getStringExtra(Constants.Pref.OTP_KEY);
        if (TextUtils.isEmpty(otp_str)) {
            showMyDialog("Alert", "Invalid OTP details\nplease try again", true, new Intent(this, SigninActivity.class));
            return;
        }

        patient_id = getIntent().getStringExtra(Constants.Pref.PATIENT_ID_KEY);
        if (TextUtils.isEmpty(patient_id)) {
            showMyDialog("Alert", "Invalid patient details\nplease try again", true, new Intent(this, SigninActivity.class));
            return;
        }
        otp_et.setText(otp_str);

        MyButtonRectangleRM verify_bt =findViewById(R.id.verify_bt);
        verify_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String enteredOTP=otp_et.getText().toString();
                if(TextUtils.isEmpty(enteredOTP))
                {
                    showToastMessage("Please enter valid OTP");
                    return;
                }
                if(enteredOTP.equalsIgnoreCase(otp_str)) {
                    //TODO API pending
                    showToastMessage("Success to verify OTP\nAPI pending");
                    /*Intent intent= new Intent(MyApplication.getCurrentActivityContext(),HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();*/
                }
               /* */
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(MyApplication.getCurrentActivityContext(),SigninActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
