package com.vempower.eezyclinic.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.vempower.eezyclinic.APICore.PatientProfileData;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.APIResponce.CityData;
import com.vempower.eezyclinic.APIResponce.CityListAPI;
import com.vempower.eezyclinic.APIResponce.CountryData;
import com.vempower.eezyclinic.APIResponce.CountryListAPI;
import com.vempower.eezyclinic.APIResponce.IdCardTypeAPI;
import com.vempower.eezyclinic.APIResponce.IdCardTypeData;
import com.vempower.eezyclinic.APIResponce.InsuranceData;
import com.vempower.eezyclinic.APIResponce.InsuranceListAPI;
import com.vempower.eezyclinic.APIResponce.NationalityData;
import com.vempower.eezyclinic.APIResponce.NationalityListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.HintAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.core.EditProfileDetails;
import com.vempower.eezyclinic.googleaddressselection.GeoData;
import com.vempower.eezyclinic.googleaddressselection.GooglePlacesAutocompleteAdapter;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.CityListMapper;
import com.vempower.eezyclinic.mappers.CountryListMapper;
import com.vempower.eezyclinic.mappers.IdCardTypeMapper;
import com.vempower.eezyclinic.mappers.InsuranceListMapper;
import com.vempower.eezyclinic.mappers.NationalityMapper;
import com.vempower.eezyclinic.mappers.ProfileSaveMapper;
import com.vempower.eezyclinic.mappers.UploadProfilePicMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.CustomSpinnerSelection;
import com.vempower.eezyclinic.views.MyAutoCompleteBlackCursorTextView;
import com.vempower.eezyclinic.views.MyAutoCompleteTextView;
import com.vempower.eezyclinic.views.MyEditTextBlackCursorRR;
import com.vempower.eezyclinic.views.MyTextViewRR;
import com.vempower.stashdealcustomer.activities.AbstractActivity;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by satish on 6/12/17.
 */

public class EditProfileFragment extends ImageProcessFragment {
    private LinearLayout contact_details_linear, insurance_details_linear, emergency_details_linear;

    private View fragmentView;
    private ExpandableLinearLayout expandableLayout_contact_el, expandableLayout_insurance_el, expandableLayout_emergency_el;
    private MyTextViewRR date_of_birth_tv,insurance_valid_from_tv,insurance_valid_to_tv;
    private SelectedDate selectedDOBObj,selectedValidFromObj,selectedValidToObj;

    private TextView profile_top_details_mask_tv, insurance_details_mask_tv,
            emergency_details_mask_tv, contact_details_mask_tv;
    private boolean isEditMode;
    private ScrollView myScrollView;
    private EditProfileDetails profileDetails;

     private HintAdapter<NationalityData> nationalityAdapter;
    private HintAdapter<InsuranceData> insuranceAdapter;
    private HintAdapter<InsuranceData> insuranceAdapter1;
    private HintAdapter<IdCardTypeData> idTypeAdapter;


    private MyEditTextBlackCursorRR patient_name_et, height_et,patient_age_et1,
            known_allergies_et, primary_contact_no_et,secondary_contact_no_et,residence_contact_no_et,
            contact_email_et,id_number_et,
            contact_address_et, contact_language_known_et, contact_nationality_et1,
            contact_id_type_et1, contact_insurance_provider_et1, contact_insurance_details_et1;

    private MyEditTextBlackCursorRR emergency_contact_name_et, emergency_contact_relationship_et1,
            emergency_contact_number_et, emergency_contact_emailid_et;

    private MyEditTextBlackCursorRR insurance_tpa_et,insurance_insurance_provider_et1,
            insurance_insurance_number_et,insurance_insurance_policy_et,
            insurance_member_id_et,insurance_type_et,
            insurance_co_pay_et,insurance_scheme_et,
            insurance_reason_et, insurance_organisation_et,insurance_max_limit_et,insurance_secoundary_number_et;

    private CustomSpinnerSelection  blood_group_spinner,country_spinner, city_type_spinner,id_type_spinner,
            insurance_secoundary_spinner, nationality_spinner,insurance_provider_spinner, relation_spinner, gender_type_spinner,marital_status_spinner;
    private SuccessToUpdateProfileListener successListener;

    private ExpandableLinearLayout expandableLayout_city_view;

