package com.vempower.eezyclinic.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.vempower.eezyclinic.APIResponce.SpecalitiesAPI;
import com.vempower.eezyclinic.APIResponce.SpecalitiyData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.HintAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.googleaddressselection.GeoData;
import com.vempower.eezyclinic.googleaddressselection.GooglePlacesAutocompleteAdapter;
import com.vempower.eezyclinic.mappers.SpecalitiesMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyTextViewRR;

import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by satish on 6/12/17.
 */

public class SearchFragment extends AbstractFragment {

    private View fragmentView;

    private AutoCompleteTextView addressAutoCompleteTextView;
    private GooglePlacesAutocompleteAdapter googlePlacesAutocompleteAdapter;

    private  Spinner speciality_type_spinner;

    private GeoData myGeodata;
    private String selectedSpeciality;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.search_layout, container, false);

        init();

        return fragmentView;
    }

    private void init() {
        addressAutoCompleteTextView = getFragemtView().findViewById(R.id.google_places_actv);
        speciality_type_spinner  = getFragemtView().findViewById(R.id. speciality_type_spinner);
        setInitForAutocompleteTextView();
        callSpecialityMapper();

    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }
    private void setInitForAutocompleteTextView() {

        googlePlacesAutocompleteAdapter = new GooglePlacesAutocompleteAdapter(
                R.layout.list_place_items);

        /*if (myLocation != null) {
            googlePlacesAutocompleteAdapter.setMyLocation(myLocation);
        }*/
        addressAutoCompleteTextView.setAdapter(googlePlacesAutocompleteAdapter);
        addressAutoCompleteTextView.setOnItemClickListener(adapterViewListener);
    }

    AdapterView.OnItemClickListener adapterViewListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            if (view.getTag() != null) {
                GeoData tmpData = (GeoData) view.getTag();

                myGeodata = tmpData;
                addressAutoCompleteTextView.setText(tmpData.getAddress());
            } else {
                addressAutoCompleteTextView.setText("");
            }
        }
    };

    private void gettingLatLanFromGoogle(GeoData myGeodata) {

        String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + myGeodata.getPlaceId() + "&sensor=nil&key=" + GooglePlacesAutocompleteAdapter.API_KEY;
        new GooglePlaceLatLangAsynTask(url).execute();

    }


    private class GooglePlaceLatLangAsynTask extends AsyncTask<Void, Void, LatLan> {

        private String url;

        GooglePlaceLatLangAsynTask(String url) {
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MyApplication.showTransparentDialog();//getResources().getString(R.string.please_wait_while_get_loc_label), getResources().getString(R.string.getting_location_label));
        }

        @Override
        protected LatLan doInBackground(Void... params) {
            HttpURLConnection conn = null;
            StringBuilder jsonResults = new StringBuilder();
            try {
                URL googlePlaceAPIUrl = new URL(url);
                conn = (HttpURLConnection) googlePlaceAPIUrl
                        .openConnection();
                InputStreamReader in = new InputStreamReader(
                        conn.getInputStream());

                // Load the results into a StringBuilder
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    jsonResults.append(buff, 0, read);
                }

                JSONObject jsonObj = new JSONObject(
                        jsonResults.toString());
                JSONObject jsonResult = jsonObj.getJSONObject("result");
                JSONObject jsonGeometryList = jsonResult
                        .getJSONObject("geometry");
                JSONObject jsonLocList = jsonGeometryList
                        .getJSONObject("location");
                LatLan latLan = new LatLan();
                latLan.lat = jsonLocList.getString("lat");
                latLan.lan = jsonLocList.getString("lng");
                return latLan;
            } catch (Exception e) {

            }
            return null;

        }

        @Override
        protected void onPostExecute(LatLan latLan) {
            super.onPostExecute(latLan);
            MyApplication.hideTransaprentDialog();
            if (latLan == null) {
                Utils.showToastMessage(getResources().getString(R.string.please_enter_valid_location));
                return;
            }
          /*  myGeodata.setLat(latLan.lat);
            myGeodata.setLng(latLan.lan);
            setFilterDataAsResult();*/

            //setLocationDetailsToShaPref(Double.parseDouble(myGeodata.getLat()), Double.parseDouble(myGeodata.getLng()), myGeodata.getAddress());
            //setAddressFromGPSLocation(myGeodata.getAddress());
        }
    }


    private class LatLan {
        public String lat, lan;
    }


    private void callSpecialityMapper()
    {
        SpecalitiesMapper mapper= new SpecalitiesMapper();
        mapper.setOnSpecalitiesdListener(new SpecalitiesMapper.SpecalitiesdListener() {
            @Override
            public void getSpecalitiesAPII(SpecalitiesAPI specalitiesAPI, String errorMessage) {
                if(!isValidResponse(specalitiesAPI,errorMessage))
                {
                    return;
                }
                List<SpecalitiyData> dataList = specalitiesAPI.getData();
                setToSpinnerAdapter(dataList);

            }
        });
    }


    public void setToSpinnerAdapter(List<SpecalitiyData> dataList) {
        final ArrayList<String> specalitiyTypeList= new ArrayList<>();
        if(dataList!=null && dataList.size()>0)
        {
            specalitiyTypeList.addAll(getStringArray(dataList));

        }

      //  SpecalitiyData hintData= new SpecalitiyData();
       // hintData.setName("Speciality");
        specalitiyTypeList.add(specalitiyTypeList.size(),"Speciality");

       /* genderTypeList.add(Constants.GenderValues.MALE);
        genderTypeList.add(Constants.GenderValues.FEMALE);
        genderTypeList.add(Constants.GenderValues.GENDER);*/
        // selectedGender= genderTypeList.get(2);
        HintAdapter aa = new HintAdapter(MyApplication.getCurrentActivityContext(),R.layout.spinner_textview,specalitiyTypeList);
     /*   {



            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                // return super.getView(position, convertView, parent);
                View view =super.getView(position, convertView, parent);
                MyTextViewRR textView=view.findViewById(android.R.id.text1);
                textView.setText(getItem(position).getName());
                //(int left, int top, int right, int bottom)
               *//*int padding= (int) MyApplication.getCurrentActivityContext().getResources().getDimension(R.dimen._6sdp);
                textView.setPadding(padding,padding,padding,padding);
                // do whatever you want with this text view
                if(Utils.isTablet()) {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (MyApplication.getCurrentActivityContext().getResources().getDimension(R.dimen._7sdp)));
                }else
                 {
                     textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (MyApplication.getCurrentActivityContext().getResources().getDimension(R.dimen._13sdp)));

                 }*//*
                return view;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                //return super.getDropDownView(position, convertView, parent);
                View view =super.getView(position, convertView, parent);
                MyTextViewRR textView=view.findViewById(android.R.id.text1);
                textView.setText(getItem(position).getName());
               *//* int pading= (int) MyApplication.getCurrentActivityContext().getResources().getDimension(R.dimen._6sdp);
                textView.setPadding(pading,pading,pading,pading);

                // do whatever you want with this text view
                if(Utils.isTablet()) {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (MyApplication.getCurrentActivityContext().getResources().getDimension(R.dimen._7sdp)));
                }else
                 {
                     textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (MyApplication.getCurrentActivityContext().getResources().getDimension(R.dimen._13sdp)));

                 }*//*
                return view;
            }
        };*/
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        speciality_type_spinner.setAdapter(aa);
        speciality_type_spinner.setSelection(aa.getCount());

        speciality_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSpeciality= null;
                //if(position!=0)
                //{
                    selectedSpeciality= specalitiyTypeList.get(position);
                //}
                 Utils.showToastMessage("selected Speciality "+selectedSpeciality);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }

    private ArrayList<String> getStringArray(List<SpecalitiyData> dataList) {
        ArrayList<String> strings= new ArrayList<>();

        for(SpecalitiyData data:dataList)
        {
            if(data!=null)
            {
                strings.add(data.getName());
            }
        }
        return strings;
    }
}
