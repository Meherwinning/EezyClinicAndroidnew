package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.SearchResultClinicData;
import com.vempower.eezyclinic.APIResponce.SearchResultClinicListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.ClinicListAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.core.SearchRequest;
import com.vempower.eezyclinic.interfaces.SearchResultListener;
import com.vempower.eezyclinic.mappers.SearchResultClinicListMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;

import java.util.ArrayList;
import java.util.List;

;

/**
 * Created by satish on 6/12/17.
 */

public class SearchClinicListFragment extends SwipedRecyclerViewFragment {

    private View fragmentView;
    private ClinicListAdapter adapter;
    private ArrayList<SearchResultClinicData> clinicList;
    private SearchRequest requestParms;

    private boolean isOnlyViewList;
    private SearchResultListener onSearchResultListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.search_doctor_list, container, false);

        setupSwipeRefreshLayout(fragmentView);

        if(isOnlyViewList)
        {
            viewList();
        }else
        {
            refreshList();
        }
        isOnlyViewList=true;

        return fragmentView;
    }

    public ArrayList<SearchResultClinicData> getClinicList() {
        return clinicList;
    }

    public void isViewOnlyList(boolean isOnlyViewList)
    {
        this.isOnlyViewList=isOnlyViewList;
    }

    private void init() {
        adapter=null;
        clinicList= new ArrayList<>();
        requestParms = MyApplication.getInstance().getSearchRequestParms();
        if(requestParms==null)
        {
            requestParms= new SearchRequest(Constants.RESULT_PAGE_ITEMS_LIMIT1);
        }
        requestParms.setPage("1");

        callSearchResultDoctorsListMapper();
    }

    private void viewList()
    {
        adapter=null;
        setOrderItemsToAdapter(clinicList);
    }

    private void refreshList()
    {
        init();

    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }

    @Override
    protected boolean isCheckTabletOrNot() {
        return false;
    }



    private void callSearchResultDoctorsListMapper()
    {

        SearchResultClinicListMapper mapper=new SearchResultClinicListMapper(requestParms);

        mapper.setOnSearchResultClinicListAPItListener(new SearchResultClinicListMapper.SearchResultClinicListAPItListener() {
            @Override
            public void getSearchResultClinicListAPI(SearchResultClinicListAPI searchResultClinicListAPI, String errorMessage) {
                if(!isValidResponse(searchResultClinicListAPI,errorMessage))
                {
                    return;
                }
                if(!(requestParms.getPage().equalsIgnoreCase("1")) && (searchResultClinicListAPI.getData()==null ||searchResultClinicListAPI.getData().size()==0) )
                {
                    Utils.showToastMsg(R.string.no_more_clinic_found_lbl);
                    return;

                }
                if(searchResultClinicListAPI.getData()!=null )
                {
                    clinicList.addAll(searchResultClinicListAPI.getData());
                }
                // Utils.showToastMessage(searchResultDoctorListAPI.toString());
                setOrderItemsToAdapter(clinicList);
            }
        });

      /*  mapper.setOnSearchResultClinicListAPItListener(new SearchResultDoctorsListMapper.SearchResultDoctorListAPItListener() {
            @Override
            public void getSearchResultDoctorListAPI(SearchResultDoctorListAPI searchResultDoctorListAPI, String errorMessage) {
                if(!isValidResponse(searchResultDoctorListAPI,errorMessage))
                {
                    return;
                }
                if(!(requestParms.getPage().equalsIgnoreCase("1")) && (searchResultDoctorListAPI.getData()==null ||searchResultDoctorListAPI.getData().size()==0) )
                {
                    Utils.showToastMsg(R.string.no_more_doctors_found_lbl);
                    return;

                }
                if(searchResultDoctorListAPI.getData()!=null )
                {
                    clinicList.addAll(searchResultDoctorListAPI.getData());
                }
               // Utils.showToastMessage(searchResultDoctorListAPI.toString());
                setOrderItemsToAdapter(clinicList);
            }
        });*/
    }

    public void setOrderItemsToAdapter(List<SearchResultClinicData> orders) {
        hideProgressView();
        ((TextView)fragmentView.findViewById(R.id.match_found_tv)).setText(orders.size()+"");

        if (onSearchResultListener != null) {
            onSearchResultListener.result(orders.size());
        }
        if (adapter == null) {

            adapter = new ClinicListAdapter(orders);

            recyclerView.setAdapter(adapter);
        } else {
            adapter.setUpdatedList(orders);
        }

        if(orders==null || orders.size()==0)
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.VISIBLE);
        }else
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.GONE);
        }



    }

    @Override
    protected void fromTopScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }
        clinicList= new ArrayList<>();
        requestParms.setPage("1");
        callSearchResultDoctorsListMapper();

    }

    @Override
    protected void fromBottomScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }
        requestParms.setPage((Integer.parseInt(requestParms.getPage())+1)+"");
        callSearchResultDoctorsListMapper();
    }

    public void setOnSearchResultListener(SearchResultListener onSearchResultListener) {
        this.onSearchResultListener = onSearchResultListener;
    }
}
