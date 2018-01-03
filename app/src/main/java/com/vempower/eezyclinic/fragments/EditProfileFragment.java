package com.vempower.eezyclinic.fragments;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.IdCardTypeMapper;
import com.vempower.eezyclinic.mappers.InsuranceListMapper;
import com.vempower.eezyclinic.mappers.NationalityMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.CustomSpinnerSelection;
import com.vempower.eezyclinic.views.MyEditTextBlackCursorRR;
import com.vempower.eezyclinic.views.MyTextViewRR;
import com.vempower.stashdealcustomer.activities.AbstractActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by satish on 6/12/17.
 */

public class EditProfileFragment extends AbstractFragment {
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


    private MyEditTextBlackCursorRR patient_name_et, height_et,
            known_allergies_et, contact_et, contact_email_et,
            contact_address_et, contact_language_known_et, contact_nationality_et1,
            contact_id_type_et, contact_insurance_provider_et1, contact_insurance_details_et;

    private MyEditTextBlackCursorRR emergency_contact_name_et, emergency_contact_relationship_et1,
            emergency_contact_number_et, emergency_contact_emailid_et;

    private MyEditTextBlackCursorRR insurance_tpa_et,insurance_insurance_provider_et1,
            insurance_insurance_number_et,insurance_insurance_policy_et,
            insurance_member_id_et,insurance_type_et,
            insurance_co_pay_et,insurance_scheme_et,
            insurance_reason_et, insurance_organisation_et,insurance_max_limit_et,insurance_secoundary_number_et;

