package com.vempower.eezyclinic.fragments;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.vempower.eezyclinic.APIResponce.CityData;
import com.vempower.eezyclinic.APIResponce.CityListAPI;
import com.vempower.eezyclinic.APIResponce.CountryData;
import com.vempower.eezyclinic.APIResponce.CountryListAPI;
import com.vempower.eezyclinic.APIResponce.DoctorClinicNameData;
import com.vempower.eezyclinic.APIResponce.DoctorClinicNameListAPI;
import com.vempower.eezyclinic.APIResponce.InsuranceData;
import com.vempower.eezyclinic.APIResponce.InsuranceListAPI;
import com.vempower.eezyclinic.APIResponce.LanguageData;
import com.vempower.eezyclinic.APIResponce.LanguageListAPI;
import com.vempower.eezyclinic.APIResponce.NationalityData;
import com.vempower.eezyclinic.APIResponce.NationalityListAPI;
import com.vempower.eezyclinic.APIResponce.SpecalitiesAPI;
import com.vempower.eezyclinic.APIResponce.SpecalitiyData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.DoctorsListActivity;
import com.vempower.eezyclinic.adapters.HintAdapter;
import com.vempower.eezyclinic.adapters.SpecialityListAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.core.DoctorClinicNamesSearch;
import com.vempower.eezyclinic.core.SearchRequest;
import com.vempower.eezyclinic.googleaddressselection.GeoData;
import com.vempower.eezyclinic.googleaddressselection.GooglePlacesAutocompleteAdapter;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.CityListMapper;
import com.vempower.eezyclinic.mappers.CountryListMapper;
import com.vempower.eezyclinic.mappers.DoctorClinicNamesListMapper;
import com.vempower.eezyclinic.mappers.InsuranceListMapper;
import com.vempower.eezyclinic.mappers.LanguageListMapper;
import com.vempower.eezyclinic.mappers.NationalityMapper;
import com.vempower.eezyclinic.mappers.SpecalitiesMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.CustomSpinnerSelection;
import com.vempower.eezyclinic.views.MyAutoCompleteTextView;
import com.vempower.eezyclinic.views.MyButtonRectangleRM;
import com.vempower.eezyclinic.activities.AbstractActivity;

import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
