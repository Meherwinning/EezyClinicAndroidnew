package com.vempower.eezyclinic.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyButtonRectangleRM;
import com.vempower.eezyclinic.views.MyEditTextRR;

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
                {  String email = email_et.getText().toString();
                if (!TextUtils.isEmpty(email)) {
                    if (!Utils.isValidEmail(email)) {
                        showToastMessage("Please enter valid email id.");
                        return;
                    }
                    //TODO call a mapper with email id
                    showToastMessage("Email id:" + email + "\nAPI pending");
                    return;

                }
            }

                String mobileNum=mobile_num_et.getText().toString();
                if(!TextUtils.isEmpty(mobileNum))
                {
                    /*if(!Utils.isValidEmail(email))
                    {
                        showToastMessage("Please enter valid email id.");
                        return;
                    }*/
                    //TODO call a mapper with mobile num id
                    showToastMessage("Mobile num:"+mobileNum+"\nAPI pending");
                    return;

                }

                showToastMessage("Please enter valid email id/mobile number");

               // startActivity(new Intent(MyApplication.getCurrentActivityContext(),ResetPasswordActivity.class));

            }
        });
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