    private MyAutoCompleteBlackCursorTextView addressAutoCompleteTextView;
    private GooglePlacesAutocompleteAdapter googlePlacesAutocompleteAdapter;
    //private File imageFile;
    private LinearLayout image_linear;
    private ImageView profile_iv;
    private PatientProfileData patientProfileObj;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.edit_profile_layout, container, false);
        profileDetails= new EditProfileDetails();
        myInit();
        return fragmentView;
    }

    private void myInit() {
        initForViews();
        profile_top_details_mask_tv = getFragemtView().findViewById(R.id.profile_top_details_mask_tv);
        insurance_details_mask_tv = getFragemtView().findViewById(R.id.insurance_details_mask_tv);
        emergency_details_mask_tv = getFragemtView().findViewById(R.id.emergency_details_mask_tv);
        contact_details_mask_tv = getFragemtView().findViewById(R.id.contact_details_mask_tv);


        contact_details_linear = getFragemtView().findViewById(R.id.contact_details_linear);
        insurance_details_linear = getFragemtView().findViewById(R.id.insurance_details_linear);
        emergency_details_linear = getFragemtView().findViewById(R.id.emergency_details_linear);

        expandableLayout_contact_el = getFragemtView().findViewById(R.id.expandableLayout_contact_el);
        expandableLayout_insurance_el = getFragemtView().findViewById(R.id.expandableLayout_insurance_el);
        expandableLayout_emergency_el = getFragemtView().findViewById(R.id.expandableLayout_emergency_el);


        myScrollView = ((ScrollView) getFragemtView().findViewById(R.id.scroll));



        date_of_birth_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateOfBirthTextviewClick(mFragmentDOBCallback,selectedDOBObj,true);
            }
        });
        insurance_valid_from_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateOfBirthTextviewClick(mFragmentValidFromCallback,selectedValidFromObj,false);
            }
        });

        insurance_valid_to_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateOfBirthTextviewClick(mFragmentValidToCallback,selectedValidToObj,false);
            }
        });





        setToBloodGroupSpinnerAdapter();
        setToGenderSpinnerAdapter();
        setToMaritalStatusSpinnerAdapter();
        setRelationSpinner();
        //callCountryListMapper();
        setInitForGooglePlacesAutocompleteTextView();

        fillProfileDataToViews();

    }

    private void setRelationSpinner() {

        // final ArrayList<String> genderTypeList = new ArrayList<>();

        final String[] relations=getResources().getStringArray(R.array.relations1);


        // genderTypeList.add(Constants.GenderValues.MALE);
        //  genderTypeList.add(Constants.GenderValues.FEMALE);
        //genderTypeList.add(Constants.GenderValues.GENDER);
        // selectedGender= genderTypeList.get(2);
        final HintAdapter aa = new HintAdapter<String>(MyApplication.getCurrentActivityContext(), R.layout.spinner_black_textview, relations);


        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relation_spinner.setAdapter(aa);


        relation_spinner.setSelection(aa.getCount());

        relation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                profileDetails.emergencyContactRelationship=null;
                if (position != (aa.getCount())) {
                    profileDetails.emergencyContactRelationship = relations[position];

                }

                //Utils.showToastMessage("selectedGender "+selectedGender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(patientProfileObj!=null)
        {
            String relation = patientProfileObj.getEmergencycontactrelationship();
            if(!TextUtils.isEmpty(relation))
            {
               for(int i=0;i<relations.length;i++)
               {
                   String temp=relations[i];
                   if(temp!=null && temp.equalsIgnoreCase(relation))
                   {
                       relation_spinner.setSelection(i);
                   }
               }
            }
        }

    }

    public void setToGenderSpinnerAdapter() {

        final ArrayList<String> genderTypeList = new ArrayList<>();

        genderTypeList.add(Constants.GenderValues.MALE);
        genderTypeList.add(Constants.GenderValues.FEMALE);
        genderTypeList.add(Constants.GenderValues.GENDER);
        // selectedGender= genderTypeList.get(2);
        final HintAdapter aa = new HintAdapter<String>(MyApplication.getCurrentActivityContext(), R.layout.spinner_black_textview, genderTypeList);


        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_type_spinner.setAdapter(aa);
        gender_type_spinner.setSelection(aa.getCount());

        gender_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                profileDetails.gender=null;
                if (position != (aa.getCount())) {
                    String selectedGender = genderTypeList.get(position);
                    if (selectedGender != null) {
                        profileDetails.gender=selectedGender;
                        //searchRequestParams.setGendersearch(null);
                       // searchRequestParams.addGendersearch(selectedGender.toLowerCase());
                    }
                }
                //Utils.showToastMessage("selectedGender "+selectedGender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(patientProfileObj!=null)
        {
            String gender = patientProfileObj.getGender();
            if(!TextUtils.isEmpty(gender))
            {
                for(int i=0;i<genderTypeList.size();i++)
                {
                    String temp=genderTypeList.get(i);
                    if(temp!=null && temp.equalsIgnoreCase(gender))
                    {
                        gender_type_spinner.setSelection(i);
                    }
                }
            }
        }

    }

    public void setToMaritalStatusSpinnerAdapter() {

        final ArrayList<String> maritalStatusList = new ArrayList<>();

        maritalStatusList.add(Constants.MaritalStatusValues.MARRIED);
        maritalStatusList.add(Constants.MaritalStatusValues.SINGLE);
        maritalStatusList.add(Constants.MaritalStatusValues.MARITAL_STATUS);
        // selectedGender= genderTypeList.get(2);
        final HintAdapter aa = new HintAdapter<String>(MyApplication.getCurrentActivityContext(), R.layout.spinner_black_textview, maritalStatusList);


        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        marital_status_spinner.setAdapter(aa);
        marital_status_spinner.setSelection(aa.getCount());

        marital_status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                profileDetails.maritialStatus=null;
                if (position != (aa.getCount())) {
                    String selectedGender = maritalStatusList.get(position);
                    if (selectedGender != null) {
                        profileDetails.maritialStatus=selectedGender;
                        //searchRequestParams.setGendersearch(null);
                        // searchRequestParams.addGendersearch(selectedGender.toLowerCase());
                    }
                }
                //Utils.showToastMessage("selectedGender "+selectedGender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(patientProfileObj!=null)
        {
            String marital = patientProfileObj.getMaritalStatus();
            if(!TextUtils.isEmpty(marital))
            {
                for(int i=0;i<maritalStatusList.size();i++)
                {
                    String temp=maritalStatusList.get(i);
                    if(temp!=null && temp.equalsIgnoreCase(marital))
                    {
                        marital_status_spinner.setSelection(i);
                    }
                }
            }
        }


    }


    private void callInsuranceAcceptedMapper() {
        if ((insuranceAdapter != null)&& (insuranceAdapter1 != null)) {
            MyApplication.hideTransaprentDialog();
            //insurance_accepted_spinner.setSelection(insuranceAdapter.getCount());
            return;
        }
        MyApplication.showTransparentDialog();
        InsuranceListMapper mapper = new InsuranceListMapper();
        mapper.setOnInsuranceListListener(new InsuranceListMapper.InsuranceListListener() {
            @Override
            public void getInsuranceListAPI(InsuranceListAPI insuranceListAPI, String errorMessage) {
                MyApplication.hideTransaprentDialog();
                if (!isValidResponse(insuranceListAPI, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_insurance_list_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()). finish();
                        }

                        @Override
                        public void retryClick() {
                            callInsuranceAcceptedMapper();
                        }
                    });
                    return;
                }
                if(insuranceAdapter1==null)
                {
                    setToInsuranceAdapter1(insuranceListAPI.getData());
                }

                if(insuranceAdapter==null) {
                    setToInsuranceAdapter(insuranceListAPI.getData());

                }

            }
        });
    }

    public void setToInsuranceAdapter1(List<InsuranceData> list) {
        final ArrayList<InsuranceData> insuranceTypeList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            insuranceTypeList.addAll(list);

        }

        InsuranceData hintData = new InsuranceData();
        hintData.setCompanyName("Insurance");
        insuranceTypeList.add(insuranceTypeList.size(), hintData);


        insuranceAdapter1 = new HintAdapter<InsuranceData>(MyApplication.getCurrentActivityContext(), R.layout.spinner_black_textview, insuranceTypeList);

        insuranceAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        insurance_provider_spinner.setAdapter(insuranceAdapter1);
        insurance_provider_spinner.setSelection(insuranceAdapter1.getCount());

        insurance_provider_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //if(position!=0)
                //{
                profileDetails.insurancePackage=null;
                if (position != (insuranceAdapter1.getCount())) {
                    InsuranceData selectedInsurance = insuranceTypeList.get(position);
                    // Utils.showToastMessage("selected insurance " + selectedInsurance);

                    if (selectedInsurance != null) {
                        profileDetails.insurancePackage=selectedInsurance.getCompanyName();
                        // searchRequestParams.setInsurenceList(null);
                        //searchRequestParams.addInsurence(selectedInsurance.getCompanyName());
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(patientProfileObj!=null)
        {
            String insurance = patientProfileObj.getInsuranceProvider();
            if(!TextUtils.isEmpty(insurance))
            {
                for(int i=0;i<insuranceTypeList.size();i++)
                {
                    String temp=insuranceTypeList.get(i).getCompanyName();
                    if(temp!=null && temp.equalsIgnoreCase(insurance))
                    {
                        insurance_provider_spinner.setSelection(i);
                    }
                }
            }
        }



    }

    public void setToInsuranceAdapter(List<InsuranceData> list) {
        final ArrayList<InsuranceData> insuranceTypeList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            insuranceTypeList.addAll(list);

        }

        InsuranceData hintData = new InsuranceData();
        hintData.setCompanyName("Secondary Insurance");
        insuranceTypeList.add(insuranceTypeList.size(), hintData);


        insuranceAdapter = new HintAdapter<InsuranceData>(MyApplication.getCurrentActivityContext(), R.layout.spinner_black_textview, insuranceTypeList);

        insuranceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        insurance_secoundary_spinner.setAdapter(insuranceAdapter);
        insurance_secoundary_spinner.setSelection(insuranceAdapter.getCount());

        insurance_secoundary_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //if(position!=0)
                //{
                profileDetails.secondaryinsurancePackage=null;
                if (position != (insuranceAdapter.getCount())) {
                    InsuranceData selectedInsurance = insuranceTypeList.get(position);
                    // Utils.showToastMessage("selected insurance " + selectedInsurance);

                    if (selectedInsurance != null) {
                        profileDetails.secondaryinsurancePackage=selectedInsurance.getCompanyName();
                       // searchRequestParams.setInsurenceList(null);
                        //searchRequestParams.addInsurence(selectedInsurance.getCompanyName());
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(patientProfileObj!=null) {
            String insurance1 = patientProfileObj.getSecondaryinsurancepackage();
            if(!TextUtils.isEmpty(insurance1))
            {
                for(int i=0;i<insuranceTypeList.size();i++)
                {
                    String temp=insuranceTypeList.get(i).getCompanyName();
                    if(temp!=null && temp.equalsIgnoreCase(insurance1))
                    {
                        insurance_secoundary_spinner.setSelection(i);
                    }
                }
            }
        }


    }



    private void callIdTypeMapper() {
        if (idTypeAdapter != null) {
            MyApplication.hideTransaprentDialog();
            //insurance_accepted_spinner.setSelection(insuranceAdapter.getCount());
            return;
        }
        MyApplication.showTransparentDialog();
        IdCardTypeMapper mapper = new IdCardTypeMapper();
        mapper.setOnIdTypeListListener(new IdCardTypeMapper.IdTypeListListener() {
            @Override
            public void getIdTypeListAPI(IdCardTypeAPI idCardTypeAPI, String errorMessage) {
                MyApplication.hideTransaprentDialog();
                if (!isValidResponse(idCardTypeAPI, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_id_type_list_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()). finish();
                        }

                        @Override
                        public void retryClick() {
                            callIdTypeMapper();
                        }
                    });
                    return;
                }
                setToIdTypeAdapter(idCardTypeAPI.getData());
                callCountryListMapper();
            }
        });


    }

    public void setToIdTypeAdapter(List<IdCardTypeData> list) {
        final ArrayList<IdCardTypeData> idCardTypeList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            idCardTypeList.addAll(list);

        }

        IdCardTypeData hintData = new IdCardTypeData();
        hintData.setIdCardName("ID Type");
        idCardTypeList.add(idCardTypeList.size(), hintData);


        idTypeAdapter = new HintAdapter<IdCardTypeData>(MyApplication.getCurrentActivityContext(), R.layout.spinner_black_textview, idCardTypeList);

        idTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        id_type_spinner.setAdapter(idTypeAdapter);
        id_type_spinner.setSelection(idTypeAdapter.getCount());

        id_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //if(position!=0)
                //{
                profileDetails.idType=null;
                if (position != (idTypeAdapter.getCount())) {
                    IdCardTypeData idCardTypeData = idCardTypeList.get(position);
                    // Utils.showToastMessage("selected insurance " + selectedInsurance);

                    if (idCardTypeData != null) {
                        profileDetails.idType=idCardTypeData.getIdCardName();
                    }
                }


                //}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(patientProfileObj!=null) {
            String idtype = patientProfileObj.getIdtype();
            if(!TextUtils.isEmpty(idtype))
            {
                for(int i=0;i<idCardTypeList.size();i++)
                {
                    String temp=idCardTypeList.get(i).getIdCardName();
                    if(temp!=null && temp.equalsIgnoreCase(idtype))
                    {
                        id_type_spinner.setSelection(i);
                    }
                }
            }
        }


    }

    private void callNatinalityMapper() {

        if (nationalityAdapter != null) {
            // nationality_spinner.setSelection(nationalityAdapter.getCount());
            return;
        }
        MyApplication.showTransparentDialog();
        NationalityMapper mapper = new NationalityMapper();

        mapper.setOnNationalityListListener(new NationalityMapper.NationalityListListener() {
            @Override
            public void getNationalityListAPI(NationalityListAPI nationalityListAPI, String errorMessage) {
                MyApplication.hideTransaprentDialog();
                if (!isValidResponse(nationalityListAPI, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_nationality_list_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()). finish();

                        }

                        @Override
                        public void retryClick() {
                            callNatinalityMapper();
                        }
                    });
                    return;
                }
                setToNationalityListAdapter(nationalityListAPI.getData());
                callIdTypeMapper();
                //callInsuranceAcceptedMapper();
            }
        });

    }

    public void setToNationalityListAdapter(List<NationalityData> list) {
        final ArrayList<NationalityData> nationalityTypeList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            nationalityTypeList.addAll(list);

        }

        NationalityData hintData = new NationalityData();
        hintData.setNationalityName("Nationality");
        nationalityTypeList.add(nationalityTypeList.size(), hintData);


        nationalityAdapter = new HintAdapter<NationalityData>(MyApplication.getCurrentActivityContext(), R.layout.spinner_black_textview, nationalityTypeList);

        nationalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nationality_spinner.setAdapter(nationalityAdapter);
        nationality_spinner.setSelection(nationalityAdapter.getCount());

        nationality_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                profileDetails.nationality=null;

                if (position != (nationalityAdapter.getCount())) {
                    NationalityData selectedNationality = nationalityTypeList.get(position);
                    //Utils.showToastMessage("selected Nationality " + selectedNationality);

                    if (selectedNationality != null) {
                        //searchRequestParams.setNationalityList(null);
                        profileDetails.nationality=selectedNationality.getNationalityName();
                        //searchRequestParams.addNationality(selectedNationality.getId());
                    }
                }


                //}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(patientProfileObj!=null) {
            String nationality = patientProfileObj.getNationality();
            if(!TextUtils.isEmpty(nationality))
            {
                for(int i=0;i<nationalityTypeList.size();i++)
                {
                    String temp=nationalityTypeList.get(i).getNationalityName();
                    if(temp!=null && temp.equalsIgnoreCase(nationality))
                    {
                        nationality_spinner.setSelection(i);
                    }
                }
            }
        }

    }

    public void setToBloodGroupSpinnerAdapter() {

        final ArrayList<String> bloodGroupList = new ArrayList<>();

        bloodGroupList.add(Constants.BloodGroupValues.O_NEGE);
        bloodGroupList.add(Constants.BloodGroupValues.O_POSI);

        bloodGroupList.add(Constants.BloodGroupValues.A_NEGE);
        bloodGroupList.add(Constants.BloodGroupValues.A_POSI);

        bloodGroupList.add(Constants.BloodGroupValues.B_NEGE);
        bloodGroupList.add(Constants.BloodGroupValues.B_POSI);

        bloodGroupList.add(Constants.BloodGroupValues.AB_NEGE);
        bloodGroupList.add(Constants.BloodGroupValues.AB_POSI);

        bloodGroupList.add(Constants.BloodGroupValues.BLOOD_GROUP);
        // selectedGender= genderTypeList.get(2);
        final HintAdapter aa = new HintAdapter<String>(MyApplication.getCurrentActivityContext(), R.layout.spinner_black_textview, bloodGroupList);


        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        blood_group_spinner.setAdapter(aa);
        blood_group_spinner.setSelection(aa.getCount());

        blood_group_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != (aa.getCount())) {
                    String selectedGender = bloodGroupList.get(position);
                    if (selectedGender != null) {
                        profileDetails.bloodGroup=selectedGender;
                    }
                }
                //Utils.showToastMessage("selectedGender "+selectedGender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(patientProfileObj!=null) {
            String bloodgroup = patientProfileObj.getBloodgroup();
            if(!TextUtils.isEmpty(bloodgroup))
            {
                for(int i=0;i<bloodGroupList.size();i++)
                {
                    String temp=bloodGroupList.get(i);
                    if(temp!=null && temp.equalsIgnoreCase(bloodgroup))
                    {
                        blood_group_spinner.setSelection(i);
                    }
                }
            }
        }


    }

    private void initForViews() {
        image_linear= getFragemtView().findViewById(R.id.image_linear);

        profile_iv  = getFragemtView().findViewById(R.id.profile_iv);

        patient_name_et = getFragemtView().findViewById(R.id. patient_name_et);
        gender_type_spinner  = getFragemtView().findViewById(R.id.gender_spinner);
        marital_status_spinner = getFragemtView().findViewById(R.id. marital_status_spinner);
       // patient_age_et   = getFragemtView().findViewById(R.id.patient_age_et);
        date_of_birth_tv = getFragemtView().findViewById(R.id.date_of_birth_tv);
        blood_group_spinner = getFragemtView().findViewById(R.id.blood_group_spinner);
        height_et = getFragemtView().findViewById(R.id.height_et);
        known_allergies_et = getFragemtView().findViewById(R.id.known_allergies_et);


        //My Profile
        primary_contact_no_et = getFragemtView().findViewById(R.id.primary_contact_no_et);
        secondary_contact_no_et  = getFragemtView().findViewById(R.id.secondary_contact_no_et);
        residence_contact_no_et  = getFragemtView().findViewById(R.id.residence_contact_no_et);
        contact_email_et = getFragemtView().findViewById(R.id.email_et);
        contact_address_et = getFragemtView().findViewById(R.id.address_et);
        contact_language_known_et = getFragemtView().findViewById(R.id.language_known_et);
        nationality_spinner = getFragemtView().findViewById(R.id.nationality_spinner);
        id_type_spinner = getFragemtView().findViewById(R.id. id_type_spinner);
        id_number_et = getFragemtView().findViewById(R.id. id_number_et);
        country_spinner = getFragemtView().findViewById(R.id.country_spinner);
        addressAutoCompleteTextView = getFragemtView().findViewById(R.id.google_places_actv);





        //Emergency Contact
        emergency_contact_name_et = getFragemtView().findViewById(R.id.emergency_contact_name_et);
        //emergency_contact_relationship_et = getFragemtView().findViewById(R.id.emergency_contact_relationship_et);
        emergency_contact_number_et = getFragemtView().findViewById(R.id.emergency_contact_number_et);
        emergency_contact_emailid_et = getFragemtView().findViewById(R.id.emergency_contact_emailid_et);
        relation_spinner  = getFragemtView().findViewById(R.id.relation_spinner);



        //Insurance
        insurance_tpa_et  = getFragemtView().findViewById(R.id.insurance_tpa_et);
        insurance_provider_spinner  = getFragemtView().findViewById(R.id.insurance_provider_spinner);
        insurance_insurance_number_et = getFragemtView().findViewById(R.id.insurance_insurance_number_et);
        insurance_insurance_policy_et  = getFragemtView().findViewById(R.id.insurance_insurance_policy_et);
        insurance_member_id_et  = getFragemtView().findViewById(R.id.insurance_member_id_et);
        insurance_type_et  = getFragemtView().findViewById(R.id.insurance_type_et);
        insurance_valid_from_tv  = getFragemtView().findViewById(R.id.insurance_valid_from_tv);
        insurance_valid_to_tv  = getFragemtView().findViewById(R.id.insurance_valid_to_tv);
        insurance_co_pay_et  = getFragemtView().findViewById(R.id.insurance_co_pay_et);
        insurance_scheme_et  = getFragemtView().findViewById(R.id.insurance_scheme_et);
        insurance_reason_et  = getFragemtView().findViewById(R.id.insurance_reason_et);
        insurance_organisation_et  = getFragemtView().findViewById(R.id.insurance_organisation_et);
        insurance_max_limit_et  = getFragemtView().findViewById(R.id.insurance_max_limit_et);
        insurance_secoundary_spinner  = getFragemtView().findViewById(R.id.insurance_secoundary_spinner);
        insurance_secoundary_number_et  = getFragemtView().findViewById(R.id.insurance_secoundary_number_et);




        expandableLayout_city_view = getFragemtView().findViewById(R.id.expandableLayout_city_view);
       // setExpandedCityViewListener(expandableLayout_city_view);
        expandableLayout_city_view.setInRecyclerView(false);
        //expandableLayout.setBackgroundColor(ContextCompat.getColor(this, item.colorId2));
        expandableLayout_city_view.setInterpolator(com.github.aakira.expandablelayout.Utils.createInterpolator(com.github.aakira.expandablelayout.Utils.LINEAR_OUT_SLOW_IN_INTERPOLATOR));
        //expandableLayout.setExpanded(expandState.get(position));
        expandableLayout_city_view.setExpanded(false);
          //contact_insurance_details_et = getFragemtView().findViewById(R.id.insurance_details_et);

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


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        compute();
        setEditModeOrNot();
    }

    private void setEditModeOrNot() {
        profile_top_details_mask_tv.setVisibility(isEditMode ? View.GONE : View.VISIBLE);
        insurance_details_mask_tv.setVisibility(isEditMode ? View.GONE : View.VISIBLE);
        emergency_details_mask_tv.setVisibility(isEditMode ? View.GONE : View.VISIBLE);
        contact_details_mask_tv.setVisibility(isEditMode ? View.GONE : View.VISIBLE);
    }

    private void compute() {

        {
            image_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showImageSourceDialog();
                }
            });
        }

        {
            setExpandedViewListener(expandableLayout_contact_el, contact_details_linear, R.id.contact_iv);
            expandableLayout_contact_el.initLayout();
            contact_details_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandableLayout_contact_el.toggle();
                    hideKeyBord(expandableLayout_contact_el);
                    callNatinalityMapper();
                }
            });
        }


        {
            setExpandedViewListener(expandableLayout_emergency_el, emergency_details_linear, R.id.emergency_iv);
            expandableLayout_emergency_el.initLayout();


            emergency_details_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandableLayout_emergency_el.toggle();
                    hideKeyBord(expandableLayout_emergency_el);
                }
            });
        }
        {
            setExpandedViewListener(expandableLayout_insurance_el, insurance_details_linear, R.id.insurance_iv);
            expandableLayout_insurance_el.initLayout();

            insurance_details_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandableLayout_insurance_el.toggle();
                    hideKeyBord(expandableLayout_insurance_el);
                    callInsuranceAcceptedMapper();
                }
            });
        }


        // expandableLayout_emergency_el.collapse();
        //expandableLayout_insurance_el.collapse();


    }


    private void setExpandedViewListener(final ExpandableLinearLayout expandableLayout, final View view, int imageId) {
        expandableLayout.setInRecyclerView(false);
        //expandableLayout.setBackgroundColor(ContextCompat.getColor(this, item.colorId2));
        expandableLayout.setInterpolator(com.github.aakira.expandablelayout.Utils.createInterpolator(com.github.aakira.expandablelayout.Utils.LINEAR_OUT_SLOW_IN_INTERPOLATOR));
        //expandableLayout.setExpanded(expandState.get(position));
        expandableLayout.setExpanded(false);
        //expandableLayout.
        final ImageView imageView = view.findViewById(imageId);
        imageView.setBackgroundResource(R.drawable.plus_icon_grey);
        expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //expandableLayout.setFocusable(true);
                        if (imageView != null) {
                            imageView.setBackgroundResource(R.drawable.minus_icon_grey);
                            //expandableLayout.getParent().requestChildFocus(expandableLayout,expandableLayout);
                            scrollToView(myScrollView, view);
                            //((ScrollView)getFragemtView().findViewById(R.id.scroll)).fullScroll(View.FOCUS_DOWN);

                        }
                    }
                }, 200);

            }

            @Override
            public void onPreClose() {
                if (imageView != null) {
                    imageView.setBackgroundResource(R.drawable.plus_icon_grey);
                }
            }
        });
    }



    public void onDateOfBirthTextviewClick(final SublimePickerFragment.Callback callback,final SelectedDate selectedObj,boolean isDOB) {

        SublimePickerFragment pickerFrag = new SublimePickerFragment();
        pickerFrag.setCallback(callback);

        // Options
        Pair<Boolean, SublimeOptions> optionsPair = getOptions(selectedObj,isDOB);

        if (!optionsPair.first) { // If options are not valid
            // showToastMessage("No pickers activated");
            return;
        }

        // Valid options
        Bundle bundle = new Bundle();
        bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
        pickerFrag.setArguments(bundle);

        pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        pickerFrag.show(getChildFragmentManager(), "SUBLIME_PICKER");


    }


    Pair<Boolean, SublimeOptions> getOptions(SelectedDate selectedDate,boolean isDOB) {
        SublimeOptions options = new SublimeOptions();
        if(isDOB) {
            Calendar endCalendar = Calendar.getInstance();
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.set(Calendar.YEAR, startCalendar.get(Calendar.YEAR) - 120);

            options.setDateRange(startCalendar.getTimeInMillis(), endCalendar.getTimeInMillis());
        }

        if (selectedDate != null) {
            options.setDateParams(selectedDate);
        }
        int displayOptions = SublimeOptions.ACTIVATE_DATE_PICKER;


        // if (rbDatePicker.getVisibility() == View.VISIBLE && rbDatePicker.isChecked()) {
        options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);
        /*} else if (rbTimePicker.getVisibility() == View.VISIBLE && rbTimePicker.isChecked()) {
            options.setPickerToShow(SublimeOptions.Picker.TIME_PICKER);
        } else if (rbRecurrencePicker.getVisibility() == View.VISIBLE && rbRecurrencePicker.isChecked()) {
            options.setPickerToShow(SublimeOptions.Picker.REPEAT_OPTION_PICKER);
        }
*/
        options.setDisplayOptions(displayOptions);

        // Enable/disable the date range selection feature
        //options.setCanPickDateRange(cbAllowDateRangeSelection.isChecked());

        // Example for setting date range:
        // Note that you can pass a date range as the initial date params
        // even if you have date-range selection disabled. In this case,
        // the user WILL be able to change date-range using the header
        // TextViews, but not using long-press.

        /*Calendar startCal = Calendar.getInstance();
        startCal.set(2016, 2, 4);
        Calendar endCal = Calendar.getInstance();
        endCal.set(2016, 2, 17);

        options.setDateParams(startCal, endCal);*/

        // If 'displayOptions' is zero, the chosen options are not valid
        return new Pair<>(displayOptions != 0 ? Boolean.TRUE : Boolean.FALSE, options);
    }


    SublimePickerFragment.Callback mFragmentDOBCallback = new SublimePickerFragment.Callback() {
        @Override
        public void onCancelled() {
            // rlDateTimeRecurrenceInfo.setVisibility(View.GONE);
        }

        @Override
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate1,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {


            if (selectedDate1 == null || selectedDate1.getFirstDate() == null) {
                return;
            }
            selectedDOBObj = selectedDate1;
            Calendar selectedCal = selectedDate1.getFirstDate();

          //  String date = selectedCal.get(Calendar.YEAR) + "-" + (selectedCal.get(Calendar.MONTH) + 1) + "-" + selectedCal.get(Calendar.DAY_OF_MONTH);
            SimpleDateFormat format = new SimpleDateFormat(Constants.DISPLAY_DATE_FORMAT);
            SimpleDateFormat requestFormat = new SimpleDateFormat(Constants.REQUEST_DATE_FORMAT);
            profileDetails.dateofBirth=requestFormat.format(selectedCal.getTime());
            date_of_birth_tv.setText(format.format(selectedCal.getTime()));

        }
    };

    SublimePickerFragment.Callback mFragmentValidFromCallback = new SublimePickerFragment.Callback() {
        @Override
        public void onCancelled() {
            // rlDateTimeRecurrenceInfo.setVisibility(View.GONE);
        }

        @Override
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate1,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {


            if (selectedDate1 == null || selectedDate1.getFirstDate() == null) {
                return;
            }
            selectedValidFromObj = selectedDate1;
            Calendar selectedCal = selectedDate1.getFirstDate();

            //  String date = selectedCal.get(Calendar.YEAR) + "-" + (selectedCal.get(Calendar.MONTH) + 1) + "-" + selectedCal.get(Calendar.DAY_OF_MONTH);
            SimpleDateFormat format = new SimpleDateFormat(Constants.DISPLAY_DATE_FORMAT);
            SimpleDateFormat requestFormat = new SimpleDateFormat(Constants.REQUEST_DATE_FORMAT);
            profileDetails.fromvalidity=requestFormat.format(selectedCal.getTime());
            insurance_valid_from_tv.setText(format.format(selectedCal.getTime()));

        }
    };

    SublimePickerFragment.Callback mFragmentValidToCallback = new SublimePickerFragment.Callback() {
        @Override
        public void onCancelled() {
            // rlDateTimeRecurrenceInfo.setVisibility(View.GONE);
        }

        @Override
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate1,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {


            if (selectedDate1 == null || selectedDate1.getFirstDate() == null) {
                return;
            }
            selectedValidToObj = selectedDate1;
            Calendar selectedCal = selectedDate1.getFirstDate();

            //  String date = selectedCal.get(Calendar.YEAR) + "-" + (selectedCal.get(Calendar.MONTH) + 1) + "-" + selectedCal.get(Calendar.DAY_OF_MONTH);
            SimpleDateFormat format = new SimpleDateFormat(Constants.DISPLAY_DATE_FORMAT);
            SimpleDateFormat requestFormat = new SimpleDateFormat(Constants.REQUEST_DATE_FORMAT);
            profileDetails.tovalidity=requestFormat.format(selectedCal.getTime());
            insurance_valid_to_tv.setText(format.format(selectedCal.getTime()));

        }
    };


    @Override
    View getFragemtView() {
        return fragmentView;
    }

    public void isFromEditMode(boolean isEditMode) {
        this.isEditMode = isEditMode;
    }

