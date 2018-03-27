package com.vempower.eezyclinic.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Messenger;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.vempower.eezyclinic.APICore.EditProfileViewInsurance;
import com.vempower.eezyclinic.APICore.PatientProfileAddress;
import com.vempower.eezyclinic.APICore.PatientProfileData;
import com.vempower.eezyclinic.APICore.Patientinsurancedetail;
import com.vempower.eezyclinic.APICore.Tpalist;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.APIResponce.GetPatientProfileAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.ClinicProfileActivity;
import com.vempower.eezyclinic.activities.ImageExpandViewActivity;
import com.vempower.eezyclinic.adapters.HintAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.core.SecondaryInsurance;
import com.vempower.eezyclinic.interfaces.AbstractIBinder;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.interfaces.ImageProcessListener;
import com.vempower.eezyclinic.interfaces.IntentObjectListener;
import com.vempower.eezyclinic.mappers.DeleteSecoundaryInsuranceMapper;
import com.vempower.eezyclinic.mappers.GetPatientProfileMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.CustomSpinnerSelection;
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

public class MyProfileFragment extends AbstractFragment {
    SimpleDateFormat format = new SimpleDateFormat(Constants.DISPLAY_DATE_FORMAT);
    SimpleDateFormat requestFormat = new SimpleDateFormat(Constants.REQUEST_DATE_FORMAT);
    private LayoutInflater inflater;

    private LinearLayout contact_details_linear, insurance_details_linear, emergency_details_linear,secondary_insurance_details_linear;

    private View fragmentView;
    private ExpandableLinearLayout expandableLayout_contact_el, expandableLayout_insurance_el,expandableLayout_secondary_insurance_el, expandableLayout_emergency_el;
    private String mySelectedDate;
    private SelectedDate selectedDateObj;

    private TextView profile_top_details_mask_tv1, insurance_details_mask_tv,
            emergency_details_mask_tv, contact_details_mask_tv,secondary_insurance_details_mask_tv;
    private boolean isEditMode;
    private ScrollView myScrollView;
    private  TextView patient_details_tv, blood_group_tv2, height_tv2, weight_tv2;
    private MyEditTextBlackCursor blood_group_et, height_et, date_of_birth_tv, id_number_et,
            known_allergies_et, contact_et1, contact_email_et, primary_contact_no_et,
            secondary_contact_no_et, residence_contact_no_et,
            contact_address_et, contact_language_known_et, contact_nationality_et,
            contact_id_type_et, contact_insurance_provider_et1, contact_insurance_details_et1;

    private MyEditTextBlackCursor emergency_contact_name_et, emergency_contact_relationship_et,
            emergency_contact_number_et, emergency_contact_emailid_et;

    private MyEditTextBlackCursor insurance_tpa_et,insurance_tpa_id_et, insurance_insurance_provider_et,
            insurance_insurance_number_et, insurance_insurance_policy_et,insurance_insurance_policy_number_et,
            insurance_member_id_et, insurance_type_et, insurance_valid_from_et,
            insurance_valid_to_et, insurance_co_pay_et, insurance_scheme_et,
            insurance_reason_et, insurance_organisation_et, insurance_max_limit_et;

    private ImageView patient_profile_iv1,id_back_iv,id_front_iv,insurance_back_iv,insurance_front_iv;
    private TextView patient_name_tv;
    private PatientProfileData profileData;
    private LinearLayout insurance_add_linear;

