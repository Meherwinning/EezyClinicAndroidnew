package com.vempower.eezyclinic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vempower.eezyclinic.APICore.PendingFeedbackData;
import com.vempower.eezyclinic.APICore.PrescriptionAPIData;
import com.vempower.eezyclinic.APIResponce.PendingFeedbackListAPI;
import com.vempower.eezyclinic.APIResponce.PrescriptionsListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.AddPrescriptionReportActivity;
import com.vempower.eezyclinic.adapters.PendingFeedbackListAdapter;
import com.vempower.eezyclinic.adapters.PrescriptionListAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.PendingFeedbackListMapper;
import com.vempower.eezyclinic.mappers.PrescriptionsListMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.activities.AbstractActivity;

import java.util.ArrayList;
import java.util.List;

public class PendingFeedbackFragment extends  SwipedRecyclerViewFragment {

    public static final String TITLE = "Pending";
    private View fragmentView;
    private PendingFeedbackListAdapter adapter;
    private List<PendingFeedbackData> pendingFeedbackList;

    public static PendingFeedbackFragment newInstance() {

       /* if(fragment==null)
        {
            fragment= new PrescriptionsFragment();
        }*/
        return new PendingFeedbackFragment();
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
        pendingFeedbackList= new ArrayList<>();
        //AddModifyPrescriptionActivity



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callPrescriptionListMapper();
    }



    private void callPrescriptionListMapper()
    {

        PendingFeedbackListMapper mapper= new PendingFeedbackListMapper();

        mapper.setOnPendingFeedbackListListener(new PendingFeedbackListMapper.PendingFeedbackListListener() {
            @Override
            public void getPendingFeedbackListAPI(PendingFeedbackListAPI feedbackListAPI, String errorMessage) {
                if (!isValidResponse(feedbackListAPI, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_pending_feedback_list_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()). finish();
                        }

                        @Override
                        public void retryClick() {
                            callPrescriptionListMapper();
                        }
                    });
                    return;
                }

                if(feedbackListAPI.getData()!=null )
                {
                    pendingFeedbackList.addAll(feedbackListAPI.getData());
                }
                setPrescriptionListToAdapter(pendingFeedbackList);
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
        pendingFeedbackList= new ArrayList<>();
       // requestParms.setPage("1");
        callPrescriptionListMapper();

    }

    @Override
    protected void fromBottomScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }
      /*  requestParms.setPage((Integer.parseInt(requestParms.getPage())+1)+"");
        callPrescriptionListMapper();*/

    }

    public void setPrescriptionListToAdapter(List<PendingFeedbackData> prescriptionList) {
        hideProgressView();

        if (adapter == null) {

            adapter = new PendingFeedbackListAdapter(prescriptionList);

            recyclerView.setAdapter(adapter);
        } else {
            adapter.setUpdatedList(prescriptionList);
        }
        if(prescriptionList==null || prescriptionList.size()==0)
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.VISIBLE);
        }else
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.GONE);
        }



    }



}