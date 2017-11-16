package com.vempower.eezyclinic.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.vempower.eezyclinic.application.MyApplication;

/**
 * Created by Satish on 11/15/2017.
 */

public class AbstractFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.setCurrentActivityContext(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MyApplication.setCurrentActivityContext(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MyApplication.setCurrentActivityContext(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.setCurrentActivityContext(this);
    }
}
