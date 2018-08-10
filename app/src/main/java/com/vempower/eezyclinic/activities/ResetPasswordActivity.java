package com.vempower.eezyclinic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.LoginActivity;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.mappers.ResetPasswordMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyButtonRectangleRM;


/**
 * Created by satish on 23/11/17.
 */

public class ResetPasswordActivity extends AbstractFragmentActivity {

 private EditText otp_et , new_password_et  , re_password_et ;
    private String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        init();
    }

    private void init() {
        //intent.putExtra(Constants.Pref.USER_ACCOUNT_OBJ_KEY,userAccounts.get(0));
       // intent.putExtra(Constants.Pref.PATIENT_ID_KEY, passwordData.getId());
        //intent.putExtra(Constants.Pref.PATIENT_OTP_KEY, passwordData.getOtp());


        if(getIntent()==null || !getIntent().hasExtra(Constants.Pref.PATIENT_ID_KEY))
        {
            showMyDialog("Alert", Utils.getStringFromResources(R.string.invalid_user_details_lbl),true);
            return;
        }
         id=getIntent().getStringExtra(Constants.Pref.PATIENT_ID_KEY);

        if(TextUtils.isEmpty(id))
        {
            showMyDialog("Alert",Utils.getStringFromResources(R.string.invalid_user_details_lbl),true);
            return;

        }

        otp_et = findViewById(R.id.otp_et);
        new_password_et  = findViewById(R.id.new_password_et);
        re_password_et = findViewById(R.id.re_password_et);



       // otpTv.setVisibility(View.GONE);
        if(Constants.IS_TESTING)
        {
            TextView otpTv= findViewById(R.id.opt_display_tv);
            if(otpTv!=null) {
                String otp=getIntent().getStringExtra(Constants.Pref.FORGOT_OTP_KEY);
                otpTv.setVisibility(View.VISIBLE);
                otpTv.setText(otp);
            }
        }

        //UserAccount userAccount=
        MyButtonRectangleRM reset_password_bt =findViewById(R.id.reset_password_bt);
        reset_password_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetButtonClick();

               /* showAccountListDialog(new ApiErrorDialogInterface() {
                    @Override
                    public void onCloseClick() {
                        showToastMessage("onCloseClick");
                    }

                    @Override
                    public void retryClick() {
                        showToastMessage("retryClick");
                    }
                });*/
               // showToastMessage("Success to reset password");
               /* Intent intent= new Intent(MyApplication.getCurrentActivityContext(),LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();*/
            }
        });
    }

    private void resetButtonClick() {
        if(!isValidDetails())
        {
            return;
        }
        String otp = otp_et.getText().toString();
        String password = new_password_et.getText().toString();
        String repassword = re_password_et.getText().toString();
        //id;
        //resetP
        //String otp, String id,String newpw,String confirmnewpw
        int pid=-1;

        try
        {
            pid= Integer.parseInt(id);
        }catch (Exception e)
        {
            Utils.showToastMsg("Invalid patient details");
            return;
        }
        ResetPasswordMapper mapper= new ResetPasswordMapper(otp,pid,password,repassword);
        mapper.setOnResetPasswordListener(new ResetPasswordMapper.ResetPasswordListener() {
            @Override
            public void getResetPasswordAPI(AbstractResponse responce, String errorMessage) {
                if(!isValidResponse(responce,errorMessage))
                {
                    return;
                }

                Intent intent= new Intent(ResetPasswordActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                showMyDialog("Alert",responce.getStatusMessage(),true,intent);

            }
        });
    }

    /*@Override
    public void otpReceived(String smsText) {
        //Do whatever you want to do with the text
        //Toast.makeText(this,"Got "+smsText,Toast.LENGTH_LONG).show();
        //Log.d("Otp",smsText);
        showToastMessage("From Reset Password\n"+smsText);

    }*/

    public boolean isValidDetails() {
        //otp_et , new_password_et  , re_password_et
        {
            String otp = otp_et.getText().toString();

            if (TextUtils.isEmpty(otp)) {
                showToastMessage(R.string.please_enter_valid_otp_lbl);
                //otp_et.setError("Please enter valid OTP");
                return false;
            }

        }
        String password = new_password_et.getText().toString();
        {


            if (TextUtils.isEmpty(password)) {
                showToastMessage(R.string.please_enter_valid_new_enter_password_lbl);
                //login_password_et.setError("Please enter valid password");
                return false;
            }

        }
        String repassword = re_password_et.getText().toString();
        {


            if (TextUtils.isEmpty(repassword)) {
                //showToastMessage("Please enter valid re password");
                showToastMessage(R.string.please_enter_valid_re_enter_password_lbl);
                //login_password_et.setError("Please enter valid password");
                return false;
            }

        }
        {


            if (!repassword.equals(password)) {
                showToastMessage(R.string.new_password_and_re_password_should_be_same_lbl);
                //login_password_et.setError("Please enter valid password");
                return false;
            }

        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //hideKeyBord(dateofBirth_tv);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideKeyBord(otp_et);
            }
        }, 100);


    }

}
