package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.Appointment;
import com.vempower.eezyclinic.APIResponce.AppointmentHistoryListAPI;
import com.vempower.eezyclinic.APIResponce.UpcomingAppointmentListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.AppointmentHistoryListAdapter;
import com.vempower.eezyclinic.adapters.UpcomingAppointmentListAdapter;
import com.vempower.eezyclinic.mappers.AppointmentHistoryListMapper;
import com.vempower.eezyclinic.mappers.UpcomingAppointmentListMapper;
import com.vempower.eezyclinic.utils.Utils;
 ;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satish on 6/12/17.
 */

public class AppointmentHistoryListFragment extends SwipedRecyclerViewFragment {

    private View fragmentView;
    private AppointmentHistoryListAdapter adapter;
    //private ArrayList<SearchResultDoctorListData> doctorsList;

    //private boolean isOnlyViewList;
    private List<Appointment> appointmentList;
    //private  TextView match_found_tv;
    private int page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.appointment_list_fragment, container, false);
        appointmentList= new ArrayList<>();
        setupSwipeRefreshLayout(fragmentView);
        page=1;


        refreshList();


        return fragmentView;
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

    public void refreshList()
    {

        callUpcomingAppointmentsMapper();
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
        ((TextView)fragmentView.findViewById(R.id.match_found_tv)).setText(appointments==null?"0":appointments.size()+"");
        fragmentView.findViewById(R.id.top_linear).setVisibility(View.GONE);

        if (adapter == null) {

            adapter = new AppointmentHistoryListAdapter(appointments);

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

        AppointmentHistoryListMapper mapper= new AppointmentHistoryListMapper(page) ;

        mapper.setOnAppointmentHistoryListListener(new AppointmentHistoryListMapper.AppointmentHistoryListListener() {
            @Override
            public void getAppointments(AppointmentHistoryListAPI appointmentHistoryListAPI, String errorMessage) {

                if(!isValidResponse(appointmentHistoryListAPI,errorMessage))
                {
                    return;
                }

                if((appointmentHistoryListAPI.getData()==null || appointmentHistoryListAPI.getData().size()==0) && page!=1 )
                {
                    Utils.showToastMsg("No more Appointment(s) found");
                    page--;
                    return;
                }

                if(appointmentHistoryListAPI.getData()!=null) {
                    appointmentList.addAll(appointmentHistoryListAPI.getData());
                }
                viewList();
            }
        });
    }

    @Override
    protected void fromTopScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }
        appointmentList= new ArrayList<>();
        page=1;
        callUpcomingAppointmentsMapper();

    }

    @Override
    protected void fromBottomScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }
        page=page+1;
        callUpcomingAppointmentsMapper();
      //  callSearchResultDoctorsListMapper();
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }


}
