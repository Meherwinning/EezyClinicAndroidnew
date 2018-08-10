package com.vempower.eezyclinic.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.vempower.eezyclinic.APICore.Followup;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.APIResponce.AppointmentTimeSlotsAPI;
import com.vempower.eezyclinic.activities.AppointmentBookReviewActivity;
import com.vempower.eezyclinic.activities.CasesheetsDetailsActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.interfaces.AbstractIBinder;
import com.vempower.eezyclinic.interfaces.IntentObjectListener;
import com.vempower.eezyclinic.mappers.DoctorAppointmentTimeSlotsListMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by challa on 23/12/17.
 */

public class FollowupAppointmentTimeSlotFragment extends AbstractCalenderViewFragment {

    //private SearchResultDoctorListData searchResultDoctorListData;
    private Followup data;

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
        if(data!=null)
        {
            Date date = Utils.changeStringToDateFormat(data.getUpcomingVist(), SERVER_DATE_FORMAT_NEW);

            Calendar calendar= Calendar.getInstance();
            if(date!=null)
            {
                calendar.setTime(date);
            }
            setCalderDay(calendar);

        }else
        {
            setCurrentDate();
        }

    }

    protected  void dateSelectFromCalender(final String dateStr)
    {
        if(data==null)
        {
            Utils.showToastMessage("Invalid details");
            return;
        }
        int docId=-1;
        int branchId=-1;

        try
        {
            docId= Integer.parseInt(data.getDoctorId());
            branchId= Integer.parseInt(data.getBranchId());
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

        //2017-12-28 02:15 PM
        //15-12-2017 05:00 PM

        if(confirmDateTime==null)
        {
            Utils.showToastMsg("Please select time slot");
            return;
        }


        String DISPLAY_DATE_TIME="dd-MM-yyyy h:mm a";//15-12-2017 05:00 PM
        // String DISPLAY_TIME="h:mm a 'on' EEEE";
        String  SERVER_DATE_FORMAT_NEW="yyyy-MM-dd h:mm a";//"2017-12-26 16:55:00"
        SimpleDateFormat DISPLAY_DATE_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_DATE_TIME);
        // SimpleDateFormat DISPLAY_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_TIME);

        try {
            Date date = Utils.changeStringToDateFormat(confirmDateTime, SERVER_DATE_FORMAT_NEW);
            confirmDateTime= DISPLAY_DATE_TIME_FORMATTER.format(date);
            //String timeStr= DISPLAY_TIME_FORMATTER.format(date);
            //With Dr. First name Middle name Last Name at 07:00 PM on Tuesday, 26-12-2017
            // appointment_details_tv.setText("With "+ data.getDoctorName() +" at "+ timeStr+", "+ dateStr+"\nat "+data.getAddress());
        }catch (Exception e)
        {
            //appointment_details_tv.setText("-");
            Utils.showToastMessage("Invalid date format");
           return;

        }

       final SearchResultDoctorListData  searchResultDoctorListData= new SearchResultDoctorListData();


        searchResultDoctorListData.setBranchName(data.getBranchName());
        searchResultDoctorListData.setDoctorLogo(data.getDoctorLogo());
        searchResultDoctorListData.setClinicName(data.getClinicName());
        searchResultDoctorListData.setAddress(data.getAddress());


        searchResultDoctorListData.setDoctorName(data.getDoctorName());
        searchResultDoctorListData.setSpecalities(data.getSpecalities());

        searchResultDoctorListData.setDocId(data.getDoctorId());
        searchResultDoctorListData.setBranchId(data.getBranchId());


       // Utils.showToastMessage(confirmDateTime);
        if(!TextUtils.isEmpty(confirmDateTime))
        {

            Intent intent = ((Activity)MyApplication.getCurrentActivityContext()).getIntent();
            intent.setClass(MyApplication.getCurrentActivityContext(),AppointmentBookReviewActivity.class);
                           /*((Activity) MyApplication.getCurrentActivityContext()).getIntent();*/

            intent.putExtra(Constants.Pref.SELECTED_SCHDULE_DATE_TIME_KEY,confirmDateTime);
            intent.putExtra(ListenerKey.ObjectKey.SEARCH_RESULT_DOCTOR_LIST_DATA_KEY,new Messenger(new AbstractIBinder(){
                @Override
                protected IntentObjectListener getMyObject() {
                    return new IntentObjectListener(){

                        @Override
                        public Object getObject() {
                            return searchResultDoctorListData;
                        }
                    };
                }
            }));
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);

            MyApplication.getCurrentActivityContext().startActivity(intent);


           /* {
                AbstractMenuActivity menuActivity = ((AbstractMenuActivity) MyApplication.getCurrentActivityContext());

                Intent intent = menuActivity.getIntent();

                intent.setClass(MyApplication.getCurrentActivityContext(), AppointmentBookReviewActivity.class);
                menuActivity.startActivity(intent);
            }*/

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

    public void setSearchResultDoctorListData(Followup data) {
        this.data = data;
    }
}
