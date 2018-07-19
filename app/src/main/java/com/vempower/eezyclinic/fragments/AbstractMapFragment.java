package com.vempower.eezyclinic.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.PopupAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.core.MarkerDetails;
import com.vempower.eezyclinic.interfaces.GoogleMarkerClickListener;
import com.vempower.eezyclinic.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by challa on 12/12/17.
 */

public abstract class AbstractMapFragment extends AbstractFragment implements OnMapReadyCallback/*, GoogleMap.OnMarkerClickListener */ {

    public static final String LOCATION_OBJECT_KEY = "location_object_key";
    public static final String ENTITY_NAME_KEY = "entity_name_key";

    private GoogleMap mMap;
    //private ActiveDealsGeoLocation geoLocation;
    protected View fragmentView;
    private MapView mapView;
    private GoogleMarkerClickListener markerClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     MyApplication.showTransparentDialog();
        fragmentView = inflater.inflate(R.layout.some_layout, container, false);
        mapView = (MapView) fragmentView.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
       // init();

        return fragmentView;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MyApplication.hideTransaprentDialog();
       // showMapDetails();
    }

    protected void clearMap()
    {
        if(mMap==null)
        {
            return;
        }
        mMap.clear();
    }


   protected void showMapDetails() {
       clearMap();
        //final List<MyLatLang> allPos = getLatLangList(); //MyApplication.getInstance().getAllDeals();
        if (mMap != null) {
            //mMap.setOnMarkerClickListener(this);
            //final MarkerDetails details=addAllMarkersToMap(mMap);
            LatLngBounds.Builder builder = addAllMarkersToMap(mMap);

            if(builder==null )
            {
                return;
            }
            LatLngBounds latLngBounds = builder.build();
            if(latLngBounds==null )
            {
                return;
            }
            final LatLngBounds bounds = adjustBoundsForMaxZoomLevel(latLngBounds);

           final float[] results = new float[1];
            android.location.Location.distanceBetween(bounds.northeast.latitude, bounds.northeast.longitude,
                    bounds.southwest.latitude, bounds.southwest.longitude, results);

           // Utils.showToastMessage("Size :"+details.size);

           // LatLngBounds.Builder builder = addAllMarkersToMap(mMap);

           // final LatLngBounds bounds = builder.build();

            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    LinearLayout info = new LinearLayout(MyApplication.getCurrentActivityContext());
                    info.setOrientation(LinearLayout.VERTICAL);

                    TextView title = new TextView(MyApplication.getCurrentActivityContext());
                    title.setTextColor(Color.BLACK);
                    title.setGravity(Gravity.CENTER);
                    title.setTypeface(null, Typeface.BOLD);
                    title.setText(marker.getTitle());

                    TextView snippet = new TextView(MyApplication.getCurrentActivityContext());
                    snippet.setTextColor(Color.GRAY);
                    snippet.setGravity(Gravity.CENTER);
                    snippet.setText(marker.getSnippet());

                    info.addView(title);
                    info.addView(snippet);

                    return info;
                }
            });


            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {

                    CameraUpdate cu = null;
                    if (results[0] == 1) { // distance is less than 1 km -> set to zoom level 15
                        cu = CameraUpdateFactory.newLatLngZoom(bounds.getCenter(), 15);
                    } /*else if (results[0] < 1000) { // distance is less than 1 km -> set to zoom level 15
                        cu = CameraUpdateFactory.newLatLngZoom(bounds.getCenter(), 15);
                    } */else {
                        int padding = 50; // offset from edges of the map in pixels
                        cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                    }
                    if (cu != null) {
                        mMap.animateCamera(cu);
                    }
                    //mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 30));
             /*       int padding = 50; // offset from edges of the map in pixels
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

                    mMap.animateCamera(cu);
                    mMap.setInfoWindowAdapter(new PopupAdapter(getLayoutInflater()));
*/

                    //Utils.showToastMessage("Size :"+details.size);
                    /*if(results[>1)
                    {
                        int padding = 50; // offset from edges of the map in pixels
                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                        mMap.animateCamera(cu);
                    }else
                    {
                        mMap.moveCamera(CameraUpdateFactory.zoomTo(3.0f));
                    }*/
                }
            });

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                public void onInfoWindowClick(Marker marker) {
                    // Utils.showToastMessage("123 "+marker.getZIndex());

                   // int dealId= (int) marker.getZIndex();

                    if(markerClickListener!=null)
                    {
                        markerClickListener.onClick((int) marker.getZIndex());
                    }
                    /*Deal deal= new Deal();
                    deal.setDealID(dealId+"");
                   int index= allDeals.indexOf(deal);
                   if(index<0)
                   {
                       return;
                   }

                    callDealViewPage(allDeals.get(index));*/
                }
            });


            // Add a marker in Sydney and move the camera

        }
    }

    private LatLngBounds adjustBoundsForMaxZoomLevel(LatLngBounds bounds) {
        LatLng sw = bounds.southwest;
        LatLng ne = bounds.northeast;
        double deltaLat = Math.abs(sw.latitude - ne.latitude);
        double deltaLon = Math.abs(sw.longitude - ne.longitude);

        final double zoomN = 0.005; // minimum zoom coefficient
        if (deltaLat < zoomN) {
            sw = new LatLng(sw.latitude - (zoomN - deltaLat / 2), sw.longitude);
            ne = new LatLng(ne.latitude + (zoomN - deltaLat / 2), ne.longitude);
            bounds = new LatLngBounds(sw, ne);
        }
        else if (deltaLon < zoomN) {
            sw = new LatLng(sw.latitude, sw.longitude - (zoomN - deltaLon / 2));
            ne = new LatLng(ne.latitude, ne.longitude + (zoomN - deltaLon / 2));
            bounds = new LatLngBounds(sw, ne);
        }

        return bounds;
    }

    /*protected void callDealViewPage(Deal deal) {
        //showToastMessage("Call deal view page");

        if(deal==null)
        {
            showToastMessage("Invalid deal detals.Please try again");
            return;
        }
        Intent intent= new Intent(this,ParallaxToolbarScrollViewActivity.class);
        intent.putExtra(Constants.Pref.DEAL_OBJECT_KEY,deal);
        startActivity(intent);

    }*/

    private List<MyLatLang> getLatLangList() {
        ArrayList<MyLatLang> list = new ArrayList<>();
        list.add(new MyLatLang(25.278491, 55.329208, "Dubai"));
        list.add(new MyLatLang(17.399177, 78.415167, "Hyderabad"));

        list.add(new MyLatLang(25.2788468, 55.3309395, "Abu Dhabi"));

        return list;

    }

    public void refreshMap() {
        //TODO refresh the map
    }

    protected void setOnGoogleMarkerClickListener(GoogleMarkerClickListener markerClickListener)
    {
        this.markerClickListener=markerClickListener;

    }



    private class MyLatLang {
        double lat, lng;
        String name;

        public MyLatLang(double lat, double lng, String name) {
            this.lat = lat;
            this.lng = lng;
            this.name = name;
        }
    }

    protected abstract LatLngBounds.Builder addAllMarkersToMap(GoogleMap mMap);
    @Override
    public void onResume() {
        if (mapView != null)
            mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null)
            mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null)
            mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null)
            mapView.onLowMemory();
    }



   /* @Override
    public boolean onMarkerClick(Marker marker) {

        Utils.showToastMessage(marker.getTitle());
        return false;
    }*/
}