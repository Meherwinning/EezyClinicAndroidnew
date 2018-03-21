package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
    private final NewHomeDoctorsList doctor;
    private TextView nameLabel,designation_tv2;

    private ImageView popular_doctor_iv;

    public FirstFragment() {
        doctor=null;
    }

    public FirstFragment(NewHomeDoctorsList doctor) {
       this.doctor=doctor;
    }

    // newInstance constructor for creating fragment with arguments
   /* public  FirstFragment newInstance(NewHomeDoctorsList doctor1, int page, String title) {
        FirstFragment fragmentFirst = new FirstFragment(doctor1);
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }*/


    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        init(view);
        return view;
    }

    private void init(View view) {
        popular_doctor_iv= view.findViewById(R.id.popular_doctor_iv);
        nameLabel = view.findViewById(R.id.doctor_name_tv);
        designation_tv2 = view.findViewById(R.id. designation_tv2);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindValues();
    }

    private void bindValues() {
        if(popular_doctor_iv!=null && doctor!=null)
        {
            String name="";

            if(!TextUtils.isEmpty(doctor.getFirstName()))
            {
                name=doctor.getFirstName();
            }

            if(!TextUtils.isEmpty(doctor.getMiddleName()))
            {
                name=name+" "+doctor.getMiddleName();
            }
            if(!TextUtils.isEmpty(doctor.getLastName()))
            {
                name=name+" "+doctor.getLastName();
            }


            nameLabel.setText(name);
            designation_tv2 .setText(doctor.getSpecalities());

            MyApplication.getInstance().setBitmapToImageview(R.drawable.default_doctor_image,popular_doctor_iv,doctor.getDoctorLogo());
        }
    }
}
