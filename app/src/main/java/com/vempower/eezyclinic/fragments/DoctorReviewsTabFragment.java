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

import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.DoctorsListAdapter;
import com.vempower.eezyclinic.adapters.ReviewsListAdapter;
import com.vempower.eezyclinic.delegate.AbsListViewDelegate;
import com.vempower.eezyclinic.delegate.RecyclerViewDelegate;

import java.util.List;

/**
 * Created by satish on 18/12/17.
 */

public class DoctorReviewsTabFragment extends SwipedRecyclerViewFragment1 implements AbsListView.OnItemClickListener {

    /*private ListView mListView;
    private ListAdapter mAdapter;*/
    private RecyclerViewDelegate recyclerViewDelegate = new RecyclerViewDelegate();
    private ReviewsListAdapter adapter;

    public DoctorReviewsTabFragment()
    {
        Bundle args = new Bundle();
        args.putInt(BUNDLE_FRAGMENT_INDEX, 1);
        setArguments(args);
    }
  /*  public static ListViewFragment newInstance(int index) {
        ListViewFragment fragment = new ListViewFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_FRAGMENT_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    public ListViewFragment() {
    }*/

  /*  @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] listArrays = getResources().getStringArray(R.array.countries);
       *//* switch (mFragmentIndex) {
            case 1:
                listArrays = getResources().getStringArray(R.array.countries);
                break;
           *//**//* case 2:
                //listArrays = getResources().getStringArray(R.array.cities);
                listArrays = getResources().getStringArray(R.array.continents);
                break;*//**//*
            default:
                listArrays = getResources().getStringArray(R.array.cities);
                break;
        }*//*
        mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                android.R.id.text1, listArrays);
    }*/

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


       // if (adapter == null) {
        String[] listArrays = getResources().getStringArray(R.array.countries);

            adapter = new ReviewsListAdapter(listArrays);

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
}