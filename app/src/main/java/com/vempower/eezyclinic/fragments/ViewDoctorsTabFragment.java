package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.APIResponce.SearchResultDoctorListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.DoctorsListAdapter;
import com.vempower.eezyclinic.adapters.ReviewsListAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.core.SearchRequest;
import com.vempower.eezyclinic.delegate.RecyclerViewDelegate;
import com.vempower.eezyclinic.mappers.SearchResultDoctorsListMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
 ;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satish on 18/12/17.
 */

public class ViewDoctorsTabFragment extends SwipedRecyclerViewFragment1 implements AbsListView.OnItemClickListener {

    /*private ListView mListView;
    private ListAdapter mAdapter;*/
    private RecyclerViewDelegate recyclerViewDelegate = new RecyclerViewDelegate();
    private View fragmentView;
    private DoctorsListAdapter adapter;
    private  ArrayList<SearchResultDoctorListData> doctorsList;

    public static ViewDoctorsTabFragment getInstance(ArrayList<SearchResultDoctorListData> doctorsList1) {
        ViewDoctorsTabFragment fragment = new ViewDoctorsTabFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_FRAGMENT_INDEX, 1);
        fragment.setArguments(args);
        fragment.setDoctorsList(doctorsList1);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.search_doctor_list, container, false);

        setupSwipeRefreshLayout(fragmentView);


            viewList();


        return fragmentView;
    }

    public ArrayList<SearchResultDoctorListData> getDoctorsList() {
        return doctorsList;
    }


    private void init() {
        adapter=null;
        doctorsList= new ArrayList<>();

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





    public void setOrderItemsToAdapter(List<SearchResultDoctorListData> orders) {
        hideProgressView();
        fragmentView.findViewById(R.id.top_linear).setVisibility(View.GONE);

       // (( TextView)fragmentView.findViewById(R.id.match_found_tv)).setText(orders.size()+"");

        if (adapter == null) {

            adapter = new DoctorsListAdapter(orders);

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

    }

    @Override
    protected void fromBottomScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public boolean isViewBeingDragged(MotionEvent event) {
        return recyclerViewDelegate.isViewBeingDragged(event, recyclerView);
    }

    public void setDoctorsList(ArrayList<SearchResultDoctorListData> doctorsList) {
        this.doctorsList = doctorsList;
    }
}