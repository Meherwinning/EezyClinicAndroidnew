package com.vempower.eezyclinic.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.SearchFamilyMemberData;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.APIResponce.SearchFamilyMemberAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.mappers.SearchFamilyMemberMapper;
import com.vempower.eezyclinic.mappers.SendRequestFamilyMemberAddMapper;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyEditTextBlackCursor;
 ;

/**
 * Created by satish on 6/12/17.
 */

public class AddFamilyMemberFragment extends AbstractFragment {

    private MyEditTextBlackCursor patient_id_et;
    private AppCompatButton submit_bt;
    private LinearLayout patient_details_linear;
    private TextView family_member_patient_name;
    private boolean isSendRequest;

    private View fragmentView;
    private SearchFamilyMemberData memberData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.add_family_member_layout, container, false);

        myInit();
        return fragmentView;
    }

    private void myInit() {
        patient_id_et = getFragemtView().findViewById(R.id.patient_id_et);
        submit_bt = getFragemtView().findViewById(R.id.submit_bt);

        family_member_patient_name = getFragemtView().findViewById(R.id.family_member_patient_name);
        patient_details_linear = getFragemtView().findViewById(R.id.patient_details_linear);


        submit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSendRequest) {
                    if(memberData==null)
                    {
                        return;
                    }

                    callSendRequestFamilyMember();

                } else {

                    String patientId = patient_id_et.getText().toString();

                    if (TextUtils.isEmpty(patientId)) {
                        Utils.showToastMessage(Utils.getStringFromResources(R.string.please_enter_valid_patient_id_lbl));
                        return;
                    }
                   // Utils.showToastMessage("PatientId :" + patientId);
                    callSearchFamilyMemberMapper(patientId);
                }
            }
        });
        refreshLayout();

    }

    private void callSendRequestFamilyMember() {
        SendRequestFamilyMemberAddMapper mapper= new SendRequestFamilyMemberAddMapper(memberData.getId());
        mapper.setOnSendFamilyMemberAddRequestListener(new SendRequestFamilyMemberAddMapper.SendFamilyMemberAddRequestListener() {
            @Override
            public void sendRequest(AbstractResponse response, String errorMessage) {
                if (!isValidResponse(response, errorMessage, true, false)) {
                    return;
                }
                showAlertDialog("Success", response.getStatusMessage(), true);

            }
        });
    }

    private void callSearchFamilyMemberMapper(String patientId) {
        SearchFamilyMemberMapper mapper = new SearchFamilyMemberMapper(patientId);
        mapper.setOnSearchFamilyMemberListener(new SearchFamilyMemberMapper.SearchFamilyMemberListener() {
            @Override
            public void searchFamilyMember(SearchFamilyMemberAPI memberAPI, String errorMessage) {
               /* if (memberAPI != null) {

                    if (!TextUtils.isEmpty(memberAPI.getStatusMessage()) && memberAPI.getStatusMessage().equalsIgnoreCase("Success")) {
                        memberAPI.setStatusCode("1");
                    }

                }*/
                if (!isValidResponse(memberAPI, errorMessage, true, false)) {
                    return;
                }
                if (memberAPI.getData() == null) {
                    showAlertDialog("Alert", Utils.getStringFromResources(R.string.inbvalid_family_member_details_lbl), false);
                    return;
                }
                  memberData= memberAPI.getData();

               // Utils.showToastMessage(memberAPI.getData().toString());

                isSendRequest=true;
                refreshLayout();
            }
        });
    }

    private void refreshLayout()
    {
        if(isSendRequest)
        {
            submit_bt.setText(Utils.getStringFromResources(R.string.send_request_lbl));
            patient_details_linear.setVisibility(View.VISIBLE);
            if(memberData!=null)
            {
                family_member_patient_name.setText(memberData.getPatientName());
            }
            patient_id_et.setEnabled(false);
        }else
        {
            submit_bt.setText(Utils.getStringFromResources(R.string.search_btn_lbl));
            patient_details_linear.setVisibility(View.GONE);
            family_member_patient_name.setText("");
            patient_id_et.setEnabled(true);
            memberData=null;
        }
    }




    @Override
    View getFragemtView() {
        return fragmentView;
    }

    public void onBackPressed() {
        if(isSendRequest)
        {
            isSendRequest=false;
            refreshLayout();
        }else
        {
            ((Activity)MyApplication.getCurrentActivityContext()).finish();
        }
    }
}
