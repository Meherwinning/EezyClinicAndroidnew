package com.vempower.eezyclinic.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vempower.eezyclinic.R;

public class MainActivity extends AbstractFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
