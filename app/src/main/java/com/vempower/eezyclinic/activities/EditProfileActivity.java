package com.vempower.eezyclinic.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.PatientProfileData;
import com.vempower.eezyclinic.APICore.SearchResultClinicData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.AppointmentListFragment;
import com.vempower.eezyclinic.fragments.EditProfileFragment;
import com.vempower.eezyclinic.fragments.MyProfileFragment;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.activities.AbstractActivity;

import static com.vempower.eezyclinic.fragments.ImageProcessFragment.CODE_WRITE_SETTINGS_PERMISSION;

public class EditProfileActivity extends AbstractMenuActivity {


    public static final int IS_REFRESH_PROFILE = 7368;
    private EditProfileFragment fragment;
    private PatientProfileData data;

    @Override
    protected void setMyContectntView() {
        super.setMyContectntView();
       // setContentView(R.layout.activity_menu_clinic_profile_layout);
        // myInit();


        Object obj = getObjectFromIntent(getIntent(), ListenerKey.ObjectKey.PATIENT_PROFILE_OBJECT_KEY);

        if (obj!=null && obj instanceof PatientProfileData) {
            data = (PatientProfileData) obj;
            // showToastMessage("Data :" + data);
        }else
        {
            showMyAlertDialog("Alert","Invalid Patient profile.Please try again","Close",true);
            return;
        }

        if(data==null)
        {
            showMyAlertDialog("Alert","Invalid Patient profile.Please try again","Close",true);
            return;

        }
       // showToastMessage(data.toString());
        // computeHeaderHeight();

       // callClinicProfileMapper(data);

        fragment= new EditProfileFragment();

        fragment.isFromEditMode(true);
        fragment.setPatientProfileObj(data);

        fragment.setOnSuccessToUpdateProfileListener(new EditProfileFragment.SuccessToUpdateProfileListener() {
            @Override
            public void success() {
                setResult(RESULT_OK);
                finish();
            }
        });

        setFragment(fragment);





    }

    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.profile_edit_menu, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_profile_save:
                 Utils.showToastMsg("Profile save click");
                 if(fragment!=null) {
                     fragment.saveButtonClickListener.saveButtonClick();
                 }

               /* Intent intent= getIntent();
                intent.setClass(this,EditProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);*/
                break;
        }

        return super.onOptionsItemSelected(item);
    }



    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView titleName = toolbar.findViewById(R.id.title_logo_tv);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
       // titleName.setText(Utils.getStringFromResources(R.string.title_activity_appointments));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something you want
                setResult(RESULT_OK);
                finish();
            }
        });
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);
        //mImageView = findViewById(R.id.image);
        //View  mToolbarView = findViewById(R.id.toolbar);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        getSupportActionBar().setTitle("Profile");
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
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // super.onRequestPermissionsResult(requestCode, permissions,grantResults);
        switch (requestCode) {
            case CODE_WRITE_SETTINGS_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        String permission = permissions[i];

                        if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                                    Log.v("Permission", Manifest.permission.WRITE_EXTERNAL_STORAGE + "  Permission is revoked");
                                    //ActivityCompat.requestPermissions(this, new String[]{per}, PERMISSION_REQUEST_CODE);
                                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_WRITE_SETTINGS_PERMISSION);
                                    return;
                                }
                            } else {
                                showAlertDialog(Utils.getStringFromResources(R.string.alert_lbl), Utils.getStringFromResources(R.string.write_on_sd_card_permission_required_msg), false);
                            }
                        }

                        if (Manifest.permission.CAMERA.equals(permission)) {
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                                    Log.v("Permission", Manifest.permission.CAMERA + "  Permission is revoked");
                                    //ActivityCompat.requestPermissions(this, new String[]{per}, PERMISSION_REQUEST_CODE);
                                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CODE_WRITE_SETTINGS_PERMISSION);
                                    return;
                                }
                            } else {
                                showAlertDialog(Utils.getStringFromResources(R.string.alert_lbl), Utils.getStringFromResources(R.string.camera_permission_required_msg), false);
                            }
                        }

                    }
                }
                break;
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(RESULT_OK);
    }
}
