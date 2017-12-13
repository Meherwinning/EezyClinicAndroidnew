package com.vempower.eezyclinic.fragments;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.gc.materialdesign.views.ButtonFloat;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.SearchDoctorsListActivity;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.stashdealcustomer.activities.AbstractActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by challa on 12/12/17.
 */

public class MapFragment extends AbstractFragment implements OnMapReadyCallback/*, GoogleMap.OnMarkerClickListener */{

    public static final String LOCATION_OBJECT_KEY = "location_object_key";
    public static final String ENTITY_NAME_KEY = "entity_name_key";

    private GoogleMap mMap;
    //private ActiveDealsGeoLocation geoLocation;
    private View  fragmentView;
    private MapView mapView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.some_layout, container, false);

        mapView = (MapView) fragmentView.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);


        mapView.getMapAsync(this);

        init();

        return fragmentView;
    }
   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        init();

    }*/

    private void init() {
        //TODO
        /*if(MyApplication.getInstance().getAllDeals()==null || MyApplication.getInstance().getAllDeals().size()==0)
        {
            showAlertDialog("Alert","Invalid location details",true);
            return;
        }*/


         /*geoLocation= (ActiveDealsGeoLocation) getIntent().getSerializableExtra(LOCATION_OBJECT_KEY);

        if(geoLocation==null)
        {
            showAlertDialog("Alert","Invalid location details",true);
            return;
        }*/


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
   /*     SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
*/


        //setFloatingActionButtonListners();

        /*{
            ButtonFloat fab = getFragemtView().findViewById(R.id.fab_list);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MyApplication.getCurrentActivityContext(), SearchDoctorsListActivity.class);
                    startActivity(intent);
                }
            });
        }*/


    }

    /*private void setFloatingActionButtonListners() {
        {
            ButtonFloat fab = findViewById(R.id.fab_deals_list);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    *//*Intent intent = new Intent(MyApplication.getCurrentActivityContext(), MapsActivity.class);
                    startActivity(intent);*//*
                }
            });
        }
        {
            ButtonFloat fab = findViewById(R.id.fab_filter);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // showToastMessage("Coming soon");
                    Intent intent = new Intent(MyApplication.getCurrentActivityContext(), FilterDealsActivity.class);
                    startActivity(intent);

                }
            });
        }
        {
            ButtonFloat fab = findViewById(R.id.fab_twaky);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callTawkChatWebView();

                }
            });
        }
    }*/




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final List<MyLatLang> allPos = getLatLangList(); //MyApplication.getInstance().getAllDeals();
        if(mMap!=null && allPos!=null) {
            //mMap.setOnMarkerClickListener(this);
            LatLngBounds.Builder builder= addAllMarkersToMap(allPos);
            final LatLngBounds bounds= builder.build();



            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    //mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 30));
                    int padding = 50; // offset from edges of the map in pixels
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                    mMap.animateCamera(cu);
                }
            });

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                public void onInfoWindowClick(Marker marker)
                {
                    // Utils.showToastMessage("123 "+marker.getZIndex());

                    /*int dealId= (int) marker.getZIndex();
                    Deal deal= new Deal();
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
           /* LatLng latLng = new LatLng(Double.parseDouble(geoLocation.getLatitude()), Double.parseDouble(geoLocation.getLongitude()));
            MarkerOptions options= new MarkerOptions();
            options.position(latLng);
            options.title(getIntent().getStringExtra(ENTITY_NAME_KEY));
            String snippet=geoLocation.getSubLocality()+", "+geoLocation.getLocality()+", "+geoLocation.getDistrict()+", "+geoLocation.getState()+", "+geoLocation.getCountry()+" - "+geoLocation.getZipcode();
            options.snippet(snippet);

            mMap.addMarker(options);//new MarkerOptions().position(sydney).title("Marker in Sydney"));
*/
            /*CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)      // Sets the center of the map to location user
                    .zoom(15)           // Sets the zoom
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
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

    private List<MyLatLang> getLatLangList()
    {
        ArrayList<MyLatLang> list= new ArrayList<>();
        list.add(new MyLatLang(25.278491,55.329208,"Dubai"));
        list.add(new MyLatLang(17.399177,78.415167,"Hyderabad"));

        list.add(new MyLatLang(25.2788468,55.3309395,"Abu Dhabi"));

        return list;

    }

    public void refreshMap() {
        //TODO refresh the map
    }

    private class MyLatLang
    {
        double lat,lng;
        String name;

        public MyLatLang(double lat, double lng, String name) {
            this.lat = lat;
            this.lng = lng;
            this.name = name;
        }
    }

    private  LatLngBounds.Builder addAllMarkersToMap(List<MyLatLang> allPos) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(MyLatLang pos:allPos) {

            if(pos==null)
            {
                continue;
            }
            // GeoLocation geoLocation= deal.getLocation().getGeoLocation();
            LatLng latLng = new LatLng(pos.lat, pos.lng);
            MarkerOptions options = new MarkerOptions();
            options.position(latLng);
            options.title(pos.name);
            // String snippet = geoLocation.getSubLocality() + ", " + geoLocation.getLocality() + ", " + geoLocation.getDistrict() + ", " + geoLocation.getState() + ", " + geoLocation.getCountry() + " - " + geoLocation.getZipcode();
            // options.snippet(snippet);
            // options.zIndex(Float.parseFloat(deal.getDealID()));

            builder.include(latLng);


            mMap.addMarker(options);
        }
        return builder;
    }

    @Override
    public void onResume() {
        if(mapView!=null)
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        if(mapView!=null)
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mapView!=null)
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if(mapView!=null)
        mapView.onLowMemory();
    }



   /* @Override
    public boolean onMarkerClick(Marker marker) {

        Utils.showToastMessage(marker.getTitle());
        return false;
    }*/
}