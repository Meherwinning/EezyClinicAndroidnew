package com.vempower.eezyclinic.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.ChangeEmailIdActivity;
import com.vempower.eezyclinic.activities.ChangeMobileNumberActivity;
import com.vempower.eezyclinic.activities.ChangePasswordActivity;
import com.vempower.eezyclinic.activities.PrivacySettingsActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Utils;

/**
 * Created by satish on 6/12/17.
 */

public class SettingsFragment extends AbstractFragment implements  View.OnClickListener {

    private View fragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.settings_layout, container, false);

        myInit();

        return fragmentView;
    }

    private void myInit() {
       getFragemtView().findViewById(R.id.privacy_settings_linear).setOnClickListener(this);
       getFragemtView().findViewById(R.id.change_password_linear).setOnClickListener(this);
       getFragemtView().findViewById(R.id.change_mobile_number_linear).setOnClickListener(this);
       getFragemtView().findViewById(R.id.change_email_id_linear).setOnClickListener(this);
    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.privacy_settings_linear:
                startActivity(new Intent(MyApplication.getCurrentActivityContext(), PrivacySettingsActivity.class));
                 break;
            case R.id.change_password_linear:
               // Utils.showToastMsg("change_password_linear");
                startActivity(new Intent(MyApplication.getCurrentActivityContext(), ChangePasswordActivity.class));

                break;
            case R.id.change_mobile_number_linear:
               // Utils.showToastMsg("change_mobile_number_linear");
                startActivity(new Intent(MyApplication.getCurrentActivityContext(), ChangeMobileNumberActivity.class));

                break;
            case R.id.change_email_id_linear:
               // Utils.showToastMsg("change_email_id_linear");
                startActivity(new Intent(MyApplication.getCurrentActivityContext(), ChangeEmailIdActivity.class));

                break;
        }

    }
}
