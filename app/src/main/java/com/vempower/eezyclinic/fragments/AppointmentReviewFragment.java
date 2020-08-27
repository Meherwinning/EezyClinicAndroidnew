package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.rey.material.widget.Button;
import com.vempower.eezyclinic.APICore.BookAppointmentRequestDetails;
import com.vempower.eezyclinic.APICore.PatientData;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.HintAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.AppointmentBookingMapper;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.CustomSpinnerSelection;
import com.vempower.eezyclinic.views.MyEditTextBlackCursor;
import com.vempower.eezyclinic.views.MyRadioButtonRR;
 ;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by satish on 6/12/17.
 */

public class AppointmentReviewFragment extends AbstractFragment {

    private View fragmentView;
    private LinearLayout consultancy_type_linear,relation_linear,patient_email_id_linear,patient_phone_number_linear;
    private MyRadioButtonRR self_radio_button;
    private RadioGroup appointment_radio_group;

    private BookAppointmentRequestDetails requestDetails;
    // private CheckBox self_checkbox,others_checkbox;

    //private boolean isSelfAppointment;
    private CustomSpinnerSelection relation_spinner,counsltancy_type_spinner;
    private Button appointment_bt;
    //private String selectedRelation;
    private MyEditTextBlackCursor patient_name_et,reason_for_appointment_et,email_et,phone_et;
    private SearchResultDoctorListData searchResultDoctorListData;
    private String dateTimeStr;
    private  TextView time_date_display_tv,requestappointment_conform_tv,appointment_time_before_tv;
    private PatientData patientData;
    private View success_view;
    private LinearLayout review_view;
    private DoneButtonClickListener doneButtonClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.appointment_book_review_layout, container, false);


        myInit();
        return fragmentView;
    }

    private void myInit() {

        MyApplication.showTransparentDialog();
        patientData = MyApplication.getInstance().getLoggedUserDetailsFromSharedPref();
        MyApplication.hideTransaprentDialog();


        setHeaderDetails();
        appointment_bt =fragmentView.findViewById(R.id. appointment_bt);

        review_view  =fragmentView.findViewById(R.id. review_view);

        success_view  =fragmentView.findViewById(R.id.success_view);
        review_view.setVisibility(View.VISIBLE);
        success_view.setVisibility(View.GONE);
        //appointment_bt.setText("Book Appointment");
        appointment_radio_group   =fragmentView.findViewById(R.id. appointment_radio_group);
        time_date_display_tv =fragmentView.findViewById(R.id. time_date_display_tv);
        relation_linear =fragmentView.findViewById(R.id.relation_linear);
        consultancy_type_linear =fragmentView.findViewById(R.id.consultancy_type_linear);
        patient_email_id_linear =fragmentView.findViewById(R.id. patient_email_id_linear);
        patient_phone_number_linear  =fragmentView.findViewById(R.id.patient_phone_number_linear);

        relation_spinner =fragmentView.findViewById(R.id.  relation_spinner);
        counsltancy_type_spinner =fragmentView.findViewById(R.id.  counsltancy_type_spinner);

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
                            if(patientData!=null) {
                                patient_name_et.setText(patientData.getPatientName());
                            }
                            requestDetails.setSelfAppointment(true);
                        }
                        updateUIelements(true);
                        break;

                    case R.id.others_radio_button:
                        if(requestDetails!=null)
                        {
                            patient_name_et.setText(null);
                            requestDetails.setSelfAppointment(false);
                        }
                        updateUIelements(false);
                        //requestDetails.setSelfAppointment(false);
                        break;
                }

            }
        });

        setRelationSpinner();
        setconsultationtypeSpinner();

        ((MyRadioButtonRR)fragmentView.findViewById(R.id.self_radio_button)).setChecked(true);



        appointment_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyBord(fragmentView);

                MyApplication.showTransparentDialog();
                patientData = MyApplication.getInstance().getLoggedUserDetailsFromSharedPref();
                MyApplication.hideTransaprentDialog();

                if(patientData==null)
                {
                    showAlertDialog("Alert","Something wrong with patient details.\nPlease try again",false);


                    return;
                }



                if(success_view.getVisibility()==View.VISIBLE)
                {
                   // Utils.showToastMsg("Now click Done" );
                    if(doneButtonClickListener!=null)
                    {
                        doneButtonClickListener.onClick();
                        MyApplication.getInstance().setSearchResultDoctorListData(null);
                    }
                    return;
                }


               String patientName= patient_name_et.getText().toString();
               if(TextUtils.isEmpty(patientName))
               {
                   Utils.showToastMsg("Please enter patient name");

                   return;
               }
               String email=email_et.getText().toString();
               if(!requestDetails.isSelfAppointment()) {
                   if (!TextUtils.isEmpty(email) && !Utils.isValidEmail(email)) {
                       Utils.showToastMsg("Please enter valid email");
                       return;
                   }
               }
                if(requestDetails!=null)
                {
                    /*patient_name_et  =fragmentView.findViewById(R.id.patient_name_et);
                    reason_for_appointment_et =fragmentView.findViewById(R.id.reason_for_appointment_et);
                    email_et =fragmentView.findViewById(R.id.email_et);
                    phone_et =fragmentView.findViewById(R.id.phone_et);*/

                    requestDetails.setPatientname(patient_name_et.getText().toString());
                    requestDetails.setReasonsforappoinment(reason_for_appointment_et.getText().toString());
                    requestDetails.setEmail(email);
                    requestDetails.setMobilenum(phone_et.getText().toString());

                    if(!requestDetails.isSelfAppointment() && TextUtils.isEmpty(requestDetails.getEmail()))
                    {
                        showMyDialog("Alert", Utils.getStringFromResources(R.string.other_appointment_create_alert_lbl),"Yes","No", new ApiErrorDialogInterface() {


                            @Override
                            public void retryClick() {
                                callAppointmentBookingMapper();
                            }
                            @Override
                            public void onCloseClick() {

                            }
                        });
                        return;
                    }
                    callAppointmentBookingMapper();

                }

            }
        });

    }


    private void callAppointmentBookingMapper() {

        AppointmentBookingMapper mapper= new AppointmentBookingMapper(requestDetails);
        mapper.setOnAppointmentBookingListener(new AppointmentBookingMapper.AppointmentBookingListener() {
            @Override
            public void getAppointmentBookingAPI(AbstractResponse response, String errorMessage) {
                if(!isValidResponse(response,errorMessage,true,false))
                {
                    return;
                }
                showSuccessAppoiintmentView(response);
                MyApplication.getInstance().setSearchResultDoctorListData(null);
            }
        });

    }

    private void showSuccessAppoiintmentView(AbstractResponse response) {
        review_view.setVisibility(View.GONE);
        success_view.setVisibility(View.VISIBLE);
        TextView requestappointment_conform_tv = fragmentView.findViewById(R.id.requestappointment_conform_tv);
        if(searchResultDoctorListData.getInstantBooking().equals("2"))
        {
            requestappointment_conform_tv.setText("Request Appointment Confirmed");
        }else{
            requestappointment_conform_tv.setText("Appointment Confirmed");
        }
        //
        appointment_bt.setText("Done");



        String  SERVER_DATE_FORMAT_NEW="dd-MM-yyyy h:mm a";//"yyyy-MM-dd h:mm a";//"2017-11-22 10:23 AM"

        String DISPLAY_DATE="MMM d, yyyy";
        String DISPLAY_TIME="h:mm a 'on' EEEE";


        SimpleDateFormat DISPLAY_DATE_FORMATTER = new SimpleDateFormat(DISPLAY_DATE);
        SimpleDateFormat DISPLAY_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_TIME);
         TextView appointment_conform_tv = fragmentView.findViewById(R.id.appointment_conform_tv);

        try {
            Date date = Utils.changeStringToDateFormat(dateTimeStr, SERVER_DATE_FORMAT_NEW);
            String dateStr= DISPLAY_DATE_FORMATTER.format(date);
            String timeStr= DISPLAY_TIME_FORMATTER.format(date);
            appointment_conform_tv.setText("with "+ searchResultDoctorListData.getDoctorName() +" \n at "+ timeStr+", "+ dateStr);
        }catch (Exception e)
        {

        }
    }

    private void setHeaderDetails() {

        if(searchResultDoctorListData==null || fragmentView==null)
        {
            return;
        }




      ImageView imageView= fragmentView.findViewById(R.id.profile_iv);

        MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.profile_icon,imageView,searchResultDoctorListData.getDoctorLogo());

        if(searchResultDoctorListData.getTeleconsultation().equals("1")) {
            fragmentView.findViewById(R.id.consultancy_type_linear).setVisibility(View.VISIBLE);
        }else{
            fragmentView.findViewById(R.id.consultancy_type_linear).setVisibility(View.GONE);
        }

        if(searchResultDoctorListData.getInstantBooking().equals("2")) {
            ((Button)fragmentView.findViewById(R.id.appointment_bt)).setText("Request Appointment Booking");
            ((TextView)fragmentView.findViewById(R.id.appointment_time_before_tv)).setText("Request Appointment:");
        }else{
            ((Button)fragmentView.findViewById(R.id.appointment_bt)).setText("Book Appointment");
            ((TextView)fragmentView.findViewById(R.id.appointment_time_before_tv)).setText("Appointment:");
        }

        ((TextView)fragmentView.findViewById(R.id.doctor_name_tv)).setText(searchResultDoctorListData.getDoctorName());
        (( TextView)fragmentView.findViewById(R.id.doctor_designation_tv)).setText(searchResultDoctorListData.getSpecalities());
        ((TextView)fragmentView.findViewById(R.id.clinic_name_tv)).setText(searchResultDoctorListData.getClinicName());
        /*
        locality
cityName
         */
        (( TextView) fragmentView.findViewById(R.id.clinic_address_tv)).setText(searchResultDoctorListData.getAddress()
                +", "+searchResultDoctorListData.getLocality()+", "+searchResultDoctorListData.getCityName());



        String  SERVER_DATE_FORMAT_NEW="dd-MM-yyyy h:mm a";//"yyyy-MM-dd h:mm a";//"2017-11-22 10:23 AM" //29-12-2017 2:00 AM

        String DISPLAY_DATE="MMM d, yyyy";
        String DISPLAY_TIME="h:mm a 'on' EEEE";


        SimpleDateFormat DISPLAY_DATE_FORMATTER = new SimpleDateFormat(DISPLAY_DATE);
        SimpleDateFormat DISPLAY_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_TIME);
         TextView time_date_display_tv = fragmentView.findViewById(R.id.time_date_display_tv);

        try {
            Date date = Utils.changeStringToDateFormat(dateTimeStr, SERVER_DATE_FORMAT_NEW);
           String dateStr= DISPLAY_DATE_FORMATTER.format(date);
            String timeStr= DISPLAY_TIME_FORMATTER.format(date);

            time_date_display_tv.setText("at "+timeStr+",  "+dateStr);
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
        relation_spinner.setSelection(0);

        relation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   String selectedRelation=null;
                    if (position != 0) {
                         selectedRelation = relations[position];
                        //Utils.showToastMessage(selectedRelation);

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

    private void setconsultationtypeSpinner() {

        // final ArrayList<String> genderTypeList = new ArrayList<>();

        final String[] consultationtype=getResources().getStringArray(R.array.consultation);


        // genderTypeList.add(Constants.GenderValues.MALE);
        //  genderTypeList.add(Constants.GenderValues.FEMALE);
        //genderTypeList.add(Constants.GenderValues.GENDER);
        // selectedGender= genderTypeList.get(2);
        final HintAdapter aa = new HintAdapter<String>(MyApplication.getCurrentActivityContext(), R.layout.spinner_black_textview, consultationtype);


        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        counsltancy_type_spinner.setAdapter(aa);
        counsltancy_type_spinner.setSelection(0);

        counsltancy_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedConsultation=null;
                if (position != 0) {
                    selectedConsultation = consultationtype[position];
                    //Utils.showToastMessage(selectedRelation);

                }
                if(requestDetails!=null)
                {
                    requestDetails.setConsultationtype(selectedConsultation);
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

    public boolean isSuccesViewShown()
    {
        return success_view.getVisibility()==View.VISIBLE;
    }


    @Override
    public void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideKeyBord(fragmentView);
            }
        },200);
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

    public void setOnDoneButtonClickListener(DoneButtonClickListener doneButtonClickListener)
    {
        this.doneButtonClickListener=doneButtonClickListener;
    }

    public interface DoneButtonClickListener
    {
        void onClick();
    }
}
