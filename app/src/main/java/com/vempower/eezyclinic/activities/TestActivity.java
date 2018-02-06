package com.vempower.eezyclinic.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;

import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.AbstractActivity;

import org.jetbrains.annotations.Nullable;

/**
 * Created by satish on 5/1/18.
 */

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //((PinView) findViewById(R.id.firstPinView)).setAnimationEnable(true);
        //((PinView) findViewById(R.id.secondPinView)).setAnimationEnable(true);
    }
}
