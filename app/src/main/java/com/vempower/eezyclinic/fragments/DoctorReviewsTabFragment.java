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
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.vempower.eezyclinic.APICore.DoctorProfileData;
import com.vempower.eezyclinic.APICore.DoctorReview;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.DoctorsListAdapter;
import com.vempower.eezyclinic.adapters.ReviewsListAdapter;
import com.vempower.eezyclinic.delegate.AbsListViewDelegate;
import com.vempower.eezyclinic.delegate.RecyclerViewDelegate;
import com.vempower.eezyclinic.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satish on 18/12/17.
 */

public class DoctorReviewsTabFragment extends SwipedRecyclerViewFragment1 implements AbsListView.OnItemClickListener {

    /*private ListView mListView;
    private ListAdapter mAdapter;*/
    private RecyclerViewDelegate recyclerViewDelegate = new RecyclerViewDelegate();
    private ReviewsListAdapter adapter;
    private DoctorProfileData doctorProfileData;

    public DoctorReviewsTabFragment()
    {
        Bundle args = new Bundle();
        args.putInt(BUNDLE_FRAGMENT_INDEX, 1);
        setArguments(args);
    }

    public static DoctorReviewsTabFragment getInstance(final DoctorProfileData profileData) {
        DoctorReviewsTabFragment fragment = new DoctorReviewsTabFragment();

        fragment.setDoctorProfileData(profileData);
       // Utils.showToastMessage("DoctorReviewsTabFragment");
        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);

        setupSwipeRefreshLayout(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOrderItemsToAdapter();
    }

    public void setOrderItemsToAdapter() {
        hideProgressView();
        ArrayList< DoctorReview > reviews= new ArrayList<>();
        if(doctorProfileData!=null || doctorProfileData.getReviews()!=null || doctorProfileData.getReviews().size()!=0)
        {
            reviews.addAll(doctorProfileData.getReviews());
        }

       // if (adapter == null) {
       // String[] listArrays = getResources().getStringArray(R.array.countries);

            adapter = new ReviewsListAdapter(reviews);

            recyclerView.setAdapter(adapter);
       /* } else {
            adapter.setUpdatedList(orders);
        }*/


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public boolean isViewBeingDragged(MotionEvent event) {
        return recyclerViewDelegate.isViewBeingDragged(event, recyclerView);
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

    }

    @Override
    protected void fromBottomScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }

    }

    public void setDoctorProfileData(DoctorProfileData doctorProfileData) {
        this.doctorProfileData = doctorProfileData;
    }
}