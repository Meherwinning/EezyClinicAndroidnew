package com.vempower.eezyclinic.activities;

import android.os.Bundle;

import com.vempower.eezyclinic.R;
import com.vempower.stashdealcustomer.activities.AbstractActivity;

import org.jetbrains.annotations.Nullable;

/**
 * Created by satish on 9/1/18.
 */

public class ImageExpandViewActivity extends AbstractActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.image_expand_layout_activity);
    }
}
