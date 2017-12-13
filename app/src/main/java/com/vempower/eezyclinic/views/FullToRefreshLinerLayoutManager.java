package com.vempower.eezyclinic.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sathishkumar on 25/7/16.
 */
public class FullToRefreshLinerLayoutManager extends
        android.support.v7.widget.LinearLayoutManager{

    private static final long DELAY_TIME = 500;
    private boolean isBottomUp=true;

    private MyScrollListener listener;
    public FullToRefreshLinerLayoutManager(Context context) {
        super(context);
    }

    public FullToRefreshLinerLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public FullToRefreshLinerLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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

    public void setOnMyScrollListener(MyScrollListener listener)
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
