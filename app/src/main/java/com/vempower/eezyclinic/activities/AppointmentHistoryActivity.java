package com.vempower.eezyclinic.activities;

import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.Appointment;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.AppointmentHistoryFragment;
import com.vempower.eezyclinic.fragments.AppointmentHistoryListFragment;

public class AppointmentHistoryActivity extends AbstractMenuActivity {

    private Appointment appointment;

    @Override
    protected void setMyContectntView() {
        super.setMyContectntView();

        myInit();
    }

    private void myInit() {
        Object obj = getObjectFromIntent(getIntent(), ListenerKey.ObjectKey.APPOINTMENT_OBJECT_KEY);

        if (obj != null && obj instanceof Appointment) {
            appointment = (Appointment) obj;

            // showToastMessage("Data :" + data);
        } else {
            showMyAlertDialog("Alert", "Invalid Appointment details.Please try again", "Close", true);
            return;
        }

        if (appointment == null) {
            showMyAlertDialog("Alert", "Invalid Appointment details .Please try again", "Close", true);
            return;

        }

        AppointmentHistoryFragment fragment= new AppointmentHistoryFragment();
        fragment.setAppointment(appointment);
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
      //  callDashboard();
        finish();
    }
    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView titleName = toolbar.findViewById(R.id.title_logo_tv);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        titleName.setText("My Appointments History");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something you want
               // callDashboard();
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


}
