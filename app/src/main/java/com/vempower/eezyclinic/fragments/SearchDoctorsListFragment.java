package com.vempower.eezyclinic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
 ;

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
    //private  TextView match_found_tv;

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

    public ArrayList<SearchResultDoctorListData> getDoctorsList() {
        return doctorsList;
    }

    public void isViewOnlyList(boolean isOnlyViewList)
    {
        this.isOnlyViewList=isOnlyViewList;
    }

    private void init() {

       // match_found_tv=  fragmentView.findViewById(R.id.match_found_tv);
       // match_found_tv.setText("0");
        adapter=null;
        doctorsList= new ArrayList<>();
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
        ((TextView)fragmentView.findViewById(R.id.match_found_tv)).setText(orders.size()+"");

        if (adapter == null) {

            adapter = new DoctorsListAdapter(getChildFragmentManager(),orders);

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
