package com.vempower.eezyclinic.fragments;

import android.content.Intent;
import android.text.TextUtils;

import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.APIResponce.AppointmentTimeSlotsAPI;
import com.vempower.eezyclinic.activities.AbstractMenuActivity;
import com.vempower.eezyclinic.activities.AppointmentBookReviewActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.mappers.DoctorAppointmentTimeSlotsListMapper;
import com.vempower.eezyclinic.utils.Utils;

import java.util.List;

/**
 * Created by challa on 23/12/17.
 */

public class ScheduleAppointmentTimeSlotFragment extends AbstractCalenderViewFragment {

    private SearchResultDoctorListData searchResultDoctorListData;

    /*
        {
            "doc_id": 46,
            "branch_id": 40,
            "appt_date": "2018-01-16"
    }
         */
    protected  void dateSelectFromCalender(final String dateStr)
    {
        if(searchResultDoctorListData==null)
        {
            Utils.showToastMessage("Invalid doctor object");
            return;
        }
        int docId=-1;
        int branchId=-1;

        try
        {
            docId= Integer.parseInt(searchResultDoctorListData.getDocId());
            branchId= Integer.parseInt(searchResultDoctorListData.getBranchId());
        }catch (Exception e)
        {
            Utils.showToastMessage("Invalid doctor id and branch id----1");
            return;
        }

        if(docId<=0 || branchId<=0)
        {
            Utils.showToastMessage("Invalid doctor id and branch id-----2");
            return;

        }

        callTimeSlotMapper(docId,branchId,dateStr);
    }

    @Override
    protected void clickOnConfirmButton(String confirmDateTime) {
        Utils.showToastMessage(confirmDateTime);
        if(!TextUtils.isEmpty(confirmDateTime))
        {
            AbstractMenuActivity menuActivity= ((AbstractMenuActivity) MyApplication.getCurrentActivityContext());

            Intent intent = menuActivity.getIntent();

            intent.setClass(MyApplication.getCurrentActivityContext(),AppointmentBookReviewActivity.class);
            menuActivity.startActivity(intent);

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

    public void setSearchResultDoctorListData(SearchResultDoctorListData searchResultDoctorListData) {
        this.searchResultDoctorListData = searchResultDoctorListData;
    }
}
