package com.vempower.eezyclinic.activities;

import android.os.Bundle;
import android.app.Activity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.vempower.eezyclinic.APIResponce.HealthChecksListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.HealthChecksFragment;
import com.vempower.eezyclinic.fragments.HealthRecordsFragment;
import com.vempower.eezyclinic.fragments.MedicalRecordsFragment;
import com.vempower.eezyclinic.mappers.HealthCheckListMapper;
import com.vempower.eezyclinic.utils.Utils;

public class HealthChecksActivity extends AbstractMenuActivity {


    @Override
    protected void setMyContectntView() {
        super.setMyContectntView();

        HealthCheckListMapper mapper= new HealthCheckListMapper();
        mapper.setOnHealthChecksListListener(new HealthCheckListMapper.HealthChecksListListener() {
            @Override
            public void getHealthChecksListAPI(HealthChecksListAPI checksListAPI, String errorMessage) {
                if(!isValidResponse(checksListAPI,errorMessage,true,true))
                {
                    return;
                }
                if(checksListAPI.getData()==null)
                {
                    showAlertDialog("Alert",Utils.getStringFromResources(R.string.invalid_health_checks_list_data_lbl),true);
                    return;
                }
                HealthChecksFragment  fragment= new HealthChecksFragment();
                fragment.setHealthChecksData(checksListAPI.getData());
                setFragment(fragment);
            }
        });
    }

    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView titleName = toolbar.findViewById(R.id.title_logo_tv);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        titleName.setText(Utils.getStringFromResources(R.string.title_health_checks));

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
