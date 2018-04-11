package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.ChangePasswordMapper;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyEditTextBlackCursor;
import com.vempower.eezyclinic.activities.AbstractActivity;

/**
 * Created by satish on 6/12/17.
 */

public class ChangePasswordFragment extends AbstractFragment {

    private View fragmentView;
    private MyEditTextBlackCursor current_password_et,new_password_et,re_password_et;
    private AppCompatButton change_password_bt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.change_password, container, false);


        myInit();

        return fragmentView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        compute();
    }

    private void compute() {

        change_password_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword= current_password_et.getText().toString();
                String newPassword=  new_password_et.getText().toString();
                String rePassword= re_password_et.getText().toString();
                if(!isValidateInputs(currentPassword,newPassword,rePassword))
                {

                    return;
                }
                callCahngePasswordMapper(currentPassword,newPassword,rePassword);

            }


        });

    }

    private void callCahngePasswordMapper(final String currentPassword,final  String newPassword,final  String rePassword) {
        ChangePasswordMapper mapper= new ChangePasswordMapper(currentPassword,newPassword,rePassword);

        mapper.setOnChangePasswordListener(new ChangePasswordMapper.ChangePasswordListener() {
            @Override
            public void changePassword(AbstractResponse response, String errorMessage) {
                if((!isValidResponse(response,errorMessage))) {
                    showMyDialog("Alert", com.vempower.eezyclinic.utils.Utils.getStringFromResources(R.string.unable_to_change_password_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()).finish();

                        }

                        @Override
                        public void retryClick() {
                            callCahngePasswordMapper(currentPassword,newPassword,rePassword);
                        }
                    });
                    return;
                }
                Utils.showToastMsg(response.getStatusMessage());
                ((AbstractActivity) MyApplication.getCurrentActivityContext()).finish();

            }
        });
    }

    private boolean isValidateInputs(String currentPassword, String newPassword, String rePassword) {
        if(TextUtils.isEmpty(currentPassword))
        {
            Utils.showToastMsg("Please enter valid Current password");
            return false;
        }


        if(TextUtils.isEmpty(newPassword))
        {
            Utils.showToastMsg("Please enter valid new password");
            return false;
        }


        if(TextUtils.isEmpty(rePassword))
        {
            Utils.showToastMsg("Please enter valid re-entered password");
            return false;
        }

        if(!newPassword.equalsIgnoreCase(rePassword))
        {
            Utils.showToastMsg("Mismatch of re-entered password");
            return false;
        }


        return true;

    }

    private void myInit() {
        //MyEditTextBlackCursor
        current_password_et = getFragemtView().findViewById(R.id.current_password_et);

        new_password_et  = getFragemtView().findViewById(R.id.new_password_et);

        re_password_et  = getFragemtView().findViewById(R.id.re_password_et);

        change_password_bt = getFragemtView().findViewById(R.id. change_password_bt);
    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }
}
