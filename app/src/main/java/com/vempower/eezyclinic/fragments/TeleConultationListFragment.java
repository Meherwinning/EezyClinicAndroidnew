package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.Appointment;
import com.vempower.eezyclinic.APICore.TeleConsultation;
import com.vempower.eezyclinic.APIResponce.AppointmentHistoryListAPI;
import com.vempower.eezyclinic.APIResponce.TeleConsultationListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.AppointmentHistoryListAdapter;
import com.vempower.eezyclinic.adapters.TeleConsultationListAdapter;
import com.vempower.eezyclinic.mappers.AppointmentHistoryListMapper;
import com.vempower.eezyclinic.mappers.TeleConsultationListMapper;
import com.vempower.eezyclinic.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class TeleConultationListFragment extends SwipedRecyclerViewFragment {
    private View fragmentView;
    private TeleConsultationListAdapter adapter;
    //private ArrayList<SearchResultDoctorListData> doctorsList;

    //private boolean isOnlyViewList;
    private List<TeleConsultation> teleConsultationList;
    //private  TextView match_found_tv;
    private int page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.tele_consultation_list, container, false);
        teleConsultationList= new ArrayList<>();
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
        setOrderItemsToAdapter(teleConsultationList);
    }

    private void viewList()
    {
        adapter=null;
        setOrderItemsToAdapter(teleConsultationList);
    }

    public void refreshList()
    {

        callTeleconsultationMapper();
    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }

    @Override
    protected boolean isCheckTabletOrNot() {
        return false;
    }





    public void setOrderItemsToAdapter(List<TeleConsultation> teleconsultation) {
        hideProgressView();
        ((TextView)fragmentView.findViewById(R.id.match_found_tv)).setText(teleconsultation==null?"0":teleconsultation.size()+"");
        fragmentView.findViewById(R.id.top_linear).setVisibility(View.GONE);

        if (adapter == null) {

            adapter = new TeleConsultationListAdapter(teleconsultation);

            recyclerView.setAdapter(adapter);
        } else {
            adapter.setUpdatedList(teleconsultation);
        }
        if(teleconsultation==null || teleconsultation.size()==0)
        {
            fragmentView.findViewById(R.id.no_teleconsultation_result_tv).setVisibility(View.VISIBLE);
        }else
        {
            fragmentView.findViewById(R.id.no_teleconsultation_result_tv).setVisibility(View.GONE);
        }

    }

    private void callTeleconsultationMapper() {

        TeleConsultationListMapper mapper= new TeleConsultationListMapper(page) ;

        mapper.setOnTeleConsultationListListener(new TeleConsultationListMapper.TeleConsultationListListener() {
            @Override
            public void getTeleconsultation(TeleConsultationListAPI teleconsultationListAPI, String errorMessage) {

                if(!isValidResponse(teleconsultationListAPI,errorMessage))
                {
                    return;
                }

                if((teleconsultationListAPI.getData()==null || teleconsultationListAPI.getData().size()==0) && page!=1 )
                {
                    Utils.showToastMsg("No more Appointment(s) found");
                    page--;
                    return;
                }

                if(teleconsultationListAPI.getData()!=null) {
                    teleConsultationList.addAll(teleconsultationListAPI.getData());
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
        teleConsultationList= new ArrayList<>();
        page=1;
        callTeleconsultationMapper();

    }

    @Override
    protected void fromBottomScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }
        page=page+1;
        callTeleconsultationMapper();
        //  callSearchResultDoctorsListMapper();
    }

    public void setTeleConsultationList(List<TeleConsultation> teleConsultationList) {
        this.teleConsultationList = teleConsultationList;
    }
}
