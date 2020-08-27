package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.vempower.eezyclinic.APICore.Appointment;
import com.vempower.eezyclinic.APICore.Followup;
import com.vempower.eezyclinic.APIResponce.UpcomingAppointmentListAPI;
import com.vempower.eezyclinic.APIResponce.UpcomingFollowupsAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.UpcomingAppointmentListAdapter;
import com.vempower.eezyclinic.adapters.UpcomingFollowupsListAdapter;
import com.vempower.eezyclinic.mappers.UpcomingAppointmentListMapper;
import com.vempower.eezyclinic.mappers.UpcomingFollowupMapper;
 ;

import java.util.List;

/**
 * Created by satish on 6/12/17.
 */

public class FollowupsFragment extends SwipedRecyclerViewFragment {

    private View fragmentView;
    private UpcomingFollowupsListAdapter adapter;
    //private ArrayList<SearchResultDoctorListData> doctorsList;

    //private boolean isOnlyViewList;
    private List<Followup> followupList;
    //private  TextView match_found_tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.appointment_list_fragment, container, false);

        setupSwipeRefreshLayout(fragmentView);


        refreshList();


        return fragmentView;
    }




    private void init() {

        // match_found_tv=  fragmentView.findViewById(R.id.match_found_tv);
        // match_found_tv.setText("0");
        adapter=null;

        //callSearchResultDoctorsListMapper();
        setOrderItemsToAdapter(followupList);
    }

    private void viewList()
    {
        adapter=null;
        setOrderItemsToAdapter(followupList);
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





    public void setOrderItemsToAdapter(List<Followup> followupList) {
        hideProgressView();
        ((TextView)fragmentView.findViewById(R.id.match_found_tv)).setText(followupList==null?"0":followupList.size()+"");
        fragmentView.findViewById(R.id.top_linear).setVisibility(View.GONE);
        if (adapter == null) {

            adapter = new UpcomingFollowupsListAdapter(followupList);

            recyclerView.setAdapter(adapter);
        } else {
            adapter.setUpdatedList(followupList);
        }
        if(followupList==null || followupList.size()==0)
        {
            fragmentView.findViewById(R.id.no_appointment_result_tv).setVisibility(View.VISIBLE);
        }else
        {
            fragmentView.findViewById(R.id.no_appointment_result_tv).setVisibility(View.GONE);
        }




    }
    private void callUpcomingAppointmentsMapper()
    {
        UpcomingFollowupMapper mapper= new UpcomingFollowupMapper();
        mapper.setOnUpcomingFollowupsListener(new UpcomingFollowupMapper.UpcomingFollowupsListener() {
            @Override
            public void getUpcomingFollowupsAPI(UpcomingFollowupsAPI followupsAPI, String errorMessage) {
                if(!isValidResponse(followupsAPI,errorMessage))
                {
                    return;
                }

                followupList=followupsAPI.getData().getFollowups();
                viewList();
            }
        });
    }

   /* private void callUpcomingAppointmentsMapper1() {
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

    }*/

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

   /* public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }
*/

}
