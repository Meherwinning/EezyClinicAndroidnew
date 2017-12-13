package com.vempower.eezyclinic.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Satishk on 9/27/2017.
 */

public class FullToRefreshGridLayoutManager extends android.support.v7.widget.GridLayoutManager {

    private static final long DELAY_TIME = 500;
    private boolean isBottomUp=true;

    private FullToRefreshLinerLayoutManager.MyScrollListener listener;

    public FullToRefreshGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FullToRefreshGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public FullToRefreshGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }


    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scrollRange = super.scrollVerticallyBy(dy, recycler, state);

        int overscroll = dy - scrollRange;
        if (overscroll > 0) {

            if(listener!=null )
            {
                if(isBottomUp) {
                    isBottomUp=false;
                    autoCallback();
                    listener.fromBottomScroll();
                }

            }

            // bottom overscroll
        } else if (overscroll < 0) {
            // top overscroll
            if(listener!=null)
            {

                if(isBottomUp) {
                    isBottomUp=false;
                    autoCallback();
                    // listener.topScroll();
                }
            }
        }
        return scrollRange;
    }

    public void setOnMyScrollListener(FullToRefreshLinerLayoutManager.MyScrollListener listener)
    {
        this.listener=listener;
    }

    public interface MyScrollListener
    {
        public void fromBottomScroll();
        //public void topScroll();
    }

    private void autoCallback() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isBottomUp = true;
            }
        }, DELAY_TIME);
    }




}
