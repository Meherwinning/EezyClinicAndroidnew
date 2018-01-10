package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vempower.eezyclinic.APICore.CaseSheetsListDate;
import com.vempower.eezyclinic.APICore.HelathReportsData;
import com.vempower.eezyclinic.APIResponce.CaseSheetsListAPI;
import com.vempower.eezyclinic.APIResponce.HelathReportsListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.CaseSheetsListAdapter;
import com.vempower.eezyclinic.adapters.ReportsListAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.CaseSheetsListMapper;
import com.vempower.eezyclinic.mappers.ReportsListMapper;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.stashdealcustomer.activities.AbstractActivity;

import java.util.ArrayList;
import java.util.List;

public class CasesheetsFragment extends SwipedRecyclerViewFragment {

    public static final String TITLE = "Casesheets";

    private View fragmentView;
    private CaseSheetsListAdapter adapter;
    private List<CaseSheetsListDate> caseSheetsList;

    public static CasesheetsFragment newInstance() {


        return  new CasesheetsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragmentView =  inflater.inflate(R.layout.fragment_casesheets, container, false);


        setupSwipeRefreshLayout(fragmentView);
        init();
        return fragmentView;
    }





    private void init() {

        // match_found_tv=  fragmentView.findViewById(R.id.match_found_tv);
        // match_found_tv.setText("0");
        adapter = null;
        caseSheetsList= new ArrayList<>();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callCaseSheetsListMapper();
    }


    private void callCaseSheetsListMapper()
    {

        CaseSheetsListMapper listMapper= new CaseSheetsListMapper();
        listMapper.setOnCaseSheetsListListener(new CaseSheetsListMapper.CaseSheetsListListener() {
            @Override
            public void getCaseSheetsListAPI(CaseSheetsListAPI caseSheetsListAPI, String errorMessage) {


                if (!isValidResponse(caseSheetsListAPI, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_casesheets_list_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()). finish();
                        }

                        @Override
                        public void retryClick() {
                            callCaseSheetsListMapper();
                        }
                    });
                    return;
                }

                if(caseSheetsListAPI.getData()!=null )
                {
                    caseSheetsList.addAll(caseSheetsListAPI.getData());
                }
                setPrescriptionListToAdapter(caseSheetsList);

            }
        });

        ReportsListMapper mapper= new ReportsListMapper();

        mapper.setOnHelathReportsListListener(new ReportsListMapper.HelathReportsListListener() {
            @Override
            public void getHelathReportsListAPI(HelathReportsListAPI helathReportsListAPI, String errorMessage) {

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
        caseSheetsList= new ArrayList<>();
        // requestParms.setPage("1");
        callCaseSheetsListMapper();

    }

    @Override
    protected void fromBottomScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }
      /*  requestParms.setPage((Integer.parseInt(requestParms.getPage())+1)+"");
        callPrescriptionListMapper();*/

    }

    public void setPrescriptionListToAdapter(List<CaseSheetsListDate> caseSheetsList) {
        hideProgressView();

        if (adapter == null) {

            adapter = new CaseSheetsListAdapter(caseSheetsList);

            recyclerView.setAdapter(adapter);
        } else {
            adapter.setUpdatedList(caseSheetsList);
        }
        if(caseSheetsList==null || caseSheetsList.size()==0)
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.VISIBLE);
        }else
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.GONE);
        }



    }


}