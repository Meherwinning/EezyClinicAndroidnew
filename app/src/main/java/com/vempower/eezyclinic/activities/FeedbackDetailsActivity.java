package com.vempower.eezyclinic.activities;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.PendingFeedbackData;
import com.vempower.eezyclinic.APICore.SubmitedFeedbackListData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.FeedbackDetailsFragment;
import com.vempower.eezyclinic.fragments.SubmitFeedbackFragment;
import com.vempower.eezyclinic.utils.Utils;

public class FeedbackDetailsActivity extends AbstractMenuActivity {


    @Override
    protected void setMyContectntView() {
        super.setMyContectntView();

        SubmitedFeedbackListData data=null;
        Object obj = getObjectFromIntent(getIntent(), ListenerKey.ObjectKey.SUBMITED_FEEDBACK_DATA_OBJECT_KEY);

        if (obj != null && obj instanceof SubmitedFeedbackListData) {
            data = (SubmitedFeedbackListData) obj;

            // showToastMessage("Data :" + data);
        } else {
            showMyAlertDialog("Alert", Utils.getStringFromResources(R.string.invalid_feddback_details_lbl), "Close", true);
            return;
        }

        if (data == null) {
            showMyAlertDialog("Alert", Utils.getStringFromResources(R.string.invalid_feddback_details_lbl), "Close", true);
            return;

        }

        FeedbackDetailsFragment fragment= new FeedbackDetailsFragment();
        fragment.setSubmitedFeedbackListData(data);

        setFragment(fragment);


    }

    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView titleName = toolbar.findViewById(R.id.title_logo_tv);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        titleName.setText(Utils.getStringFromResources(R.string.title_activity_my_feedback_lbl));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something you want
                finish();
            }
        });
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);
        //mImageView = findViewById(R.id.image);
        //View  mToolbarView = findViewById(R.id.toolbar);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        getSupportActionBar().setTitle("");
        // mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.app_red)));

        //super.setActionBar(false);
    }

    @Override
    protected AbstractFragment getFragment() {
        return null;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}
