package com.vempower.eezyclinic.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.activities.AbstractActivity;
import com.vempower.eezyclinic.utils.Constants;

import org.jetbrains.annotations.Nullable;

/**
 * Created by satish on 9/1/18.
 */

public class ImageExpandViewActivity extends AbstractFragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_expand_layout_activity);

        myInit();
    }

    private void myInit() {

        if (getIntent() == null || !getIntent().hasExtra(Constants.Pref.IMAGE_VIEW_URL_KEY)) {
            showAlertDialog("Alert", "Invalid image details.", true);
            return;
        }


        String imageUrl = getIntent().getStringExtra(Constants.Pref.IMAGE_VIEW_URL_KEY);

        findViewById(R.id.close_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // expand_iv   fin
        ImageView expand_iv = findViewById(R.id.expand_iv);


        if (TextUtils.isEmpty(imageUrl)) {
            showMyAlertDialog("Alert", "Invalid image.Please try again", "Close", true);
            return;
        }

        MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_icon,expand_iv,imageUrl);


    }


}
