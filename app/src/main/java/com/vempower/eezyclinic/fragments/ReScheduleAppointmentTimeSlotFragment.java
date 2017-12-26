package com.vempower.eezyclinic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.vempower.eezyclinic.APICore.Appointment;
import com.vempower.eezyclinic.APICore.ReScheduleAppointmentRequestDetails;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.APIResponce.AppointmentTimeSlotsAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.AppointmentBookReviewActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.interfaces.AbstractIBinder;
import com.vempower.eezyclinic.interfaces.IntentObjectListener;
import com.vempower.eezyclinic.mappers.DoctorAppointmentTimeSlotsListMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyTextViewRR;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by challa on 23/12/17.
 */

public class ReScheduleAppointmentTimeSlotFragment extends AbstractCalenderViewFragment {

    private ReScheduleAppointmentRequestDetails reScheduleDetails;

    private String disabledDateTime;

    /*
        {
            "doc_id": 46,
            "branch_id": 40,
            "appt_date": "2018-01-16"
    }
         */

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCurrentDate();
    }

    protected  void dateSelectFromCalender(final String dateStr)
    {
        if(reScheduleDetails==null)
        {
            Utils.showToastMessage("Invalid doctor object");
            return;
        }


        callTimeSlotMapper(reScheduleDetails.doctor_id,reScheduleDetails.branch_id,dateStr);
    }

    @Override
    protected void clickOnConfirmButton(String confirmDateTime) {
        Utils.showToastMessage(confirmDateTime);
        if(!TextUtils.isEmpty(confirmDateTime))
        {
            reScheduleDetails.setNew_appointmenttime(confirmDateTime);

            Utils.showToastMsg(reScheduleDetails.toString());




        }

    }

    protected void callTimeSlotMapper(int doctId,int branchId,final String dateStr)
    {
        DoctorAppointmentTimeSlotsListMapper mapper = new DoctorAppointmentTimeSlotsListMapper( doctId, branchId, dateStr);
        mapper.setOnAppointmentTimeSlotsListenerr(new DoctorAppointmentTimeSlotsListMapper.AppointmentTimeSlotsListener() {
            @Override
            public void getAppointmentTimeSlotsListAPI(AppointmentTimeSlotsAPI timeSlotsAPI, String errorMessage) {
                if(!isValidResponse(timeSlotsAPI,errorMessage))
                {
                    return;
                }

                if(timeSlotsAPI.getData()==null)
                {
                    Utils.showToastMessage("Time slots data ");
                    return;
                }
                /*
                "08:00 AM| 1",1ew2432w1
                
                "08:45 AM| 1",
                "09:30 AM| 1",
                 */
                List<String> slots = timeSlotsAPI.getData().getFirst().getSlots();

                setOrderItemsToAdapter(dateStr,slots);

            }
        });
    }

    public String getDiabledDateAndtime() {
        return disabledDateTime;
    }


    public void setReScheduleDetails(ReScheduleAppointmentRequestDetails reScheduleDetails) {
        this.reScheduleDetails = reScheduleDetails;

        String DISPLAY_DATE_TIME="yyyy-MM-dd h:mm a";//15-12-2017 05:00 PM
       // String DISPLAY_TIME="h:mm a 'on' EEEE";
        String  SERVER_DATE_FORMAT_NEW="yyyy-MM-dd HH:mm:ss";//"2017-12-26 16:55:00"
        SimpleDateFormat DISPLAY_DATE_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_DATE_TIME);
       // SimpleDateFormat DISPLAY_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_TIME);

        try {
            Date date = Utils.changeStringToDateFormat(reScheduleDetails.appointmentDateTime, SERVER_DATE_FORMAT_NEW);
            disabledDateTime= DISPLAY_DATE_TIME_FORMATTER.format(date);
            //String timeStr= DISPLAY_TIME_FORMATTER.format(date);
            //With Dr. First name Middle name Last Name at 07:00 PM on Tuesday, 26-12-2017
           // appointment_details_tv.setText("With "+ data.getDoctorName() +" at "+ timeStr+", "+ dateStr+"\nat "+data.getAddress());
        }catch (Exception e)
        {
            //appointment_details_tv.setText("-");
            disabledDateTime=null;

        }

    }
}
