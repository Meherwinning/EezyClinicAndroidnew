package com.vempower.eezyclinic.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vempower.eezyclinic.APICore.Appointment;
import com.vempower.eezyclinic.APIResponce.AppointmentHistoryListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.AppointmentHistoryListAdapter;
import com.vempower.eezyclinic.mappers.AppointmentHistoryListMapper;
import com.vempower.eezyclinic.utils.Utils;
 ;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by satish on 6/12/17.
 */

public class AppointmentHistoryFragment extends AbstractFragment {

    private View fragmentView;
    private AppointmentHistoryListAdapter adapter;
    //private ArrayList<SearchResultDoctorListData> doctorsList;

    //private boolean isOnlyViewList;
    private Appointment appointment;
    //private  TextView match_found_tv;
    private int page;
    private TextView appointment_id_tv;
    private  TextView date_time_tv;
    private  TextView check_in_time_tv;
    private  TextView check_out_time_tv;
    private  TextView waiting_time_tv;
    private  TextView consoltation_time_tv;
    private  TextView reason_for_app_tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.appointment_history_fragment, container, false);

        init();
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAppointmentDetails();
    }

    private void init() {
        appointment_id_tv =fragmentView.findViewById(R.id.appointment_id_tv);
        date_time_tv =fragmentView.findViewById(R.id.date_time_tv);
        check_in_time_tv =fragmentView.findViewById(R.id.check_in_time_tv);
        check_out_time_tv=fragmentView.findViewById(R.id. check_out_time_tv);
        waiting_time_tv =fragmentView.findViewById(R.id. waiting_time_tv);
        consoltation_time_tv=fragmentView.findViewById(R.id. consoltation_time_tv);
        reason_for_app_tv =fragmentView.findViewById(R.id. reason_for_app_tv);

    }

    private void setAppointmentDetails() {
        //TODO
        if(appointment==null)
        {

            return;
        }

        String DISPLAY_DATE_TIME="d MMM yyyy, EEEE h:mm a";
        String  SERVER_DATE_FORMAT_NEW="yyyy-MM-dd HH:mm:ss";//"2017-12-26 16:55:00"
        SimpleDateFormat DISPLAY_DATE_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_DATE_TIME);


        String dateTimeStr=appointment.getAppointmentDateTime();
        if(dateTimeStr!=null) {
            try {
                Date date = Utils.changeStringToDateFormat(appointment.getAppointmentDateTime(), SERVER_DATE_FORMAT_NEW);
                String dateStr = DISPLAY_DATE_TIME_FORMATTER.format(date);
                // String timeStr= DISPLAY_TIME_FORMATTER.format(date);
                //With Dr. First name Middle name Last Name at 07:00 PM on Tuesday, 26-12-2017
                dateTimeStr=dateStr;
            } catch (Exception e) {

            }
        }


        appointment_id_tv.setText(appointment.getId());
        date_time_tv.setText(dateTimeStr);
        check_in_time_tv.setText(appointment.getPatientInTime()==null?"-":appointment.getPatientInTime());
        check_out_time_tv.setText(appointment.getPatientOutTime()==null?"-":appointment.getPatientOutTime());
        waiting_time_tv.setText(appointment.getWaitingTime()==null?"-":appointment.getWaitingTime());
        consoltation_time_tv.setText(appointment.getConsultationTime()==null?"-":appointment.getConsultationTime());
        reason_for_app_tv.setText(appointment.getReasonsForAppoinment()==null?"-":appointment.getReasonsForAppoinment());

    }


    @Override
    View getFragemtView() {
        return fragmentView;
    }



    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }


}
