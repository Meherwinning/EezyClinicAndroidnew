package com.vempower.eezyclinic.activities;

import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.CasesheetDetailFragment;
import com.vempower.eezyclinic.fragments.NewHomeFragment;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;

public class CasesheetsDetailsActivity extends AbstractMenuActivity {


    @Override
    protected void setMyContectntView() {
        super.setMyContectntView();

        String appId = getIntent().getStringExtra( Constants.Pref.CASESHEET_APPOINTMENT_ID_KEY); //getObjectFromIntent(getIntent(), ListenerKey.ObjectKey.SEARCH_RESULT_DOCTOR_LIST_DATA_KEY);



        if (TextUtils.isEmpty(appId)) {
            showMyAlertDialog("Alert", "Invalid Casesheet details.Please try again", "Close", true);
            return;

        }

        CasesheetDetailFragment  fragment= new CasesheetDetailFragment();
        fragment.setAppointmentId(appId);

        setFragment(fragment);


    }

    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView titleName = toolbar.findViewById(R.id.title_logo_tv);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        titleName.setText("Casesheet Details");

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
        return null;//new CasesheetDetailFragment();
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
