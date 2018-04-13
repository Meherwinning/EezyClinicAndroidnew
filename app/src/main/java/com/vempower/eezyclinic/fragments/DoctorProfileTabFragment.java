package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.DoctorProfileData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.delegate.AbsListViewDelegate;
import com.vempower.eezyclinic.delegate.ScrollViewDelegate;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyButtonRectangleRM;
 ;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.InputStream;

/**
 * Created by satish on 18/12/17.
 */

public class DoctorProfileTabFragment extends BaseViewPagerFragment {

    private ScrollView mScrollView;
    private ScrollViewDelegate mScrollViewDelegate = new ScrollViewDelegate();
    private DoctorProfileData doctorProfileData;
    private  TextView dd;
    private  View fragment;

    public DoctorProfileTabFragment()
    {
        Bundle args = new Bundle();
        args.putInt(BUNDLE_FRAGMENT_INDEX, 0);
        setArguments(args);
    }

    public static DoctorProfileTabFragment getInstance(final DoctorProfileData profileData) {
        DoctorProfileTabFragment fragment = new DoctorProfileTabFragment();

        fragment.setDoctorProfileData(profileData);
       // Utils.showToastMessage("DoctorProfileTabFragment");
        return fragment;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.doctor_profile_tap_layout, container, false);
        mScrollView = (ScrollView) fragment.findViewById(R.id.scrollview);

        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(fragment);

    }


    private void init(View view) {


        if(doctorProfileData==null)
        {
            return;
        }
        (( TextView )view.findViewById(R.id.gender_tv)).setText(doctorProfileData.getDoctorsProfile().getGender());
        (( TextView )view.findViewById(R.id. nationality_tv)).setText(doctorProfileData.getDoctorsProfile().getNationality());
        (( TextView )view.findViewById(R.id. language_known_tv)).setText(doctorProfileData.getDoctorsProfile().getLanguagesKnown());
        (( TextView )view.findViewById(R.id. about_tv)).setText(doctorProfileData.getDoctorsProfile().getAboutDoctor());
        (( TextView )view.findViewById(R.id.  consultation_fee_tv)).setText(doctorProfileData.getDoctorsProfile().getConsultationFee());
        (( TextView )view.findViewById(R.id. specialization_tv)).setText(doctorProfileData.getSpecializations());
        (( TextView )view.findViewById(R.id.  service_tv)).setText(doctorProfileData.getServices());
        (( TextView )view.findViewById(R.id. education_details_tv)).setText(doctorProfileData.getEducationalQualifications());
        (( TextView )view.findViewById(R.id. experiance_tv)).setText(doctorProfileData.getExperience());
        (( TextView )view.findViewById(R.id.  awards_and_recognitions_tv)).setText(doctorProfileData.getAwardsRecognitions());
        (( TextView )view.findViewById(R.id. memberships_tv)).setText(doctorProfileData.getMemberships());
        (( TextView )view.findViewById(R.id.  registrations_tv)).setText(doctorProfileData.getRegistrations());
        (( TextView ) view.findViewById(R.id. clinic_details_tv)).setText(doctorProfileData.getClinicdetails());


        (view.findViewById(R.id.gender_tv1)).setVisibility(TextUtils.isEmpty(doctorProfileData.getDoctorsProfile().getGender())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. nationality_tv1)).setVisibility(TextUtils.isEmpty(doctorProfileData.getDoctorsProfile().getNationality())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. language_known_tv1)).setVisibility(TextUtils.isEmpty(doctorProfileData.getDoctorsProfile().getLanguagesKnown())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. about_tv1)).setVisibility(TextUtils.isEmpty(doctorProfileData.getDoctorsProfile().getAboutDoctor())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id.  consultation_fee_tv1)).setVisibility(TextUtils.isEmpty(doctorProfileData.getDoctorsProfile().getConsultationFee())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. specialization_tv1)).setVisibility(TextUtils.isEmpty(doctorProfileData.getSpecializations())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id.  service_tv1)).setVisibility(TextUtils.isEmpty(doctorProfileData.getServices())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. education_details_tv1)).setVisibility(TextUtils.isEmpty(doctorProfileData.getEducationalQualifications())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. experiance_tv1)).setVisibility(TextUtils.isEmpty(doctorProfileData.getExperience())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id.  awards_and_recognitions_tv1)).setVisibility(TextUtils.isEmpty(doctorProfileData.getAwardsRecognitions())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. memberships_tv1)).setVisibility(TextUtils.isEmpty(doctorProfileData.getMemberships())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id.  registrations_tv1)).setVisibility(TextUtils.isEmpty(doctorProfileData.getRegistrations())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. clinic_details_tv1)).setVisibility(TextUtils.isEmpty(doctorProfileData.getClinicdetails())?View.GONE:View.VISIBLE);

        (view.findViewById(R.id.gender_tv)).setVisibility(TextUtils.isEmpty(doctorProfileData.getDoctorsProfile().getGender())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. nationality_tv)).setVisibility(TextUtils.isEmpty(doctorProfileData.getDoctorsProfile().getNationality())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. language_known_tv)).setVisibility(TextUtils.isEmpty(doctorProfileData.getDoctorsProfile().getLanguagesKnown())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. about_tv)).setVisibility(TextUtils.isEmpty(doctorProfileData.getDoctorsProfile().getAboutDoctor())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id.  consultation_fee_tv)).setVisibility(TextUtils.isEmpty(doctorProfileData.getDoctorsProfile().getConsultationFee())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. specialization_tv)).setVisibility(TextUtils.isEmpty(doctorProfileData.getSpecializations())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id.  service_tv)).setVisibility(TextUtils.isEmpty(doctorProfileData.getServices())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. education_details_tv)).setVisibility(TextUtils.isEmpty(doctorProfileData.getEducationalQualifications())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. experiance_tv)).setVisibility(TextUtils.isEmpty(doctorProfileData.getExperience())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id.  awards_and_recognitions_tv)).setVisibility(TextUtils.isEmpty(doctorProfileData.getAwardsRecognitions())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. memberships_tv)).setVisibility(TextUtils.isEmpty(doctorProfileData.getMemberships())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id.  registrations_tv)).setVisibility(TextUtils.isEmpty(doctorProfileData.getRegistrations())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. clinic_details_tv)).setVisibility(TextUtils.isEmpty(doctorProfileData.getClinicdetails())?View.GONE:View.VISIBLE);


        (view.findViewById(R.id.gender_tv2)).setVisibility(TextUtils.isEmpty(doctorProfileData.getDoctorsProfile().getGender())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. nationality_tv2)).setVisibility(TextUtils.isEmpty(doctorProfileData.getDoctorsProfile().getNationality())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. language_known_tv2)).setVisibility(TextUtils.isEmpty(doctorProfileData.getDoctorsProfile().getLanguagesKnown())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. about_tv2)).setVisibility(TextUtils.isEmpty(doctorProfileData.getDoctorsProfile().getAboutDoctor())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id.  consultation_fee_tv2)).setVisibility(TextUtils.isEmpty(doctorProfileData.getDoctorsProfile().getConsultationFee())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. specialization_tv2)).setVisibility(TextUtils.isEmpty(doctorProfileData.getSpecializations())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id.  service_tv2)).setVisibility(TextUtils.isEmpty(doctorProfileData.getServices())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. education_details_tv2)).setVisibility(TextUtils.isEmpty(doctorProfileData.getEducationalQualifications())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. experiance_tv2)).setVisibility(TextUtils.isEmpty(doctorProfileData.getExperience())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id.  awards_and_recognitions_tv2)).setVisibility(TextUtils.isEmpty(doctorProfileData.getAwardsRecognitions())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id. memberships_tv2)).setVisibility(TextUtils.isEmpty(doctorProfileData.getMemberships())?View.GONE:View.VISIBLE);
        (view.findViewById(R.id.  registrations_tv2)).setVisibility(TextUtils.isEmpty(doctorProfileData.getRegistrations())?View.GONE:View.VISIBLE);


    }

    @Override
    public boolean isViewBeingDragged(MotionEvent event) {
        return mScrollViewDelegate.isViewBeingDragged(event, mScrollView);
    }

    public void setDoctorProfileData(DoctorProfileData doctorProfileData) {
        this.doctorProfileData = doctorProfileData;
    }


}