package com.vempower.eezyclinic.activities;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.Appointment;
import com.vempower.eezyclinic.APICore.ReScheduleAppointmentRequestDetails;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.ReScheduleAppointmentTimeSlotFragment;
import com.vempower.eezyclinic.fragments.ScheduleAppointmentTimeSlotFragment;

public class ReScheduleAppointmentActivity extends AbstractMenuActivity {


    private ReScheduleAppointmentRequestDetails data;


    @Override
    protected void setMyContectntView() {
        super.setMyContectntView();
        myInit();
    }

    private void myInit() {



        Object obj = getObjectFromIntent(getIntent(), ListenerKey.ObjectKey.RESCHEDULE_APPOINTMENT_OBJECT_KEY);

        if (obj != null && obj instanceof ReScheduleAppointmentRequestDetails) {
            data = (ReScheduleAppointmentRequestDetails) obj;

            // showToastMessage("Data :" + data);
        } else {
            showMyAlertDialog("Alert", "Invalid Doctor profile.Please try again", "Close", true);
            return;
        }

        if (data == null) {
            showMyAlertDialog("Alert", "Invalid Doctor profile.Please try again", "Close", true);
            return;

        }


      //  myView = findViewById(R.id.header_view_linear);
        // myInit();
        //callDoctorProfileMapper(data);

        ReScheduleAppointmentTimeSlotFragment fragment= new ReScheduleAppointmentTimeSlotFragment();
        fragment.setReScheduleDetails(data);
        setFragment(fragment);


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

    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView titleNametv = toolbar.findViewById(R.id.title_logo_tv);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        // titleName.setText(Utils.getStringFromResources(R.string.title_activity_appointments));
        titleNametv.setText("Re-schedule your Appointment");

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
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}
