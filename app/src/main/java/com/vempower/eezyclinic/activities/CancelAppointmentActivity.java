package com.vempower.eezyclinic.activities;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.CancelAppointmentMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
;

public class CancelAppointmentActivity extends AbstractFragmentActivity /*implements MySwitch.OnChangeAttemptListener, CompoundButton.OnCheckedChangeListener*/ {


    public static final String IS_FROM_APPOINTMENT_LIST_KEY = "is_from_appointment_list_key";
    private String appointmentId;

    // private ExpandableLinearLayout expandableLayout_gender_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_appointment);
        setActionBar();
        myInit();
    }

    private void myInit() {

        if(getIntent()==null || !getIntent().hasExtra(Constants.Pref.APPOINTMENT_ID_KEY))
        {
            showMyAlertDialog("Alert", "Invalid Appointment details.Please try again", "Close", true);
            return;

        }

         appointmentId=getIntent().getStringExtra(Constants.Pref.APPOINTMENT_ID_KEY);

        if(TextUtils.isEmpty(appointmentId))
        {
            showMyAlertDialog("Alert", "Invalid Appointment details.Please try again", "Close", true);
            return;

        }



    }



    public void onCancelButtonClick(View view)
    {
        Utils.showToastMsg("Cancel button click");
        String reasonStr= "";

        callCancelAppointmentMapper(reasonStr);
    }

    private void callCancelAppointmentMapper(String reasonStr) {
       CancelAppointmentMapper mapper= new CancelAppointmentMapper(appointmentId,reasonStr);
       mapper.setOnCancelAppointmentListener(new CancelAppointmentMapper.CancelAppointmentListener() {
           @Override
           public void cancelAppointment(AbstractResponse response, String errorMessage) {
               if(!isValidResponse(response,errorMessage,true,false))
               {
                   return;
               }
               showMyDialog("Success", response.getStatusMessage(), "Ok", new ApiErrorDialogInterface() {
                   @Override
                   public void onCloseClick() {

                   }

                   @Override
                   public void retryClick() {
                      /* if(getIntent()!=null && getIntent().getBooleanExtra(CancelAppointmentActivity.IS_FROM_APPOINTMENT_LIST_KEY,false))
                       {*/ setResult(RESULT_OK);
                       finish();
                   }
                      /* else {
                           callDashboard();
                           finish();
                       }*/
                  // }
               });
           }
       });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.close_iv:
                // ProjectsActivity is my 'home' activity
                finish();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setHomeButtonEnabled(true);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView titleName = toolbar.findViewById(R.id.title_logo_tv);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        if (titleName != null)
            titleName.setText("Cancel Appointment");

        ImageView imageView = toolbar.findViewById(R.id.close_iv);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

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


}
