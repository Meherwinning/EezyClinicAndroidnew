package com.vempower.eezyclinic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gc.materialdesign.views.ButtonFloat;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.APIResponce.SearchResultDoctorListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.MapDoctorsActivity;
import com.vempower.eezyclinic.adapters.DoctorsListAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.core.SearchRequest;
import com.vempower.eezyclinic.mappers.SearchResultDoctorsListMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satish on 6/12/17.
 */

public class SearchDoctorsListFragment extends SwipedRecyclerViewFragment {

    private View fragmentView;
    private DoctorsListAdapter adapter;
    private ArrayList<SearchResultDoctorListData> doctorsList;
    private SearchRequest requestParms;

    private boolean isOnlyViewList;
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

        return fragmentView;
    }

    public ArrayList<SearchResultDoctorListData> getDoctorsList() {
        return doctorsList;
    }

    public void isViewOnlyList(boolean isOnlyViewList)
    {
        this.isOnlyViewList=isOnlyViewList;
    }

    private void init() {
        adapter=null;
        doctorsList= new ArrayList<>();
        requestParms = MyApplication.getInstance().getSearchRequestParms();
        if(requestParms==null)
        {
            requestParms= new SearchRequest(Constants.RESULT_PAGE_ITEMS_LIMIT);
        }

        callSearchResultDoctorsListMapper();
    }

    private void viewList()
    {
        adapter=null;
        setOrderItemsToAdapter(doctorsList);
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
        SearchResultDoctorsListMapper mapper=new SearchResultDoctorsListMapper(requestParms);

        mapper.setOnSearchResultDoctorListAPItListener(new SearchResultDoctorsListMapper.SearchResultDoctorListAPItListener() {
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
                    doctorsList.addAll(searchResultDoctorListAPI.getData());
                }
               // Utils.showToastMessage(searchResultDoctorListAPI.toString());
                setOrderItemsToAdapter(doctorsList);
            }
        });
    }

    public void setOrderItemsToAdapter(List<SearchResultDoctorListData> orders) {
        hideProgressView();


        if (adapter == null) {

            adapter = new DoctorsListAdapter(orders);

            recyclerView.setAdapter(adapter);
        } else {
            adapter.setUpdatedList(orders);
        }


    }

    @Override
    protected void fromTopScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }
        doctorsList= new ArrayList<>();
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
}
