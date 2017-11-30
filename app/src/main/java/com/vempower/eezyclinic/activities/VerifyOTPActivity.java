package com.vempower.eezyclinic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.LoginActivity;
import com.vempower.eezyclinic.APIResponce.LoginAPI;
import com.vempower.eezyclinic.APIResponce.VerifyOTPAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.interfaces.OTPListener;
import com.vempower.eezyclinic.mappers.SignupMapper;
import com.vempower.eezyclinic.mappers.VerifyOTPMapper;
import com.vempower.eezyclinic.receivers.OtpReader;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyButtonRectangleRM;
import com.vempower.eezyclinic.views.MyEditTextRR;


/**
 * Created by satish on 23/11/17.
 */

public class VerifyOTPActivity extends AbstractFragmentActivity  {
    private String otp_str;
    private String patient_id;

    private MyEditTextRR otp_et;
    private TextView otp_display_tv;

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

        otp_display_tv=findViewById(R.id. otp_display_tv);

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
        if(Constants.IS_TESTING) {
            otp_display_tv.setText("OTP " + otp_str);
        }

        MyButtonRectangleRM verify_bt =findViewById(R.id.verify_bt);
        verify_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callOTP_VerifyMapper();
                    /*Intent intent= new Intent(MyApplication.getCurrentActivityContext(),HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();*/
               // }
               /* */
            }
        });
    }

    private void callOTP_VerifyMapper() {
        String enteredOTP=otp_et.getText().toString();
        if(TextUtils.isEmpty(enteredOTP))
        {
            showToastMessage("Please enter valid OTP");
            return;
        }
        // if(enteredOTP.equalsIgnoreCase(otp_str)) {
        //TODO API pending

        VerifyOTPMapper mapper= new VerifyOTPMapper(enteredOTP,patient_id);
        mapper.setOnVerifyOtpListener(new VerifyOTPMapper.VerifyOtpListener() {
            @Override
            public void getVerifyOTPAPI(VerifyOTPAPI loginAPI,String errorMessage) {

                /*if(loginAPI==null && TextUtils.isEmpty(errorMessage))
                {
                    showMyAlertDialog("Alert",  Utils.getStringFromResources(R.string.invalid_service_response_lbl),"Ok",false);
                    return;
                }*/
                if(loginAPI==null && !TextUtils.isEmpty(errorMessage))
                {
                    showMyAlertDialog("Alert", errorMessage,"Ok",false);
                    return;
                }

                if(loginAPI==null && TextUtils.isEmpty(errorMessage))
                {

                    showMyDialog("Alert", Utils.getStringFromResources(R.string.invalid_service_response_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {
                          /*  Intent intent= new Intent(MyApplication.getCurrentActivityContext(),SignupMapper.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();*/
                        }

                        @Override
                        public void retryClick() {
                            callOTP_VerifyMapper();
                        }
                    });
                    return;
                }

                validateLoginUserDetails(loginAPI) ;
            }
        });
    }

    private void validateLoginUserDetails(final VerifyOTPAPI loginAPI) {
        if(loginAPI==null)
        {
            showMyAlertDialog("Alert", "Invalid service response.\nPlease check Network/Try again","Ok",false);
            return;
        }
        if(!loginAPI.getStatusCode().equalsIgnoreCase(Constants.SUCCESS_STATUS_CODE))
        {
            showMyAlertDialog("Alert",loginAPI.getStatusMessage() ,"Ok",false);
            return;

        }

        if(loginAPI.getData()==null || TextUtils.isEmpty(loginAPI.getAccessToken()))
        {
            showMyAlertDialog("Alert", "Invalid service response.\nPlease check Network/Try again","Ok",false);
            return;
        }
        showToastMessage(R.string.signup_success_msg);
        //title: String, message: String,  positiveBtnName: String="Retry",negativeBtnName: String="Close", dialogInterface: ApiErrorDialogInterface?
/*        showMyDialog1("Alert", "Ok","Message", new ApiErrorDialogInterface() {
            @Override
            public void onCloseClick() {
                //TODO nothing

            }

            @Override
            public void retryClick() {*/
                MyApplication.getInstance().setLoggedUserDetailsToSharedPref(loginAPI.getData());
                SharedPreferenceUtils.setStringValueToSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,loginAPI.getAccessToken());
                Intent intent= new Intent(MyApplication.getCurrentActivityContext(),HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
           // }
      //  });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(MyApplication.getCurrentActivityContext(),SigninActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

  /*  @Override
    public void otpReceived(String smsText) {
        //Do whatever you want to do with the text
        //Toast.makeText(this,"Got "+smsText,Toast.LENGTH_LONG).show();
        //Log.d("Otp",smsText);
        showToastMessage("From Verify OTP\n"+smsText);

    }*/

    @Override
    protected void onResume() {
        super.onResume();
        //hideKeyBord(dateofBirth_tv);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideKeyBord(otp_et);
            }
        },100);


    }

}
