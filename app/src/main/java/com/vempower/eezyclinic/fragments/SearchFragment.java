package com.vempower.eezyclinic.fragments;

import android.animation.ObjectAnimator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import com.vempower.eezyclinic.APIResponce.InsuranceData;
import com.vempower.eezyclinic.APIResponce.InsuranceListAPI;
import com.vempower.eezyclinic.APIResponce.LanguageData;
import com.vempower.eezyclinic.APIResponce.LanguageListAPI;
import com.vempower.eezyclinic.APIResponce.NationalityData;
import com.vempower.eezyclinic.APIResponce.NationalityListAPI;
import com.vempower.eezyclinic.APIResponce.SpecalitiesAPI;
import com.vempower.eezyclinic.APIResponce.SpecalitiyData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.HintAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.googleaddressselection.GeoData;
import com.vempower.eezyclinic.googleaddressselection.GooglePlacesAutocompleteAdapter;
import com.vempower.eezyclinic.mappers.CityListMapper;
import com.vempower.eezyclinic.mappers.CountryListMapper;
import com.vempower.eezyclinic.mappers.InsuranceListMapper;
import com.vempower.eezyclinic.mappers.LanguageListMapper;
import com.vempower.eezyclinic.mappers.NationalityMapper;
import com.vempower.eezyclinic.mappers.SpecalitiesMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.CustomSpinnerSelection;
import com.vempower.eezyclinic.views.MyAutoCompleteTextView;

import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by satish on 6/12/17.
 */

public class SearchFragment extends AbstractFragment {

    private View fragmentView;

    private MyAutoCompleteTextView addressAutoCompleteTextView,speciality_actv;
    private GooglePlacesAutocompleteAdapter googlePlacesAutocompleteAdapter;

    private CustomSpinnerSelection country_spinner,city_type_spinner,
            insurance_accepted_spinner, nationality_spinner, gender_type_spinner, language_spinner;

    private GeoData myGeodata;
    private LinearLayout advance_search_linear;
    private String selectedSpeciality;
    private String selectedGender;
    private CountryData selectedCountry;
    private CityData selectedCity;
    private HintAdapter<InsuranceData> insuranceAdapter;
    private InsuranceData selectedInsurance;
    private HintAdapter<NationalityData> nationalityAdapter;
    private NationalityData selectedNationality;

