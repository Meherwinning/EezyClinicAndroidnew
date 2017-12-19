package com.vempower.eezyclinic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.APIResponce.SearchResultDoctorListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.DoctorProfileActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.core.SearchRequest;
import com.vempower.eezyclinic.interfaces.AbstractIBinder;
import com.vempower.eezyclinic.interfaces.GoogleMarkerClickListener;
import com.vempower.eezyclinic.interfaces.IntentObjectListener;
import com.vempower.eezyclinic.mappers.SearchResultDoctorsListMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by challa on 12/12/17.
 */

public class DoctorsMapFragment extends AbstractMapFragment/*, GoogleMap.OnMarkerClickListener */ {


    private List<SearchResultDoctorListData> doctorsLsit;
    private boolean isOnlyViewList;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);


        if(isOnlyViewList)
        {
            showMapDetails();
        }else
        {
            refreshMap();
        }
        isOnlyViewList=true;
    }

    public void isViewOnlyList(boolean isOnlyViewList)
    {
        this.isOnlyViewList=isOnlyViewList;
    }



    public DoctorsMapFragment()
    {
        setOnGoogleMarkerClickListener(new GoogleMarkerClickListener() {
            @Override
            public void onClick(int id) {

                SearchResultDoctorListData temp= new SearchResultDoctorListData();
                temp.setDocId(id+"");
              final int index= doctorsLsit.lastIndexOf(temp);
               if(index>=0 && doctorsLsit.get(index)!=null) {

                   Intent intent = new Intent(MyApplication.getCurrentActivityContext(), DoctorProfileActivity.class);
                           /*((Activity) MyApplication.getCurrentActivityContext()).getIntent();*/
                   intent.putExtra(ListenerKey.ObjectKey.SEARCH_RESULT_DOCTOR_LIST_DATA_KEY, new Messenger(new AbstractIBinder() {
                       @Override
                       protected IntentObjectListener getMyObject() {
                           return new IntentObjectListener() {

                               @Override
                               public Object getObject() {
                                   return doctorsLsit.get(index);
                               }
                           };
                       }
                   }));
                   MyApplication.getCurrentActivityContext().startActivity(intent);
               }


            }
        });
    }


    public void setDoctorsList(List<SearchResultDoctorListData> doctorsLsit) {
        this.doctorsLsit = doctorsLsit;
    }

    public void refreshMap() {
        SearchRequest requestParms = MyApplication.getInstance().getSearchRequestParms();
        if(requestParms==null)
        {
            requestParms= new SearchRequest(Constants.RESULT_PAGE_ITEMS_LIMIT1);
        }
        requestParms.setPage("1");
        callSearchResultDoctorsListMapper(requestParms);
    }



    private void callSearchResultDoctorsListMapper( final SearchRequest requestParms)
    {
        clearMap();
        SearchResultDoctorsListMapper mapper=new SearchResultDoctorsListMapper(requestParms);

        mapper.setOnSearchResultDoctorListAPItListener(new SearchResultDoctorsListMapper.SearchResultDoctorListAPItListener() {
            @Override
            public void getSearchResultDoctorListAPI(SearchResultDoctorListAPI searchResultDoctorListAPI, String errorMessage) {
                if(!isValidResponse(searchResultDoctorListAPI,errorMessage))
                {
                    return;
                }
                if(!(requestParms.getPage().equalsIgnoreCase("1")) && (searchResultDoctorListAPI.getData()==null ||searchResultDoctorListAPI.getData().size()==0) )
                {
                    Utils.showToastMsg(R.string.no_more_doctors_found_lbl);
                    return;

                }
               /* if(searchResultDoctorListAPI.getData()!=null )
                {
                    doctorsList.addAll(searchResultDoctorListAPI.getData());
                }*/
                // Utils.showToastMessage(searchResultDoctorListAPI.toString());
               // setOrderItemsToAdapter(doctorsList);
                setDoctorsList(searchResultDoctorListAPI.getData());
                showMapDetails();
            }
        });
    }


    protected LatLngBounds.Builder addAllMarkersToMap(GoogleMap mMap) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if(doctorsLsit==null || doctorsLsit.size()==0)
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.VISIBLE);
        }else
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.GONE);
        }
        if(doctorsLsit==null || doctorsLsit.size()==0)
        {
            return null;
        }
      //  MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.path_352_2));
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.path_352_2);

        for (SearchResultDoctorListData data : doctorsLsit) {

            if (data == null) {
                continue;
            }
            double lat,lon;

            try
            {
                lat =Double.parseDouble(data.getGoogleMapLatitude());
                lon=Double.parseDouble(data.getGoogleMapLongitude());
            }catch(Exception e)
            {
                continue;
            }
            // GeoLocation geoLocation= deal.getLocation().getGeoLocation();
            LatLng latLng = new LatLng(lat, lon);
            MarkerOptions options = new MarkerOptions();
              options.position(latLng);
            options.icon(bitmapDescriptor);
            options.title(data.getDoctorName());
             String snippet = data.getSpecalities() + "\n" + data.getCityName() + ", " + data.getAddress();
             options.snippet(snippet);
             options.zIndex(Float.parseFloat(data.getDocId()));

            builder.include(latLng);


            mMap.addMarker(options);
        }
        return builder;
    }

}