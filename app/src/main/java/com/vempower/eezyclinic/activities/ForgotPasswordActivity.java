package com.vempower.eezyclinic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.vempower.eezyclinic.APICore.UserAccount;
import com.vempower.eezyclinic.APIResponce.ForgotPasswordAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.core.AdapterItem;
import com.vempower.eezyclinic.interfaces.AccountListDialogInterface;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.ForgotPasswordMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyButtonRectangleRM;
import com.vempower.eezyclinic.views.MyEditTextRR;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satish on 20/11/17.
 */

public class ForgotPasswordActivity extends  AbstractFragmentActivity {

    private MyEditTextRR mobile_num_et,email_et;
    private  TextWatcher mobileNumTextWacher,emailTextWacher;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();
    }

    private void init() {

        mobile_num_et =findViewById(R.id.mobile_num_et1);
        email_et =findViewById(R.id.email_et1);

         mobileNumTextWacher=getTextWacher(email_et,true);
        mobile_num_et.addTextChangedListener(mobileNumTextWacher);

         emailTextWacher=getTextWacher(mobile_num_et,false);
        email_et.addTextChangedListener(emailTextWacher);

        MyButtonRectangleRM submit_bt =findViewById(R.id.submit_bt);
        submit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {


                    // boolean isEmail=false;

                    String email = email_et.getText().toString();
                    String mobileNum = mobile_num_et.getText().toString();
                     if (TextUtils.isEmpty(mobileNum) && TextUtils.isEmpty(email))
                     {
                         showToastMessage("Please enter valid email id/mobile number");
                         return;
                     }
                    else if (!TextUtils.isEmpty(email)) {
                        if (!Utils.isValidEmail(email)) {
                            showToastMessage("Please enter valid email id.");
                            return;
                        }
                        //TODO call a mapper with email id
                        showToastMessage("Email id:" + email );
                        // isEmail=true;
                        //return;

                    } else if (!TextUtils.isEmpty(mobileNum)) {
                    /*if(!Utils.isValidEmail(email))
                    {
                        showToastMessage("Please enter valid email id.");
                        return;
                    }*/
                        //TODO call a mapper with mobile num id
                        showToastMessage("Mobile num:" + mobileNum );
                        //return;

                    }

                        // showToastMessage("Please enter valid email id/mobile number");

                    ForgotPasswordMapper mapper= new ForgotPasswordMapper(email,mobileNum);

                    mapper.setOnForgotPasswordListener(new ForgotPasswordMapper.ForgotPasswordListener() {
                        @Override
                        public void getForgotPasswordAPI(ForgotPasswordAPI forgotPasswordAPI, String errorMessage) {
                            if(forgotPasswordAPI==null && TextUtils.isEmpty(errorMessage))
                            {
                                showMyAlertDialog("Alert",  Utils.getStringFromResources(R.string.invalid_service_response_lbl),"Ok",false);
                                return;
                            }
                            if(forgotPasswordAPI==null && !TextUtils.isEmpty(errorMessage))
                            {
                                showMyAlertDialog("Alert", errorMessage,"Ok",false);
                                return;
                            }
                            if(!forgotPasswordAPI.getStatusCode().equalsIgnoreCase(Constants.SUCCESS_STATUS_CODE))
                            {
                                showMyAlertDialog("Alert",forgotPasswordAPI.getStatusMessage() ,"Ok",false);
                                return;

                            }
                           final List<UserAccount> userAccounts = forgotPasswordAPI.getData();

                            if(userAccounts==null || userAccounts.size()==0)
                            {
                                showMyAlertDialog("Alert","No account found in database." ,"Ok",false);
                                return;
                            }
                           final Intent intent= new Intent(MyApplication.getCurrentActivityContext(),ResetPasswordActivity.class);

                            if(userAccounts.size()==0)
                            {
                                callForgotAccountMapper(userAccounts.get(0));
                               /* intent.putExtra(Constants.Pref.USER_ACCOUNT_OBJ_KEY,userAccounts.get(0));
                                startActivity(intent);
                                finish();*/
                                return;
                            }else
                            {
                                showAccountListDialog(userAccounts, new AccountListDialogInterface() {
                                    @Override
                                    public void okClick(int selectedItemIndex) {
                                        if(selectedItemIndex<=-1)
                                        {
                                            return;
                                        }
                                        callForgotAccountMapper(userAccounts.get(selectedItemIndex));
                                        /*intent.putExtra(Constants.Pref.USER_ACCOUNT_OBJ_KEY,userAccounts.get(selectedItemIndex));
                                        startActivity(intent);
                                        finish();*/
                                        return;

                                    }
                                });
                            }






                        }
                    });
                }


            }
        });
    }


    private void callForgotAccountMapper(UserAccount account)
    {

        showToastMessage(account.toString());
    }

    private TextWatcher getTextWacher(final EditText editText,final boolean isMobile)
    {
      return  new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editText!=null) {
                    editText.removeTextChangedListener(isMobile?emailTextWacher:mobileNumTextWacher);
                    editText.setText(null);
                    editText.addTextChangedListener(isMobile?emailTextWacher:mobileNumTextWacher);
                }
            }
        };
    }
}
