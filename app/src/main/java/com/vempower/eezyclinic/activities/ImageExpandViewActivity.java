package com.vempower.eezyclinic.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.stashdealcustomer.activities.AbstractActivity;

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


        findViewById(R.id.close_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       // expand_iv   fin
      ImageView expand_iv =findViewById(R.id.expand_iv);
        Drawable data;
        Object obj = getObjectFromIntent(getIntent(), ListenerKey.ObjectKey.IMAGE_DRAWABLE_KEY);

        if (obj != null && obj instanceof Drawable) {
            data = (Drawable) obj;

            // showToastMessage("Data :" + data);
        } else {
            showMyAlertDialog("Alert", "Invalid image.Please try again", "Close", true);
            return;
        }

        if (data == null) {
            showMyAlertDialog("Alert", "Invalid image1.Please try again", "Close", true);
            return;

        }
        expand_iv.setImageDrawable(data);
    }


}
