package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodiebag.pinview.Pinview;
import com.vempower.eezyclinic.APICore.PatientData;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.APIResponce.ChangeMobileNumberAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.ChangeMobileNumberActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.ChangeMobileNumberMapper;
import com.vempower.eezyclinic.mappers.ChangeMobileVerifyOTPMapper;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyEditTextBlackCursorRR;
import com.vempower.stashdealcustomer.activities.AbstractActivity;

/**
 * Created by satish on 6/12/17.
 */

public class ChangeMobileNumberFragment extends AbstractFragment {

    private View fragmentView;
    private MyEditTextBlackCursorRR registed_mobile_num_et,change_mobile_number_et;
    private AppCompatButton bottom_bt;
    private boolean isVerifyOTP;
    private LinearLayout otp_verify_linear;
    private Pinview pinview;
    private String verifyOTP;
    private String patientId;
    private TextView otptv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.change_mobile_number, container, false);


        myInit();
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        compute();
    }

    private void compute() {
        otp_verify_linear.setVisibility(View.GONE);
      final  PatientData patientData = MyApplication.getInstance().getLoggedUserDetailsFromSharedPref();

        registed_mobile_num_et.setEnabled(false);
        if(patientData!=null)
        {
            registed_mobile_num_et.setText(patientData.getMobile());
        }

       /* pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                verifyOTP=pinview.getValue();
                Utils.showToastMsg("fromUser "+fromUser);
               // Toast.makeText(MainActivity.this, pinview.getValue(), Toast.LENGTH_SHORT).show();
            }
        });*/
       /* pinview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOTP="";
            }
        });*/


        bottom_bt.setText("Verify Number");
        bottom_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isVerifyOTP)
                {
                    verifyOTP=pinview.getValue();
                    if(TextUtils.isEmpty(verifyOTP) || verifyOTP.length()<6)
                    {
                        Utils.showToastMsg("Please enter valid OTP");
                        return;
                    }
                    callChangeMobileVerifyOTPMapper();
                   // Utils.showToastMsg("OTP "+verifyOTP);

                }else {
                    String mobileNum = change_mobile_number_et.getText().toString();

                    if (TextUtils.isEmpty(mobileNum)) {
                        Utils.showToastMsg("Please enter valid mobile number");
                        return;
                    }
                    callChangeMobileNumberMapper(mobileNum);
                }

            }

            private void callChangeMobileVerifyOTPMapper() {
                ChangeMobileVerifyOTPMapper mapper= new ChangeMobileVerifyOTPMapper(patientId,verifyOTP);
                mapper.setOnChangeMobileVerifyOTPListener(new ChangeMobileVerifyOTPMapper.ChangeMobileVerifyOTPListener() {
                    @Override
                    public void verifyOTP(AbstractResponse response, String errorMessage) {
                        if((!isValidResponse(response,errorMessage))) {
                            Utils.showToastMsg("Invalid OTP,Please check network connection.");
                            return;
                        }
                        Utils.showToastMsg(response.getStatusMessage());
                        MyApplication.showTransparentDialog();
                       // PatientData data = MyApplication.getInstance().getLoggedUserDetailsFromSharedPref();
                        patientData.setMobile(change_mobile_number_et.getText().toString());
                        MyApplication.getInstance().setLoggedUserDetailsToSharedPref(patientData);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                MyApplication.hideTransaprentDialog();
                                ((AbstractActivity) MyApplication.getCurrentActivityContext()).finish();
                            }
                        },2500);


                    }
                });

            }

            private void callChangeMobileNumberMapper(final String mobileNum) {
                otptv.setText(null);

                ChangeMobileNumberMapper mapper= new ChangeMobileNumberMapper(mobileNum);
                mapper.setOnChangeMobileNumberListener(new ChangeMobileNumberMapper.ChangeMobileNumberListener() {
                    @Override
                    public void changeMobileNumber(ChangeMobileNumberAPI response, String errorMessage) {
                        if((!isValidResponse(response,errorMessage))) {
                            showMyDialog("Alert", com.vempower.eezyclinic.utils.Utils.getStringFromResources(R.string.unable_to_mobile_number_lbl), new ApiErrorDialogInterface() {
                                @Override
                                public void onCloseClick() {

                                    ((AbstractActivity) MyApplication.getCurrentActivityContext()).finish();

                                }

                                @Override
                                public void retryClick() {
                                    callChangeMobileNumberMapper(mobileNum);
                                    //callCahngePasswordMapper(currentPassword,newPassword,rePassword);
                                }
                            });
                            return;
                        }

                        //ChangeMobileVerifyOTPMapper
                        bottom_bt.setText("Confirm");
                        isVerifyOTP=true;
                        change_mobile_number_et.setEnabled(false);
                        otp_verify_linear.setVisibility(View.VISIBLE);
                        patientId=response.getData().getPatientId();
                        otptv.setText("OTP:"+response.getOtp());

                    }

                });

               // ChangeMobileNumberActivity
            }
        });
    }

    private void myInit() {
        verifyOTP="";

        registed_mobile_num_et = getFragemtView().findViewById(R.id.registed_mobile_num_et);
        change_mobile_number_et = getFragemtView().findViewById(R.id. change_mobile_number_et);
        bottom_bt  = getFragemtView().findViewById(R.id.bottom_bt);
        otp_verify_linear = getFragemtView().findViewById(R.id. otp_verify_linear);
        pinview  = getFragemtView().findViewById(R.id. pinview);
        otp_verify_linear.setVisibility(View.GONE);
       otptv= getFragemtView().findViewById(R.id.otp_tv);

    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }
}
