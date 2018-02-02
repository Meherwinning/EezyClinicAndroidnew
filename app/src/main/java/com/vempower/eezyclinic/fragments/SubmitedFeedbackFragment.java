package com.vempower.eezyclinic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vempower.eezyclinic.APICore.HelathReportsData;
import com.vempower.eezyclinic.APICore.SubmitedFeedbackListData;
import com.vempower.eezyclinic.APIResponce.HelathReportsListAPI;
import com.vempower.eezyclinic.APIResponce.SubmitedFeedbackListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.AddPrescriptionReportActivity;
import com.vempower.eezyclinic.adapters.ReportsListAdapter;
import com.vempower.eezyclinic.adapters.SubmitedFeedbackListAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.ReportsListMapper;
import com.vempower.eezyclinic.mappers.SubmitedFeedbackListMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.stashdealcustomer.activities.AbstractActivity;

import java.util.ArrayList;
import java.util.List;

public class SubmitedFeedbackFragment extends  SwipedRecyclerViewFragment  {

    public static final String TITLE = "Submitted";
    private static SubmitedFeedbackFragment fragment;
    private View fragmentView;
    private SubmitedFeedbackListAdapter adapter;
    private List<SubmitedFeedbackListData> submitedFeedbackList;

    public static SubmitedFeedbackFragment newInstance() {

       /* if(fragment==null)
        { rentwithtez
            fragment= new ReportsFragment();
        }*/
        return new SubmitedFeedbackFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_feedback, container, false);


        setupSwipeRefreshLayout(fragmentView);
        init();
        return fragmentView;
    }





    private void init() {

        // match_found_tv=  fragmentView.findViewById(R.id.match_found_tv);
        // match_found_tv.setText("0");
        adapter = null;
        submitedFeedbackList= new ArrayList<>();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callReportListMapper();
    }


    private void callReportListMapper(){

        SubmitedFeedbackListMapper mapper= new SubmitedFeedbackListMapper();

        mapper.setOnSubmitedFeedbackListListener(new SubmitedFeedbackListMapper.SubmitedFeedbackListListener() {
            @Override
            public void getSubmitedFeedbackListAPI(SubmitedFeedbackListAPI submitedFeedbackListAPI, String errorMessage) {
                if (!isValidResponse(submitedFeedbackListAPI, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_submited_feedback_list_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()). finish();
                        }

                        @Override
                        public void retryClick() {
                            callReportListMapper();
                        }
                    });
                    return;
                }

                if(submitedFeedbackListAPI.getData()!=null )
                {
                    submitedFeedbackList.addAll(submitedFeedbackListAPI.getData());
                }
                setPrescriptionListToAdapter(submitedFeedbackList);
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
        submitedFeedbackList= new ArrayList<>();
        // requestParms.setPage("1");
        callReportListMapper();

    }

    @Override
    protected void fromBottomScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }
      /*  requestParms.setPage((Integer.parseInt(requestParms.getPage())+1)+"");
        callPrescriptionListMapper();*/

    }

    public void setPrescriptionListToAdapter(List<SubmitedFeedbackListData> reportsDataList) {
        hideProgressView();

        if (adapter == null) {

            adapter = new SubmitedFeedbackListAdapter(reportsDataList);

            recyclerView.setAdapter(adapter);
        } else {
            adapter.setUpdatedList(reportsDataList);
        }
        if(reportsDataList==null || reportsDataList.size()==0)
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.VISIBLE);
        }else
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.GONE);
        }

    }


}