public SaveButtonClickListener saveButtonClickListener= new SaveButtonClickListener() {
    @Override
    public void saveButtonClick() {

        updateProfileDetails();
    }
};


    private void callCountryListMapper() {
        //TODO call country list mapper
        CountryListMapper mapper = new CountryListMapper();
        mapper.setOnCountryListListener(new CountryListMapper.CountryListListener() {
            @Override
            public void getCountryListAPI(CountryListAPI countryListAPI, String errorMessage) {
                if (!isValidResponse(countryListAPI, errorMessage)) {

                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_country_list_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()). finish();
                        }

                        @Override
                        public void retryClick() {
                            callCountryListMapper();
                        }
                    });
                    return;
                }

                setToCountryAdapter(countryListAPI.getData());

            }
        });

    }

    public void setToCountryAdapter(List<CountryData> list) {
        final ArrayList<CountryData> countryTypeList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            countryTypeList.addAll(list);

        }

        CountryData hintData = new CountryData();
        hintData.setCountry("Country");
        countryTypeList.add(countryTypeList.size(), hintData);
        setToCityListAdapter(null);


        final HintAdapter aa = new HintAdapter<CountryData>(MyApplication.getCurrentActivityContext(), R.layout.spinner_black_textview, countryTypeList);

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country_spinner.setAdapter(aa);
        country_spinner.setSelection(aa.getCount());

        country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //if(position!=0)
                //{
                // expandableLayout_city_view.collapse();
                profileDetails.country=null;
                if (position != (aa.getCount())) {
                    CountryData selectedCountry = countryTypeList.get(position);
                    if (selectedCountry != null) {
                        // Utils.showToastMessage("selected country " + selectedCountry);
                        if (!expandableLayout_city_view.isExpanded()) {
                            expandableLayout_city_view.toggle();
                        }
                        callCityListMapper(selectedCountry.getId());
                        profileDetails.country=selectedCountry.getCountry();
                       // searchRequestParams.setCountry(selectedCountry.getId());

                       // namesSearch.setCountryId(selectedCountry.getId());
                    }
                }


                //}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(patientProfileObj!=null) {
            String country = patientProfileObj.getAddress().getCountry();
            if(!TextUtils.isEmpty(country))
            {
                for(int i=0;i<countryTypeList.size();i++)
                {
                    String temp=countryTypeList.get(i).getCountry();
                    if(temp!=null && temp.equalsIgnoreCase(country))
                    {
                        country_spinner.setSelection(i);
                    }
                }
            }
        }


    }

    private void callCityListMapper(String country) {
        CityListMapper cityListMapper = new CityListMapper(country);
        cityListMapper.setOnCityListListener(new CityListMapper.CityListListener() {
            @Override
            public void getCityListAPI(CityListAPI cityListAPI, String errorMessage) {
                if (!isValidResponse(cityListAPI, errorMessage)) {
                    return;
                }
                setToCityListAdapter(cityListAPI.getData());

            }
        });

    }


    public void setToCityListAdapter(List<CityData> list) {
        if (list == null) {
            expandableLayout_city_view.collapse();
            return;
        } else {
            expandableLayout_city_view.expand();
        }
        final ArrayList<CityData> cityTypeList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            cityTypeList.addAll(list);

        }


        CityData hintData = new CityData();
        hintData.setCityName("City");
        cityTypeList.add(cityTypeList.size(), hintData);


        city_type_spinner = getFragemtView().findViewById(R.id.city_type_spinner);
        final HintAdapter aa = new HintAdapter<CityData>(MyApplication.getCurrentActivityContext(), R.layout.spinner_black_textview, cityTypeList);

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

               /* if(aa.getCount()==1)
                {
                    Utils.showToastMessage("Please select country");
                    return;
                }*/
                //if(position!=0)
                //{
                profileDetails.city=null;
                if (position != (aa.getCount())) {
                    CityData selectedCity = cityTypeList.get(position);
                    Utils.showToastMessage("selected city " + selectedCity);
                    if (selectedCity != null) {
                        profileDetails.city=selectedCity.getCityName();
                        //namesSearch.setCity(selectedCity.getId());
                       // callDoctorsClinicNamesMapper();
                        //searchRequestParams.setCity(selectedCity.getId());
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if(patientProfileObj!=null) {
            String city = patientProfileObj.getAddress().getCity();
            if(!TextUtils.isEmpty(city))
            {
                for(int i=0;i<cityTypeList.size();i++)
                {
                    String temp=cityTypeList.get(i).getCityName();
                    if(temp!=null && temp.equalsIgnoreCase(city))
                    {
                        city_type_spinner.setSelection(i);
                    }
                }
            }
        }

    }

    private void updateProfileDetails() {
       // Utils.showToastMsg("Now click on Save");
        setProfileData();
       // Utils.showToastMsg(profileDetails.toString());

        ProfileSaveMapper mapper= new ProfileSaveMapper(profileDetails);
        mapper.setOnProfileSaveListener(new ProfileSaveMapper.ProfileSaveListener() {
            @Override
            public void profileSave(AbstractResponse response, String errorMessage) {
                if (!isValidResponse(response, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_save_profile_details_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()). finish();
                        }

                        @Override
                        public void retryClick() {
                            updateProfileDetails();
                        }
                    });
                    return;
                }
                if(successListener!=null)
                {
                    successListener.success();
                }
            }
        });




    }

     /*
    { "access_key": "360f19438d8cb884057f79a1a55df441",
            "patientName" : "Swathi k", "gender" : "Female",
            "maritial_status" : "married", "dateofBirth" : "01-01-1986",
            "mobile" : "9876543210", "secondarymobile" :"1234567890",
            "residencemobile" :"8965321025", "country" : "ind",
            "city" : "Hyd", "locality":"airport area", "address" : "begumpet",
            "languagesKnown" : "Telugu,Hindi", "nationality" :"Indian" ,
            "idType": "Aadhar Card", "idNumber": "343434534534536468",
            "occupation" : "emp1", "organisation" : "abc", "bloodGroup" : "O-" ,
            "height" : "5.6", "knownAllergies" : "test knownAllergies", "tpa": "1" ,
            "tpaid" : "TPA002", "insurancePackage" : "Ace Life" , "insuranceNumber" : "98745632102154" ,
            "policy" : "1", "policy_number" : "1236987456", "memberid" : "845632569",
            "type" : "1", "scheme" : "1", "reason" : "1", "copay" : "3" , "maxlimit" : "5",
            "fromvalidity" : "2017-01-02", "tovalidity" : "2017-08-02", "secondaryinsurancePackage" : "LIC",
            "secondaryinsuranceNumber" : "45568877654566879", "emergencyContactName" : "suresh" ,
            "emergencyContactRelationship" : "Brother", "emergencyContactNumber" : "7894561236",
            "emergencyContactEmail" : "suresh@gmail.com"
    }
    */

    private void setProfileData() {
        profileDetails.patientName= patient_name_et.getText().toString();
       // profileDetails.age=  patient_age_et.getText().toString();//TODO add age kay in API
        profileDetails.height=height_et.getText().toString();
        profileDetails.knownAllergies= known_allergies_et.getText().toString();


        //Contact details
      // profileDetails.mobile= contact_et.getText().toString();
        profileDetails.mobile= primary_contact_no_et.getText().toString();
        profileDetails.secondarymobile= secondary_contact_no_et.getText().toString();
        profileDetails.residencemobile= residence_contact_no_et.getText().toString();
       // contact_email_et = getFragemtView().findViewById(R.id.email_et);
        profileDetails.address= contact_address_et.getText().toString();
        profileDetails.languagesKnown= contact_language_known_et.getText().toString();
        profileDetails.idNumber=id_number_et.getText().toString();


        //Emergency Contact
        profileDetails.emergencyContactName= emergency_contact_name_et.getText().toString();
        profileDetails.emergencyContactNumber= emergency_contact_number_et.getText().toString();
        profileDetails.emergencyContactEmail= emergency_contact_emailid_et.getText().toString();
       // relation_spinner  = getFragemtView().findViewById(R.id.relation_spinner);



        //Insurance
        profileDetails.tpa= insurance_tpa_et.getText().toString();
        //insurance_provider_spinner  = getFragemtView().findViewById(R.id.insurance_provider_spinner);
        profileDetails.insuranceNumber= insurance_insurance_number_et.getText().toString();
        profileDetails.policy= insurance_insurance_policy_et.getText().toString();
        profileDetails.memberid= insurance_member_id_et.getText().toString();
        profileDetails.type=insurance_type_et.getText().toString();
        //insurance_valid_from_tv.getText().toString();
       // insurance_valid_to_tv.getText().toString();
        profileDetails.copay=insurance_co_pay_et.getText().toString();
        profileDetails.scheme=insurance_scheme_et.getText().toString();
        profileDetails.reason=insurance_reason_et.getText().toString();
        profileDetails.organisation=insurance_organisation_et.getText().toString();
        profileDetails.maxlimit=insurance_max_limit_et.getText().toString();
       // insurance_secoundary_spinner  = getFragemtView().findViewById(R.id.insurance_secoundary_spinner);
        profileDetails.secondaryinsuranceNumber= insurance_secoundary_number_et.getText().toString();


    }

    private void fillProfileDataToViews()
    {
        if(patientProfileObj==null)
        {
            return;
        }
        MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.profile_icon, profile_iv, patientProfileObj.getPatientlogo());



        patient_name_et.setText(patientProfileObj.getPatientname());
       height_et.setText(patientProfileObj.getHeight());
       known_allergies_et.setText(patientProfileObj.getKnownallergies());


        //Contact details
        // profileDetails.mobile= contact_et.getText().toString();
       primary_contact_no_et.setText(patientProfileObj.getContactNo().getPrimary());
       secondary_contact_no_et.setText(patientProfileObj.getContactNo().getSecondarymobile());
       residence_contact_no_et.setText(patientProfileObj.getContactNo().getResidencemobile());
        // contact_email_et = getFragemtView().findViewById(R.id.email_et);
        contact_address_et.setText(patientProfileObj.getAddress().getAddress());

       contact_language_known_et.setText(patientProfileObj.getLanguagesknown());
        id_number_et.setText(patientProfileObj.getIdnumber());

        addressAutoCompleteTextView.setOnItemClickListener(null);
        addressAutoCompleteTextView.setText(patientProfileObj.getAddress().getLocality());
        addressAutoCompleteTextView.setOnItemClickListener(adapterViewListener);



        //Emergency Contact
        emergency_contact_name_et.setText(patientProfileObj.getEmergencycontactname());
        emergency_contact_number_et.setText(patientProfileObj.getEmergencycontactnumber());
        emergency_contact_emailid_et.setText(patientProfileObj.getEmergencycontactemail());
        // relation_spinner  = getFragemtView().findViewById(R.id.relation_spinner);



        //Insurance
       insurance_tpa_et.setText(patientProfileObj.getTpa());
        //insurance_provider_spinner  = getFragemtView().findViewById(R.id.insurance_provider_spinner);
        insurance_insurance_number_et.setText(patientProfileObj.getInsuranceId());
        insurance_insurance_policy_et.setText(patientProfileObj.getPolicy());
        insurance_member_id_et.setText(patientProfileObj.getMemberid());
        insurance_type_et.setText(patientProfileObj.getType());
        //insurance_valid_from_tv.getText().toString();
        // insurance_valid_to_tv.getText().toString();
        insurance_co_pay_et.setText(patientProfileObj.getCopay());
        insurance_scheme_et.setText(patientProfileObj.getScheme());
        insurance_reason_et.setText(patientProfileObj.getReason());
        insurance_organisation_et.setText(patientProfileObj.getOrganisation());
        insurance_max_limit_et.setText(patientProfileObj.getMaxlimit());
        // insurance_secoundary_spinner  = getFragemtView().findViewById(R.id.insurance_secoundary_spinner);
       insurance_secoundary_number_et.setText(patientProfileObj.getSecondaryinsurancenumber());

        //"dateofbirth": "01/01/1986",
        date_of_birth_tv.setText(patientProfileObj.getDateofbirth());


        SimpleDateFormat format = new SimpleDateFormat(Constants.DISPLAY_DATE_FORMAT);
        SimpleDateFormat requestFormat = new SimpleDateFormat(Constants.REQUEST_DATE_FORMAT);


        //For Date of birth
        if(patientProfileObj.getDateofbirth()!=null) {
            try {

                Date date = requestFormat.parse(patientProfileObj.getDateofbirth());
                if (selectedDOBObj == null) {
                    selectedDOBObj = new SelectedDate(Calendar.getInstance());
                }
                selectedDOBObj.setTimeInMillis(date.getTime());
                date_of_birth_tv.setText(format.format(date));
                profileDetails.dateofBirth=patientProfileObj.getDateofbirth();

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        //Validity from
        if(patientProfileObj.getValidityfrom()!=null) {
            try {

                Date date = requestFormat.parse(patientProfileObj.getValidityfrom());
                if (selectedValidFromObj == null) {
                    selectedValidFromObj = new SelectedDate(Calendar.getInstance());
                }
                selectedValidFromObj.setTimeInMillis(date.getTime());
                insurance_valid_from_tv.setText(format.format(date));
                profileDetails.fromvalidity=patientProfileObj.getValidityfrom();

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }



        //For validity to
        if(patientProfileObj.getValidityto()!=null) {
            try {

                Date date = requestFormat.parse(patientProfileObj.getValidityto());
                if (selectedValidToObj == null) {
                    selectedValidToObj = new SelectedDate(Calendar.getInstance());
                }
                selectedValidToObj.setTimeInMillis(date.getTime());
                insurance_valid_to_tv.setText(format.format(date));
                profileDetails.fromvalidity=patientProfileObj.getValidityto();

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

      /*  insurance_valid_to_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateOfBirthTextviewClick(mFragmentValidToCallback,selectedValidToObj,false);
            }
        });
*/

    }

    public void setOnSuccessToUpdateProfileListener(SuccessToUpdateProfileListener successListener)
    {
        this.successListener=successListener;
    }

    @Override
    protected void setImage(final File file) {
        if(file!=null)
        {
            UploadProfilePicMapper picMapper= new UploadProfilePicMapper(file);
            picMapper.setOnUpdateProfilePicListener(new UploadProfilePicMapper.UpdateProfilePicListener() {
                @Override
                public void uploadProfilePic(AbstractResponse response, String errorMessage) {
                    if(!isValidResponse(response,errorMessage))
                    {
                       return;
                    }
                    Utils.showToastMsg(response.getStatusMessage());
                    MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.profile_icon, profile_iv, file);
                }

            });
        }

    }

    public void setPatientProfileObj(PatientProfileData patientProfileObj) {
        this.patientProfileObj = patientProfileObj;
    }

    public interface SuccessToUpdateProfileListener
    {
        void success();
    }

    public interface SaveButtonClickListener
    {
        void saveButtonClick();
    }

    AdapterView.OnItemClickListener adapterViewListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            if (view.getTag() != null) {
                GeoData tmpData = (GeoData) view.getTag();
                addressAutoCompleteTextView.setOnItemClickListener(null);
                // addressAutoCompleteTextView.setText(myGeodata.getAddress());
                profileDetails.locality=tmpData.getAddress();
                Utils.showToastMsg("Locality :"+profileDetails.locality);
                addressAutoCompleteTextView.setOnItemClickListener(adapterViewListener);
               // myGeodata = tmpData;
                //gettingLatLanFromGoogle(myGeodata);
                /*addressAutoCompleteTextView.setText(tmpData.getAddress());
                namesSearch.setLocality(tmpData.getAddress());
                callDoctorsClinicNamesMapper();*/

            } else {
                addressAutoCompleteTextView.setText("");
            }
        }
    };

    private void showImageSourceDialog() {
        final CharSequence[] items = {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MyApplication.getCurrentActivityContext());
        builder.setTitle("Select");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                switch (item) {
                    case 0:
                        callCamera();
                        break;
                    case 1:
                       callGallery();
                        break;

                }
                dialog.dismiss();
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}
