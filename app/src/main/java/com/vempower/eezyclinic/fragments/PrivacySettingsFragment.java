package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.rey.material.widget.LinearLayout;
import com.vempower.eezyclinic.R;

/**
 * Created by satish on 6/12/17.
 */

public class PrivacySettingsFragment extends AbstractFragment {

    private View fragmentView;
    private android.widget.LinearLayout upload_files_linear, doctor_genarated_linear,
            diagnostic_report_linear, patient_profile_linear, medical_history_linear, patient_notes_linear;
    private ExpandableLinearLayout expandableLayout_upload_files_el, expandableLayout_doctor_genarated_el,
            expandableLayout_diagnostic_report_el, expandableLayout_patient_profile_el, expandableLayout_medical_history_el, expandableLayout_patient_notes_el;
    private ScrollView myScrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.privacy_settings, container, false);

        myInit();

        return fragmentView;
    }

    private void myInit() {
        myScrollView = getFragemtView().findViewById(R.id.settings_scroll);

        upload_files_linear = getFragemtView().findViewById(R.id.upload_files_linear);
        expandableLayout_upload_files_el = getFragemtView().findViewById(R.id.expandableLayout_upload_files_el);

        doctor_genarated_linear = getFragemtView().findViewById(R.id.doctor_genarated_linear);
        expandableLayout_doctor_genarated_el = getFragemtView().findViewById(R.id.expandableLayout_doctor_genarated_el);

        diagnostic_report_linear = getFragemtView().findViewById(R.id.diagnostic_report_linear);
        expandableLayout_diagnostic_report_el = getFragemtView().findViewById(R.id.expandableLayout_diagnostic_report_el);

        patient_profile_linear = getFragemtView().findViewById(R.id.patient_profile_linear);
        expandableLayout_patient_profile_el = getFragemtView().findViewById(R.id.expandableLayout_patient_profile_el);

        medical_history_linear = getFragemtView().findViewById(R.id.medical_history_linear);
        expandableLayout_medical_history_el = getFragemtView().findViewById(R.id.expandableLayout_medical_history_el);

        patient_notes_linear = getFragemtView().findViewById(R.id.patient_notes_linear);
        expandableLayout_patient_notes_el = getFragemtView().findViewById(R.id.expandableLayout_patient_notes_el);
    }


    private void compute() {
        {
            setExpandedViewListener(expandableLayout_patient_notes_el, patient_notes_linear, R.id.patient_notes_iv);
            patient_notes_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandableLayout_patient_notes_el.toggle();
                }
            });
        }

        {
            setExpandedViewListener(expandableLayout_medical_history_el, medical_history_linear, R.id.medical_history_iv);
            medical_history_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandableLayout_medical_history_el.toggle();
                }
            });
        }

        {
            setExpandedViewListener(expandableLayout_patient_profile_el, patient_profile_linear, R.id.patient_profile_iv);
            patient_profile_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandableLayout_patient_profile_el.toggle();
                }
            });
        }
        {
            setExpandedViewListener(expandableLayout_diagnostic_report_el, diagnostic_report_linear, R.id.diagnostic_report_iv);
            diagnostic_report_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandableLayout_diagnostic_report_el.toggle();
                }
            });
        }
        {
            setExpandedViewListener(expandableLayout_upload_files_el, upload_files_linear, R.id.contact_iv);
            upload_files_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandableLayout_upload_files_el.toggle();
                }
            });
        }

        {
            setExpandedViewListener(expandableLayout_doctor_genarated_el, doctor_genarated_linear, R.id.doctor_genarated_iv);
            doctor_genarated_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandableLayout_doctor_genarated_el.toggle();
                }
            });
        }


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        compute();
    }


    private void setExpandedViewListener(final ExpandableLinearLayout expandableLayout, final View view, int imageId) {
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
                        //expandableLayout.setFocusable(true);
                        if (imageView != null) {
                            imageView.setBackgroundResource(R.drawable.minus_icon_grey);
                            //expandableLayout.getParent().requestChildFocus(expandableLayout,expandableLayout);
                            scrollToView(myScrollView, view);
                            //((ScrollView)getFragemtView().findViewById(R.id.scroll)).fullScroll(View.FOCUS_DOWN);

                        }
                    }
                }, 300);

            }

            @Override
            public void onPreClose() {
                if (imageView != null) {
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
