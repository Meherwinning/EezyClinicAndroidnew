package com.vempower.eezyclinic.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.vempower.eezyclinic.APICore.EditProfileViewAPIData;
import com.vempower.eezyclinic.APICore.EditProfileViewCountry;
import com.vempower.eezyclinic.APICore.EditProfileViewIdcardlist;
import com.vempower.eezyclinic.APICore.EditProfileViewInsurance;
import com.vempower.eezyclinic.APICore.EditProfileViewNationality;
import com.vempower.eezyclinic.APICore.PatientProfileData;
import com.vempower.eezyclinic.APICore.Patientinsurancedetail;
import com.vempower.eezyclinic.APICore.Tpalist;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.APIResponce.CityData;
import com.vempower.eezyclinic.APIResponce.CityListAPI;
import com.vempower.eezyclinic.APIResponce.CountryData;
import com.vempower.eezyclinic.APIResponce.CountryListAPI;
import com.vempower.eezyclinic.APIResponce.EditProfileViewAPI;
import com.vempower.eezyclinic.APIResponce.IdCardTypeAPI;
import com.vempower.eezyclinic.APIResponce.IdCardTypeData;
import com.vempower.eezyclinic.APIResponce.InsuranceData;
import com.vempower.eezyclinic.APIResponce.InsuranceListAPI;
import com.vempower.eezyclinic.APIResponce.NationalityData;
import com.vempower.eezyclinic.APIResponce.NationalityListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.EditProfileActivity;
import com.vempower.eezyclinic.adapters.HintAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.core.EditProfileDetails;
import com.vempower.eezyclinic.core.SecondaryInsurance;
import com.vempower.eezyclinic.googleaddressselection.GeoData;
import com.vempower.eezyclinic.googleaddressselection.GooglePlacesAutocompleteAdapter;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.interfaces.ImageProcessListener;
import com.vempower.eezyclinic.mappers.AddSecoundaryInsuranceMapper;
import com.vempower.eezyclinic.mappers.CityListMapper;
import com.vempower.eezyclinic.mappers.CountryListMapper;
import com.vempower.eezyclinic.mappers.DeleteSecoundaryInsuranceMapper;
import com.vempower.eezyclinic.mappers.EditProfileViewMapper;
import com.vempower.eezyclinic.mappers.EditSecoundaryInsuranceMapper;
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
import com.vempower.eezyclinic.views.MyEditTextBlackCursor;
;
import com.vempower.eezyclinic.activities.AbstractActivity;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by satish on 6/12/17.
 */

public class EditProfileFragment extends ImageProcessFragment {
    private LinearLayout contact_details_linear, insurance_details_linear, emergency_details_linear, secondary_insurance_details_linear;

    private View fragmentView;
    private ExpandableLinearLayout expandableLayout_contact_el, expandableLayout_insurance_el, secondary_expandableLayout_insurance_el, expandableLayout_emergency_el;
    private TextView date_of_birth_tv, insurance_valid_from_tv, insurance_valid_to_tv;
    private SelectedDate selectedDOBObj, selectedValidFromObj, selectedValidToObj;

    private TextView profile_top_details_mask_tv, insurance_details_mask_tv,
            emergency_details_mask_tv, contact_details_mask_tv, secondary_insurance_details_mask_tv;
    private boolean isEditMode;
    private ScrollView myScrollView;
    private EditProfileDetails profileDetails;


    SimpleDateFormat format = new SimpleDateFormat(Constants.DISPLAY_DATE_FORMAT);
    SimpleDateFormat requestFormat = new SimpleDateFormat(Constants.REQUEST_DATE_FORMAT);


    private HintAdapter<EditProfileViewNationality> nationalityAdapter;
    private HintAdapter<InsuranceData> insuranceAdapter;
    private HintAdapter<EditProfileViewInsurance> insuranceAdapter1;
    private HintAdapter<EditProfileViewIdcardlist> idTypeAdapter;
    private HintAdapter<Tpalist> tPAListAdapter;

    // private List<InsuranceData> insuranceNameList;


    private MyEditTextBlackCursor patient_name_et, height_et, patient_age_et1,
            known_allergies_et, primary_contact_no_et, secondary_contact_no_et, residence_contact_no_et,
            contact_email_et, id_number_et,
            contact_address_et, contact_language_known_et, contact_nationality_et1,
            contact_id_type_et1, contact_insurance_provider_et1, contact_insurance_details_et1;

    private MyEditTextBlackCursor emergency_contact_name_et, emergency_contact_relationship_et1,
            emergency_contact_number_et, emergency_contact_emailid_et;

    private MyEditTextBlackCursor primary_policy_number_et, insurance_tpa_id_et, insurance_insurance_provider_et1,
            insurance_insurance_number_et, insurance_insurance_policy_et,
            insurance_member_id_et, insurance_type_et,
            insurance_co_pay_et, insurance_scheme_et,
            insurance_reason_et, insurance_organisation_et, insurance_max_limit_et, insurance_secoundary_number_et1;

    private CustomSpinnerSelection primary_tpa_list_spinner, blood_group_spinner, country_spinner, city_type_spinner, id_type_spinner,
            insurance_secoundary_spinner1, nationality_spinner, insurance_provider_spinner, relation_spinner, gender_type_spinner, marital_status_spinner;
    private SuccessToUpdateProfileListener successListener;

    private ExpandableLinearLayout expandableLayout_city_view;

    private MyAutoCompleteBlackCursorTextView addressAutoCompleteTextView;
    private GooglePlacesAutocompleteAdapter googlePlacesAutocompleteAdapter;
    //private File imageFile;
    private LinearLayout image_linear, insurance_add_linear;
    private ImageView profile_iv, id_back_iv, id_front_iv, insurance_back_iv, insurance_front_iv;
    //private PatientProfileData patientProfileObj;
    private AppCompatButton add_insurance_btn;
    private LayoutInflater inflater;