    private HintAdapter<LanguageData> languageAdapter;
    private LanguageData selectedLanguage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.search_layout, container, false);

        init();

        return fragmentView;
    }

    private void init() {
        addressAutoCompleteTextView = getFragemtView().findViewById(R.id.google_places_actv);
        speciality_actv= getFragemtView().findViewById(R.id.speciality_actv);
        country_spinner = getFragemtView().findViewById(R.id.country_spinner);
        insurance_accepted_spinner = getFragemtView().findViewById(R.id.insurance_accepted_spinner);
        nationality_spinner = getFragemtView().findViewById(R.id.nationality_spinner);
        gender_type_spinner = getFragemtView().findViewById(R.id.gender_spinner);
        language_spinner = getFragemtView().findViewById(R.id.language_spinner);
        advance_search_linear  = getFragemtView().findViewById(R.id.advance_search_linear);
        city_type_spinner  = getFragemtView().findViewById(R.id.city_type_spinner);
        final ExpandableLinearLayout expandableLayout_advance_search = getFragemtView().findViewById(R.id.expandableLayout_advance_search);

        setExpandedViewListener(expandableLayout_advance_search,advance_search_linear);
        advance_search_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableLayout_advance_search.toggle();
                callInsuranceAcceptedMapper();
                callNatinalityMapper();
                callLanguageMapper();
            }
        });


        setInitForGooglePlacesAutocompleteTextView();
        callSpecialityMapper();
        callCountryListMapper();
        setToGenderSpinnerAdapter();

    }


    private void callLanguageMapper() {

        if(languageAdapter!=null)
        {
            return;
        }

        LanguageListMapper mapper= new LanguageListMapper();
        mapper.setOnLanguageListListener(new LanguageListMapper.LanguageListListener() {
            @Override
            public void getLanguageListAPII(LanguageListAPI languageListAPI, String errorMessage) {
                if(!isValidResponse(languageListAPI,errorMessage))
                {
                    return;
                }
                setToLanguagesAdapter(languageListAPI.getData());
            }
        });


    }

    public void setToLanguagesAdapter(List<LanguageData> list) {
        final ArrayList<LanguageData> languageTypeList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            languageTypeList.addAll(list);

        }

        LanguageData hintData= new LanguageData();
        hintData.setLanguageName("Language");
        languageTypeList.add(languageTypeList.size(),hintData);


       languageAdapter = new HintAdapter<LanguageData>(MyApplication.getCurrentActivityContext(), R.layout.spinner_textview, languageTypeList);

        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        language_spinner.setAdapter(languageAdapter);
        language_spinner.setSelection(languageAdapter.getCount());

        language_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLanguage= null;
                //if(position!=0)
                //{
                if(position!=(languageAdapter.getCount()))
                {
                    selectedLanguage = languageTypeList.get(position);
                    Utils.showToastMessage("selected language " + selectedLanguage);
                }


                //}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }



    private void callNatinalityMapper() {

        if(nationalityAdapter!=null)
        {
            return;
        }

        NationalityMapper mapper= new NationalityMapper();

        mapper.setOnNationalityListListener(new NationalityMapper.NationalityListListener() {
            @Override
            public void getNationalityListAPI(NationalityListAPI nationalityListAPI, String errorMessage) {

                if(!isValidResponse(nationalityListAPI,errorMessage))
                {
                    return;
                }
                setToNationalityListAdapter(nationalityListAPI.getData());
            }
        });

    }

    public void setToNationalityListAdapter(List<NationalityData> list) {
        final ArrayList<NationalityData> nationalityTypeList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            nationalityTypeList.addAll(list);

        }

        NationalityData hintData= new NationalityData();
        hintData.setNationalityName("Nationality");
        nationalityTypeList.add(nationalityTypeList.size(),hintData);


        nationalityAdapter = new HintAdapter<NationalityData>(MyApplication.getCurrentActivityContext(), R.layout.spinner_textview, nationalityTypeList);

        nationalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nationality_spinner.setAdapter(nationalityAdapter);
        nationality_spinner.setSelection(nationalityAdapter.getCount());

        nationality_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedNationality= null;
                //if(position!=0)
                //{
                if(position!=(nationalityAdapter.getCount()))
                {
                    selectedNationality = nationalityTypeList.get(position);
                    Utils.showToastMessage("selected Nationality " + selectedNationality);
                }


                //}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void callInsuranceAcceptedMapper() {
        if(insuranceAdapter!=null)
        {
            return;
        }
        InsuranceListMapper mapper = new InsuranceListMapper();
        mapper.setOnInsuranceListListener(new InsuranceListMapper.InsuranceListListener() {
            @Override
            public void getInsuranceListAPI(InsuranceListAPI insuranceListAPI, String errorMessage) {
                if(!isValidResponse(insuranceListAPI,errorMessage))
                {
                    return;
                }
                setToInsuranceAdapter(insuranceListAPI.getData());
            }
        });
    }

    public void setToInsuranceAdapter(List<InsuranceData> list) {
        final ArrayList<InsuranceData> insuranceTypeList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            insuranceTypeList.addAll(list);

        }

        InsuranceData hintData= new InsuranceData();
        hintData.setCompanyName("Insurance");
        insuranceTypeList.add(insuranceTypeList.size(),hintData);


       insuranceAdapter = new HintAdapter<InsuranceData>(MyApplication.getCurrentActivityContext(), R.layout.spinner_textview, insuranceTypeList);

        insuranceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        insurance_accepted_spinner.setAdapter(insuranceAdapter);
        insurance_accepted_spinner.setSelection(insuranceAdapter.getCount());

        insurance_accepted_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedInsurance= null;
                //if(position!=0)
                //{
                if(position!=(insuranceAdapter.getCount()))
                {
                    selectedInsurance = insuranceTypeList.get(position);
                    Utils.showToastMessage("selected insurance " + selectedInsurance);
                }


                //}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void callCountryListMapper() {
        //TODO call country list mapper
        CountryListMapper mapper= new CountryListMapper();
        mapper.setOnCountryListListener(new CountryListMapper.CountryListListener() {
            @Override
            public void getCountryListAPI(CountryListAPI countryListAPI, String errorMessage) {
                if(!isValidResponse(countryListAPI,errorMessage))
                {
                    return;
                }

                setToCountryAdapter(countryListAPI.getData());

            }
        });

    }

    private void callLanguagesMapper() {
        //TODO call language list mapper

    }

    private void callCityListMapper(String country) {
        CityListMapper cityListMapper= new CityListMapper(country);
        cityListMapper.setOnCityListListener(new CityListMapper.CityListListener() {
            @Override
            public void getCityListAPI(CityListAPI cityListAPI, String errorMessage) {
                if(!isValidResponse(cityListAPI,errorMessage))
                {
                    return;
                }
                setToCityListAdapter(cityListAPI.getData());

            }
        });

    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }

    private void setInitForGooglePlacesAutocompleteTextView() {

        googlePlacesAutocompleteAdapter = new GooglePlacesAutocompleteAdapter(
                R.layout.list_place_items);

        /*if (myLocation != null) {
            googlePlacesAutocompleteAdapter.setMyLocation(myLocation);
        }*/
        addressAutoCompleteTextView.setAdapter(googlePlacesAutocompleteAdapter);
        addressAutoCompleteTextView.setOnItemClickListener(adapterViewListener);
    }


    AdapterView.OnItemClickListener adapterViewListener = new AdapterView.OnItemClickListener() {
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


    private void callSpecialityMapper() {
        SpecalitiesMapper mapper = new SpecalitiesMapper();
        mapper.setOnSpecalitiesdListener(new SpecalitiesMapper.SpecalitiesdListener() {
            @Override
            public void getSpecalitiesAPII(SpecalitiesAPI specalitiesAPI, String errorMessage) {
                if (!isValidResponse(specalitiesAPI, errorMessage)) {
                    return;
                }
                List<SpecalitiyData> dataList = specalitiesAPI.getData();
                setToAutoComepteAdapter(dataList);

            }
        });
    }
    public void setToGenderSpinnerAdapter() {

        final ArrayList<String> genderTypeList= new ArrayList<>();

        genderTypeList.add(Constants.GenderValues.MALE);
        genderTypeList.add(Constants.GenderValues.FEMALE);
        genderTypeList.add(Constants.GenderValues.GENDER);
        // selectedGender= genderTypeList.get(2);
      final  HintAdapter aa = new HintAdapter<String >(MyApplication.getCurrentActivityContext(),R.layout.spinner_textview,genderTypeList);


        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_type_spinner.setAdapter(aa);
        gender_type_spinner.setSelection(aa.getCount());

        gender_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender= null;
                if(position!=(aa.getCount()))
                {
                    selectedGender= genderTypeList.get(position);
                }
                Utils.showToastMessage("selectedGender "+selectedGender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }


    public void setToCityListAdapter(List<CityData> list) {
        final ArrayList<CityData> cityTypeList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            cityTypeList.addAll(list);

        }

        CityData hintData= new CityData();
        hintData.setCityName("City");
        cityTypeList.add(cityTypeList.size(),hintData);

        if(list==null)
        {
            cityTypeList.add(cityTypeList.size(),hintData);
        }


        city_type_spinner = getFragemtView().findViewById(R.id.city_type_spinner);
        final   HintAdapter aa = new HintAdapter<CityData>(MyApplication.getCurrentActivityContext(), R.layout.spinner_textview, cityTypeList);

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        city_type_spinner.setAdapter(aa);
        city_type_spinner.setSelection(aa.getCount());
       /* if(cityTypeList.size()==1)
        {
            aa.isHintEnable(false);
            aa.notifyDataSetChanged();
        }else
        {
            cityTypeList.add(cityTypeList.size(),hintData);
            aa.isHintEnable(true);
            aa.notifyDataSetInvalidated();
            city_type_spinner.setSelection(aa.getCount());
        }*/

        city_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity= null;

               /* if(aa.getCount()==1)
                {
                    Utils.showToastMessage("Please select country");
                    return;
                }*/
                //if(position!=0)
                //{
                if(position!=(aa.getCount()))
                {
                    selectedCity = cityTypeList.get(position);
                    Utils.showToastMessage("selected city " + selectedCity);
                }


                //}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void setToCountryAdapter(List<CountryData> list) {
        final ArrayList<CountryData> countryTypeList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            countryTypeList.addAll(list);

        }

        CountryData hintData= new CountryData();
        hintData.setCountry("Country");
        countryTypeList.add(countryTypeList.size(),hintData);
        setToCityListAdapter(null);


      final   HintAdapter aa = new HintAdapter<CountryData>(MyApplication.getCurrentActivityContext(), R.layout.spinner_textview, countryTypeList);

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country_spinner.setAdapter(aa);
        country_spinner.setSelection(aa.getCount());

        country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCountry = null;
                //if(position!=0)
                //{
                if(position!=(aa.getCount()))
                {
                    selectedCountry = countryTypeList.get(position);
                    Utils.showToastMessage("selected country " + selectedCountry);
                    callCityListMapper(selectedCountry.getId());
                }


                //}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void setToAutoComepteAdapter(List<SpecalitiyData> dataList) {
        final ArrayList<String> specalitiyTypeList = new ArrayList<>();
        if (dataList != null && dataList.size() > 0) {
            specalitiyTypeList.addAll(getStringArray(dataList));

        }

        //  SpecalitiyData hintData= new SpecalitiyData();
        // hintData.setName("Speciality");
       // specalitiyTypeList.add(specalitiyTypeList.size(), "Speciality");

       /* genderTypeList.add(Constants.GenderValues.MALE);
        genderTypeList.add(Constants.GenderValues.FEMALE);
        genderTypeList.add(Constants.GenderValues.GENDER);*/
        // selectedGender= genderTypeList.get(2);
        HintAdapter aa = new HintAdapter<String >(MyApplication.getCurrentActivityContext(), R.layout.auto_complete_textview, specalitiyTypeList);
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
        //aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        speciality_actv.setAdapter(aa);
       // speciality_actv.setSelection(aa.getCount());

        speciality_actv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSpeciality = null;
                //if(position!=0)
                //{
                selectedSpeciality = specalitiyTypeList.get(position);
                //}
                Utils.showToastMessage("selected Speciality " + selectedSpeciality);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private ArrayList<String> getStringArray(List<SpecalitiyData> dataList) {
        ArrayList<String> strings = new ArrayList<>();

        for (SpecalitiyData data : dataList) {
            if (data != null) {
                strings.add(data.getName());
            }
        }
        return strings;
    }



    private void setExpandedViewListener(ExpandableLinearLayout expandableLayout, View view)
    {
        expandableLayout.setInRecyclerView(false);
        //expandableLayout.setBackgroundColor(ContextCompat.getColor(this, item.colorId2));
        expandableLayout.setInterpolator(com.github.aakira.expandablelayout.Utils.createInterpolator(com.github.aakira.expandablelayout.Utils.LINEAR_OUT_SLOW_IN_INTERPOLATOR));
        //expandableLayout.setExpanded(expandState.get(position));
        expandableLayout.setExpanded(false);
        final ImageView imageView= view.findViewById(R.id.advanced_search_iv);
        imageView.setBackgroundResource(R.drawable.plus_icon);
        expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                //imageView.setBackgroundResource(0);

                //createRotateAnimator(buttonLayout, 0f, 180f).start();
                //expandState.put(position, true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setBackgroundResource(R.drawable.minus_icon);
                        ((ScrollView)getFragemtView().findViewById(R.id.scroll)).fullScroll(View.FOCUS_DOWN);

                    }
                },300);
            }

            @Override
            public void onPreClose() {
                //imageView.setBackgroundResource(0);
                imageView.setBackgroundResource(R.drawable.plus_icon);
                //createRotateAnimator(buttonLayout, 180f, 0f).start();
                // expandState.put(position, false);
            }
        });
    }

    private ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(com.github.aakira.expandablelayout.Utils.createInterpolator(com.github.aakira.expandablelayout.Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}
