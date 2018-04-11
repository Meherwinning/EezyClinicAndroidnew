package com.vempower.eezyclinic.googleaddressselection;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.SSLHandshakeException;


public class GooglePlacesAutocompleteAdapter extends ArrayAdapter<GeoData>
        implements Filterable {
    private ArrayList<GeoData> resultList;
    private GeoData selectLoc;
    // private PlaceAutocompleteAdapter mAdapter;
    private static final String LOG_TAG = "GooglePlaces";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String TYPE_DETAILS = "/details";
    private static final String OUT_JSON = "/json";
    public static final String API_KEY = "AIzaSyAnMkiBfZ4JYivYtNYe0qCKcXUfAzbs-ZU";
    private String lan;
    private String lat;
    private String region;
    //private Location myLocation;

    public GooglePlacesAutocompleteAdapter(
                                           int textViewResourceId) {
        super(MyApplication.getCurrentActivityContext(), textViewResourceId);
        region="IN";
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public GeoData getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) MyApplication.getCurrentActivityContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.list_place_items, null);
            TextView place = (TextView) convertView
                    .findViewById(R.id.placeItem);
            place.setText(resultList.get(position).getAddress());
            place.setTag(resultList.get(position));
        } else {
            TextView place = (TextView) convertView
                    .findViewById(R.id.placeItem);
            place.setText(resultList.get(position).getAddress());
            place.setTag(resultList.get(position));
        }
        convertView.setTag(resultList.get(position));
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();

                if (constraint != null) {
                    if (selectLoc != null) {
                        if (selectLoc.getAddress().equals(constraint)) {
                            return null;
                        }
                    }
                    // Retrieve the autocomplete results.
                    resultList = autocomplete(constraint.toString());
                    // Assign the data to the FilterResults
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    private   ArrayList<GeoData> autocomplete(String input) {
        ArrayList<GeoData> resultList = null;
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();


        LatLang latLang=null;//getLatLang();
        if(lat!=null && lan!=null)
        {
            latLang = new LatLang(lat+"",lan+"",null);
        }
        if(latLang == null || latLang.lat == null || latLang.lng == null ){
            latLang = new LatLang("0","0",null);
        }


        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE
                    + TYPE_AUTOCOMPLETE + OUT_JSON);
            //Lombardia
            //https://maps.googleapis.com/maps/api/place/autocomplete/json?key=AIzaSyAnMkiBfZ4JYivYtNYe0qCKcXUfAzbs-ZU&components=country:AE&types=(cities)&location=0,0&input=duba
            //https://maps.googleapis.com/maps/api/place/textsearch/json?query=Lombardia&lang‌​uage=Your_language&key=YOUR_API_KEY
            sb.append("?key=" + API_KEY);
           // &components=country:AE
            sb.append("&components=country:" + region);
           //sb.append("&region=" + region);
            sb.append("&types=geocode");
            sb.append("&location=" + latLang.lat + "," + latLang.lng);
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e("GooglePlaces", "Error processing Places API URL", e);
            return resultList;
        } catch (SSLHandshakeException e) {
            Utils.showToastMsg(MyApplication.getCurrentActivityContext(), "SSL exception");
            Log.e("GooglePlaces", "Error connecting to Places API", e);
            return resultList;

        }

        catch (IOException e) {
            Log.e("GooglePlaces", "Error connecting to Places API", e);
            return resultList;

        }

        catch (Exception e) {
            Log.e("GooglePlaces", "Error connecting to Places API", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(jsonResults.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<GeoData>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                Log.i("GooglePlaces", predsJsonArray.getJSONObject(i)
                        .getString("description"));
                Log.i("GooglePlaces",
                        "============================================================");
                GeoData tmpData = new GeoData();
                tmpData.setAddress(predsJsonArray.getJSONObject(i).getString(
                        "description"));
                tmpData.setPlaceId(predsJsonArray.getJSONObject(i).getString(
                        "place_id"));

               // place_id
                resultList.add(tmpData);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results");
        }

        return resultList;
    }

    public void setMyLocation(String lat, String lan) {
        this.lat = lat;
        this.lan = lan;
    }


    /*private LatLang getLatLang()
    {

        return new LatLang(infoData.getUabLatitude(),infoData.getUabLangitude(),infoData.getUabGoogleAddress()+","+infoData.getUabDoorAddress());

        *//*if(SharedPreferencesUtil.getBooleanValueFromSharedPrefarence(
                Constants.KEY_IS_SELECTED_ADDRESS, false))
        {*//*
            *//*Address infoData=getSelectedAddressFromSharedPref();
            if(infoData==null)
            {
                return getLatLangFromSharedPref();
            }else
            {
                // setAddrestextToTileBar(infoData.getUabFullAddress()+"", titleBsrTextview);
                return new LatLang(infoData.getUabLatitude(),infoData.getUabLangitude(),infoData.getUabGoogleAddress()+","+infoData.getUabDoorAddress());
            }*//*

       *//* }else {
            // String addr = SharedPreferencesUtil.getStringValueFromSharedPrefarence(Constants.KEY_LAT_LNG_ADDR, "");
            //titleBsrTextview.setText(addr + "");
            //setAddrestextToTileBar(addr, titleBsrTextview);
            return getLatLangFromSharedPref();

        }*//*


    }*/

    private  class LatLang
    {
        public String lat,lng,addr;

        public LatLang(String lat, String lng,String addr) {
            this.lat = lat;
            this.lng = lng;
            this.addr=addr;
        }
    }

    public void setRegion(String region)
    {
        if(!TextUtils.isEmpty(region))
        {
           this.region=region;
        }
    }



/*    private LatLang getLatLangFromSharedPref()
    {
        String addr = SharedPreferencesUtil.getStringValueFromSharedPrefarence(Constants.KEY_LAT_LNG_ADDR, "");
        String lat = SharedPreferencesUtil.getStringValueFromSharedPrefarence(Constants.KEY_LAT, null);
        String lng = SharedPreferencesUtil.getStringValueFromSharedPrefarence(Constants.KEY_LNG, null);

        return new LatLang(lat,lng,addr);

    }

    protected com.talktranscriptions.restaurant.APIResponse.Address getSelectedAddressFromSharedPref() {
        Gson gson = new Gson();
        String json = SharedPreferencesUtil.getStringValueFromSharedPrefarence(
                Constants.ADDRESS_SELECTED_DETAILS_OBJECT, "");
        com.talktranscriptions.restaurant.APIResponse.Address obj = gson.fromJson(json, com.talktranscriptions.restaurant.APIResponse.Address.class);
        return obj;
    }*/

}