    private EditProfileViewAPIData profileAPIData;
    private ProgressDialog progress;
    private ProgressBar loding_progress;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.edit_profile_layout, container, false);
        profileDetails = new EditProfileDetails();
        myInit();
        return fragmentView;

    }

    private void myInit() {
        inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        initForViews();
        profile_top_details_mask_tv = getFragemtView().findViewById(R.id.profile_top_details_mask_tv);
        insurance_details_mask_tv = getFragemtView().findViewById(R.id.insurance_details_mask_tv);
        emergency_details_mask_tv = getFragemtView().findViewById(R.id.emergency_details_mask_tv);
        contact_details_mask_tv = getFragemtView().findViewById(R.id.contact_details_mask_tv);
        secondary_insurance_details_mask_tv = getFragemtView().findViewById(R.id.secondary_insurance_details_mask_tv);

        contact_details_linear = getFragemtView().findViewById(R.id.contact_details_linear);
        insurance_details_linear = getFragemtView().findViewById(R.id.insurance_details_linear);
        emergency_details_linear = getFragemtView().findViewById(R.id.emergency_details_linear);

        secondary_insurance_details_linear = getFragemtView().findViewById(R.id.secondary_insurance_details_linear);

        expandableLayout_contact_el = getFragemtView().findViewById(R.id.expandableLayout_contact_el);
        expandableLayout_insurance_el = getFragemtView().findViewById(R.id.expandableLayout_insurance_el);
        expandableLayout_emergency_el = getFragemtView().findViewById(R.id.expandableLayout_emergency_el);
        secondary_expandableLayout_insurance_el = getFragemtView().findViewById(R.id.secondary_expandableLayout_insurance_el);

        myScrollView = ((ScrollView) getFragemtView().findViewById(R.id.scroll));
        loding_progress = getFragemtView().findViewById(R.id.loding_progress);


        //Insurance add
        insurance_add_linear = getFragemtView().findViewById(R.id.insurance_add_linear);
        add_insurance_btn = getFragemtView().findViewById(R.id.add_insurance_btn);


        add_insurance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                View convertView = inflater
                        .inflate(R.layout.profile_secoundary_insurance_edit_layout1, null, false);
                final InsuranceHolder holder = new InsuranceHolder(convertView);

                holder.setOnImageProcessListener(new ImageProcessListener() {
                    @Override
                    public void callShowImageSourceDialog(int id) {
                        showImageSourceDialog(id, this);
                    }

                    @Override
                    public void setImage(File file, int responseId) {
                        holder.setImage(file, responseId);
                    }
                });

            }
        });


        date_of_birth_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateOfBirthTextviewClick(mFragmentDOBCallback, selectedDOBObj, true);
            }
        });
        insurance_valid_from_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateOfBirthTextviewClick(mFragmentValidFromCallback, selectedValidFromObj, false);
            }
        });

        insurance_valid_to_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateOfBirthTextviewClick(mFragmentValidToCallback, selectedValidToObj, false);
            }
        });

        callEditProfileViewMapper();


    }

    private void callEditProfileViewMapper() {
        loding_progress.setVisibility(View.VISIBLE);
        EditProfileViewMapper mapper = new EditProfileViewMapper();

        mapper.setOnEditProfileViewAPIListener(new EditProfileViewMapper.EditProfileViewAPIListener() {
            @Override
            public void getEditProfileViewAPI(EditProfileViewAPI profileAPI, String errorMessage) {
                if (!isValidResponse(profileAPI, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_patient_profile_details_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()).finish();
                        }

                        @Override
                        public void retryClick() {
                            callEditProfileViewMapper();
                        }
                    });
                    return;
                }

                profileAPIData = profileAPI.getData();
                //MyApplication.showTransparentDialog();

                setToBloodGroupSpinnerAdapter();
                setToGenderSpinnerAdapter();
                setToMaritalStatusSpinnerAdapter();
                setRelationSpinner();
                callCountryListMapper();
                callNatinalityMapper();
                callIdTypeMapper();
                setInitForGooglePlacesAutocompleteTextView();
                setTpaListSpinnerData();


                fillProfileDataToViews();
                loding_progress.setVisibility(View.GONE);

            }
        });
    }


    private void setTpaListSpinnerData() {
        if (profileAPIData != null && profileAPIData.getTpalist() != null) {
            setToTpaListAdapter11(profileAPIData.getTpalist());
        }

    }

    private class InsuranceHolder {

        final View convertView;
        private TextView titleTv;
        private TextView delete_tv;
        private Patientinsurancedetail insurance;
        private HintAdapter<EditProfileViewInsurance> myInsuranceAdapter;
        private HintAdapter<Tpalist> myTPAListAdapter;
        private ImageProcessListener imageProcessListener;

        public final int FRONT_IMAGE_ID;
        public final int BACK_IMAGE_ID;

        private File frontImageFile, backImageFile;


        private MyEditTextBlackCursor insurance_tpa_id_et,
                insurance_insurance_number_et, insurance_insurance_policy_et, insurance_insurance_policy_number_et,
                insurance_member_id_et, insurance_type_et,
                insurance_co_pay_et, insurance_scheme_et,
                insurance_reason_et, insurance_max_limit_et;
        private ImageView insurance_back_iv, insurance_front_iv;

        private TextView insurance_valid_from_tv, insurance_valid_to_tv;


        private CustomSpinnerSelection insurance_provider_spinner, tpa_list_spinner;
        SelectedDate selectedValidFromObj, selectedValidToObj;

        public InsuranceHolder(View convertView) {
            this.convertView = convertView;
            FRONT_IMAGE_ID = new Random().nextInt();
            BACK_IMAGE_ID = new Random().nextInt();
            init();
            createNewInsurance();
        }

        public void setOnImageProcessListener(ImageProcessListener imageProcessListener) {
            this.imageProcessListener = imageProcessListener;
        }

        private void createNewInsurance() {
            setTitleName("Secondary Insurance - " + (insurance_add_linear.getChildCount() + 1));

            setExpandListener();
            setDeleteListener();

            convertView.setTag(this);
            insurance_add_linear.addView(convertView);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    scrollToView(myScrollView, add_insurance_btn);
                }
            }, 400);
        }

        private void setDeleteListener() {
            delete_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Utils.showToastMessage("Coming soon");
                    showMyDialog("Alert", "Are you sure to delete ?", "Yes", "No", new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                        }

                        @Override
                        public void retryClick() {

                            if (!TextUtils.isEmpty(insurance.getId())) {

                                DeleteSecoundaryInsuranceMapper mapper = new DeleteSecoundaryInsuranceMapper(insurance.getId());
                                mapper.setOnDeleteSecoundaryInsuranceListener(new DeleteSecoundaryInsuranceMapper.DeleteSecoundaryInsuranceListener() {
                                    @Override
                                    public void deleteSecoundaryInsurance(AbstractResponse response, String errorMessage) {
                                        if (!isValidResponse(response, errorMessage, true, false)) {
                                            return;
                                        }
                                        insurance_add_linear.removeView(convertView);
                                        Utils.showToastMessage(response.getStatusMessage());
                                        refreshNames();
                                    }
                                });


                                return;
                            } else {

                                insurance_add_linear.removeView(convertView);
                                Utils.showToastMessage("Success to delete");
                                refreshNames();
                            }


                        }
                    });
                }
            });
        }

        private void refreshNames() {
            if (insurance_add_linear.getChildCount() > 0) {
                int count = 1;
                for (int i = 0; i < insurance_add_linear.getChildCount(); i++) {
                    View view = insurance_add_linear.getChildAt(i);
                    if (view == null) {
                        continue;
                    }
                    TextView titleTv = view.findViewById(R.id.insurance_title_tv);
                    if (titleTv != null) {
                        titleTv.setText("Secondary Insurance - " + count);
                        count++;
                    }
                }
            }


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    scrollToView(myScrollView, insurance_add_linear);
                }
            }, 400);
        }

        private void setExpandListener() {
            final LinearLayout new_secondary_insurance_details_linear = convertView.findViewById(R.id.secondary_insurance_details_linear);
            final ExpandableLinearLayout new_secondary_expandableLayout_insurance_el = convertView.findViewById(R.id.secondary_expandableLayout_insurance_el);

            {
                setExpandedViewListener(new_secondary_expandableLayout_insurance_el, new_secondary_insurance_details_linear, R.id.secondary_insurance_iv);
                // secondary_insurance_details_linear.initLayout();
                new_secondary_insurance_details_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new_secondary_expandableLayout_insurance_el.toggle();
                        hideKeyBord(new_secondary_expandableLayout_insurance_el);

                    }
                });
            }
        }


        private void setTitleName(String title) {
            if (titleTv != null) {
                titleTv.setText(title);
            }
        }

        private void init() {
            titleTv = convertView.findViewById(R.id.insurance_title_tv);
            delete_tv = convertView.findViewById(R.id.delete_tv);
            insurance = new Patientinsurancedetail();

            insurance_tpa_id_et = convertView.findViewById(R.id.insurance_tpa_id_et);
            insurance_provider_spinner = convertView.findViewById(R.id.insurance_provider_spinner);
            insurance_insurance_number_et = convertView.findViewById(R.id.insurance_insurance_number_et);
            insurance_insurance_policy_et = convertView.findViewById(R.id.insurance_insurance_policy_et);
            insurance_member_id_et = convertView.findViewById(R.id.insurance_member_id_et);
            insurance_type_et = convertView.findViewById(R.id.insurance_type_et);
            insurance_valid_from_tv = convertView.findViewById(R.id.insurance_valid_from_tv);
            insurance_valid_to_tv = convertView.findViewById(R.id.insurance_valid_to_tv);
            insurance_co_pay_et = convertView.findViewById(R.id.insurance_co_pay_et);
            insurance_scheme_et = convertView.findViewById(R.id.insurance_scheme_et);
            insurance_reason_et = convertView.findViewById(R.id.insurance_reason_et);
            insurance_max_limit_et = convertView.findViewById(R.id.insurance_max_limit_et);

            insurance_back_iv = convertView.findViewById(R.id.insurance_back_iv);
            insurance_front_iv = convertView.findViewById(R.id.insurance_front_iv);

            tpa_list_spinner = convertView.findViewById(R.id.tpa_list_spinner);

            insurance_insurance_policy_number_et = convertView.findViewById(R.id.insurance_insurance_policy_number_et);


            setInsurance(insurance);

        }

        private void setInsuranceSpinnerData() {
            if (profileAPIData != null && profileAPIData.getInsurance() != null) {
                setToInsuranceAdapter11(profileAPIData.getInsurance());
            }

        }

        private void setTpaListSpinnerData() {
            if (profileAPIData != null && profileAPIData.getTpalist() != null) {
                setToTpaListAdapter11(profileAPIData.getTpalist());
            }

        }


        public void setInsurance(Patientinsurancedetail insurance) {
            if (insurance == null) {
                return;
            }

            this.insurance = insurance;
            setInsuranceSpinnerData();
            setInsuranceDetailsToview();
            setTpaListSpinnerData();
        }

        public void setImage(final File file, final int responseId) {
            if (file != null) {

                if (responseId == FRONT_IMAGE_ID) {
                    MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, insurance_front_iv, file);
                    frontImageFile = file;

                } else if (responseId == BACK_IMAGE_ID) {
                    MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, insurance_back_iv, file);

                    backImageFile = file;
                }


