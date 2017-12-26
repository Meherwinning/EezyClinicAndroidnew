package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vempower.eezyclinic.APICore.Appointment;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.APIResponce.SearchResultDoctorListAPI;
import com.vempower.eezyclinic.APIResponce.UpcomingAppointmentListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.DoctorsListAdapter;
import com.vempower.eezyclinic.adapters.UpcomingAppointmentListAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.core.SearchRequest;
import com.vempower.eezyclinic.interfaces.HomeListener;
import com.vempower.eezyclinic.mappers.SearchResultDoctorsListMapper;
import com.vempower.eezyclinic.mappers.UpcomingAppointmentListMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyTextViewRR;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satish on 6/12/17.
 */

public class UpcomingAppointmentListFragment extends SwipedRecyclerViewFragment {

    private View fragmentView;
    private UpcomingAppointmentListAdapter adapter;
    //private ArrayList<SearchResultDoctorListData> doctorsList;

    private boolean isOnlyViewList;
    private List<Appointment> appointmentList;
    //private MyTextViewRR match_found_tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.appointment_list_fragment, container, false);

        setupSwipeRefreshLayout(fragmentView);

        if(isOnlyViewList)
        {
            viewList();
        }else
        {
            refreshList();
        }
        isOnlyViewList=true;

        return fragmentView;
    }



    public void isViewOnlyList(boolean isOnlyViewList)
    {
        this.isOnlyViewList=isOnlyViewList;
    }

    private void init() {

       // match_found_tv=  fragmentView.findViewById(R.id.match_found_tv);
       // match_found_tv.setText("0");
        adapter=null;

        //callSearchResultDoctorsListMapper();
        setOrderItemsToAdapter(appointmentList);
    }

    private void viewList()
    {
        adapter=null;
        setOrderItemsToAdapter(appointmentList);
    }

    private void refreshList()
    {
        init();

    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }

    @Override
    protected boolean isCheckTabletOrNot() {
        return false;
    }





    public void setOrderItemsToAdapter(List<Appointment> appointments) {
        hideProgressView();
        ((MyTextViewRR)fragmentView.findViewById(R.id.match_found_tv)).setText(appointments==null?"0":appointments.size()+"");

        if (adapter == null) {

            adapter = new UpcomingAppointmentListAdapter(appointments);

            recyclerView.setAdapter(adapter);
        } else {
            adapter.setUpdatedList(appointments);
        }
        if(appointments==null || appointments.size()==0)
        {
            fragmentView.findViewById(R.id.no_appointment_result_tv).setVisibility(View.VISIBLE);
        }else
        {
            fragmentView.findViewById(R.id.no_appointment_result_tv).setVisibility(View.GONE);
        }




    }

    private void callUpcomingAppointmentsMapper() {
        UpcomingAppointmentListMapper appointmentListMapper= new UpcomingAppointmentListMapper();
        appointmentListMapper.setOnUpcomingAppointmentListListener(new UpcomingAppointmentListMapper.UpcomingAppointmentListListener() {
            @Override
            public void getAppointments(final UpcomingAppointmentListAPI upcomingAppointmentListAPI, String errorMessage) {

                if(!isValidResponse(upcomingAppointmentListAPI,errorMessage))
                {
                     return;
                }

                appointmentList=upcomingAppointmentListAPI.getData();
                viewList();
            }
        });

    }

    @Override
    protected void fromTopScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }
        callUpcomingAppointmentsMapper();

    }

    @Override
    protected void fromBottomScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }
      //  callSearchResultDoctorsListMapper();
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }
}
