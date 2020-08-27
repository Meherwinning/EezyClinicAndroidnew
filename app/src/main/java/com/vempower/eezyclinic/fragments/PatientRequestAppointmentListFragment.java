package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.PatientRequestAppointment;
import com.vempower.eezyclinic.APICore.TeleConsultation;
import com.vempower.eezyclinic.APIResponce.PatientRequestAppointmentListAPI;
import com.vempower.eezyclinic.APIResponce.TeleConsultationListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.PatientRequestApoointmentListAdapter;
import com.vempower.eezyclinic.adapters.TeleConsultationListAdapter;
import com.vempower.eezyclinic.mappers.PatientRequestAppointmentListMapper;
import com.vempower.eezyclinic.mappers.TeleConsultationListMapper;
import com.vempower.eezyclinic.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class PatientRequestAppointmentListFragment extends SwipedRecyclerViewFragment {
    private View fragmentView;
    private PatientRequestApoointmentListAdapter adapter;
    //private ArrayList<SearchResultDoctorListData> doctorsList;

    //private boolean isOnlyViewList;
    private List<PatientRequestAppointment> patientRequestAppointmentList;
    //private  TextView match_found_tv;
    private int page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.patient_request_appointment_list, container, false);
        patientRequestAppointmentList= new ArrayList<>();
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
        setOrderItemsToAdapter(patientRequestAppointmentList);
    }

    private void viewList()
    {
        adapter=null;
        setOrderItemsToAdapter(patientRequestAppointmentList);
    }

    public void refreshList()
    {

        callPatinetRequestAppointmentMapper();
    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }

    @Override
    protected boolean isCheckTabletOrNot() {
        return false;
    }





    public void setOrderItemsToAdapter(List<PatientRequestAppointment> patientRequestAppointments) {
        hideProgressView();
        ((TextView)fragmentView.findViewById(R.id.match_found_tv)).setText(patientRequestAppointments==null?"0":patientRequestAppointments.size()+"");
        fragmentView.findViewById(R.id.top_linear).setVisibility(View.GONE);

        if (adapter == null) {

            adapter = new PatientRequestApoointmentListAdapter(patientRequestAppointments);

            recyclerView.setAdapter(adapter);
        } else {
            adapter.setUpdatedList(patientRequestAppointments);
        }
        if(patientRequestAppointments==null || patientRequestAppointments.size()==0)
        {
            fragmentView.findViewById(R.id.no_patient_request_appointment_result_tv).setVisibility(View.VISIBLE);
        }else
        {
            fragmentView.findViewById(R.id.no_patient_request_appointment_result_tv).setVisibility(View.GONE);
        }

    }

    private void callPatinetRequestAppointmentMapper() {

        PatientRequestAppointmentListMapper mapper= new PatientRequestAppointmentListMapper(page) ;

        mapper.setOnPatientRequestAppointmentListListener(new PatientRequestAppointmentListMapper.PatientRequestAppointmentListListener() {
            @Override
            public void getPatientRequestappointment(PatientRequestAppointmentListAPI patientRequestAppointmentListAPI, String errorMessage) {

                if(!isValidResponse(patientRequestAppointmentListAPI,errorMessage))
                {
                    return;
                }

                if((patientRequestAppointmentListAPI.getData()==null || patientRequestAppointmentListAPI.getData().size()==0) && page!=1 )
                {
                    Utils.showToastMsg("No more Appointment(s) found");
                    page--;
                    return;
                }

                if(patientRequestAppointmentListAPI.getData()!=null) {
                    patientRequestAppointmentList.addAll(patientRequestAppointmentListAPI.getData());
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
        patientRequestAppointmentList= new ArrayList<>();
        page=1;
        callPatinetRequestAppointmentMapper();

    }

    @Override
    protected void fromBottomScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }
        page=page+1;
        callPatinetRequestAppointmentMapper();
        //  callSearchResultDoctorsListMapper();
    }

    public void setTeleConsultationList(List<PatientRequestAppointment> patientRequestAppointmentList) {
        this.patientRequestAppointmentList = patientRequestAppointmentList;
    }
}
