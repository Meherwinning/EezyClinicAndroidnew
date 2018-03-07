package com.vempower.eezyclinic.fragments;

import android.content.Intent;
import android.os.Messenger;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vempower.eezyclinic.APICore.SearchResultClinicData;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.APIResponce.SearchResultClinicListAPI;
import com.vempower.eezyclinic.APIResponce.SearchResultDoctorListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.ClinicProfileActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.core.SearchRequest;
import com.vempower.eezyclinic.interfaces.AbstractIBinder;
import com.vempower.eezyclinic.interfaces.GoogleMarkerClickListener;
import com.vempower.eezyclinic.interfaces.IntentObjectListener;
import com.vempower.eezyclinic.mappers.SearchResultClinicListMapper;
import com.vempower.eezyclinic.mappers.SearchResultDoctorsListMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
 ;
import com.vempower.eezyclinic.activities.AbstractActivity;

import java.util.List;

/**
 * Created by challa on 12/12/17.
 */

public class ClinicsMapFragment extends AbstractMapFragment /*, GoogleMap.OnMarkerClickListener */ {


    private List<SearchResultClinicData> clinicsLsit;
    private boolean isOnlyViewList;
    private boolean isFinish;

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



    public ClinicsMapFragment()
    {
        setOnGoogleMarkerClickListener(new GoogleMarkerClickListener() {
            @Override
            public void onClick(int id) {

                SearchResultClinicData temp= new SearchResultClinicData();
                temp.setClncId(id+"");
                final int index= clinicsLsit.lastIndexOf(temp);
                if(index>=0 && clinicsLsit.get(index)!=null) {


                    Intent intent = new Intent(MyApplication.getCurrentActivityContext(), ClinicProfileActivity.class);
                           /*((Activity) MyApplication.getCurrentActivityContext()).getIntent();*/
                    intent.putExtra(ListenerKey.ObjectKey.SEARCH_RESULT_CLINIC_LIST_DATA_KEY, new Messenger(new AbstractIBinder() {
                        @Override
                        protected IntentObjectListener getMyObject() {
                            return new IntentObjectListener() {

                                @Override
                                public Object getObject() {
                                    return clinicsLsit.get(index);
                                }
                            };
                        }
                    }));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);

                    MyApplication.getCurrentActivityContext().startActivity(intent);

                    if(isFinish)
                    {
                        ((AbstractActivity) MyApplication.getCurrentActivityContext()).finish();
                    }
                }

            }
        });
    }


    public void setClinicsList(List<SearchResultClinicData> clinicsLsit) {
        this.clinicsLsit = clinicsLsit;
    }

    public void refreshMap() {
        SearchRequest requestParms = MyApplication.getInstance().getSearchRequestParms();
        if(requestParms==null)
        {
            requestParms= new SearchRequest(Constants.RESULT_PAGE_ITEMS_LIMIT1);
        }
        requestParms.setPage("1");
        callSearchResultClinisListMapper(requestParms);
    }


    private void callSearchResultClinisListMapper(final SearchRequest requestParms)
    {

        SearchResultClinicListMapper mapper=new SearchResultClinicListMapper(requestParms);

        mapper.setOnSearchResultClinicListAPItListener(new SearchResultClinicListMapper.SearchResultClinicListAPItListener() {
            @Override
            public void getSearchResultClinicListAPI(SearchResultClinicListAPI searchResultClinicListAPI, String errorMessage) {
                if(!isValidResponse(searchResultClinicListAPI,errorMessage))
                {
                    return;
                }
                if(!(requestParms.getPage().equalsIgnoreCase("1")) && (searchResultClinicListAPI.getData()==null ||searchResultClinicListAPI.getData().size()==0) )
                {
                    Utils.showToastMsg(R.string.no_more_clinic_found_lbl);
                    return;

                }
               /* if(searchResultClinicListAPI.getData()!=null )
                {
                    clinicList.addAll(searchResultClinicListAPI.getData());
                }*/
                // Utils.showToastMessage(searchResultDoctorListAPI.toString());
              //  setOrderItemsToAdapter(clinicList);
                setClinicsList(searchResultClinicListAPI.getData());
                showMapDetails();
            }
        });


    }




    protected LatLngBounds.Builder addAllMarkersToMap(GoogleMap mMap) {
        fragmentView.findViewById(R.id.top_linear).setVisibility(View.GONE);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if(clinicsLsit==null || clinicsLsit.size()==0)
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.VISIBLE);
        }else
        {
            fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.GONE);
        }
        if(clinicsLsit==null || clinicsLsit.size()==0)
        {
            return null;
        }
      //  MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.path_352_2));
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.path_352_2);

        int addedCount=0;
        for (SearchResultClinicData data : clinicsLsit) {

            if (data == null) {
                continue;
            }
            double lat,lon;

            try
            {
                lat =Double.parseDouble(data.getGoogle_map_latitude());
                lon=Double.parseDouble(data.getGoogle_map_longitude());
            }catch(Exception e)
            {
                continue;
            }
            // GeoLocation geoLocation= deal.getLocation().getGeoLocation();
            LatLng latLng = new LatLng(lat, lon);
            MarkerOptions options = new MarkerOptions();
              options.position(latLng);
            options.icon(bitmapDescriptor);
            options.title(data.getClinicName());
             String snippet = data.getBranchName() + "\n" + data.getCityName() + ", " + data.getAddress();
             options.snippet(snippet);
             options.zIndex(Float.parseFloat(data.getClncId()));

            builder.include(latLng);


            mMap.addMarker(options);
            addedCount++;
        }

        if(addedCount==0)
        {
            return null;
        }
        if (!isFinish) {
            fragmentView.findViewById(R.id.top_linear).setVisibility(View.VISIBLE);
            ((TextView) fragmentView.findViewById(R.id.match_found_tv)).setText(addedCount + "");
        }
        return builder;
    }

    public void isFinishAfterMarkerClick(boolean isFinish) {
        this.isFinish=isFinish;
    }
}