/*
                UploadProfilePicMapper picMapper= new UploadProfilePicMapper(file,keyName);
                picMapper.setOnUpdateProfilePicListener(new UploadProfilePicMapper.UpdateProfilePicListener() {
                    @Override
                    public void uploadProfilePic(AbstractResponse response, String errorMessage) {
                        if(!isValidResponse(response,errorMessage))
                        {
                            return;
                        }
                        Utils.showToastMsg(response.getStatusMessage());
                        switch (responseId)
                        {
                            case Constants.ImagePic.FROM_PROFILE:
                                MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.profile_icon, profile_iv, file);

                                break;
                            case Constants.ImagePic.FROM_ID_FRONT:
                                MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, id_front_iv, file);

                                break;
                            case Constants.ImagePic.FROM_ID_BACK:
                                MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, id_back_iv, file);

                                break;


                            case Constants.ImagePic.FROM_INSURANCE_FRONT:
                                MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, insurance_front_iv, file);

                                break;
                            case Constants.ImagePic.FROM_INSURANCE_BACK:
                                MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, insurance_back_iv, file);

                                break;
                        }

                    }

                });*/
            }

        }

        private void setInsuranceDetailsToview() {

            insurance_tpa_id_et.setText(insurance.getTpaid());
            //insurance_provider_spinner  = getFragemtView().findViewById(R.id.insurance_provider_spinner);
            insurance_insurance_number_et.setText(insurance.getInsuranceNumber());
            insurance_insurance_policy_et.setText(insurance.getPolicy());
            insurance_insurance_policy_number_et.setText(insurance.getPolicyNumber());
            insurance_member_id_et.setText(insurance.getMemberid());
            insurance_type_et.setText(insurance.getType());
            //insurance_valid_from_tv.getText().toString();
            // insurance_valid_to_tv.getText().toString();
            insurance_co_pay_et.setText(insurance.getCopay());
            insurance_scheme_et.setText(insurance.getScheme());
            insurance_reason_et.setText(insurance.getReason());
            insurance_max_limit_et.setText(insurance.getMaxlimit());

            MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, insurance_front_iv, insurance.getInsuranceCardFront());

            MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, insurance_back_iv, insurance.getInsuranceCardRear());


            //Validity from
            if (insurance.getFromvalidity() != null) {
                try {

                    Date date = requestFormat.parse(insurance.getFromvalidity());
                    if (selectedValidFromObj == null) {
                        selectedValidFromObj = new SelectedDate(Calendar.getInstance());
                    }
                    selectedValidFromObj.setTimeInMillis(date.getTime());
                    insurance_valid_from_tv.setText(format.format(date));
                    //profileDetails.fromvalidity = profileAPIData.getPatientdata().getFromvalidity();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }


            //For validity to
            if (insurance.getTovalidity() != null) {
                try {

                    Date date = requestFormat.parse(insurance.getTovalidity());
                    if (selectedValidToObj == null) {
                        selectedValidToObj = new SelectedDate(Calendar.getInstance());
                    }
                    selectedValidToObj.setTimeInMillis(date.getTime());
                    insurance_valid_to_tv.setText(format.format(date));
                    // profileDetails.fromvalidity = profileAPIData.getPatientdata().getTovalidity();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }


            insurance_valid_from_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDateOfBirthTextviewClick(mFragmentValidFromCallback, selectedValidFromObj, false);
                }
            });

            insurance_valid_to_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDateOfBirthTextviewClick(mFragmentValidToCallback, selectedValidToObj, false);
                }
            });


            {
                insurance_front_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageProcessListener.callShowImageSourceDialog(FRONT_IMAGE_ID);
                        //showImageSourceDialog(Constants.ImagePic.FROM_INSURANCE_FRONT);
                    }
                });
            }

            {
                insurance_back_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //showImageSourceDialog(Constants.ImagePic.FROM_INSURANCE_BACK);
                        imageProcessListener.callShowImageSourceDialog(BACK_IMAGE_ID);

                    }
                });
            }

        }

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
                // profileDetails.fromvalidity=requestFormat.format(selectedCal.getTime());
                insurance.setFromvalidity(requestFormat.format(selectedCal.getTime()));
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
                // profileDetails.tovalidity=requestFormat.format(selectedCal.getTime());
                insurance.setTovalidity(requestFormat.format(selectedCal.getTime()));
                insurance_valid_to_tv.setText(format.format(selectedCal.getTime()));

            }
        };


        public SecondaryInsurance getInsurance() {
            SecondaryInsurance secondaryInsurance = new SecondaryInsurance(insurance.getId());
            secondaryInsurance.insurancePackage = insurance.getInsurancePackage();
            secondaryInsurance.insuranceNumber = insurance_insurance_number_et.getText().toString();
            secondaryInsurance.tpa = insurance.getTpa();
            secondaryInsurance.tpaid = insurance_tpa_id_et.getText().toString();
            secondaryInsurance.policy = insurance_insurance_policy_et.getText().toString();
            secondaryInsurance.policy_number = insurance_insurance_policy_number_et.getText().toString();
            secondaryInsurance.memberid = insurance_member_id_et.getText().toString();
            secondaryInsurance.type = insurance_type_et.getText().toString();
            secondaryInsurance.scheme = insurance_scheme_et.getText().toString();
            secondaryInsurance.reason = insurance_reason_et.getText().toString();
            secondaryInsurance.copay = insurance_co_pay_et.getText().toString();
            secondaryInsurance.maxlimit = insurance_max_limit_et.getText().toString();
            secondaryInsurance.frontImageFile = frontImageFile;
            secondaryInsurance.backImageFile = backImageFile;

            secondaryInsurance.fromvalidity = insurance.getFromvalidity();
            secondaryInsurance.tovalidity = insurance.getTovalidity();

            //insurance_provider_spinner  = getFragemtView().findViewById(R.id.insurance_provider_spinner);
            // insurance.setPolicy(insurance_insurance_policy_et.getText().toString());
            // insurance.setMemberid(insurance_member_id_et.getText().toString());
            //insurance.setType(insurance_type_et.getText().toString());
            //insurance_valid_from_tv.getText().toString();
            // insurance_valid_to_tv.getText().toString();
            // insurance.setCopay(insurance_co_pay_et.getText().toString());
            // insurance.setScheme(insurance_scheme_et.getText().toString());
            // insurance.setReason(insurance_reason_et.getText().toString());
            //insurance.setMaxlimit(insurance_max_limit_et.getText().toString());

            return secondaryInsurance;
        }

        private void setToInsuranceAdapter11(List<EditProfileViewInsurance> list) {

            final ArrayList<EditProfileViewInsurance> insuranceTypeList = new ArrayList<>();
            if (list != null && list.size() > 0) {
                insuranceTypeList.addAll(list);

            }

            EditProfileViewInsurance hintData = new EditProfileViewInsurance();
            hintData.setCompanyName("Select Insurance");
            insuranceTypeList.add(insuranceTypeList.size(), hintData);


            myInsuranceAdapter = new HintAdapter<EditProfileViewInsurance>(MyApplication.getCurrentActivityContext(), R.layout.spinner_black_textview, insuranceTypeList);

            myInsuranceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            insurance_provider_spinner.setAdapter(myInsuranceAdapter);
            insurance_provider_spinner.setSelection(myInsuranceAdapter.getCount());

            insurance_provider_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //if(position!=0)
                    //{
                    insurance.setInsurancePackage(null);
                    if (position != (myInsuranceAdapter.getCount())) {
                        EditProfileViewInsurance selectedInsurance = insuranceTypeList.get(position);
                        // Utils.showToastMessage("selected insurance " + selectedInsurance);

                        if (selectedInsurance != null) {
                            //profileDetails.insurancePackage=selectedInsurance.getCompanyName();
                            insurance.setInsurancePackage(selectedInsurance.getCompanyName());
                            // searchRequestParams.setInsurenceList(null);
                            //searchRequestParams.addInsurence(selectedInsurance.getCompanyName());
                        }
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            if (insurance != null && !TextUtils.isEmpty(insurance.getInsurancePackage())) {
                String insuranceStr = insurance.getInsurancePackage();

                for (int i = 0; i < insuranceTypeList.size(); i++) {
                    String temp = insuranceTypeList.get(i).getCompanyName();
                    if (temp != null && temp.equalsIgnoreCase(insuranceStr)) {
                        insurance_provider_spinner.setSelection(i);
                        break;
                    }
                }
            }


        }

        private void setToTpaListAdapter11(List<Tpalist> list) {

            final ArrayList<Tpalist> insuranceTypeList = new ArrayList<>();
            if (list != null && list.size() > 0) {
                insuranceTypeList.addAll(list);

            }

            Tpalist hintData = new Tpalist();
            hintData.setTpa("Select TPA");
            insuranceTypeList.add(insuranceTypeList.size(), hintData);


            myTPAListAdapter = new HintAdapter<Tpalist>(MyApplication.getCurrentActivityContext(), R.layout.spinner_black_textview, insuranceTypeList);

            myTPAListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            tpa_list_spinner.setAdapter(myTPAListAdapter);
            tpa_list_spinner.setSelection(myTPAListAdapter.getCount());

            tpa_list_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //if(position!=0)
                    //{
                    insurance.setTpa(null);
                    if (position != (myTPAListAdapter.getCount())) {
                        Tpalist tpa = insuranceTypeList.get(position);
                        // Utils.showToastMessage("selected insurance " + selectedInsurance);

                        if (tpa != null) {
                            //profileDetails.insurancePackage=selectedInsurance.getCompanyName();
                            //insurance.setInsurancePackage(selectedInsurance.getCompanyName());
                            insurance.setTpa(tpa.getId());
                            // searchRequestParams.setInsurenceList(null);
                            //searchRequestParams.addInsurence(selectedInsurance.getCompanyName());
                        }
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            if (insurance != null && !TextUtils.isEmpty(insurance.getTpa())) {
                String tpa = insurance.getTpa();

                for (int i = 0; i < insuranceTypeList.size(); i++) {
                    String temp = insuranceTypeList.get(i).getId();
                    if (temp != null && temp.equalsIgnoreCase(tpa)) {
                        tpa_list_spinner.setSelection(i);
                        break;
                    }
                }
            }


        }
    }

    private void compute() {

        {
            image_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showImageSourceDialog(Constants.ImagePic.FROM_PROFILE);
                }
            });
        }
        {
            id_front_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showImageSourceDialog(Constants.ImagePic.FROM_ID_FRONT);
                }
            });
        }
        {
            id_back_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showImageSourceDialog(Constants.ImagePic.FROM_ID_BACK);
                }
            });
        }

        {
            insurance_front_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showImageSourceDialog(Constants.ImagePic.FROM_INSURANCE_FRONT);
                }
            });
        }

        {
            insurance_back_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showImageSourceDialog(Constants.ImagePic.FROM_INSURANCE_BACK);
                }
            });
        }
        callNatinalityMapper();

        {
            setExpandedViewListener(secondary_expandableLayout_insurance_el, secondary_insurance_details_linear, R.id.secondary_insurance_iv);
            // secondary_insurance_details_linear.initLayout();
            secondary_insurance_details_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    secondary_expandableLayout_insurance_el.toggle();
                    hideKeyBord(secondary_expandableLayout_insurance_el);

                }
            });
        }

        {
            setExpandedViewListener(expandableLayout_contact_el, contact_details_linear, R.id.contact_iv);
            // expandableLayout_contact_el.initLayout();
            contact_details_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandableLayout_contact_el.toggle();
                    hideKeyBord(expandableLayout_contact_el);

                }
            });
        }


        {
            setExpandedViewListener(expandableLayout_emergency_el, emergency_details_linear, R.id.emergency_iv);
            //expandableLayout_emergency_el.initLayout();


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
            // expandableLayout_insurance_el.initLayout();

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

    private void setRelationSpinner() {

        // final ArrayList<String> genderTypeList = new ArrayList<>();

        final String[] relations = getResources().getStringArray(R.array.relations1);


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
                profileDetails.emergencyContactRelationship = null;
                if (position != (aa.getCount())) {
                    profileDetails.emergencyContactRelationship = relations[position];

                }

                //Utils.showToastMessage("selectedGender "+selectedGender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (profileAPIData != null && profileAPIData.getPatientdata() != null) {
            String relation = profileAPIData.getPatientdata().getEmergencyContactRelationship();
            if (!TextUtils.isEmpty(relation)) {
                for (int i = 0; i < relations.length; i++) {
                    String temp = relations[i];
                    if (temp != null && temp.equalsIgnoreCase(relation)) {
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
                profileDetails.gender = null;
                if (position != (aa.getCount())) {
                    String selectedGender = genderTypeList.get(position);
                    if (selectedGender != null) {
                        profileDetails.gender = selectedGender;
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

        if (profileAPIData != null && profileAPIData.getPatientdata() != null) {
            String gender = profileAPIData.getPatientdata().getGender();
            if (!TextUtils.isEmpty(gender)) {
                for (int i = 0; i < genderTypeList.size(); i++) {
                    String temp = genderTypeList.get(i);
                    if (temp != null && temp.equalsIgnoreCase(gender)) {
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
                profileDetails.maritialStatus = null;
                if (position != (aa.getCount())) {
                    String selectedGender = maritalStatusList.get(position);
                    if (selectedGender != null) {
                        profileDetails.maritialStatus = selectedGender;
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
        if (profileAPIData != null && profileAPIData.getPatientdata() != null) {
            String marital = profileAPIData.getPatientdata().getMaritialStatus();
            if (!TextUtils.isEmpty(marital)) {
                for (int i = 0; i < maritalStatusList.size(); i++) {
                    String temp = maritalStatusList.get(i);
                    if (temp != null && temp.equalsIgnoreCase(marital)) {
                        marital_status_spinner.setSelection(i);
                    }
                }
            }
        }


    }


    private void callInsuranceAcceptedMapper() {
        if (profileAPIData != null && profileAPIData.getInsurance() != null) {
            setToInsuranceAdapter1(profileAPIData.getInsurance());
        }
        if (true) {
            return;
        }

       /* if ((insuranceAdapter != null)&& (insuranceAdapter1 != null)) {
            MyApplication.hideTransaprentDialog();
            //insurance_accepted_spinner.setSelection(insuranceAdapter.getCount());
            return;
        }*/
        //MyApplication.showTransparentDialog();
        InsuranceListMapper mapper = new InsuranceListMapper();
        mapper.setOnInsuranceListListener(new InsuranceListMapper.InsuranceListListener() {
            @Override
            public void getInsuranceListAPI(InsuranceListAPI insuranceListAPI, String errorMessage) {
                MyApplication.hideTransaprentDialog();
                if (!isValidResponse(insuranceListAPI, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_insurance_list_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()).finish();
                        }

                        @Override
                        public void retryClick() {
                            callInsuranceAcceptedMapper();
                        }
                    });
                    return;
                }
                // insuranceNameList=insuranceListAPI.getData();
               /* if(insuranceAdapter1==null)
                {
                    setToInsuranceAdapter1(insuranceListAPI.getData());
                }

                if(insuranceAdapter==null) {
                    setToInsuranceAdapter(insuranceListAPI.getData());

                }*/

            }
        });
    }

    public void setToInsuranceAdapter1(List<EditProfileViewInsurance> list) {

        final ArrayList<EditProfileViewInsurance> insuranceTypeList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            insuranceTypeList.addAll(list);

        }

        EditProfileViewInsurance hintData = new EditProfileViewInsurance();
        hintData.setCompanyName("Select Insurance");
        insuranceTypeList.add(insuranceTypeList.size(), hintData);


        insuranceAdapter1 = new HintAdapter<EditProfileViewInsurance>(MyApplication.getCurrentActivityContext(), R.layout.spinner_black_textview, insuranceTypeList);

        insuranceAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        insurance_provider_spinner.setAdapter(insuranceAdapter1);
        insurance_provider_spinner.setSelection(insuranceAdapter1.getCount());

        insurance_provider_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //if(position!=0)
                //{
                profileDetails.insurancePackage = null;
                if (position != (insuranceAdapter1.getCount())) {
                    EditProfileViewInsurance selectedInsurance = insuranceTypeList.get(position);
                    // Utils.showToastMessage("selected insurance " + selectedInsurance);

                    if (selectedInsurance != null) {
                        profileDetails.insurancePackage = selectedInsurance.getCompanyName();
                        // searchRequestParams.setInsurenceList(null);
                        //searchRequestParams.addInsurence(selectedInsurance.getCompanyName());
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (profileAPIData != null && profileAPIData.getPatientdata() != null) {
            String insurance = profileAPIData.getPatientdata().getInsurancePackage();
            if (!TextUtils.isEmpty(insurance)) {
                for (int i = 0; i < insuranceTypeList.size(); i++) {
                    String temp = insuranceTypeList.get(i).getCompanyName();
                    if (temp != null && temp.equalsIgnoreCase(insurance)) {
                        insurance_provider_spinner.setSelection(i);
                        break;
                    }
                }
            }
        }


    }

    public void setToInsuranceAdapter(List<InsuranceData> list) {
       /* final ArrayList<InsuranceData> insuranceTypeList = new ArrayList<>();
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
        }*/


    }


    private void callIdTypeMapper() {
        if (profileAPIData != null && profileAPIData.getIdcardlist() != null) {
            setToIdTypeAdapter(profileAPIData.getIdcardlist());
        }
        callCountryListMapper();

        if (true) {
            return;
        }

        if (idTypeAdapter != null) {
            MyApplication.hideTransaprentDialog();
            //insurance_accepted_spinner.setSelection(insuranceAdapter.getCount());
            return;
        }
        //MyApplication.showTransparentDialog();
        IdCardTypeMapper mapper = new IdCardTypeMapper();
        mapper.setOnIdTypeListListener(new IdCardTypeMapper.IdTypeListListener() {
            @Override
            public void getIdTypeListAPI(IdCardTypeAPI idCardTypeAPI, String errorMessage) {
                //MyApplication.hideTransaprentDialog();
                if (!isValidResponse(idCardTypeAPI, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_id_type_list_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()).finish();
                        }

                        @Override
                        public void retryClick() {
                            callIdTypeMapper();
                        }
                    });
                    return;
                }
                //setToIdTypeAdapter(idCardTypeAPI.getData());
                callCountryListMapper();
            }
        });


    }

    public void setToIdTypeAdapter(List<EditProfileViewIdcardlist> list) {
        final ArrayList<EditProfileViewIdcardlist> idCardTypeList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            idCardTypeList.addAll(list);

        }

        EditProfileViewIdcardlist hintData = new EditProfileViewIdcardlist();
        hintData.setIdCardName("Select ID Type");
        idCardTypeList.add(idCardTypeList.size(), hintData);


        idTypeAdapter = new HintAdapter<EditProfileViewIdcardlist>(MyApplication.getCurrentActivityContext(), R.layout.spinner_black_textview, idCardTypeList);

        idTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        id_type_spinner.setAdapter(idTypeAdapter);
        id_type_spinner.setSelection(idTypeAdapter.getCount());

        id_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //if(position!=0)
                //{
                profileDetails.idType = null;
                if (position != (idTypeAdapter.getCount())) {
                    EditProfileViewIdcardlist idCardTypeData = idCardTypeList.get(position);
                    // Utils.showToastMessage("selected insurance " + selectedInsurance);

                    if (idCardTypeData != null) {
                        profileDetails.idType = idCardTypeData.getIdCardName();
                    }
                }


                //}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (profileAPIData != null && profileAPIData.getPatientdata() != null) {
            String idtype = profileAPIData.getPatientdata().getIdType();
            if (!TextUtils.isEmpty(idtype)) {
                for (int i = 0; i < idCardTypeList.size(); i++) {
                    String temp = idCardTypeList.get(i).getIdCardName();
                    if (temp != null && temp.equalsIgnoreCase(idtype)) {
                        id_type_spinner.setSelection(i);
                    }
                }
            }
        }


    }

    private void callNatinalityMapper() {

        if (profileAPIData != null && profileAPIData.getNationalities() != null) {
            setToNationalityListAdapter(profileAPIData.getNationalities());

        }
        callIdTypeMapper();
        callInsuranceAcceptedMapper();
        if (true) {
            // nationality_spinner.setSelection(nationalityAdapter.getCount());
            return;
        }
        //MyApplication.showTransparentDialog();
        NationalityMapper mapper = new NationalityMapper();

        mapper.setOnNationalityListListener(new NationalityMapper.NationalityListListener() {
            @Override
            public void getNationalityListAPI(NationalityListAPI nationalityListAPI, String errorMessage) {
                //MyApplication.hideTransaprentDialog();
                if (!isValidResponse(nationalityListAPI, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_nationality_list_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()).finish();

                        }

                        @Override
                        public void retryClick() {
                            callNatinalityMapper();
                        }
                    });
                    return;
                }

                //callInsuranceAcceptedMapper();
            }
        });

    }

    public void setToNationalityListAdapter(List<EditProfileViewNationality> list) {
        final ArrayList<EditProfileViewNationality> nationalityTypeList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            nationalityTypeList.addAll(list);

        }

        EditProfileViewNationality hintData = new EditProfileViewNationality();
        hintData.setNationalityName("Select Nationality");
        nationalityTypeList.add(nationalityTypeList.size(), hintData);


        nationalityAdapter = new HintAdapter<EditProfileViewNationality>(MyApplication.getCurrentActivityContext(), R.layout.spinner_black_textview, nationalityTypeList);

        nationalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nationality_spinner.setAdapter(nationalityAdapter);
        nationality_spinner.setSelection(nationalityAdapter.getCount());

        nationality_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                profileDetails.nationality = null;

                if (position != (nationalityAdapter.getCount())) {
                    EditProfileViewNationality selectedNationality = nationalityTypeList.get(position);
                    //Utils.showToastMessage("selected Nationality " + selectedNationality);

                    if (selectedNationality != null) {
                        //searchRequestParams.setNationalityList(null);
                        profileDetails.nationality = selectedNationality.getNationalityName();
                        //searchRequestParams.addNationality(selectedNationality.getId());
                    }
                }


                //}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (profileAPIData != null && profileAPIData.getPatientdata() != null) {
            String nationality = profileAPIData.getPatientdata().getNationality();
            if (!TextUtils.isEmpty(nationality)) {
                for (int i = 0; i < nationalityTypeList.size(); i++) {
                    String temp = nationalityTypeList.get(i).getNationalityName();
                    if (temp != null && temp.equalsIgnoreCase(nationality)) {
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
                        profileDetails.bloodGroup = selectedGender;
                    }
                }
                //Utils.showToastMessage("selectedGender "+selectedGender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (profileAPIData != null && profileAPIData.getPatientdata() != null) {
            String bloodgroup = profileAPIData.getPatientdata().getBloodGroup();
            if (!TextUtils.isEmpty(bloodgroup)) {
                for (int i = 0; i < bloodGroupList.size(); i++) {
                    String temp = bloodGroupList.get(i);
                    if (temp != null && temp.equalsIgnoreCase(bloodgroup)) {
                        blood_group_spinner.setSelection(i);
                    }
                }
            }
        }


    }

    private void initForViews() {
        image_linear = getFragemtView().findViewById(R.id.image_linear);

        profile_iv = getFragemtView().findViewById(R.id.profile_iv);

        id_back_iv = getFragemtView().findViewById(R.id.id_back_iv);

        id_front_iv = getFragemtView().findViewById(R.id.id_front_iv);
        insurance_back_iv = getFragemtView().findViewById(R.id.insurance_back_iv);
        insurance_front_iv = getFragemtView().findViewById(R.id.insurance_front_iv);

        patient_name_et = getFragemtView().findViewById(R.id.patient_name_et);
        gender_type_spinner = getFragemtView().findViewById(R.id.gender_spinner);
        marital_status_spinner = getFragemtView().findViewById(R.id.marital_status_spinner);
        // patient_age_et   = getFragemtView().findViewById(R.id.patient_age_et);
        date_of_birth_tv = getFragemtView().findViewById(R.id.date_of_birth_tv);
        blood_group_spinner = getFragemtView().findViewById(R.id.blood_group_spinner);
        height_et = getFragemtView().findViewById(R.id.height_et);
        known_allergies_et = getFragemtView().findViewById(R.id.known_allergies_et);


        //My Profile
        primary_contact_no_et = getFragemtView().findViewById(R.id.primary_contact_no_et);
        secondary_contact_no_et = getFragemtView().findViewById(R.id.secondary_contact_no_et);
        residence_contact_no_et = getFragemtView().findViewById(R.id.residence_contact_no_et);
        contact_email_et = getFragemtView().findViewById(R.id.email_et);
        contact_address_et = getFragemtView().findViewById(R.id.address_et);
        contact_language_known_et = getFragemtView().findViewById(R.id.language_known_et);
        nationality_spinner = getFragemtView().findViewById(R.id.nationality_spinner);
        id_type_spinner = getFragemtView().findViewById(R.id.id_type_spinner);
        id_number_et = getFragemtView().findViewById(R.id.id_number_et);
        country_spinner = getFragemtView().findViewById(R.id.country_spinner);
        addressAutoCompleteTextView = getFragemtView().findViewById(R.id.google_places_actv);


        //Emergency Contact
        emergency_contact_name_et = getFragemtView().findViewById(R.id.emergency_contact_name_et);
        //emergency_contact_relationship_et = getFragemtView().findViewById(R.id.emergency_contact_relationship_et);
        emergency_contact_number_et = getFragemtView().findViewById(R.id.emergency_contact_number_et);
        emergency_contact_emailid_et = getFragemtView().findViewById(R.id.emergency_contact_emailid_et);
        relation_spinner = getFragemtView().findViewById(R.id.relation_spinner);


        //Insurance
        primary_tpa_list_spinner = getFragemtView().findViewById(R.id.primary_tpa_list_spinner);
        insurance_tpa_id_et = getFragemtView().findViewById(R.id.insurance_tpa_id_et);
        primary_policy_number_et = getFragemtView().findViewById(R.id.primary_policy_number_et);

        insurance_provider_spinner = getFragemtView().findViewById(R.id.insurance_provider_spinner);
        insurance_insurance_number_et = getFragemtView().findViewById(R.id.insurance_insurance_number_et);
        insurance_insurance_policy_et = getFragemtView().findViewById(R.id.insurance_insurance_policy_et);
        insurance_member_id_et = getFragemtView().findViewById(R.id.insurance_member_id_et);
        insurance_type_et = getFragemtView().findViewById(R.id.insurance_type_et);
        insurance_valid_from_tv = getFragemtView().findViewById(R.id.insurance_valid_from_tv);
        insurance_valid_to_tv = getFragemtView().findViewById(R.id.insurance_valid_to_tv);
        insurance_co_pay_et = getFragemtView().findViewById(R.id.insurance_co_pay_et);
        insurance_scheme_et = getFragemtView().findViewById(R.id.insurance_scheme_et);
        insurance_reason_et = getFragemtView().findViewById(R.id.insurance_reason_et);
        insurance_organisation_et = getFragemtView().findViewById(R.id.insurance_organisation_et);
        insurance_max_limit_et = getFragemtView().findViewById(R.id.insurance_max_limit_et);
        //insurance_secoundary_spinner  = getFragemtView().findViewById(R.id.insurance_secoundary_spinner);
        // insurance_secoundary_number_et  = getFragemtView().findViewById(R.id.insurance_secoundary_number_et);


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
        secondary_insurance_details_mask_tv.setVisibility(isEditMode ? View.GONE : View.VISIBLE);
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


    public void onDateOfBirthTextviewClick(final SublimePickerFragment.Callback callback, final SelectedDate selectedObj, boolean isDOB) {

        SublimePickerFragment pickerFrag = new SublimePickerFragment();
        pickerFrag.setCallback(callback);

        // Options
        Pair<Boolean, SublimeOptions> optionsPair = getOptions(selectedObj, isDOB);

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


    Pair<Boolean, SublimeOptions> getOptions(SelectedDate selectedDate, boolean isDOB) {
        SublimeOptions options = new SublimeOptions();
        if (isDOB) {
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
            profileDetails.dateofBirth = requestFormat.format(selectedCal.getTime());
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
            profileDetails.fromvalidity = requestFormat.format(selectedCal.getTime());
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
            profileDetails.tovalidity = requestFormat.format(selectedCal.getTime());
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

    public SaveButtonClickListener saveButtonClickListener = new SaveButtonClickListener() {
        @Override
        public void saveButtonClick() {

            updateProfileDetails();
        }
    };


    private void callCountryListMapper() {
        //TODO call country list mapper
        if (profileAPIData != null && profileAPIData.getCountries() != null) {
            setToCountryAdapter(profileAPIData.getCountries());
        }

        if (true) {
            return;
        }


        CountryListMapper mapper = new CountryListMapper();
        mapper.setOnCountryListListener(new CountryListMapper.CountryListListener() {
            @Override
            public void getCountryListAPI(CountryListAPI countryListAPI, String errorMessage) {
                if (!isValidResponse(countryListAPI, errorMessage)) {

                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_country_list_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()).finish();
                        }

                        @Override
                        public void retryClick() {
                            callCountryListMapper();
                        }
                    });
                    return;
                }

                //setToCountryAdapter(countryListAPI.getData());

            }
        });

    }

    public void setToCountryAdapter(List<EditProfileViewCountry> list) {
        final ArrayList<EditProfileViewCountry> countryTypeList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            countryTypeList.addAll(list);

        }

        EditProfileViewCountry hintData = new EditProfileViewCountry();
        hintData.setCountryName("Select Country");
        countryTypeList.add(countryTypeList.size(), hintData);
        setToCityListAdapter(null);


        final HintAdapter aa = new HintAdapter<EditProfileViewCountry>(MyApplication.getCurrentActivityContext(), R.layout.spinner_black_textview, countryTypeList);

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country_spinner.setAdapter(aa);
        country_spinner.setSelection(aa.getCount());

        country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //if(position!=0)
                //{
                // expandableLayout_city_view.collapse();
                profileDetails.country = null;
                if (position != (aa.getCount())) {
                    EditProfileViewCountry selectedCountry = countryTypeList.get(position);
                    if (selectedCountry != null) {
                        // Utils.showToastMessage("selected country " + selectedCountry);
                        if (!expandableLayout_city_view.isExpanded()) {
                            expandableLayout_city_view.toggle();
                        }
                        callCityListMapper(selectedCountry.getId());
                        profileDetails.country = selectedCountry.getCurrencyName();
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

        if (profileAPIData != null && profileAPIData.getPatientdata() != null) {
            String country = profileAPIData.getPatientdata().getCountry();
            if (!TextUtils.isEmpty(country)) {
                for (int i = 0; i < countryTypeList.size(); i++) {
                    String temp = countryTypeList.get(i).getCountryName();
                    if (temp != null && temp.equalsIgnoreCase(country)) {
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
                profileDetails.city = null;
                if (position != (aa.getCount())) {
                    CityData selectedCity = cityTypeList.get(position);
                    Utils.showToastMessage("selected city " + selectedCity);
                    if (selectedCity != null) {
                        profileDetails.city = selectedCity.getCityName();
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


        if (profileAPIData != null && profileAPIData.getPatientdata() != null) {
            String city = profileAPIData.getPatientdata().getCity();
            if (!TextUtils.isEmpty(city)) {
                for (int i = 0; i < cityTypeList.size(); i++) {
                    String temp = cityTypeList.get(i).getCityName();
                    if (temp != null && temp.equalsIgnoreCase(city)) {
                        city_type_spinner.setSelection(i);
                    }
                }
            }
        }

    }

    private void updateProfileDetails() {
        // Utils.showToastMsg("Now click on Save");
        if (!setProfileData()) {
            return;
        }
        // Utils.showToastMsg(profileDetails.toString());

        ProfileSaveMapper mapper = new ProfileSaveMapper(profileDetails);
        mapper.setOnProfileSaveListener(new ProfileSaveMapper.ProfileSaveListener() {
            @Override
            public void profileSave(AbstractResponse response, String errorMessage) {
                if (!isValidResponse(response, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_save_profile_details_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()).finish();
                        }

                        @Override
                        public void retryClick() {
                            updateProfileDetails();
                        }
                    });
                    return;
                }

                saveSecoundaryInsuranceDetails();

                if (successListener != null) {
                    successListener.success();
                }
            }
        });


    }

    private void saveSecoundaryInsuranceDetails() {
        if (insurance_add_linear == null || insurance_add_linear.getChildCount() == 0) {
            return;
        }

        for (int i = 0; i < insurance_add_linear.getChildCount(); i++) {
            View view = insurance_add_linear.getChildAt(i);
            if (view == null) {
                continue;
            }

            Object obj = view.getTag();
            if (obj != null && obj instanceof InsuranceHolder) {
                InsuranceHolder holder = (InsuranceHolder) obj;
                SecondaryInsurance insurance = holder.getInsurance();
                if (insurance != null) {
                    if (TextUtils.isEmpty(insurance.id)) {
                        AddSecoundaryInsuranceMapper mapper = new AddSecoundaryInsuranceMapper(insurance);

                        mapper.setOnAddSecoundaryInsuranceListener(new AddSecoundaryInsuranceMapper.AddSecoundaryInsuranceListener() {
                            @Override
                            public void addSecoundaryInsurance(AbstractResponse response, String errorMessage) {
                                if (!isValidResponse(response, errorMessage)) {
                                    return;
                                }

                                // Utils.showToastMessage(response.getStatusMessage());
                            }
                        });
                    } else {

                        EditSecoundaryInsuranceMapper mapper = new EditSecoundaryInsuranceMapper(insurance);

                        mapper.setOnEditSecoundaryInsuranceListener(new EditSecoundaryInsuranceMapper.EditSecoundaryInsuranceListener() {
                            @Override
                            public void editSecoundaryInsurance(AbstractResponse preferencesAPI, String errorMessage) {
                                if (!isValidResponse(preferencesAPI, errorMessage)) {
                                    return;
                                }

                                // Utils.showToastMessage(preferencesAPI.getStatusMessage());
                            }
                        });
                    }
                }

            }
        }

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

    private boolean setProfileData() {
        profileDetails.patientName = patient_name_et.getText().toString();
        // profileDetails.age=  patient_age_et.getText().toString();//TODO add age kay in API
        profileDetails.height = height_et.getText().toString();
        profileDetails.knownAllergies = known_allergies_et.getText().toString();


        //Contact details
        // profileDetails.mobile= contact_et.getText().toString();
        profileDetails.mobile = primary_contact_no_et.getText().toString();
        profileDetails.secondarymobile = secondary_contact_no_et.getText().toString();
        profileDetails.residencemobile = residence_contact_no_et.getText().toString();
        // contact_email_et = getFragemtView().findViewById(R.id.email_et);
        profileDetails.address = contact_address_et.getText().toString();
        profileDetails.languagesKnown = contact_language_known_et.getText().toString();
        profileDetails.idNumber = id_number_et.getText().toString();


        //Emergency Contact
        profileDetails.emergencyContactName = emergency_contact_name_et.getText().toString();

        String contactNum=emergency_contact_number_et.getText().toString();
        if (!TextUtils.isEmpty(contactNum) && contactNum.length()<10) {
            Utils.showToastMessage(R.string.please_enter_valid_emerengency_contact_num_lbl);
            return false;
        }

        profileDetails.emergencyContactNumber = emergency_contact_number_et.getText().toString();

        String email = emergency_contact_emailid_et.getText().toString();
        if (!TextUtils.isEmpty(email) && !Utils.isValidEmail(email)) {
            Utils.showToastMessage(R.string.please_enter_valid_emerengency_email_id_lbl);
            return false;
        }
        profileDetails.emergencyContactEmail = emergency_contact_emailid_et.getText().toString();
        // relation_spinner  = getFragemtView().findViewById(R.id.relation_spinner);


        //Insurance
        profileDetails.tpaid = insurance_tpa_id_et.getText().toString();
        profileDetails.policy_number = primary_policy_number_et.getText().toString();
        //insurance_provider_spinner  = getFragemtView().findViewById(R.id.insurance_provider_spinner);
        profileDetails.insuranceNumber = insurance_insurance_number_et.getText().toString();
        profileDetails.policy = insurance_insurance_policy_et.getText().toString();
        profileDetails.memberid = insurance_member_id_et.getText().toString();
        profileDetails.type = insurance_type_et.getText().toString();
        //insurance_valid_from_tv.getText().toString();
        // insurance_valid_to_tv.getText().toString();
        profileDetails.copay = insurance_co_pay_et.getText().toString();
        profileDetails.scheme = insurance_scheme_et.getText().toString();
        profileDetails.reason = insurance_reason_et.getText().toString();
        profileDetails.organisation = insurance_organisation_et.getText().toString();
        profileDetails.maxlimit = insurance_max_limit_et.getText().toString();
        // insurance_secoundary_spinner  = getFragemtView().findViewById(R.id.insurance_secoundary_spinner);
        //profileDetails.secondaryinsuranceNumber= insurance_secoundary_number_et.getText().toString();


        return true;

    }

    private void fillProfileDataToViews() {
        if (profileAPIData == null || profileAPIData.getPatientdata() == null) {
            return;
        }
        //Patientinsurancedetail

        if (profileAPIData.getPatientinsurancedetails() != null) {
            for (Patientinsurancedetail insurance : profileAPIData.getPatientinsurancedetails()) {

                if (insurance == null) {
                    continue;
                }

                View convertView = inflater
                        .inflate(R.layout.profile_secoundary_insurance_edit_layout1, null, false);
                final InsuranceHolder holder = new InsuranceHolder(convertView);

                holder.setOnImageProcessListener(new ImageProcessListener() {
                    @Override
                    public void callShowImageSourceDialog(int id) {
                        showImageSourceDialog(id, this);
                    }

                    @Override
                    public void setImage(File file, int responseId) {
                        holder.setImage(file, responseId);
                    }
                });

                holder.setInsurance(insurance);
            }


        }


        MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.profile_icon, profile_iv, profileAPIData.getPatientdata().getPatientLogo());

        MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, id_front_iv, profileAPIData.getPatientdata().getIdcardImageFront());

        MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, id_back_iv, profileAPIData.getPatientdata().getIdcardImageBack());


        MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, insurance_front_iv, profileAPIData.getPatientdata().getInsuranceCardFront());

        MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, insurance_back_iv, profileAPIData.getPatientdata().getInsuranceCardBack());


        patient_name_et.setText(profileAPIData.getPatientdata().getPatientName());
        height_et.setText(profileAPIData.getPatientdata().getHeight());
        known_allergies_et.setText(profileAPIData.getPatientdata().getKnownAllergies());


        //Contact details
        // profileDetails.mobile= contact_et.getText().toString();
        primary_contact_no_et.setText(profileAPIData.getPatientdata().getMobile());
        secondary_contact_no_et.setText(profileAPIData.getPatientdata().getSecondarymobile());
        residence_contact_no_et.setText(profileAPIData.getPatientdata().getResidencemobile());
        contact_email_et.setText(profileAPIData.getPatientdata().getPatentEmail());
        contact_address_et.setText(profileAPIData.getPatientdata().getAddress());

        contact_language_known_et.setText(profileAPIData.getPatientdata().getLanguagesKnown());
        id_number_et.setText(profileAPIData.getPatientdata().getIdNumber());

        addressAutoCompleteTextView.setOnItemClickListener(null);
        addressAutoCompleteTextView.setText(profileAPIData.getPatientdata().getLocality());
        addressAutoCompleteTextView.setOnItemClickListener(adapterViewListener);


        //Emergency Contact
        emergency_contact_name_et.setText(profileAPIData.getPatientdata().getEmergencyContactName());
        emergency_contact_number_et.setText(profileAPIData.getPatientdata().getEmergencyContactNumber());
        emergency_contact_emailid_et.setText(profileAPIData.getPatientdata().getEmergencyContactEmail());
        // relation_spinner  = getFragemtView().findViewById(R.id.relation_spinner);


        //Insurance
        insurance_tpa_id_et.setText(profileAPIData.getPatientdata().getTpaid());
        primary_policy_number_et.setText(profileAPIData.getPatientdata().getPolicyNumber());
        //profileDetails.tpaid = insurance_tpa_id_et.getText().toString();
        // profileDetails.policyNumber=primary_policy_number_et.getText().toString();
        //insurance_provider_spinner  = getFragemtView().findViewById(R.id.insurance_provider_spinner);
        insurance_insurance_number_et.setText(profileAPIData.getPatientdata().getInsuranceNumber());
        insurance_insurance_policy_et.setText(profileAPIData.getPatientdata().getPolicy());
        insurance_member_id_et.setText(profileAPIData.getPatientdata().getMemberid());
        insurance_type_et.setText(profileAPIData.getPatientdata().getType());
        //insurance_valid_from_tv.getText().toString();
        // insurance_valid_to_tv.getText().toString();
        insurance_co_pay_et.setText(profileAPIData.getPatientdata().getCopay());
        insurance_scheme_et.setText(profileAPIData.getPatientdata().getScheme());
        insurance_reason_et.setText(profileAPIData.getPatientdata().getReason());
        insurance_organisation_et.setText(profileAPIData.getPatientdata().getOrganisation());
        insurance_max_limit_et.setText(profileAPIData.getPatientdata().getMaxlimit());
        // insurance_secoundary_spinner  = getFragemtView().findViewById(R.id.insurance_secoundary_spinner);
        // insurance_secoundary_number_et.setText(patientProfileObj.getSecondaryinsurancenumber());

        //"dateofbirth": "01/01/1986",
        date_of_birth_tv.setText(profileAPIData.getPatientdata().getDateofBirth());


        //For Date of birth
        if (profileAPIData.getPatientdata().getDateofBirth() != null) {
            try {

                Date date = requestFormat.parse(profileAPIData.getPatientdata().getDateofBirth());
                if (selectedDOBObj == null) {
                    selectedDOBObj = new SelectedDate(Calendar.getInstance());
                }
                selectedDOBObj.setTimeInMillis(date.getTime());
                date_of_birth_tv.setText(format.format(date));
                profileDetails.dateofBirth = profileAPIData.getPatientdata().getDateofBirth();

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        //Validity from
        if (profileAPIData.getPatientdata().getFromvalidity() != null) {
            try {

                Date date = requestFormat.parse(profileAPIData.getPatientdata().getFromvalidity());
                if (selectedValidFromObj == null) {
                    selectedValidFromObj = new SelectedDate(Calendar.getInstance());
                }
                selectedValidFromObj.setTimeInMillis(date.getTime());
                insurance_valid_from_tv.setText(format.format(date));
                profileDetails.fromvalidity = profileAPIData.getPatientdata().getFromvalidity();

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        //For validity to
        if (profileAPIData.getPatientdata().getTovalidity() != null) {
            try {

                Date date = requestFormat.parse(profileAPIData.getPatientdata().getTovalidity());
                if (selectedValidToObj == null) {
                    selectedValidToObj = new SelectedDate(Calendar.getInstance());
                }
                selectedValidToObj.setTimeInMillis(date.getTime());
                insurance_valid_to_tv.setText(format.format(date));
                profileDetails.fromvalidity = profileAPIData.getPatientdata().getTovalidity();

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

    public void setOnSuccessToUpdateProfileListener(SuccessToUpdateProfileListener successListener) {
        this.successListener = successListener;
    }

    /*
     "image_file" : "imagefile", (Profile Image)
    "idcard_image_back" : "imagefile",
    "idcard" : "imagefile",
    "insurance_card_1" : "imagefile",
     "insurance_card_2" : "imagefile"
     */
    @Override
    protected void setImage(final File file, final int responseId) {

        super.setImage(file, responseId);
        if (file != null) {
            String keyName = null;
            switch (responseId) {
                case Constants.ImagePic.FROM_PROFILE:
                    keyName = "image_file";
                    break;
                case Constants.ImagePic.FROM_ID_FRONT:
                    keyName = "idcard";
                    break;
                case Constants.ImagePic.FROM_ID_BACK:
                    keyName = "idcard_image_back";
                    break;

                case Constants.ImagePic.FROM_INSURANCE_BACK:
                    keyName = "insurance_card_2";
                    break;
                case Constants.ImagePic.FROM_INSURANCE_FRONT:
                    keyName = "insurance_card_1";
                    break;
            }

            if (TextUtils.isEmpty(keyName)) {
                //Utils.showToastMsg("Invalid image source");
                return;
            }


            UploadProfilePicMapper picMapper = new UploadProfilePicMapper(file, keyName);
            picMapper.setOnUpdateProfilePicListener(new UploadProfilePicMapper.UpdateProfilePicListener() {
                @Override
                public void uploadProfilePic(AbstractResponse response, String errorMessage) {
                    if (!isValidResponse(response, errorMessage)) {
                        return;
                    }
                    Utils.showToastMsg(response.getStatusMessage());
                    switch (responseId) {
                        case Constants.ImagePic.FROM_PROFILE:
                            MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.profile_icon, profile_iv, file);

                            break;
                        case Constants.ImagePic.FROM_ID_FRONT:
                            MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, id_front_iv, file);

                            break;
                        case Constants.ImagePic.FROM_ID_BACK:
                            MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, id_back_iv, file);

                            break;


                        case Constants.ImagePic.FROM_INSURANCE_FRONT:
                            MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, insurance_front_iv, file);

                            break;
                        case Constants.ImagePic.FROM_INSURANCE_BACK:
                            MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, insurance_back_iv, file);

                            break;
                    }

                }

            });
        }

    }

    /*public void setPatientProfileObj(PatientProfileData patientProfileObj) {
        this.patientProfileObj = patientProfileObj;
    }*/

    public interface SuccessToUpdateProfileListener {
        void success();
    }

    public interface SaveButtonClickListener {
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
                profileDetails.locality = tmpData.getAddress();
                Utils.showToastMsg("Locality :" + profileDetails.locality);
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


    /* private interface ImageProcessListener
     {
         void callShowImageSourceDialog(int id);
     }*/
    private void setToTpaListAdapter11(List<Tpalist> list) {

        final ArrayList<Tpalist> insuranceTypeList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            insuranceTypeList.addAll(list);

        }

        Tpalist hintData = new Tpalist();
        hintData.setTpa("Select TPA");
        insuranceTypeList.add(insuranceTypeList.size(), hintData);


        tPAListAdapter = new HintAdapter<Tpalist>(MyApplication.getCurrentActivityContext(), R.layout.spinner_black_textview, insuranceTypeList);

        tPAListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        primary_tpa_list_spinner.setAdapter(tPAListAdapter);
        primary_tpa_list_spinner.setSelection(tPAListAdapter.getCount());

        primary_tpa_list_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //if(position!=0)
                //{
                //profileAPIData.getPatientdata().setTpa(null);
                profileDetails.tpa = null;
                if (position != (tPAListAdapter.getCount())) {
                    Tpalist tpa = insuranceTypeList.get(position);
                    // Utils.showToastMessage("selected insurance " + selectedInsurance);

                    if (tpa != null) {
                        //profileDetails.insurancePackage=selectedInsurance.getCompanyName();
                        //insurance.setInsurancePackage(selectedInsurance.getCompanyName());
                        //profileAPIData.getPatientdata().setTpa(tpa.getId());
                        profileDetails.tpa = tpa.getId();
                        // searchRequestParams.setInsurenceList(null);
                        //searchRequestParams.addInsurence(selectedInsurance.getCompanyName());
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (profileAPIData.getPatientdata() != null && !TextUtils.isEmpty(profileAPIData.getPatientdata().getTpa())) {
            String tpa = profileAPIData.getPatientdata().getTpa();

            for (int i = 0; i < insuranceTypeList.size(); i++) {
                String temp = insuranceTypeList.get(i).getId();
                if (temp != null && temp.equalsIgnoreCase(tpa)) {
                    primary_tpa_list_spinner.setSelection(i);
                    break;
                }
            }
        }


    }
}
