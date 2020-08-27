package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vempower.eezyclinic.APIResponce.SpecalitiesAPI;
import com.vempower.eezyclinic.APIResponce.SpecalitiyData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.SpecialityListAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.SpecalitiesMapper;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.activities.AbstractActivity;

import java.util.List;

/**
 * Created by satish on 6/12/17.
 */

public class NewHomeFragment extends SwipedAutoFitRecyclerViewFragment{

    private View fragmentView;

    private SpecialityListAdapter adapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.new_home_layout, container, false);
        setupSwipeRefreshLayout(fragmentView);
        init();

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callSpecialityMapper();
    }

    private void init() {


    }




    private void callSpecialityMapper() {
        MyApplication.showTransparentDialog();
        SpecalitiesMapper mapper = new SpecalitiesMapper();
        mapper.setOnSpecalitiesdListener(new SpecalitiesMapper.SpecalitiesdListener() {
            @Override
            public void getSpecalitiesAPII(SpecalitiesAPI specalitiesAPI, String errorMessage) {
                MyApplication.hideTransaprentDialog();
                if ((!isValidResponse(specalitiesAPI, errorMessage))|| specalitiesAPI.getData()==null) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_speciality_list_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {
                            ((AbstractActivity) MyApplication.getCurrentActivityContext()). finish();
                        }

                        @Override
                        public void retryClick() {
                            callSpecialityMapper();
                        }
                    });
                    return;
                }
                List<SpecalitiyData> dataList = specalitiesAPI.getData();
                setSpecialitiesToAdapter(dataList);

            }
        });
    }


    private void setSpecialitiesToAdapter( List<SpecalitiyData> dataList)
    {
        if(adapter==null)
        {
            adapter= new SpecialityListAdapter(dataList);
            recyclerView.setAdapter(adapter);
        }else
        {
            adapter.setUpdatedList(dataList);
        }

    }


    @Override
    protected boolean isCheckTabletOrNot() {
        return false;
    }


    @Override
    protected void fromTopScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }

    }

    @Override
    protected void fromBottomScroll() {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(false);
        }

    }
}
