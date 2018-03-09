package com.vempower.eezyclinic.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.vempower.eezyclinic.APIResponce.LoginAPI;
import com.vempower.eezyclinic.APIResponce.SignupAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.ResendOTPMapper;
import com.vempower.eezyclinic.mappers.SignInMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyButtonRectangleRM;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.vempower.eezyclinic.utils.Utils.showToastMessage;

/**
 * Created by satish on 20/11/17.
 */

public class SigninActivity extends AbstractSocialLoginActivity {
    private static final String TAG = "SigninActivity";

    //private fname_et,emane_et

    private MyButtonRectangleRM login_bt;
    private EditText user_id_et,login_password_et;
    //private OnRequestSocialPersonCompleteListener listener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        onShowFacebookButton();
        init();
    }

    private void init() {
        login_bt = findViewById(R.id.login_bt);
        login_password_et = findViewById(R.id.login_password_et);
        user_id_et = findViewById(R.id.user_id_et);

        if(Constants.IS_TESTING)
        {
            //user_id_et.setText("swathi.kits.1251@gmail.com");
            //user_id_et.setText("wahedjaved@gmail.com");
            //wahedjaved@gmail.com
            //user_id_et.setText("quddus.mohd@v-empower.com");
        }

        setLoginListener();

    }

    private void setLoginListener() {
        login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* if(false)
                {
                    Intent intent= new Intent(MyApplication.getCurrentActivityContext(),HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return;

                }

                if(false) {
                    generateHashkey();
                    return;
                }*/

                if (isNotValidLoginDetails()) {

                    return;
                }

                String userid=user_id_et.getText().toString();
                String password=login_password_et.getText().toString();

               // showToastMessage("userid :"+userid);

                SignInMapper mapper= new SignInMapper(userid,password);
               mapper.setOnSignInListener(new SignInMapper.SignInListener() {
                   @Override
                   public void getLoginAPI(LoginAPI loginAPI,String errorMessage) {
                       validateLoginUserDetails(loginAPI,errorMessage);
                   }
               });
            }
        });
    }

    private void validateLoginUserDetails(final LoginAPI loginAPI,String errorMessage) {



        if(loginAPI!=null && loginAPI.getIs_account_activated().equalsIgnoreCase(Constants.ACCOUNT_NOT_ACTIVATE_STATUS_CODE))
        {
            if(true)
            {
                ResendOTPMapper otpMapper= new ResendOTPMapper(loginAPI.getId());
                otpMapper.setOnResendOTPListener(new ResendOTPMapper.ResendOTPListener() {
                    @Override
                    public void getSignupAPI(SignupAPI signupAPI, String errorMessage) {
                        if(!isValidResponse(signupAPI,errorMessage))
                        {
                            return;
                        }
                        callResendOTPScreen(signupAPI);
                    }
                });
                return;
            }

            //title: String, message: String,  positiveBtnName: String="Retry",negativeBtnName: String="Close", dialogInterface: ApiErrorDialogInterface?
            showMyDialog("Alert", loginAPI.getStatusMessage(), "Continue", "Close", new ApiErrorDialogInterface() {
                @Override
                public void onCloseClick() {
                    //TODO nothing
                }

                @Override
                public void retryClick() {

                    //TODO call
                    ResendOTPMapper otpMapper= new ResendOTPMapper(loginAPI.getId());
                    otpMapper.setOnResendOTPListener(new ResendOTPMapper.ResendOTPListener() {
                        @Override
                        public void getSignupAPI(SignupAPI signupAPI, String errorMessage) {
                            if(!isValidResponse(signupAPI,errorMessage))
                            {
                                return;
                            }
                            callResendOTPScreen(signupAPI);
                        }
                    });
                }
            });

            return;
        }

        if(!isValidResponse(loginAPI,errorMessage))
        {
            return;
        }


        if(loginAPI.getPatientData()==null || TextUtils.isEmpty(loginAPI.getAccessToken()))
        {
            showMyAlertDialog("Alert", Utils.getStringFromResources(R.string.invalid_service_response_lbl),Utils.getStringFromResources(R.string.ok_label),false);
            return;
        }
        MyApplication.getInstance().setLoggedUserDetailsToSharedPref(loginAPI.getPatientData());
        SharedPreferenceUtils.setStringValueToSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,loginAPI.getAccessToken());
        Intent intent= new Intent(MyApplication.getCurrentActivityContext(),HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        showToastMessage(R.string.normal_signup_or_signin_msg_lbl);
        finish();
    }

    private void callResendOTPScreen(SignupAPI signupAPI) {
        Intent intent= new Intent(MyApplication.getCurrentActivityContext(),VerifyOTPActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.Pref.OTP_KEY,signupAPI.getOtp());
        intent.putExtra(VerifyOTPActivity.IS_FROM_RESEND_OTP_KEY,true);
        intent.putExtra(Constants.Pref.PATIENT_ID_KEY,signupAPI.getData().patientId);

        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //hideKeyBord(dateofBirth_tv);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideKeyBord(login_bt);
            }
        }, 100);


    }




    public void onSignupClick(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }


    public void onForgotPasswordClick(View view) {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

    public boolean isNotValidLoginDetails() {
        {
            String email = user_id_et.getText().toString();

            if (TextUtils.isEmpty(email)) {
                showToastMessage(R.string.please_enter_valid_user_id_lbl);//"Please enter valid user id");
                //user_id_et.setError("Please enter valid user id");
                return true;
            }

        }
        {
            String password = login_password_et.getText().toString();

            if (TextUtils.isEmpty(password)) {
                showToastMessage(R.string.please_enter_password_lbl);
                //login_password_et.setError("Please enter valid password");
                return true;
            }

        }
        return false;
    }



    private void generateHashkey(){
        try {
            String PACKAGE = "com.vempower.eezyclinic";
            PackageInfo info = getPackageManager().getPackageInfo(
                    PACKAGE,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

               // ((TextView) findViewById(R.id.package_name)).setText(info.packageName);
                String hashkey= Base64.encodeToString(md.digest(), Base64.NO_WRAP);
               // ((TextView) findViewById(R.id.hash_key)).setText(hashkey);

                //showToastMessage("Hashkey "+hashkey);
                //Log.i("Hashkey",hashkey);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            Log.d(TAG, e.getMessage(), e);
        }
    }

}
