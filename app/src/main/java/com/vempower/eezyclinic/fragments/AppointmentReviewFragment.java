package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.rey.material.widget.Button;
import com.vempower.eezyclinic.APICore.BookAppointmentRequestDetails;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.HintAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.CustomSpinnerSelection;
import com.vempower.eezyclinic.views.MyEditTextBlackCursorRR;
import com.vempower.eezyclinic.views.MyRadioButtonRR;
import com.vempower.eezyclinic.views.MyTextViewRB;
import com.vempower.eezyclinic.views.MyTextViewRR;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by satish on 6/12/17.
 */

public class AppointmentReviewFragment extends AbstractFragment {

    private View fragmentView;
    private LinearLayout relation_linear,patient_email_id_linear,patient_phone_number_linear;
    private MyRadioButtonRR self_radio_button;
    private RadioGroup appointment_radio_group;

    private BookAppointmentRequestDetails requestDetails;
    // private MyCheckBoxRR self_checkbox,others_checkbox;

    //private boolean isSelfAppointment;
    private CustomSpinnerSelection relation_spinner;
    private Button appointment_bt;
    //private String selectedRelation;
    private MyEditTextBlackCursorRR patient_name_et,reason_for_appointment_et,email_et,phone_et;
    private SearchResultDoctorListData searchResultDoctorListData;
    private String dateTimeStr;
    private MyTextViewRR time_date_display_tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.appointment_book_review_layout, container, false);


        myInit();
        return fragmentView;
    }

    private void myInit() {

       setHeaderDetails();


        appointment_radio_group   =fragmentView.findViewById(R.id. appointment_radio_group);
        time_date_display_tv =fragmentView.findViewById(R.id. time_date_display_tv);
        relation_linear =fragmentView.findViewById(R.id.relation_linear);
        patient_email_id_linear =fragmentView.findViewById(R.id. patient_email_id_linear);
        patient_phone_number_linear  =fragmentView.findViewById(R.id.patient_phone_number_linear);

        relation_spinner =fragmentView.findViewById(R.id.  relation_spinner);


        patient_name_et  =fragmentView.findViewById(R.id.patient_name_et);
        reason_for_appointment_et =fragmentView.findViewById(R.id.reason_for_appointment_et);
        email_et =fragmentView.findViewById(R.id.email_et);
        phone_et =fragmentView.findViewById(R.id.phone_et);


        appointment_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.self_radio_button:
                        if(requestDetails!=null)
                        {
                            requestDetails.setSelfAppointment(true);
                        }
                        updateUIelements(true);
                        break;

                    case R.id.others_radio_button:
                        if(requestDetails!=null)
                        {
                            requestDetails.setSelfAppointment(false);
                        }
                        updateUIelements(false);
                        //requestDetails.setSelfAppointment(false);
                        break;
                }

            }
        });

        setRelationSpinner();

        ((MyRadioButtonRR)fragmentView.findViewById(R.id.self_radio_button)).setChecked(true);


        appointment_bt =fragmentView.findViewById(R.id. appointment_bt);
        appointment_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(requestDetails!=null)
                {
                    /*patient_name_et  =fragmentView.findViewById(R.id.patient_name_et);
                    reason_for_appointment_et =fragmentView.findViewById(R.id.reason_for_appointment_et);
                    email_et =fragmentView.findViewById(R.id.email_et);
                    phone_et =fragmentView.findViewById(R.id.phone_et);*/

                    requestDetails.setPatientname(patient_name_et.getText().toString());
                    requestDetails.setReasonsforappoinment(reason_for_appointment_et.getText().toString());
                    requestDetails.setEmail(email_et.getText().toString());
                    requestDetails.setMobilenum(phone_et.getText().toString());
                }
                /*if(requestDetails!=null && requestDetails.isSelfAppointment())
                {
                    Utils.showToastMessage("Self");

                }else
                {
                    Utils.showToastMessage("Others");
                }*/
                Utils.showToastMessage(requestDetails.toString());
            }
        });

    }

    private void setHeaderDetails() {

        if(searchResultDoctorListData==null || fragmentView==null)
        {
            return;
        }




      ImageView imageView= fragmentView.findViewById(R.id.profile_iv);

        MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.profile_icon,imageView,searchResultDoctorListData.getDoctorLogo());


        ((MyTextViewRB)fragmentView.findViewById(R.id.doctor_name_tv)).setText(searchResultDoctorListData.getDoctorName());
        ((MyTextViewRR)fragmentView.findViewById(R.id.doctor_designation_tv)).setText(searchResultDoctorListData.getSpecalities());

        ((MyTextViewRB)fragmentView.findViewById(R.id.clinic_name_tv)).setText(searchResultDoctorListData.getClinicName());
        ((MyTextViewRR) fragmentView.findViewById(R.id.clinic_address_tv)).setText(searchResultDoctorListData.getAddress());



      String  SERVER_DATE_FORMAT_NEW="yyyy-MM-dd h:mm a";//"2017-11-22";"2018-01-16"

        String DISPLAY_DATE="MMM d,yyyy";
        String DISPLAY_TIME="h:mm a 'on' EEEE";


        SimpleDateFormat DISPLAY_DATE_FORMATTER = new SimpleDateFormat(DISPLAY_DATE);
        SimpleDateFormat DISPLAY_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_TIME);
        MyTextViewRR time_date_display_tv = fragmentView.findViewById(R.id.time_date_display_tv);

        try {
            Date date = Utils.changeStringToDateFormat(dateTimeStr, SERVER_DATE_FORMAT_NEW);
           String dateStr= DISPLAY_DATE_FORMATTER.format(date);
            String timeStr= DISPLAY_TIME_FORMATTER.format(date);

            time_date_display_tv.setText("at "+timeStr+", "+dateStr);
        }catch (Exception e)
        {

        }

//at 12:15 PM on Friday,  Mar 27th, 2017

        //"h:mm a"
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
                   String selectedRelation=null;
                    if (position != (aa.getCount())) {
                         selectedRelation = relations[position];
                        Utils.showToastMessage(selectedRelation);

                    }
                    if(requestDetails!=null)
                    {
                        requestDetails.setFamilymember(selectedRelation);
                    }
                    //Utils.showToastMessage("selectedGender "+selectedGender);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


    }

    private void updateUIelements(boolean isSelefButtonClick) {
        /*if(isSelefButtonClick && isSelfAppointment)
        {
            return;
        }*/
        relation_linear.setVisibility(isSelefButtonClick?View.GONE:View.VISIBLE);
        patient_email_id_linear.setVisibility(isSelefButtonClick?View.GONE:View.VISIBLE);
        patient_phone_number_linear .setVisibility(isSelefButtonClick?View.GONE:View.VISIBLE);
        //isSelfAppointment=isSelefButtonClick;


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

    public void setSearchResultDoctorListData(SearchResultDoctorListData searchResultDoctorListData, String dateTimeStr) {
        this.searchResultDoctorListData = searchResultDoctorListData;
        //String doctor_id, String branch_id, String appointmenttime
        requestDetails= new BookAppointmentRequestDetails(searchResultDoctorListData.getDocId(),searchResultDoctorListData.getBranchId(),dateTimeStr);
        this.dateTimeStr=dateTimeStr;
    }
}
