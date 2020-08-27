package com.vempower.eezyclinic.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.vempower.eezyclinic.APICore.ForgotPasswordData;
import com.vempower.eezyclinic.APICore.UserAccount;
import com.vempower.eezyclinic.APIResponce.ForgotPasswordAPI;
import com.vempower.eezyclinic.APIResponce.ForgotPasswordOTPAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.AccountListDialogInterface;
import com.vempower.eezyclinic.mappers.ForgotPasswordMapper;
import com.vempower.eezyclinic.mappers.ForgotPasswordOTPMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyButtonRectangleRM;


import java.util.List;

/**
 * Created by satish on 20/11/17.
 */

public class ForgotPasswordActivity extends AbstractFragmentActivity {

    private EditText mobile_num_et, email_et;
    private TextWatcher mobileNumTextWacher, emailTextWacher;
    private boolean isEmail = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();
    }

    private void init() {

        mobile_num_et = findViewById(R.id.mobile_num_et1);
        email_et = findViewById(R.id.email_et1);

        mobileNumTextWacher = getTextWacher(email_et, true);
        mobile_num_et.addTextChangedListener(mobileNumTextWacher);

        emailTextWacher = getTextWacher(mobile_num_et, false);
        email_et.addTextChangedListener(emailTextWacher);

        MyButtonRectangleRM submit_bt = findViewById(R.id.submit_bt);
        submit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideKeyBord(email_et);
                        }
                    }, 100);

                    submitButtonClick();
                }


            }
        });
    }

    private void submitButtonClick() {
        //MyApplication.showTransparentDialog();

        isEmail = false;

        String email = email_et.getText().toString();
        String mobileNum = mobile_num_et.getText().toString();
        if (TextUtils.isEmpty(mobileNum) && TextUtils.isEmpty(email)) {
            showToastMessage("Please enter valid email id/mobile number");
            //MyApplication.hideTransaprentDialog();
            return;
        } else if (!TextUtils.isEmpty(email)) {
            if (!Utils.isValidEmail(email)) {
                showToastMessage("Please enter valid email id.");
                //MyApplication.hideTransaprentDialog();
                return;
            }
            //TODO call a mapper with email id
           // showToastMessage("Email id:" + email);
            isEmail = true;
            //return;

        }
        // showToastMessage("Please enter valid email id/mobile number");

        ForgotPasswordMapper mapper = new ForgotPasswordMapper(email, mobileNum);

        mapper.setOnForgotPasswordListener(new ForgotPasswordMapper.ForgotPasswordListener() {
            @Override
            public void getForgotPasswordAPI(ForgotPasswordAPI forgotPasswordAPI, String errorMessage) {
                //MyApplication.hideTransaprentDialog();

                if (!isValidResponse(forgotPasswordAPI, errorMessage)) {
                    //MyApplication.hideTransaprentDialog();
                    return;
                }

                final List<UserAccount> userAccounts = forgotPasswordAPI.getData();

                if (userAccounts == null || userAccounts.size() == 0) {
                    showMyAlertDialog("Alert", Utils.getStringFromResources(R.string.no_accounts_found_in_database_lbl), "Ok", false);
                    //MyApplication.hideTransaprentDialog();
                    return;
                }

                if (userAccounts.size() == 1) {
                    callForgotAccountMapper(userAccounts.get(0));
                   /* intent.putExtra(Constants.Pref.USER_ACCOUNT_OBJ_KEY,userAccounts.get(0));
                    startActivity(intent);
                    finish();*/
                    //MyApplication.hideTransaprentDialog();
                    return;
                } else {

                    showAccountListDialog(isEmail,userAccounts, new AccountListDialogInterface() {
                        @Override
                        public void okClick(int selectedItemIndex) {
                            if (selectedItemIndex <= -1) {
                                return;
                            }
                            callForgotAccountMapper(userAccounts.get(selectedItemIndex));
                            //MyApplication.hideTransaprentDialog();
                            return;

                        }
                    });
                }


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //hideKeyBord(dateofBirth_tv);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideKeyBord(email_et);
            }
        }, 100);


    }


    private void callForgotAccountMapper(UserAccount account) {

       // showToastMessage(account.toString());
        //MyApplication.showTransparentDialog();
        int id=-1;
        try
        {
            id= Integer.parseInt(account.getId());
        }catch ( Exception e)
        {
            Utils.showToastMsg("Invalid patient details");
           return;
        }
        ForgotPasswordOTPMapper otpMapper = new ForgotPasswordOTPMapper(id, isEmail ? "2" : "1");

        otpMapper.setOnForgotPasswordOTPListener(new ForgotPasswordOTPMapper.ForgotPasswordOTPListener() {
            @Override
            public void getForgotPasswordOTPAPI(ForgotPasswordOTPAPI forgotPasswordOTPAPI, String errorMessage) {
                //MyApplication.hideTransaprentDialog();

                if (!isValidResponse(forgotPasswordOTPAPI, errorMessage)) {
                    return;
                }

                if (forgotPasswordOTPAPI.getData() == null) {
                    showMyAlertDialog("Alert", Utils.getStringFromResources(R.string.unable_to_send_otp_lbl), "Ok", false);
                    return;
                }

                ForgotPasswordData passwordData = forgotPasswordOTPAPI.getData();
                Intent intent = ((Activity)MyApplication.getCurrentActivityContext()).getIntent();
               // intent.setClass(MyApplication.getCurrentActivityContext(), ScheduleAppointmentActivity.class);

                intent.setClass(MyApplication.getCurrentActivityContext(), ResetPasswordActivity.class);

                intent.putExtra(Constants.Pref.PATIENT_ID_KEY, passwordData.getId());
                intent.putExtra(Constants.Pref.FORGOT_OTP_KEY, passwordData.getOtp());
                startActivity(intent);
                finish();


            }
        });
    }

    private TextWatcher getTextWacher(final EditText editText, final boolean isMobile) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editText != null) {
                    editText.removeTextChangedListener(isMobile ? emailTextWacher : mobileNumTextWacher);
                    editText.setText(null);
                    editText.addTextChangedListener(isMobile ? emailTextWacher : mobileNumTextWacher);
                }
            }
        };
    }
}
