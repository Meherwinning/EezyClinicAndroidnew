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
    private LinearLayout contact_details_linear,insurance_details_linear,emergency_details_linear;

    private View fragmentView;
    private ExpandableLinearLayout expandableLayout_contact_el,expandableLayout_insurance_el,expandableLayout_emergency_el;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.my_profile_layout, container, false);


        myInit();
        return fragmentView;
    }

    private void myInit() {

        contact_details_linear = getFragemtView().findViewById(R.id.contact_details_linear);
        insurance_details_linear = getFragemtView().findViewById(R.id. insurance_details_linear);
        emergency_details_linear = getFragemtView().findViewById(R.id.emergency_details_linear);

        expandableLayout_contact_el = getFragemtView().findViewById(R.id.expandableLayout_contact_el);
        expandableLayout_insurance_el  = getFragemtView().findViewById(R.id.expandableLayout_insurance_el);
        expandableLayout_emergency_el = getFragemtView().findViewById(R.id. expandableLayout_emergency_el);

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        compute();
    }

    private void compute() {

        {
            setExpandedViewListener(expandableLayout_contact_el, contact_details_linear, R.id.contact_iv);
            expandableLayout_contact_el.initLayout();
            contact_details_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandableLayout_contact_el.toggle();
                    hideKeyBord(expandableLayout_contact_el);
                }
            });
        }


        {
            setExpandedViewListener(expandableLayout_emergency_el, emergency_details_linear,R.id.emergency_iv);
            expandableLayout_emergency_el.initLayout();


            emergency_details_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandableLayout_emergency_el.toggle();
                    hideKeyBord(expandableLayout_emergency_el);
                }
            });
        }
        {
            setExpandedViewListener(expandableLayout_insurance_el, insurance_details_linear, R.id.insurance_iv);
            expandableLayout_insurance_el.initLayout();

            insurance_details_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandableLayout_insurance_el.toggle();
                    hideKeyBord(expandableLayout_insurance_el);
                }
            });
        }


       // expandableLayout_emergency_el.collapse();
        //expandableLayout_insurance_el.collapse();


    }


    private void setExpandedViewListener( final ExpandableLinearLayout expandableLayout, View view,int imageId) {
        expandableLayout.setInRecyclerView(false);
        //expandableLayout.setBackgroundColor(ContextCompat.getColor(this, item.colorId2));
        expandableLayout.setInterpolator(com.github.aakira.expandablelayout.Utils.createInterpolator(com.github.aakira.expandablelayout.Utils.LINEAR_OUT_SLOW_IN_INTERPOLATOR));
        //expandableLayout.setExpanded(expandState.get(position));
        expandableLayout.setExpanded(false);
        //expandableLayout.
        final ImageView imageView = view.findViewById(imageId);
        imageView.setBackgroundResource(R.drawable.plus_icon_grey);
        expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(imageView!=null) {
                            imageView.setBackgroundResource(R.drawable.minus_icon_grey);
                        }
                    }
                }, 200);

            }

            @Override
            public void onPreClose() {
                if(imageView!=null) {
                    imageView.setBackgroundResource(R.drawable.plus_icon_grey);
                }
            }
        });
    }


    @Override
    View getFragemtView() {
        return fragmentView;
    }
}
