package com.vempower.eezyclinic.activities;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.AddPrescriptionReportFragment;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;

public class AddPrescriptionReportActivity extends AbstractMenuActivity {

    private TextView titleNameTv;
    private  String title;
    private boolean isFromPrescription;

    @Override
    protected void setMyContectntView() {
        super.setMyContectntView();

         title=Utils.getStringFromResources(R.string.title_activity_add_report_lbl );

        if(getIntent()!=null && getIntent().getBooleanExtra(Constants.Pref.IS_FROM_ADD_PRESCRIPTION_KEY,false))
        {
            title=Utils.getStringFromResources(R.string.title_activity_add_prescription);
            isFromPrescription=true;
        }

        AddPrescriptionReportFragment fragment= new AddPrescriptionReportFragment();
        fragment.setUploadDocumentType(!isFromPrescription);

        setFragment(fragment);


        //Utils.getStringFromResources(R.string.title_activity_appointments)
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(titleNameTv!=null)
        {
            titleNameTv.setText(title);
        }
    }


    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titleNameTv = toolbar.findViewById(R.id.title_logo_tv);
        //((Toolbar) findViewById(R.
        // id.toolbar)).setTitle(deal.getEntityName());
        titleNameTv.setText("-");

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
