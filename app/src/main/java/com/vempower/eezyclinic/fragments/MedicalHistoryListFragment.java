package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.MedicalHistoryData;
import com.vempower.eezyclinic.APICore.PrescriptionAPIData;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.APIResponce.MedicalHistoryListAPI;
import com.vempower.eezyclinic.APIResponce.PrescriptionsListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.MedicalHistoryListAdapter;
import com.vempower.eezyclinic.adapters.PrescriptionListAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.MedicalHistoryMapper;
import com.vempower.eezyclinic.mappers.PrescriptionsListMapper;
import com.vempower.eezyclinic.mappers.SaveMedicalHistorySelfMapper;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyEditTextBlackCursorRR;

import com.vempower.eezyclinic.activities.AbstractActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satish on 6/12/17.
 */

public class MedicalHistoryListFragment extends  SwipedRecyclerViewFragment {

    private View fragmentView;

    //List<MedicalHistoryData>

    private MedicalHistoryListAdapter adapter;
    private List<MedicalHistoryData> medicalHistoryList;

    private MyEditTextBlackCursorRR medical_history_et;
    private TextView save_tv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.medical_history_list, container, false);

        setupSwipeRefreshLayout(fragmentView);
        init();
        return fragmentView;
    }

    private void init() {
        adapter = null;
        medicalHistoryList= new ArrayList<>();

        medical_history_et =getFragemtView(). findViewById(R.id.medical_history_et);
        save_tv = getFragemtView().findViewById(R.id.save_tv);

        //medical_history_et.setText(data.getContent());
        medical_history_et.setSelection(medical_history_et.getText().toString().length());

        save_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Utils.showToastMsg("Now click on Save");
              String historyData=  medical_history_et.getText().toString();

              if(TextUtils.isEmpty(historyData))
              {
                  Utils.showToastMsg(R.string.please_enter_valid_medical_history_lbl);
                  return;
              }
              callAddNewMedicalHistoryMapper(historyData);


            }
        });

    }

    private void callAddNewMedicalHistoryMapper(String historyStr) {
        SaveMedicalHistorySelfMapper mapper= new SaveMedicalHistorySelfMapper(historyStr);
        mapper.setOnSaveMedicalHistorySelfListener(new SaveMedicalHistorySelfMapper.SaveMedicalHistorySelfListener() {
            @Override
            public void saveMedicalHistorySelf(AbstractResponse abstractResponse, String errorMessage) {
                if(!isValidResponse(abstractResponse,errorMessage))
                {
                    return;
                }
                //Utils.showToastMsg(abstractResponse.getStatusMessage());
                showAlertDialog("Success",abstractResponse.getStatusMessage(),false);
                medical_history_et.setText(null);
                fromTopScroll();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callMedicalHistoryListMapper();
    }

    private void callMedicalHistoryListMapper() {


        MedicalHistoryMapper mapper= new MedicalHistoryMapper();
        mapper.setOnMedicalHistoryListener(new MedicalHistoryMapper.MedicalHistoryListener() {
            @Override
            public void getMedicalHistory(MedicalHistoryListAPI historyListAPI, String errorMessage) {
                if (!isValidResponse(historyListAPI, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_medical_history_list_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()). finish();
                        }

                        @Override
                        public void retryClick() {
                            callMedicalHistoryListMapper();
                        }
                    });
                    return;
                }

                if(historyListAPI.getData()!=null )
                {
                    medicalHistoryList.addAll(historyListAPI.getData());
                }
                setPrescriptionListToAdapter(medicalHistoryList);
                // Utils.showToastMessage(searchResultDoctorListAPI.toString());
            }
        });

    }


    @Override
    View getFragemtView() {
        return fragmentView;
    }


    @Override
    protected boolean isCheckTabletOrNot() {
        return false;
    }

    @Override
    protected void fromTopScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }
        medicalHistoryList= new ArrayList<>();
        callMedicalHistoryListMapper();

    }



    @Override
    protected void fromBottomScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }

    }


    public void setPrescriptionListToAdapter(List<MedicalHistoryData> medicalHistoryList) {
        hideProgressView();

        if (adapter == null) {

            adapter = new MedicalHistoryListAdapter(medicalHistoryList);

            recyclerView.setAdapter(adapter);
        } else {
            adapter.setUpdatedList(medicalHistoryList);
        }
        if(medicalHistoryList==null || medicalHistoryList.size()==0)
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.VISIBLE);
        }else
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.GONE);
        }



    }

}
