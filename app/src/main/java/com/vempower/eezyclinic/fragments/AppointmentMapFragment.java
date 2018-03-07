package com.vempower.eezyclinic.fragments;

import android.content.Intent;
import android.os.Messenger;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vempower.eezyclinic.APICore.Appointment;
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
 ;
import com.vempower.eezyclinic.activities.AbstractActivity;

import java.util.List;

/**
 * Created by challa on 12/12/17.
 */

public class AppointmentMapFragment extends AbstractMapFragment/*, GoogleMap.OnMarkerClickListener */ {


    private Appointment appointment;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
            showMapDetails();
    }




    public AppointmentMapFragment()
    {
        setOnGoogleMarkerClickListener(new GoogleMarkerClickListener() {
            @Override
            public void onClick(int id) {
                //TOD nothing


            }
        });
    }


    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }







    protected LatLngBounds.Builder addAllMarkersToMap(GoogleMap mMap) {
        fragmentView.findViewById(R.id.top_linear).setVisibility(View.GONE);
        fragmentView.findViewById(R.id.no_matching_result_tv).setVisibility(View.GONE);

        if(appointment==null)
        {
            return null;
        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        //  MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.path_352_2));
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.path_352_2);
        int addedCount = 0;
        if(appointment!=null) {


            double lat, lon;

            try {
                lat = Double.parseDouble(appointment.getGoogleMapLatitude());
                lon = Double.parseDouble(appointment.getGoogleMapLongitude());
            } catch (Exception e) {
                return null;
            }
            // GeoLocation geoLocation= deal.getLocation().getGeoLocation();
            LatLng latLng = new LatLng(lat, lon);
            MarkerOptions options = new MarkerOptions();
            options.position(latLng);
            options.icon(bitmapDescriptor);
            options.title(appointment.getDoctorName());
            String snippet = appointment.getSpecalities() + "\n" + appointment.getAddress();
            options.snippet(snippet);
            options.zIndex(Float.parseFloat(appointment.getId()));

            builder.include(latLng);


            mMap.addMarker(options);
            addedCount++;
        }
        if (addedCount == 0) {
            return null;
        }



        return builder;
    }


}