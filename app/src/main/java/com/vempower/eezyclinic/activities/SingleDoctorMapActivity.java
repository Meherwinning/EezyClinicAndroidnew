package com.vempower.eezyclinic.activities;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.AbstractMenuActivity;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.AppointmentListFragment;
import com.vempower.eezyclinic.fragments.DoctorsMapFragment;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyTextViewRM;

import java.util.ArrayList;

public class SingleDoctorMapActivity extends AbstractMenuActivity {
    SearchResultDoctorListData data;
    private MyTextViewRM titleName;

    protected void init()
    {
        super.init();

        Object obj = getObjectFromIntent(getIntent(), ListenerKey.ObjectKey.SEARCH_RESULT_DOCTOR_LIST_DATA_KEY);

        if (obj != null && obj instanceof SearchResultDoctorListData) {
            data = (SearchResultDoctorListData) obj;

            // showToastMessage("Data :" + data);
        } else {
            showMyAlertDialog("Alert", "Invalid Doctor profile location details.Please try again", "Close", true);
            return;
        }

        if (data == null) {
            showMyAlertDialog("Alert", "Invalid Doctor profile location details.Please try again", "Close", true);
            return;

        }

        try
        {
           double lat= Double.parseDouble(data.getGoogleMapLatitude());
          double lon=Double.parseDouble(data.getGoogleMapLongitude());
        }catch(Exception e)
        {
            showMyAlertDialog("Alert", "Invalid Doctor profile location .Please try again", "Close", true);
            return;

        }

        callDoctrMapFragemt();



    }

    private void callDoctrMapFragemt() {
        if(titleName!=null) {
            titleName.setText(data.getDoctorName());
        }


        ArrayList<SearchResultDoctorListData> listData= new ArrayList<>();
        listData.add(data);
        DoctorsMapFragment    doctorsMapFragment = new DoctorsMapFragment();
            doctorsMapFragment.setDoctorsList( listData);
            doctorsMapFragment.isViewOnlyList(true);
        doctorsMapFragment.isFinishAfterMarkerClick(true);

        setFragment(doctorsMapFragment);
    }

    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         titleName = toolbar.findViewById(R.id.title_logo_tv);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        titleName.setText("Doctor Name");

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
