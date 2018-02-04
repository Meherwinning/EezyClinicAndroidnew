package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vempower.eezyclinic.APIResponce.SearchFamilyMemberAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.mappers.SearchFamilyMemberMapper;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyEditTextBlackCursorRR;

/**
 * Created by satish on 6/12/17.
 */

public class AddFamilyMemberFragment extends AbstractFragment {

    private MyEditTextBlackCursorRR patient_id_et;
    private AppCompatButton submit_bt;

    private View fragmentView;

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

        submit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String patientId = patient_id_et.getText().toString();

                if (TextUtils.isEmpty(patientId)) {
                    Utils.showToastMessage(Utils.getStringFromResources(R.string.please_enter_valid_patient_id_lbl));
                    return;
                }
                Utils.showToastMessage("PatientId :" + patientId);
                callSearchFamilyMemberMapper(patientId);

            }


        });

    }

    private void callSearchFamilyMemberMapper(String patientId) {
        SearchFamilyMemberMapper mapper = new SearchFamilyMemberMapper(patientId);
        mapper.setOnSearchFamilyMemberListener(new SearchFamilyMemberMapper.SearchFamilyMemberListener() {
            @Override
            public void searchFamilyMember(SearchFamilyMemberAPI memberAPI, String errorMessage) {
                if (memberAPI != null) {

                    if (!TextUtils.isEmpty(memberAPI.getStatusMessage()) && memberAPI.getStatusMessage().equalsIgnoreCase("Success")) {
                        memberAPI.setStatusCode("1");
                    }

                }
                if (!isValidResponse(memberAPI, errorMessage, true, false)) {
                    return;
                }
                if (memberAPI.getData() == null) {
                    showAlertDialog("Alert", Utils.getStringFromResources(R.string.inbvalid_family_member_details_lbl), false);
                    return;
                }

                Utils.showToastMessage(memberAPI.getData().toString());
            }
        });
    }


    @Override
    View getFragemtView() {
        return fragmentView;
    }
}
