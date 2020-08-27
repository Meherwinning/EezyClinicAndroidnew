package com.vempower.eezyclinic.activities;

import android.content.Intent;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.Appointment;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.AppointmentListFragment;
import com.vempower.eezyclinic.fragments.UpcomingAppointmentListFragment;
import com.vempower.eezyclinic.utils.Utils;

import java.util.List;

public class UpComingAppointmentListActivity extends AbstractMenuActivity {

    public static final int REQUESTCODE = 6234;
    private List<Appointment> list;
    private UpcomingAppointmentListFragment fragment;

    @Override
    protected void setMyContectntView() {
        super.setMyContectntView();
        myInit();
    }

    private void myInit() {



        Object obj = getObjectFromIntent(getIntent(), ListenerKey.ObjectKey.UPCOMING_APPOINTMENT_LIST_DATA_KEY);

        if (obj != null && obj instanceof List) {
            list = (List) obj;

            // showToastMessage("Data :" + data);
        }


        fragment= new  UpcomingAppointmentListFragment() ;
        fragment.setAppointmentList(list);
        setFragment(fragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            if(requestCode==REQUESTCODE && fragment!=null)
            {
                fragment.refreshList();
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
        titleName.setText("Upcoming Appointments");

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // callDashboard();
    }
}
