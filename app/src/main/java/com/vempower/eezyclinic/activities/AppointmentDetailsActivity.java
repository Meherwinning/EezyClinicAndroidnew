package com.vempower.eezyclinic.activities;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.Appointment;
import com.vempower.eezyclinic.APICore.ReScheduleAppointmentRequestDetails;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.AppointmentDetailsFragment;
import com.vempower.eezyclinic.fragments.AppointmentReviewFragment;
import com.vempower.eezyclinic.utils.Constants;

public class AppointmentDetailsActivity extends AbstractMenuActivity {

    public static final int REQUESTCODE = 2343;
    private Appointment data;
    private AppointmentDetailsFragment fragment;

    @Override
    protected void setMyContectntView() {
        super.setMyContectntView();
        myInit();
    }

    private void myInit() {
        Object obj = getObjectFromIntent(getIntent(), ListenerKey.ObjectKey.APPOINTMENT_OBJECT_KEY);

        if (obj != null && obj instanceof Appointment) {
            data = (Appointment) obj;

            // showToastMessage("Data :" + data);
        } else {
            showMyAlertDialog("Alert", "Invalid Appointment details.Please try again", "Close", true);
            return;
        }

        if (data == null) {
            showMyAlertDialog("Alert", "Invalid Appointment details.Please try again", "Close", true);
            return;

        }



        //  myView = findViewById(R.id.header_view_linear);
        // myInit();
        //callDoctorProfileMapper(data);

        fragment= new AppointmentDetailsFragment();
        fragment.setAppointment(data);
       /* fragment.setOnDoneButtonClickListener(new AppointmentReviewFragment.DoneButtonClickListener() {
            @Override
            public void onClick() {
                if(fragment!=null && fragment.isSuccesViewShown())
                {
                    callDashboard();
                    finish();
                }else
                {
                    finish();
                }
            }
        });*/

        //fragment.setSearchResultDoctorListData(data,dateTimeStr);
        setFragment(fragment);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== RESULT_OK)
        {
            if(requestCode==REQUESTCODE)
            {
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    @Override
    protected AbstractFragment getFragment() {
        return null;
    }

    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView titleName = toolbar.findViewById(R.id.title_logo_tv);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        titleName.setText("Appointment Details");

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

   /* @Override
    protected AbstractFragment getFragment() {
        return new AppointmentReviewFragment();
    }
*/
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
