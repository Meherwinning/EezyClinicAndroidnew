package com.vempower.eezyclinic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.NewHomeDoctorsList;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.DoctorProfileActivity;
import com.vempower.eezyclinic.activities.complex.ArticleBinder1;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.core.DoctorsPair;
import com.vempower.eezyclinic.interfaces.AbstractIBinder;
import com.vempower.eezyclinic.interfaces.IntentObjectListener;
import com.vempower.eezyclinic.utils.Constants;

public class FirstFragment extends AbstractFragment {
    // Store instance variables
    private final DoctorsPair doctorPair;
    /*private TextView nameLabel,designation_tv2;
    private ImageView popular_doctor_iv;*/
    //private View docView1,docView2;
    private ViewHolder docHolder1, docHolder2;

    public FirstFragment() {
        doctorPair = null;
    }

    public FirstFragment(DoctorsPair doctorPair) {
        this.doctorPair = doctorPair;
    }

    private class ViewHolder {
        private TextView nameLabel, designation_tv2;
        private ImageView popular_doctor_iv;
        private View view;

        public ViewHolder(View view) {
            this.view = view;
            popular_doctor_iv = view.findViewById(R.id.popular_doctor_iv);
            nameLabel = view.findViewById(R.id.doctor_name_tv);
            designation_tv2 = view.findViewById(R.id.designation_tv2);
        }

        public void bindValue(final NewHomeDoctorsList doctor) {
            view.setVisibility(View.INVISIBLE);
            if (doctor == null) {
                return;
            }
            view.setVisibility(View.VISIBLE);
            String name = "";

            if (!TextUtils.isEmpty(doctor.getFirstName())) {
                name = doctor.getFirstName();
            }

            if (!TextUtils.isEmpty(doctor.getMiddleName())) {
                name = name + " " + doctor.getMiddleName();
            }
            if (!TextUtils.isEmpty(doctor.getLastName())) {
                name = name + " " + doctor.getLastName();
            }


            nameLabel.setText(name);
            designation_tv2.setText(doctor.getSpecalities());

            popular_doctor_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyApplication.getCurrentActivityContext(), DoctorProfileActivity.class);
                    /*((Activity) MyApplication.getCurrentActivityContext()).getIntent();*/
                    intent.putExtra(ListenerKey.ObjectKey.SEARCH_RESULT_DOCTOR_LIST_DATA_KEY, new Messenger(new AbstractIBinder() {
                        @Override
                        protected IntentObjectListener getMyObject() {
                            return new IntentObjectListener() {

                                @Override
                                public Object getObject() {
                                    SearchResultDoctorListData data = doctor;
                                    data.setDocId(doctor.id);
                                    data.setBranchId(doctor.getBranchId());

                                    return data;
                                }
                            };
                        }
                    }));
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    MyApplication.getCurrentActivityContext().startActivity(intent);
                }
            });


            if (popular_doctor_iv != null) {
              //  MyApplication.getInstance().setBitmapToImageview(R.drawable.default_doctor_image, popular_doctor_iv, doctor.getDoctorLogo());

                //START
                if (!TextUtils.isEmpty(doctor.getDoctorLogo())) {
                    MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.default_doctor_image, popular_doctor_iv, doctor.getDoctorLogo());
                } else {
                    if (TextUtils.isEmpty(doctor.getGender())) {
                        MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.default_doctor_image, popular_doctor_iv, Constants.DefaultImage.UNISEX_URL);

                    } else {
                        switch (doctor.getGender().trim().toLowerCase()) {
                            case "male":
                                MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.default_doctor_image, popular_doctor_iv, Constants.DefaultImage.MALE_URL);
                                break;
                            case "female":
                                MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.default_doctor_image, popular_doctor_iv, Constants.DefaultImage.FEMALE_URL);

                                break;
                            default:
                                MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.default_doctor_image, popular_doctor_iv, Constants.DefaultImage.UNISEX_URL);

                                break;
                        }
                    }

                }
            }


        }
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
        // View view = inflater.inflate(R.layout.fragment_first, container, false);
        View view = inflater.inflate(R.layout.fragment_doctors_pairs, container, false);

        init(view);
        return view;
    }

    private void init(View view) {
       /* popular_doctor_iv= view.findViewById(R.id.popular_doctor_iv);
        nameLabel = view.findViewById(R.id.doctor_name_tv);
        designation_tv2 = view.findViewById(R.id. designation_tv2);*/
        View docView1 = view.findViewById(R.id.doc1);
        View docView2 = view.findViewById(R.id.doc2);
        docHolder1 = new ViewHolder(docView1);
        docHolder2 = new ViewHolder(docView2);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindValues();
    }

    private void bindValues() {
        if (/*popular_doctor_iv!=null &&*/ doctorPair != null) {
            if (doctorPair.getDoc1() != null) {
                docHolder1.bindValue(doctorPair.doc1);
            }
            if (doctorPair.getDoc2() != null) {
                docHolder2.bindValue(doctorPair.doc2);
            }
        }
    }
}
