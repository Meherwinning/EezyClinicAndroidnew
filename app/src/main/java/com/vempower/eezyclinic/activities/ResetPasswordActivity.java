package com.vempower.eezyclinic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.facebook.LoginActivity;
import com.vempower.eezyclinic.APICore.UserAccount;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.views.MyButtonRectangleRM;

/**
 * Created by satish on 23/11/17.
 */

public class ResetPasswordActivity extends AbstractFragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        init();
    }

    private void init() {
        //intent.putExtra(Constants.Pref.USER_ACCOUNT_OBJ_KEY,userAccounts.get(0));

        if(getIntent()==null || !getIntent().hasExtra(Constants.Pref.USER_ACCOUNT_OBJ_KEY))
        {
            showMyDialog("Alert","Invalid user details\nPlease try again",true);
            return;
        }
        Object obj=getIntent().getSerializableExtra(Constants.Pref.USER_ACCOUNT_OBJ_KEY);

        if(obj==null || !(obj instanceof UserAccount))
        {

        }
        //UserAccount userAccount=
        MyButtonRectangleRM reset_password_bt =findViewById(R.id.reset_password_bt);
        reset_password_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                showToastMessage("Success to reset password");
               /* Intent intent= new Intent(MyApplication.getCurrentActivityContext(),LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();*/
            }
        });
    }
    @Override
    public void otpReceived(String smsText) {
        //Do whatever you want to do with the text
        //Toast.makeText(this,"Got "+smsText,Toast.LENGTH_LONG).show();
        //Log.d("Otp",smsText);
        showToastMessage("From Reset Password\n"+smsText);

    }

}
