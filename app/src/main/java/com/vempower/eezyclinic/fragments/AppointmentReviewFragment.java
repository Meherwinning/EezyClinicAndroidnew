package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.HintAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.CustomSpinnerSelection;
import com.vempower.eezyclinic.views.MyRadioButtonRR;

/**
 * Created by satish on 6/12/17.
 */

public class AppointmentReviewFragment extends AbstractFragment {

    private View fragmentView;
    private LinearLayout relation_linear,patient_email_id_linear,patient_phone_number_linear;
    private MyRadioButtonRR self_radio_button;
    private RadioGroup appointment_radio_group;
    // private MyCheckBoxRR self_checkbox,others_checkbox;

    private boolean isSelfAppointment;
    private CustomSpinnerSelection relation_spinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.appointment_book_review_layout, container, false);


        myInit();

        return fragmentView;
    }

    private void myInit() {
     //  isSelfAppointment=true;
        //self_checkbox =fragmentView.findViewById(R.id. self_checkbox);
        //self_checkbox.setOnCheckedChangeListener(new );

       // others_checkbox  =fragmentView.findViewById(R.id.others_checkbox);


       // self_checkbox.setChecked(true);

        appointment_radio_group   =fragmentView.findViewById(R.id. appointment_radio_group);





       // others_radio_button =fragmentView.findViewById(R.id. others_radio_button);


        relation_linear =fragmentView.findViewById(R.id.relation_linear);
        patient_email_id_linear =fragmentView.findViewById(R.id. patient_email_id_linear);
        patient_phone_number_linear  =fragmentView.findViewById(R.id.patient_phone_number_linear);

        relation_spinner =fragmentView.findViewById(R.id.  relation_spinner);


        appointment_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id)
                {
                    case R.id.self_radio_button:
                        updateUIelements(true);
                        break;

                    case R.id.others_radio_button:
                        updateUIelements(false);
                        break;
                }

            }
        });

        setRelationSpinner();

        ((MyRadioButtonRR)fragmentView.findViewById(R.id.self_radio_button)).setChecked(true);

    }

    private void setRelationSpinner() {

           // final ArrayList<String> genderTypeList = new ArrayList<>();

           final String[] relations=getResources().getStringArray(R.array.relations);


       // genderTypeList.add(Constants.GenderValues.MALE);
          //  genderTypeList.add(Constants.GenderValues.FEMALE);
            //genderTypeList.add(Constants.GenderValues.GENDER);
            // selectedGender= genderTypeList.get(2);
            final HintAdapter aa = new HintAdapter<String>(MyApplication.getCurrentActivityContext(), R.layout.spinner_black_textview, relations);


            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relation_spinner.setAdapter(aa);
        relation_spinner.setSelection(aa.getCount());

        relation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != (aa.getCount())) {
                        String selectedGender = relations[position];
                        Utils.showToastMessage(selectedGender);
                    }
                    //Utils.showToastMessage("selectedGender "+selectedGender);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


    }

    private void updateUIelements(boolean isSelefButtonClick) {
        if(isSelefButtonClick && isSelfAppointment)
        {
            return;
        }
        relation_linear.setVisibility(isSelefButtonClick?View.GONE:View.VISIBLE);
        patient_email_id_linear.setVisibility(isSelefButtonClick?View.GONE:View.VISIBLE);
        patient_phone_number_linear .setVisibility(isSelefButtonClick?View.GONE:View.VISIBLE);
        isSelfAppointment=isSelefButtonClick;


    }

    @Override
    public void onResume() {
        super.onResume();
        hideKeyBord(fragmentView);
    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }
}
