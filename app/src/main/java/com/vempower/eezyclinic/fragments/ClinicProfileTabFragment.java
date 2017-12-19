package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.vempower.eezyclinic.APICore.ClinicProfileData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.delegate.ScrollViewDelegate;
import com.vempower.eezyclinic.views.MyTextViewRR;

/**
 * Created by satish on 18/12/17.
 */

public class ClinicProfileTabFragment extends BaseViewPagerFragment {

    private ScrollView mScrollView;
    private ScrollViewDelegate mScrollViewDelegate = new ScrollViewDelegate();
    private ClinicProfileData clinicProfile;
    private View fragment;

    public ClinicProfileTabFragment()
    {
        Bundle args = new Bundle();
        args.putInt(BUNDLE_FRAGMENT_INDEX, 0);
        setArguments(args);
    }

    public static ClinicProfileTabFragment getInstance(ClinicProfileData clinicProfileData) {
        ClinicProfileTabFragment fragment = new ClinicProfileTabFragment();
        fragment.setClinicProfile(clinicProfileData);

        return fragment;

    }

  /*  public static ScrollViewFragment newInstance(int index) {
        ScrollViewFragment fragment = new ScrollViewFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_FRAGMENT_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.clinic_profile_tap_layout, container, false);
        mScrollView = (ScrollView) fragment.findViewById(R.id.scrollview);
       // MyButtonRectangleRM rectangleRM= view.findViewById(R.id.appointment_bt);
       // rectangleRM.setBackground(getResources().getDrawable(R.drawable.app_gradient_bg1));
        //TextView textView = (TextView) view.findViewById(R.id.text1);
        //textView.setText(Utils.getStringFromResources(R.string.sample_txt));
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setClinicProfileData();
    }

    private void setClinicProfileData() {

        if(clinicProfile==null)
        {
            return;
        }

        ((MyTextViewRR)fragment.findViewById(R.id.clinic_name_tv)).setText(clinicProfile.getClinicName());
        ((MyTextViewRR)fragment.findViewById(R.id.timings_tv)).setText(clinicProfile.getClinicTimings());
        ((MyTextViewRR)fragment.findViewById(R.id.number_of_doctors_tv)).setText(clinicProfile.getNumberOfDoctors());
        ((MyTextViewRR)fragment.findViewById(R.id.insurance_accepted_tv)).setText(clinicProfile.getClinicInsuranceAccepted());
        ((MyTextViewRR)fragment.findViewById(R.id.addres_tv)).setText(clinicProfile.getClinicAddress1()+"\n"+clinicProfile.getClinicAddress2()+"\n"+clinicProfile.getClinicAddress3());
        ((MyTextViewRR)fragment.findViewById(R.id.specialization_tv)).setText(clinicProfile.getSpecializations());
        ((MyTextViewRR)fragment.findViewById(R.id.service_tv)).setText(clinicProfile.getServices());


    }

    @Override
    public boolean isViewBeingDragged(MotionEvent event) {
        return mScrollViewDelegate.isViewBeingDragged(event, mScrollView);
    }

    public void setClinicProfile(ClinicProfileData clinicProfile) {
        this.clinicProfile = clinicProfile;
    }

   /* private String loadContentString() {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[8 * 1024];
        int len;
        String content = "";
        try {
            inputStream = getActivity().getAssets().open("lorem.txt");
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            content = outputStream.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSilently(inputStream);
            closeSilently(outputStream);
        }

        return content;
    }

    private void closeSilently(Closeable c) {
        if (c == null) {
            return;
        }
        try {
            c.close();
        } catch (Throwable t) {
            // do nothing
        }
    }*/
}