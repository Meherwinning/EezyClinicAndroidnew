package com.vempower.eezyclinic.fragments;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;

import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.FixedRecyclerView;
import com.vempower.eezyclinic.views.FullToRefreshGridLayoutManager;
import com.vempower.eezyclinic.views.FullToRefreshLinerLayoutManager;


/**
 * Created by sathishkumar on 9/6/16.
 */
abstract class SwipedRecyclerViewFragment1 extends BaseViewPagerFragment {
    protected SwipeRefreshLayout swipeLayout;
    protected FixedRecyclerView recyclerView;
    protected boolean loading = false;
    protected boolean isFirstLoading;


    protected void setupSwipeRefreshLayout(View view) {
        if (view == null) {
            return;
        }
        swipeLayout = /*(SwipeRefreshLayout) */view.findViewById(R.id.swipeContainer);
        if (swipeLayout == null) {
            return;
        }

        recyclerView= /*(FixedRecyclerView)*/ view.findViewById(R.id.recycler_view);
        if (recyclerView == null) {
            return;
        }

        // recyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getCurrentActivityContext(),RecyclerView.VERTICAL,false));

        checkTabletOrNot();
        // swipeLayout.setEnabled(false);
        // swipeLayout.setVisibility(View.VISIBLE);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                if (!Utils.isNetworkAvailable(MyApplication
                        .getCurrentActivityContext())) {
                    if (MyApplication.getCurrentActivityContext() != null) {
                        Utils.showToastMsgForNetworkNotAvalable();
                    }
                    swipeLayout.setRefreshing(false);
                    return;
                }
                fromTopScroll();


            }

        });
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }
    protected abstract boolean isCheckTabletOrNot();

    private void checkTabletOrNot() {


        if(Utils.isTablet() && isCheckTabletOrNot())
        {
            //Context context, int spanCount, int orientation,boolean reverseLayout
            FullToRefreshGridLayoutManager gridLayoutManager= new FullToRefreshGridLayoutManager(MyApplication.getCurrentActivityContext(),2, RecyclerView.VERTICAL,false);
            gridLayoutManager.setOnMyScrollListener(new FullToRefreshLinerLayoutManager.MyScrollListener() {
                @Override
                public void fromBottomScroll() {
                    // Utils.showToastMessage("Now from bottom scroll "+(page++));
                    SwipedRecyclerViewFragment1.this.fromBottomScroll();
                }

            });
            recyclerView.setLayoutManager(gridLayoutManager);
        }else {
            FullToRefreshLinerLayoutManager layoutManager1 = new FullToRefreshLinerLayoutManager(MyApplication.getCurrentActivityContext(), RecyclerView.VERTICAL, false);
            layoutManager1.setOnMyScrollListener(new FullToRefreshLinerLayoutManager.MyScrollListener() {
                @Override
                public void fromBottomScroll() {
                    // Utils.showToastMessage("Now from bottom scroll "+(page++));
                    SwipedRecyclerViewFragment1.this.fromBottomScroll();
                }

            });
            recyclerView.setLayoutManager(layoutManager1);
        }
    }

    /*protected void showNoRecordFoundTextView(boolean isShow) {
        if (noRecordsFoundRelative == null) {
            return;
        }
        noRecordsFoundRelative.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }*/

    protected void addListenersToRecyclerView() {
        if (recyclerView == null) {
            return;
        }
        isFirstLoading = true;


       /* recyclerView.addOnScrollListener(new OnVerticalScrollListener() {

           *//* @Override
            public void onScrolledToEnd() {
                if(isFirstLoading)
                {
                    isFirstLoading=false;
                    return;
                }
                recylerViewScrollListener();
            }*//*

            @Override
            public void onScrolledUp() {
                super.onScrolledUp();
                recylerViewScrollListener();
            }
        });*/

    }
    abstract protected void fromTopScroll();
    abstract  protected void fromBottomScroll();
   /* abstract protected void onSwipeLayoutSetToDefaultPage();

    abstract  protected void recylerViewScrollListener();*/

}