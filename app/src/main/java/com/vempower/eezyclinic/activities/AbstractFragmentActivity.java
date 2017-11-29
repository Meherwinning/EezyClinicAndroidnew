package com.vempower.eezyclinic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.vempower.eezyclinic.APIResponce.SignupAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.OTPListener;
import com.vempower.eezyclinic.receivers.OtpReader;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.stashdealcustomer.activities.AbstractActivity;

/**
 * Created by Satish on 11/15/2017.
 */

public class AbstractFragmentActivity extends AbstractActivity  implements OTPListener {

   // private InputMethodManager keyBoardHide;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.setCurrentActivityContext(this);
        basicInit();

        //View view= getLayoutInflater().inflate(R.layout.radio_view_item,null);

    }

    private void basicInit() {
        OtpReader.bind(this,Constants.SMS_SENDER_NAME);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
       // String refreshedToken = FirebaseInstanceId.getInstance().getToken();
       // Log.v("FCM ID :", "FCM1: " + refreshedToken);

       // String token = FirebaseInstanceId.getInstance().getToken();

        // Log and toast
       //String msg = getString(R.string.msg_token_fmt, token);
        Log.v("FCM ID :", "FCM :"+ Utils.getFireBaseCloudMessageId());
    }


    protected void validateSignupReponse(SignupAPI signupAPI) {
        if(!signupAPI.getStatusCode().equalsIgnoreCase(Constants.SUCCESS_STATUS_CODE) || signupAPI.getData()==null)
        {
            showMyDialog("Alert",signupAPI.getStatusMessage(),false);
            return;
        }
        Intent intent= new Intent(MyApplication.getCurrentActivityContext(),VerifyOTPActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.Pref.OTP_KEY,signupAPI.getOtp());
        intent.putExtra(Constants.Pref.PATIENT_ID_KEY,signupAPI.getData().patientId);

        startActivity(intent);
        finish();
    }


    /*protected void hideKeyBord(View view ) {
        if (view != null) {
            if (keyBoardHide == null) {
                keyBoardHide = (InputMethodManager) MyApplication.getCurrentActivityContext().getSystemService(INPUT_METHOD_SERVICE);
               // as InputMethodManager
                // keyBoardHide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,
                // 0);
            }
            if (keyBoardHide != null && keyBoardHide.isActive()) {
                // to hide keyboard
                if (view != null) {
                    keyBoardHide.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }
    }*/
    @Override
    public void otpReceived(String smsText) {
        //Do whatever you want to do with the text
        //Toast.makeText(this,"Got "+smsText,Toast.LENGTH_LONG).show();
        //Log.d("Otp",smsText);
        showToastMessage(smsText);

    }

}