    private CustomSpinnerSelection blood_group_spinner,country_spinner, city_type_spinner,id_type_spinner,
            insurance_secoundary_spinner, nationality_spinner,insurance_provider_spinner, relation_spinner, gender_type_spinner,marital_status_spinner;
    private SuccessToUpdateProfileListener successListener;


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
                profileDetails.secondaryinsurancePackage=null;
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
                profileDetails.insurancePackage=null;
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
                profileDetails.insurancePackage=null;
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
                        profileDetails.gender=selectedGender;
                    }
                }
                //Utils.showToastMessage("selectedGender "+selectedGender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initForViews() {
        patient_name_et = getFragemtView().findViewById(R.id. patient_name_et);
        gender_type_spinner  = getFragemtView().findViewById(R.id.gender_spinner);
        marital_status_spinner = getFragemtView().findViewById(R.id. marital_status_spinner);

        date_of_birth_tv = getFragemtView().findViewById(R.id.date_of_birth_tv);
        //blood_group_et = getFragemtView().findViewById(R.id.blood_group_et);
        blood_group_spinner = getFragemtView().findViewById(R.id.blood_group_spinner);
        height_et = getFragemtView().findViewById(R.id.height_et);
        known_allergies_et = getFragemtView().findViewById(R.id.known_allergies_et);


        //My Profile
        contact_et = getFragemtView().findViewById(R.id.contact_et);
        contact_email_et = getFragemtView().findViewById(R.id.email_et);
        contact_address_et = getFragemtView().findViewById(R.id.address_et);
        contact_language_known_et = getFragemtView().findViewById(R.id.language_known_et);
        nationality_spinner = getFragemtView().findViewById(R.id.nationality_spinner);

        contact_id_type_et = getFragemtView().findViewById(R.id.id_type_et);
        //contact_insurance_provider_et = getFragemtView().findViewById(R.id.insurance_provider_et);
        insurance_secoundary_spinner  = getFragemtView().findViewById(R.id.insurance_secoundary_spinner);

        id_type_spinner = getFragemtView().findViewById(R.id. id_type_spinner);
        contact_insurance_details_et = getFragemtView().findViewById(R.id.insurance_details_et);

        //Emergency Contact
        emergency_contact_name_et = getFragemtView().findViewById(R.id.emergency_contact_name_et);
        //emergency_contact_relationship_et = getFragemtView().findViewById(R.id.emergency_contact_relationship_et);
        emergency_contact_number_et = getFragemtView().findViewById(R.id.emergency_contact_number_et);
        emergency_contact_emailid_et = getFragemtView().findViewById(R.id.emergency_contact_emailid_et);
        relation_spinner  = getFragemtView().findViewById(R.id.relation_spinner);



        //Insurance
        insurance_tpa_et  = getFragemtView().findViewById(R.id.insurance_tpa_et);
        //insurance_insurance_provider_et  = getFragemtView().findViewById(R.id.insurance_insurance_provider_et);
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
        insurance_secoundary_number_et  = getFragemtView().findViewById(R.id.insurance_secoundary_number_et);

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

    /**
     * Used to scroll to the given view.
     *
     * @param scrollViewParent Parent ScrollView
     * @param view             View to which we need to scroll.
     */
    private void scrollToView(final ScrollView scrollViewParent, final View view) {
        // Get deepChild Offset
        Point childOffset = new Point();
        getDeepChildOffset(scrollViewParent, view.getParent(), view, childOffset);
        // Scroll to child.
        scrollViewParent.smoothScrollTo(0, childOffset.y);
    }

    /**
     * Used to get deep child offset.
     * <p/>
     * 1. We need to scroll to child in scrollview, but the child may not the direct child to scrollview.
     * 2. So to get correct child position to scroll, we need to iterate through all of its parent views till the main parent.
     *
     * @param mainParent        Main Top parent.
     * @param parent            Parent.
     * @param child             Child.
     * @param accumulatedOffset Accumalated Offset.
     */
    private void getDeepChildOffset(final ViewGroup mainParent, final ViewParent parent, final View child, final Point accumulatedOffset) {
        ViewGroup parentGroup = (ViewGroup) parent;
        accumulatedOffset.x += child.getLeft();
        accumulatedOffset.y += child.getTop();
        if (parentGroup.equals(mainParent)) {
            return;
        }
        getDeepChildOffset(mainParent, parentGroup.getParent(), parentGroup, accumulatedOffset);
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

    private void updateProfileDetails() {
        Utils.showToastMsg("Now click on Save");
        setProfileData();
        Utils.showToastMsg(profileDetails.toString());



        /*if(successListener!=null)
        {
            successListener.success();
        }*/
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
       profileDetails.height= height_et.getText().toString();
       profileDetails.knownAllergies= known_allergies_et.getText().toString();


        //My Profile
       profileDetails.mobile= contact_et.getText().toString();
       profileDetails.address= contact_address_et.getText().toString();
       profileDetails.languagesKnown= contact_language_known_et.getText().toString();

       if(true)
       {
           return;
       }

       //profileDetails.insurance= contact_insurance_details_et = getFragemtView().findViewById(R.id.insurance_details_et);

        //Emergency Contact
        emergency_contact_name_et = getFragemtView().findViewById(R.id.emergency_contact_name_et);
        //emergency_contact_relationship_et = getFragemtView().findViewById(R.id.emergency_contact_relationship_et);
        emergency_contact_number_et = getFragemtView().findViewById(R.id.emergency_contact_number_et);
        emergency_contact_emailid_et = getFragemtView().findViewById(R.id.emergency_contact_emailid_et);
        relation_spinner  = getFragemtView().findViewById(R.id.relation_spinner);



        //Insurance
        insurance_tpa_et  = getFragemtView().findViewById(R.id.insurance_tpa_et);
        //insurance_insurance_provider_et  = getFragemtView().findViewById(R.id.insurance_insurance_provider_et);
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
    }

    public void setOnSuccessToUpdateProfileListener(SuccessToUpdateProfileListener successListener)
    {
        this.successListener=successListener;
    }

    public interface SuccessToUpdateProfileListener
    {
        void success();
    }

    public interface SaveButtonClickListener
    {
        void saveButtonClick();
    }

}