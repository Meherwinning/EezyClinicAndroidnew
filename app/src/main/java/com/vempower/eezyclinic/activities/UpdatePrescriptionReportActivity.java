package com.vempower.eezyclinic.activities;

import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.PDFDetails;
import com.vempower.eezyclinic.APICore.PrescriptionAPIData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.AddPrescriptionReportFragment;
import com.vempower.eezyclinic.fragments.UpdatePrescriptionReportFragment;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;

public class UpdatePrescriptionReportActivity extends AbstractMenuActivity {

    private TextView titleNameTv;
    private  String title;
    private boolean isFromPrescription;

    @Override
    protected void setMyContectntView() {
        super.setMyContectntView();

        PDFDetails data=null;
        Object obj = getObjectFromIntent(getIntent(), ListenerKey.ObjectKey.PDF_DETAILS_OBJECT_KEY);

        if (obj != null && obj instanceof PDFDetails) {
            data = (PDFDetails) obj;

            // showToastMessage("Data :" + data);
        } else {
            showMyAlertDialog("Alert", "Invalid details .Please try again", "Close", true);
            return;
        }

        if (data == null) {
            showMyAlertDialog("Alert", "Invalid details.Please try again", "Close", true);
            return;

        }



        title=Utils.getStringFromResources(R.string.title_activity_update_report_lbl );

        if(data instanceof PrescriptionAPIData)
        {
            title=Utils.getStringFromResources(R.string.title_activity_update_prescription);
            isFromPrescription=true;
        }

        UpdatePrescriptionReportFragment fragment= new UpdatePrescriptionReportFragment();
        fragment.setUploadDocumentType(!isFromPrescription);
        fragment.setPDFDetails(data);
        fragment.setOnSuccuessUpdateListener(new UpdatePrescriptionReportFragment.SuccussUpdateListener() {
            @Override
            public void status(boolean isSuccess) {

                if(isSuccess)
                {
                    setResult(RESULT_OK);
                }
                finish();
            }
        });

        setFragment(fragment);


        //Utils.getStringFromResources(R.string.title_activity_appointments)
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(RESULT_OK);
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
