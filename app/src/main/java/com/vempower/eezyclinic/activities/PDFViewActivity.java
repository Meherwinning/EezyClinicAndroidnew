package com.vempower.eezyclinic.activities;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.PDFDetails;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.NewHomeFragment;
import com.vempower.eezyclinic.fragments.PDFViewFragment;
import com.vempower.eezyclinic.fragments.UpdatePrescriptionReportFragment;
import com.vempower.eezyclinic.utils.Utils;

public class PDFViewActivity extends AbstractMenuActivity {

    private String titleNameStr="";
    private TextView titleName;
    public static final int MEDICAL_RECORDS_REFRESH_REQUEST_CODE=3452;
    private PDFViewFragment fragment;

    @Override
    protected void setMyContectntView() {
        super.setMyContectntView();
        titleNameStr="";
        PDFDetails data=null;
        Object obj = getObjectFromIntent(getIntent(), ListenerKey.ObjectKey.PDF_DETAILS_OBJECT_KEY);

        if (obj != null && obj instanceof PDFDetails) {
            data = (PDFDetails) obj;

            // showToastMessage("Data :" + data);
        } else {
            showMyAlertDialog("Alert", "Invalid PDF details .Please try again", "Close", true);
            return;
        }

        if (data == null) {
            showMyAlertDialog("Alert", "Invalid PDF details.Please try again", "Close", true);
            return;

        }

           fragment= new PDFViewFragment();
        fragment.setPDFDetails(data);
        fragment.setOnSuccuessUpdateListener(new PDFViewFragment.SuccussUpdateListener() {
            @Override
            public void status(boolean isSuccess) {

                if(isSuccess)
                {
                    callMedicalRecordds();
                }
                finish();
            }
        });
        // titleNameStr= data.getDocumentType();

        titleNameStr= Utils.getCapLeterString(data.getDocumentType());

       invalidateOptionsMenu();


        setFragment(fragment);

    }

    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         titleName = toolbar.findViewById(R.id.title_logo_tv);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        titleName.setText(titleNameStr);

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK)
        {
            if(requestCode==MEDICAL_RECORDS_REFRESH_REQUEST_CODE)
            {
                callMedicalRecordds();
                finish();
            }
        }

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
    protected void onRestart() {
        super.onRestart();
        if(fragment!=null)
        {
            fragment.onRestart();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}