    private RelativeLayout id_back_relative,id_front_relative, insurance_image1_relative,insurance_image2_relative;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.my_profile_layout, container, false);

        myInit();
        return fragmentView;
    }


    private void myInit() {
        inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initForViews();
        //profile_top_details_mask_tv = getFragemtView().findViewById(R.id.profile_top_details_mask_tv);
        insurance_details_mask_tv = getFragemtView().findViewById(R.id.insurance_details_mask_tv);
        emergency_details_mask_tv = getFragemtView().findViewById(R.id.emergency_details_mask_tv);
        contact_details_mask_tv = getFragemtView().findViewById(R.id.contact_details_mask_tv);
        secondary_insurance_details_mask_tv  = getFragemtView().findViewById(R.id.secondary_insurance_details_mask_tv);
        id_back_iv  = getFragemtView().findViewById(R.id.id_back_iv);

        id_front_iv  = getFragemtView().findViewById(R.id.id_front_iv);
        insurance_back_iv= getFragemtView().findViewById(R.id.insurance_back_iv);
                insurance_front_iv= getFragemtView().findViewById(R.id.insurance_front_iv);
        id_back_relative  = getFragemtView().findViewById(R.id.id_back_relative);
        id_front_relative  = getFragemtView().findViewById(R.id.id_front_relative);

        insurance_add_linear   = getFragemtView().findViewById(R.id.insurance_add_linear);

        insurance_image1_relative = getFragemtView().findViewById(R.id.insurance_image1_relative);

                insurance_image2_relative = getFragemtView().findViewById(R.id.insurance_image2_relative);

       // displayImageInLarge(patient_profile_iv1.getDrawable());

        contact_details_linear = getFragemtView().findViewById(R.id.contact_details_linear);
        insurance_details_linear = getFragemtView().findViewById(R.id.insurance_details_linear);
        emergency_details_linear = getFragemtView().findViewById(R.id.emergency_details_linear);
        secondary_insurance_details_linear  = getFragemtView().findViewById(R.id.secondary_insurance_details_linear);

        expandableLayout_contact_el = getFragemtView().findViewById(R.id.expandableLayout_contact_el);
        expandableLayout_insurance_el = getFragemtView().findViewById(R.id.expandableLayout_insurance_el);
        expandableLayout_emergency_el = getFragemtView().findViewById(R.id.expandableLayout_emergency_el);
        expandableLayout_secondary_insurance_el  = getFragemtView().findViewById(R.id.expandableLayout_secondary_insurance_el);

        myScrollView = ((ScrollView) getFragemtView().findViewById(R.id.scroll));


        date_of_birth_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateOfBirthTextviewClick();
            }
        });

        getFragemtView().findViewById(R.id.image_linear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayImageInLarge(patient_profile_iv1.getDrawable());

            }
        });



        getFragemtView().findViewById(R.id.image_linear2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayImageInLarge(id_back_iv.getDrawable());

            }
        });
        getFragemtView().findViewById(R.id.image_linear1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayImageInLarge(id_front_iv.getDrawable());

            }
        });

        getFragemtView().findViewById(R.id.insurance_image_linear1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayImageInLarge(insurance_front_iv.getDrawable());

            }
        });
        getFragemtView().findViewById(R.id.insurance_image_linear2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayImageInLarge(insurance_back_iv.getDrawable());

            }
        });


        // displayImageInLarge(patient_profile_iv1.getDrawable());


        refreshFragment();

    }





    public void refreshFragment() {
        callMyProfileMapper();
    }

    private void callMyProfileMapper() {

        GetPatientProfileMapper mapper = new GetPatientProfileMapper();
        mapper.setOnGetPatientProfileListener(new GetPatientProfileMapper.GetPatientProfileListener() {
            @Override
            public void getPatientProfileAPI(GetPatientProfileAPI profileAPI, String errorMessage) {
                if (!isValidResponse(profileAPI, errorMessage)) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_patient_profile_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()).finish();
                        }

                        @Override
                        public void retryClick() {
                            callMyProfileMapper();
                        }
                    });
                    return;
                }

                profileData = profileAPI.getData();

                if (profileData == null) {
                    showMyDialog("Alert", Utils.getStringFromResources(R.string.unable_to_get_patient_profile_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {

                            ((AbstractActivity) MyApplication.getCurrentActivityContext()).finish();
                        }

                        @Override
                        public void retryClick() {
                            callMyProfileMapper();
                        }
                    });
                    return;
                }

                setProfileDataToViews(profileData);
            }
        });
    }

    public PatientProfileData getPatientProfileObject() {
        return profileData;
    }

    private void initForViews() {
        patient_profile_iv1 = getFragemtView().findViewById(R.id.patient_profile_iv1);
        patient_name_tv = getFragemtView().findViewById(R.id.patient_name_tv);
        patient_details_tv = getFragemtView().findViewById(R.id.patient_details_tv);

        blood_group_tv2 = getFragemtView().findViewById(R.id.blood_group_tv2);
        height_tv2 = getFragemtView().findViewById(R.id.height_tv2);
        weight_tv2 = getFragemtView().findViewById(R.id.weight_tv2);

        //patient_details_tv2 = getFragemtView().findViewById(R.id. patient_details_tv2);

        date_of_birth_tv = getFragemtView().findViewById(R.id.date_of_birth_tv);
        blood_group_et = getFragemtView().findViewById(R.id.blood_group_et);
        height_et = getFragemtView().findViewById(R.id.height_et);
        known_allergies_et = getFragemtView().findViewById(R.id.known_allergies_et);


        //My Profile
        primary_contact_no_et = getFragemtView().findViewById(R.id.primary_contact_no_et);
        secondary_contact_no_et = getFragemtView().findViewById(R.id.secondary_contact_no_et);
        residence_contact_no_et = getFragemtView().findViewById(R.id.residence_contact_no_et);
        contact_email_et = getFragemtView().findViewById(R.id.email_et);
        contact_address_et = getFragemtView().findViewById(R.id.address_et);
        contact_language_known_et = getFragemtView().findViewById(R.id.language_known_et);
        contact_nationality_et = getFragemtView().findViewById(R.id.nationality_et);
        contact_id_type_et = getFragemtView().findViewById(R.id.id_type_et);
        id_number_et = getFragemtView().findViewById(R.id.id_number_et);
        //contact_insurance_details_et = getFragemtView().findViewById(R.id.insurance_details_et);

        //Emergency Contact
        emergency_contact_name_et = getFragemtView().findViewById(R.id.emergency_contact_name_et);
        emergency_contact_relationship_et = getFragemtView().findViewById(R.id.emergency_contact_relationship_et);
        emergency_contact_number_et = getFragemtView().findViewById(R.id.emergency_contact_number_et);
        emergency_contact_emailid_et = getFragemtView().findViewById(R.id.emergency_contact_emailid_et);

        //Insurance
        insurance_tpa_et = getFragemtView().findViewById(R.id.insurance_tpa_et);
        insurance_tpa_id_et  = getFragemtView().findViewById(R.id.insurance_tpa_id_et);
        insurance_insurance_provider_et = getFragemtView().findViewById(R.id.insurance_insurance_provider_et);
        insurance_insurance_number_et = getFragemtView().findViewById(R.id.insurance_insurance_number_et);
        insurance_insurance_policy_et = getFragemtView().findViewById(R.id.insurance_insurance_policy_et);
        insurance_insurance_policy_number_et  = getFragemtView().findViewById(R.id.insurance_insurance_policy_number_et);
        insurance_member_id_et = getFragemtView().findViewById(R.id.insurance_member_id_et);
        insurance_type_et = getFragemtView().findViewById(R.id.insurance_type_et);
        insurance_valid_from_et = getFragemtView().findViewById(R.id.insurance_valid_from_et);
        insurance_valid_to_et = getFragemtView().findViewById(R.id.insurance_valid_to_et);
        insurance_co_pay_et = getFragemtView().findViewById(R.id.insurance_co_pay_et);
        insurance_scheme_et = getFragemtView().findViewById(R.id.insurance_scheme_et);
        insurance_reason_et = getFragemtView().findViewById(R.id.insurance_reason_et);
        insurance_organisation_et = getFragemtView().findViewById(R.id.insurance_organisation_et);
        insurance_max_limit_et = getFragemtView().findViewById(R.id.insurance_max_limit_et);


    }

    public void setProfileDataToViews(PatientProfileData data) {

        if (data == null) {
            return;
        }

        if(insurance_add_linear!=null)
        {
            insurance_add_linear.removeAllViews();
        }

        if (data.getPatientinsurancedetails() != null) {

            for (Patientinsurancedetail insurance : data.getPatientinsurancedetails()) {

                if (insurance == null) {
                    continue;
                }

                View convertView = inflater
                        .inflate(R.layout.profile_secoundary_insurance_edit_layout12, null, false);
                final InsuranceHolder holder = new InsuranceHolder(convertView,insurance);

                holder.setOnImageProcessListener(new ImageProcessListener() {
                    @Override
                    public void callShowImageSourceDialog(int id) {
                    }

                    @Override
                    public void setImage(File file, int responseId) {
                    }
                });

               // holder.setInsurance(insurance);
            }


        }


        //Header
        MyApplication.getInstance().setBitmapToImageviewCircular(R.drawable.profile_icon, patient_profile_iv1, data.getPatientlogo());

        if(!TextUtils.isEmpty(data.getIdcardImageFront())) {
            id_front_relative.setVisibility(View.VISIBLE);
            MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, id_front_iv, data.getIdcardImageFront());
        }


        if(!TextUtils.isEmpty(data.getIdcardImageBack())) {
            id_back_relative.setVisibility(View.VISIBLE);
            MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, id_back_iv, data.getIdcardImageBack());
        }




        if(!TextUtils.isEmpty(data.getInsuranceCardFront())) {
            insurance_image1_relative.setVisibility(View.VISIBLE);
            MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, insurance_front_iv, data.getInsuranceCardFront());
        }

        if(!TextUtils.isEmpty(data.getInsuranceCardBack())) {
            insurance_image2_relative.setVisibility(View.VISIBLE);
            MyApplication.getInstance().setBitmapToImageview(R.drawable.profile_id_default_image, insurance_back_iv, data.getInsuranceCardBack());
        }


        patient_name_tv.setText(data.getPatientname());
        String gender=TextUtils.isEmpty(data.getGender())? "":data.getGender()+",";
        String age=TextUtils.isEmpty(data.getAge())? "":data.getAge()+",";
        String maritalStatus=TextUtils.isEmpty(data.getMaritalStatus())? "":data.getMaritalStatus();

        patient_details_tv.setText(gender + age + maritalStatus);


        String DISPLAY_DATE_TIME = "dd/MM/yyyy";//"d MMM yyyy, EEEE h:mm a";
        String SERVER_DATE_FORMAT_NEW = "yyyy-MM-dd";
        SimpleDateFormat DISPLAY_DATE_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_DATE_TIME);

        String dateTimeStr = data.getDateofbirth();
        try {
            Date date = Utils.changeStringToDateFormat(data.getDateofbirth(), SERVER_DATE_FORMAT_NEW);
            String dateStr = DISPLAY_DATE_TIME_FORMATTER.format(date);
            // String timeStr= DISPLAY_TIME_FORMATTER.format(date);
            //With Dr. First name Middle name Last Name at 07:00 PM on Tuesday, 26-12-2017
            dateTimeStr = dateStr;
        } catch (Exception e) {

        }

        //Below header
        date_of_birth_tv.setText(dateTimeStr);

        blood_group_et.setText(data.getBloodgroup());
        height_et.setText(data.getHeight());
        known_allergies_et.setText(data.getKnownallergies());

        blood_group_tv2.setText(data.getBloodgroup());
        height_tv2.setText(data.getHeight());
        weight_tv2.setText(data.getWeight()==null?data.getCurrentweight():data.getWeight());


        //contact Details
        primary_contact_no_et.setText(data.getContactNo().getPrimary());
        secondary_contact_no_et.setText(data.getContactNo().getSecondarymobile());
        residence_contact_no_et.setText(data.getContactNo().getResidencemobile());
        contact_email_et.setText(data.getEmail());
        if (data.getAddress() != null) {
            PatientProfileAddress address = data.getAddress();
            String locality=TextUtils.isEmpty(address.getLocality())? "":address.getLocality()+",";
            String addressStr=TextUtils.isEmpty(address.getAddress())? "":address.getAddress()+",";
            String city=TextUtils.isEmpty(address.getCity())? "":address.getCity()+",";
            String country=TextUtils.isEmpty(address.getCountry())? "":address.getCountry()+",";

            contact_address_et.setText(locality + addressStr + city + country);
        } else {
            contact_address_et.setText(null);
        }
        contact_language_known_et.setText(data.getLanguagesknown());
        contact_nationality_et.setText(data.getNationality());
        contact_id_type_et.setText(data.getIdtype());
        id_number_et.setText(data.getIdnumber());


        //Emergency Contact
        emergency_contact_name_et.setText(data.getEmergencycontactname());
        emergency_contact_relationship_et.setText(data.getEmergencycontactrelationship());
        emergency_contact_number_et.setText(data.getEmergencycontactnumber());
        emergency_contact_emailid_et.setText(data.getEmergencycontactemail());


        //Insurance Details
        insurance_tpa_et.setText(data.getTpa());
        insurance_tpa_id_et.setText(data.getTpaid());
        insurance_insurance_provider_et.setText(data.getInsuranceProvider());
        insurance_insurance_number_et.setText(data.getInsuranceId());
        insurance_insurance_policy_et.setText(data.getPolicy());
        insurance_insurance_policy_number_et.setText(data.getPolicyCardNumber());
        insurance_member_id_et.setText(data.getPolicyCardNumber());
        insurance_type_et.setText(data.getType());
        insurance_valid_from_et.setText(data.getValidityfrom());
        insurance_valid_to_et.setText(data.getValidityto());
        insurance_co_pay_et.setText(data.getCopay());
        insurance_scheme_et.setText(data.getScheme());
        insurance_reason_et.setText(data.getReason());
        insurance_organisation_et.setText(data.getOrganisation());
        insurance_max_limit_et.setText(data.getMaxlimit());


    }





    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        compute();
        setEditModeOrNot();
    }

    private void setEditModeOrNot() {
        //profile_top_details_mask_tv.setVisibility(isEditMode ? View.GONE : View.VISIBLE);
        insurance_details_mask_tv.setVisibility(isEditMode ? View.GONE : View.VISIBLE);
        emergency_details_mask_tv.setVisibility(isEditMode ? View.GONE : View.VISIBLE);
        contact_details_mask_tv.setVisibility(isEditMode ? View.GONE : View.VISIBLE);
        secondary_insurance_details_mask_tv.setVisibility(isEditMode ? View.GONE : View.VISIBLE);
    }

    private void compute() {
        {
            setExpandedViewListener(expandableLayout_secondary_insurance_el, secondary_insurance_details_linear, R.id.secondary_insurance_iv);
            expandableLayout_secondary_insurance_el.initLayout();
            secondary_insurance_details_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandableLayout_secondary_insurance_el.toggle();
                    hideKeyBord(expandableLayout_secondary_insurance_el);
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
    /*private void scrollToView(final ScrollView scrollViewParent, final View view) {
        // Get deepChild Offset
        Point childOffset = new Point();
        getDeepChildOffset(scrollViewParent, view.getParent(), view, childOffset);
        // Scroll to child.
        scrollViewParent.smoothScrollTo(0, childOffset.y);
    }
*/

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


    public void onDateOfBirthTextviewClick() {

        SublimePickerFragment pickerFrag = new SublimePickerFragment();
        pickerFrag.setCallback(mFragmentCallback);

        // Options
        Pair<Boolean, SublimeOptions> optionsPair = getOptions(selectedDateObj);

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

    Pair<Boolean, SublimeOptions> getOptions(SelectedDate selectedDate) {
        Calendar endCalendar = Calendar.getInstance();
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.YEAR, startCalendar.get(Calendar.YEAR) - 120);
        SublimeOptions options = new SublimeOptions();
        options.setDateRange(startCalendar.getTimeInMillis(), endCalendar.getTimeInMillis());
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


    SublimePickerFragment.Callback mFragmentCallback = new SublimePickerFragment.Callback() {
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
            selectedDateObj = selectedDate1;
            Calendar selectedCal = selectedDate1.getFirstDate();

            String date = selectedCal.get(Calendar.YEAR) + "-" + (selectedCal.get(Calendar.MONTH) + 1) + "-" + selectedCal.get(Calendar.DAY_OF_MONTH);
            SimpleDateFormat format = new SimpleDateFormat(Constants.DISPLAY_DATE_FORMAT);
            date_of_birth_tv.setText(format.format(selectedCal.getTime()));
            mySelectedDate = date;
            // showToastMessage("You picked the following date: "+date);

           /* mSelectedDate = selectedDate;
            mHour = hourOfDay;
            mMinute = minute;
            mRecurrenceOption = recurrenceOption != null ?
                    recurrenceOption.name() : "n/a";
            mRecurrenceRule = recurrenceRule != null ?
                    recurrenceRule : "n/a";

            updateInfoView();

            svMainContainer.post(new Runnable() {
                @Override
                public void run() {
                    svMainContainer.scrollTo(svMainContainer.getScrollX(),
                            cbAllowDateRangeSelection.getBottom());
                }
            });*/
        }
    };


    @Override
    View getFragemtView() {
        return fragmentView;
    }

    public void isFromEditMode(boolean isEditMode) {
        this.isEditMode = isEditMode;
    }


    private class InsuranceHolder {

        final View convertView;
        private TextView titleTv;
        private TextView delete_tv;
        private final Patientinsurancedetail insurance;
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

        private TextView insurance_valid_from_tv, insurance_valid_to_tv,tpa_list_tv,insurance_provider_tv;


        //private CustomSpinnerSelection insurance_provider_spinner, tpa_list_spinner;
       // SelectedDate selectedValidFromObj, selectedValidToObj;

        public InsuranceHolder(View convertView,Patientinsurancedetail insurance) {
            this.convertView = convertView;
            this.insurance=insurance;
            FRONT_IMAGE_ID = new Random().nextInt();
            BACK_IMAGE_ID = new Random().nextInt();
            init();
            createNewInsurance();
            setInsuranceDetailsToview();
        }

        public void setOnImageProcessListener(ImageProcessListener imageProcessListener) {
            this.imageProcessListener = imageProcessListener;
        }

        private void createNewInsurance() {
            setTitleName("Secondary Insurance - " + (insurance_add_linear.getChildCount() + 1));

            setExpandListener();

            convertView.setTag(this);
            insurance_add_linear.addView(convertView);


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

            insurance_tpa_id_et = convertView.findViewById(R.id.insurance_tpa_id_et);
            tpa_list_tv = convertView.findViewById(R.id.tpa_list_tv);
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

            insurance_provider_tv = convertView.findViewById(R.id.insurance_provider_tv);

            insurance_insurance_policy_number_et = convertView.findViewById(R.id.insurance_insurance_policy_number_et);

        }







        private void setInsuranceDetailsToview() {
            if (insurance == null) {
                return;
            }
            tpa_list_tv.setText(insurance.getTpa());
            insurance_tpa_id_et.setText(insurance.getTpaid());
            //insurance_provider_spinner  = getFragemtView().findViewById(R.id.insurance_provider_spinner);
            insurance_insurance_number_et.setText(insurance.getInsuranceNumber());
            insurance_insurance_policy_et.setText(insurance.getPolicy());
            insurance_insurance_policy_number_et.setText(insurance.getPolicyNumber());
            insurance_member_id_et.setText(insurance.getMemberid());
            insurance_type_et.setText(insurance.getType());
            insurance_provider_tv.setText(insurance.getInsurancePackage());
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

                    insurance_valid_to_tv.setText(format.format(date));
                    // profileDetails.fromvalidity = profileAPIData.getPatientdata().getTovalidity();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }




            {
                insurance_front_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displayImageInLarge(insurance_front_iv.getDrawable());
                        //imageProcessListener.callShowImageSourceDialog(FRONT_IMAGE_ID);
                        //showImageSourceDialog(Constants.ImagePic.FROM_INSURANCE_FRONT);
                    }
                });
            }

            {
                insurance_back_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displayImageInLarge(insurance_back_iv.getDrawable());
                        //showImageSourceDialog(Constants.ImagePic.FROM_INSURANCE_BACK);
                       // imageProcessListener.callShowImageSourceDialog(BACK_IMAGE_ID);

                    }
                });
            }

        }



    }

}
