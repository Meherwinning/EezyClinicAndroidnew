package com.vempower.eezyclinic.fragments;

import com.vempower.eezyclinic.APIResponce.AppointmentTimeSlotsAPI;
import com.vempower.eezyclinic.mappers.DoctorAppointmentTimeSlotsListMapper;
import com.vempower.eezyclinic.utils.Utils;

import java.util.List;

/**
 * Created by challa on 23/12/17.
 */

public class ScheduleAppointmentTimeSlotFragment extends AbstractCalenderViewFragment {

    /*
    {
        "doc_id": 46,
        "branch_id": 40,
        "appt_date": "2018-01-16"
}
     */
    protected  void dateSelectFromCalender(final String dateStr)
    {
        callTimeSlotMapper(46,40,dateStr);
    }

    @Override
    protected void clickOnConfirmButton(String confirmDateTime) {
        Utils.showToastMessage(confirmDateTime);
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
                "08:00 AM| 1",
                "08:45 AM| 1",
                "09:30 AM| 1",
                 */
                List<String> slots = timeSlotsAPI.getData().getFirst().getSlots();

                setOrderItemsToAdapter(dateStr,slots);

            }
        });
    }
}
