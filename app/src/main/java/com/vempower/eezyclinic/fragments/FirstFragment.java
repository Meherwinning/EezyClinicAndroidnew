package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.NewHomeDoctorsList;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;

public class FirstFragment extends AbstractFragment {
    // Store instance variables
    private String title;
    private int page;
    private static NewHomeDoctorsList doctor;

    private ImageView popular_doctor_iv;

    // newInstance constructor for creating fragment with arguments
    public static FirstFragment newInstance(NewHomeDoctorsList doctor1, int page, String title) {
        FirstFragment fragmentFirst = new FirstFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        doctor=doctor1;
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        //TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
        //tvLabel.setText(page + " -- " + title);
        init(view);
        return view;
    }

    private void init(View view) {
        popular_doctor_iv= view.findViewById(R.id.popular_doctor_iv);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindValues();
    }

    private void bindValues() {
        if(popular_doctor_iv!=null && doctor!=null)
        {
            MyApplication.getInstance().setBitmapToImageview(R.drawable.default_doctor_image,popular_doctor_iv,doctor.getDoctorLogo());
        }
    }
}
