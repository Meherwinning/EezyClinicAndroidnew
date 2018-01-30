package com.vempower.eezyclinic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vempower.eezyclinic.APICore.HelathReportsData;
import com.vempower.eezyclinic.APICore.PrescriptionAPIData;
import com.vempower.eezyclinic.APIResponce.HelathReportsListAPI;
import com.vempower.eezyclinic.APIResponce.PrescriptionsListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.AddModifyPrescriptionActivity;
import com.vempower.eezyclinic.adapters.PrescriptionListAdapter;
import com.vempower.eezyclinic.adapters.ReportsListAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.PrescriptionsListMapper;
import com.vempower.eezyclinic.mappers.ReportsListMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.stashdealcustomer.activities.AbstractActivity;

import java.util.ArrayList;
import java.util.List;

public class ReportsFragment extends  SwipedRecyclerViewFragment  {

    public static final String TITLE = "Reports";
    private static  ReportsFragment fragment;
    private View fragmentView;
    private ReportsListAdapter adapter;
    private List<HelathReportsData> reportsDataList;

    public static ReportsFragment newInstance() {

       /* if(fragment==null)
        {
            fragment= new ReportsFragment();
        }*/
        return new ReportsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_reports, container, false);


        setupSwipeRefreshLayout(fragmentView);
        init();
        return fragmentView;
    }





    private void init() {

        // match_found_tv=  fragmentView.findViewById(R.id.match_found_tv);
        // match_found_tv.setText("0");
        adapter = null;
        reportsDataList= new ArrayList<>();

        fragmentView.findViewById(R.id.add_new_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyApplication.getCurrentActivityContext(),AddModifyPrescriptionActivity.class);
                intent.putExtra(Constants.Pref.IS_FROM_ADD_PRESCRIPTION_KEY,false);
                startActivity(intent);

            }
        });



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callReportListMapper();
    }


    private void callReportListMapper()
    {

        ReportsListMapper mapper= new ReportsListMapper();

        mapper.setOnHelathReportsListListener(new ReportsListMapper.HelathReportsListListener() {
            @Override
            public void getHelathReportsListAPI(HelathReportsListAPI helathReportsListAPI, String errorMessage) {
                if (!isValidResponse(helathReportsListAPI, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_reports_list_lbl), new ApiErrorDialogInterface() {
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

                if(helathReportsListAPI.getData()!=null )
                {
                    reportsDataList.addAll(helathReportsListAPI.getData());
                }
                setPrescriptionListToAdapter(reportsDataList);
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
        reportsDataList= new ArrayList<>();
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

    public void setPrescriptionListToAdapter(List<HelathReportsData> reportsDataList) {
        hideProgressView();

        if (adapter == null) {

            adapter = new ReportsListAdapter(reportsDataList);

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