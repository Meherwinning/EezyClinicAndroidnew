package com.vempower.eezyclinic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.facebook.LoginActivity;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.views.MyButtonRectangleRM;

/**
 * Created by satish on 23/11/17.
 */

public class VerifyOTPActivity extends AbstractFragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        init();
    }
    private void init() {
        MyButtonRectangleRM verify_bt =findViewById(R.id.verify_bt);
        verify_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToastMessage("Success to verify OTP");
                Intent intent= new Intent(MyApplication.getCurrentActivityContext(),HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
