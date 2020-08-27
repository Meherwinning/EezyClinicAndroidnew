package com.vempower.eezyclinic.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.rey.material.widget.Button;
import com.telr.mobile.sdk.activty.WebviewActivity;
import com.telr.mobile.sdk.entity.request.payment.Address;
import com.telr.mobile.sdk.entity.request.payment.App;
import com.telr.mobile.sdk.entity.request.payment.Billing;
import com.telr.mobile.sdk.entity.request.payment.MobileRequest;
import com.telr.mobile.sdk.entity.request.payment.Name;
import com.telr.mobile.sdk.entity.request.payment.Tran;
import com.vempower.eezyclinic.APICore.Appointment;
import com.vempower.eezyclinic.APICore.PatientData;
import com.vempower.eezyclinic.APICore.TeleConsultation;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.AppointmentHistoryListAdapter;
import com.vempower.eezyclinic.adapters.TeleConsultationListAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.utils.Utils;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TeleConsultationPaymentReviewFragment extends AbstractFragment {

    private View fragmentView;
    private TeleConsultationListAdapter adapter;
    //private ArrayList<SearchResultDoctorListData> doctorsList;

    //private boolean isOnlyViewList;
    private TeleConsultation teleConsultation;
    //private  TextView match_found_tv;
    private int page;
    private TextView appointment_id_tv;
    private  TextView date_time_tv;
    private  TextView clinic_name_tv;
    private  TextView doctor_name_tv;
    private  TextView consultation_fee_tv;
    //private Button makepayment_linear1;
    private PatientData patientData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.tele_consultation_review_payment_fragment, container, false);

        init();
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAppointmentDetails();
    }

    private void init() {
        appointment_id_tv = fragmentView.findViewById(R.id.appointment_id_tv);
        date_time_tv = fragmentView.findViewById(R.id.date_time_tv);
        clinic_name_tv = fragmentView.findViewById(R.id.clinic_name_tv);
        doctor_name_tv = fragmentView.findViewById(R.id.doctor_name_tv);
        consultation_fee_tv = fragmentView.findViewById(R.id.consultation_fee_tv);
    }

    private void setAppointmentDetails() {
        //TODO
        if(teleConsultation==null)
        {

            return;
        }

        String DISPLAY_DATE_TIME="d MMM yyyy, EEEE h:mm a";
        String  SERVER_DATE_FORMAT_NEW="yyyy-MM-dd HH:mm:ss";//"2017-12-26 16:55:00"
        SimpleDateFormat DISPLAY_DATE_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_DATE_TIME);


        String dateTimeStr=teleConsultation.getAppointmentDateTime();
        if(dateTimeStr!=null) {
            try {
                Date date = Utils.changeStringToDateFormat(teleConsultation.getAppointmentDateTime(), SERVER_DATE_FORMAT_NEW);
                String dateStr = DISPLAY_DATE_TIME_FORMATTER.format(date);
                // String timeStr= DISPLAY_TIME_FORMATTER.format(date);
                //With Dr. First name Middle name Last Name at 07:00 PM on Tuesday, 26-12-2017
                dateTimeStr=dateStr;
            } catch (Exception e) {

            }
        }


        appointment_id_tv.setText(teleConsultation.getPatenetRandomId());
        date_time_tv.setText(dateTimeStr);
        clinic_name_tv.setText(teleConsultation.getClinicName());
        doctor_name_tv.setText(teleConsultation.getDoctorName());
        consultation_fee_tv.setText(teleConsultation.getconsultancyFee());
    }



    @Override
    View getFragemtView() {
        return fragmentView;
    }



    public void setTeleConsultation(TeleConsultation teleConsultation) {
        this.teleConsultation = teleConsultation;
    }
}
