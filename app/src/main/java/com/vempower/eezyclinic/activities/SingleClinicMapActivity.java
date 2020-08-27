package com.vempower.eezyclinic.activities;

import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.SearchResultClinicData;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.ClinicsMapFragment;
import com.vempower.eezyclinic.fragments.DoctorsMapFragment;


import java.util.ArrayList;

public class SingleClinicMapActivity extends AbstractMenuActivity {
    SearchResultClinicData data;
    private TextView titleName;

    protected void init()
    {
        super.init();

        Object obj = getObjectFromIntent(getIntent(), ListenerKey.ObjectKey.SEARCH_RESULT_CLINIC_LIST_DATA_KEY);

        if (obj!=null && obj instanceof SearchResultClinicData) {
            data = (SearchResultClinicData) obj;
            // showToastMessage("Data :" + data);
        }else
        {
            showMyAlertDialog("Alert","Invalid Clinic profile location details.Please try again","Close",true);
            return;
        }

        if(data==null)
        {
            showMyAlertDialog("Alert","Invalid Clinic profile location details.Please try again","Close",true);
            return;

        }

        try
        {
           double lat= Double.parseDouble(data.getGoogle_map_latitude());
          double lon=Double.parseDouble(data.getGoogle_map_latitude());
        }catch(Exception e)
        {
            showMyAlertDialog("Alert", "Invalid Clinic profile location .Please try again", "Close", true);
            return;

        }

        callDoctrMapFragemt();



    }

    private void callDoctrMapFragemt() {
        if(titleName!=null) {
            titleName.setText(data.getClinicName());
        }


        ArrayList<SearchResultClinicData> listData= new ArrayList<>();
        listData.add(data);
        ClinicsMapFragment  clinicsMapFragment = new ClinicsMapFragment();

            clinicsMapFragment.setClinicsList( listData);
            clinicsMapFragment.isViewOnlyList(true);


        clinicsMapFragment.isFinishAfterMarkerClick(true);

        setFragment(clinicsMapFragment);
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
