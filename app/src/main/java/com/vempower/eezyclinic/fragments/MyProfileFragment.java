package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.vempower.eezyclinic.R;

/**
 * Created by satish on 6/12/17.
 */

public class MyProfileFragment extends AbstractFragment {
    private LinearLayout contact_details_linear;

    private View fragmentView;
    private ExpandableLinearLayout expandableLayout_advance_search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.my_profile_layout, container, false);


        myInit();
        return fragmentView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        compute();
    }

    private void compute() {
        setExpandedViewListener(expandableLayout_advance_search, contact_details_linear);
        contact_details_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout_advance_search.toggle();
                hideKeyBord(expandableLayout_advance_search);


            }
        });
    }

    private void myInit() {
        contact_details_linear = getFragemtView().findViewById(R.id.contact_details_linear);
        expandableLayout_advance_search = getFragemtView().findViewById(R.id.expandableLayout_advance_search);

    }

    private void setExpandedViewListener(ExpandableLinearLayout expandableLayout, View view) {
        expandableLayout.setInRecyclerView(false);
        //expandableLayout.setBackgroundColor(ContextCompat.getColor(this, item.colorId2));
        expandableLayout.setInterpolator(com.github.aakira.expandablelayout.Utils.createInterpolator(com.github.aakira.expandablelayout.Utils.LINEAR_OUT_SLOW_IN_INTERPOLATOR));
        //expandableLayout.setExpanded(expandState.get(position));
        expandableLayout.setExpanded(false);
        final ImageView imageView = view.findViewById(R.id.advanced_search_iv);
        imageView.setBackgroundResource(R.drawable.plus_icon_grey);
        expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                //imageView.setBackgroundResource(0);

                //createRotateAnimator(buttonLayout, 0f, 180f).start();
                //expandState.put(position, true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setBackgroundResource(R.drawable.minus_icon_grey);
                       // ((ScrollView) getFragemtView().findViewById(R.id.scroll)).fullScroll(View.FOCUS_DOWN);

                    }
                }, 200);
            }

            @Override
            public void onPreClose() {
                //imageView.setBackgroundResource(0);
                imageView.setBackgroundResource(R.drawable.plus_icon_grey);
                //createRotateAnimator(buttonLayout, 180f, 0f).start();
                // expandState.put(position, false);
            }
        });
    }


    @Override
    View getFragemtView() {
        return fragmentView;
    }
}
