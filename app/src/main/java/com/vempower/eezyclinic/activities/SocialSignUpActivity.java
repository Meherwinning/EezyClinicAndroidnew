package com.vempower.eezyclinic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.vempower.eezyclinic.APIResponce.SignupAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.adapters.HintAdapter;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.core.SocialLoginDetails;
import com.vempower.eezyclinic.fragments.SublimePickerFragment;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.SignupMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyButtonRectangleRM;
import com.vempower.eezyclinic.views.MyCheckBoxRR;
import com.vempower.eezyclinic.views.MyEditTextRR;
import com.vempower.eezyclinic.views.MyTextViewRR;
//import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by satish on 20/11/17.
 */

public class SocialSignUpActivity extends AbstractSocialLoginActivity  {

    private MyTextViewRR dateofBirth_tv,social_login_header_tv;
    private Spinner gender_type_spinner;
    private String selectedGender;


    private MyEditTextRR name_et, email_et, mobile_num_et;
    private MyButtonRectangleRM signup_bt;

    private SocialLoginDetails details;
    private String form_Id;
    private String login_Id;
    private String media_type;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_signup);
       // onShowFacebookButton();
        init();
    }

    private void init() {
        if (getIntent() == null) {
            showMyDialog("Alert", "Invalid user details\nplease try again", true, new Intent(this, SigninActivity.class));
            return;
        }


        dateofBirth_tv = findViewById(R.id.dateofBirth_tv);
        gender_type_spinner = findViewById(R.id.gender_type_spinner);
        name_et = findViewById(R.id.name_et);
        email_et = findViewById(R.id.email_et);
        mobile_num_et = findViewById(R.id.mobile_num_et);
        signup_bt = findViewById(R.id.signup_bt);
        social_login_header_tv = findViewById(R.id.social_login_header_tv);
        selectedGender=null;

        setToSpinnerAdapter();



        Serializable serializableExtra = getIntent().getSerializableExtra(Constants.SocialLoginPref.LOGIN_DETAILS_OBJ_KEY);
        if (serializableExtra == null || !(serializableExtra instanceof SocialLoginDetails)) {
            showMyDialog("Alert", "Invalid user details\nplease try again", true, new Intent(this, SigninActivity.class));
            return;
        }
        details = (SocialLoginDetails) serializableExtra;

        if (!TextUtils.isEmpty(details.EMAIL)) {
            email_et.setText(details.EMAIL);
            email_et.setEnabled(false);
            email_et.setClickable(false);
        }

        if (!TextUtils.isEmpty(details.NAME)) {
            name_et.setText(details.NAME);
        }

        form_Id = getIntent().getStringExtra(Constants.SocialLoginPref.FORMID_KEY);
        if (TextUtils.isEmpty(form_Id)) {
            showMyDialog("Alert", "Invalid form id details\nplease try again", true, new Intent(this, SigninActivity.class));
            return;
        }

        login_Id = getIntent().getStringExtra(Constants.SocialLoginPref.SOCIAL_LOGIN_ID_KEY);
        if (TextUtils.isEmpty(login_Id)) {
            showMyDialog("Alert", "Invalid login id details\nplease try again", true, new Intent(this, SigninActivity.class));
            return;
        }

        media_type = getIntent().getStringExtra(Constants.SocialLoginPref.SOCIAL_MEDIA_TYPE);
        if (TextUtils.isEmpty(media_type)) {
            showMyDialog("Alert", "Invalid login media details/please try again", true, new Intent(this, SigninActivity.class));
            return;
        }
        String headerText = getResources().getString(R.string.social_login_header_message, media_type);
        social_login_header_tv.setText(headerText);

        signup_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignupbuttonClick();
            }
        });


    }

    private void onSignupbuttonClick() {
        if (isNotValidDetails()) {
            return;
        }
        String name = name_et.getText().toString();
        String dob = dateofBirth_tv.getText().toString();

        String email = email_et.getText().toString();
        String mobile_num = mobile_num_et.getText().toString();
        //String name, String dob,String gender,String email,String mobile,String password
        SignupMapper mapper= new SignupMapper(name,dob,selectedGender,email,mobile_num,null);
        //String formid,String  social_media_type,String  social_login_id
        mapper.setSocialSignupValues(form_Id,media_type,login_Id);
        mapper.setOnSignUpListener(new SignupMapper.SignUpListener() {
            @Override
            public void getSignupAPI(SignupAPI signupAPI,String errorMessage) {

                /*if(signupAPI==null && TextUtils.isEmpty(errorMessage))
                {
                    showMyAlertDialog("Alert", Utils.getStringFromResources(R.string.invalid_service_response_lbl),"Ok",false);
                    return;
                }*/
                if(signupAPI==null && !TextUtils.isEmpty(errorMessage))
                {
                    showMyAlertDialog("Alert", errorMessage,"Ok",false);
                    return;
                }
                if(signupAPI==null && TextUtils.isEmpty(errorMessage))
                {

                    showMyDialog("Alert", Utils.getStringFromResources(R.string.invalid_service_response_lbl), new ApiErrorDialogInterface() {
                        @Override
                        public void onCloseClick() {
                            Intent intent= new Intent(MyApplication.getCurrentActivityContext(),SignUpActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void retryClick() {
                            onSignupbuttonClick();
                        }
                    });
                    return;
                }
                validateSignupReponse(signupAPI);

            }
        });
    }



    public boolean isNotValidDetails() {
        {
            String name = name_et.getText().toString();

            if (TextUtils.isEmpty(name)) {
                showToastMessage("Please enter name ");
                name_et.setError("Please enter name");
                return true;
            }

        }
        /*{
            String dob = dateofBirth_tv.getText().toString();

            if (TextUtils.isEmpty(dob)) {
                showToastMessage("Please select date of birth");
                //email_et.setError("Please enter name address");
                return true;
            }

        }*/

        /*{
            // String dob = dateofBirth_tv.getText().toString();

            if (TextUtils.isEmpty(selectedGender) || selectedGender.equalsIgnoreCase(Constants.GenderValues.GENDER)) {
                showToastMessage("Please select Gender");
                //email_et.setError("Please enter name address");
                return true;
            }

        }
*/

        {
            String email = email_et.getText().toString();

            if (TextUtils.isEmpty(email)) {
                showToastMessage("Please enter email address");
                // email_et.setError("Please enter email address");
                return true;
            }
            if (!Utils.isValidEmail(email)) {
                showToastMessage("Please enter valid email address");
                // email_et.setError("Please enter valid email address");
                return true;
            }

            {
                String mobile_num = mobile_num_et.getText().toString();

                if (TextUtils.isEmpty(mobile_num)) {
                    showToastMessage("Please enter Mobile number");
                    //email_et.setError("Please enter name address");
                    return true;
                }

            }
        }


       /* String password = password_et.getText().toString();
        {


            if (TextUtils.isEmpty(password)) {
                showToastMessage("Please enter password");
                password_et.setError("Please enter password");
                return true;
            }

            if (password.length() < Constants.PASSWORD_MIN_LENGTH) {
                showToastMessage("Password should be grater than "+Constants.PASSWORD_MIN_LENGTH);
                password_et.setError("Password should be grater than "+Constants.PASSWORD_MIN_LENGTH);
                return true;
            }
        }*/

        {
            MyCheckBoxRR terms_and_cond_checkbox= findViewById(R.id.terms_and_cond_checkbox);
            if(terms_and_cond_checkbox!=null && !terms_and_cond_checkbox.isChecked())
            {
                showToastMessage("Please agree the Terms & Conditions");
                return true;

            }
        }

        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //hideKeyBord(dateofBirth_tv);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideKeyBord(dateofBirth_tv);
            }
        }, 100);


    }


    public void onDateOfBirthTextviewClick(View view) {

        SublimePickerFragment pickerFrag = new SublimePickerFragment();
        pickerFrag.setCallback(mFragmentCallback);

        // Options
        Pair<Boolean, SublimeOptions> optionsPair = getOptions();

        if (!optionsPair.first) { // If options are not valid
            showToastMessage("No pickers activated");
            return;
        }

        // Valid options
        Bundle bundle = new Bundle();
        bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
        pickerFrag.setArguments(bundle);

        pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        pickerFrag.show(getSupportFragmentManager(), "SUBLIME_PICKER");


    }

    Pair<Boolean, SublimeOptions> getOptions() {
        Calendar endCalendar=Calendar.getInstance();
        Calendar startCalendar=Calendar.getInstance();
        startCalendar.set(Calendar.YEAR,startCalendar.get(Calendar.YEAR)-120);
        SublimeOptions options = new SublimeOptions();
        options.setDateRange(startCalendar.getTimeInMillis(),endCalendar.getTimeInMillis());

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
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {


            if(selectedDate==null || selectedDate.getFirstDate()==null)
            {
                return;
            }
            Calendar selectedCal = selectedDate.getFirstDate();

            String date =  selectedCal.get(Calendar.YEAR)+"-"+(selectedCal.get(Calendar.MONTH)+1)+"-"+selectedCal.get(Calendar.DAY_OF_MONTH);
            dateofBirth_tv.setText(date);
          //  showToastMessage("You picked the following date: "+date);

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

    public void setToSpinnerAdapter() {

        final ArrayList<String> genderTypeList = new ArrayList<>();

        genderTypeList.add(Constants.GenderValues.MALE);
        genderTypeList.add(Constants.GenderValues.FEMALE);
        genderTypeList.add(Constants.GenderValues.GENDER);
       // selectedGender = genderTypeList.get(0);


        selectedGender= genderTypeList.get(2);
        HintAdapter aa = new HintAdapter(MyApplication.getCurrentActivityContext(),R.layout.spinner_textview,genderTypeList);

       // ArrayAdapter aa = new ArrayAdapter(MyApplication.getCurrentActivityContext(), R.layout.spinner_textview, genderTypeList);
       /* {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                // return super.getView(position, convertView, parent);
                View view =super.getView(position, convertView, parent);
                MyTextViewRR textView=view.findViewById(android.R.id.text1);
                //(int left, int top, int right, int bottom)
               int padding= (int) MyApplication.getCurrentActivityContext().getResources().getDimension(R.dimen._6sdp);
                textView.setPadding(padding,padding,padding,padding);
                // do whatever you want with this text view
               *//* if(Utils.isTablet()) {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (MyApplication.getCurrentActivityContext().getResources().getDimension(R.dimen._7sdp)));
                }*//**//*else
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
                int pading= (int) MyApplication.getCurrentActivityContext().getResources().getDimension(R.dimen._6sdp);
                textView.setPadding(pading,pading,pading,pading);

                // do whatever you want with this text view
                *//*if(Utils.isTablet()) {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (MyApplication.getCurrentActivityContext().getResources().getDimension(R.dimen._7sdp)));
                }*//**//*else
                 {
                     textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (MyApplication.getCurrentActivityContext().getResources().getDimension(R.dimen._13sdp)));

                 }*//*
                return view;
            }
        };*/
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_type_spinner.setAdapter(aa);
        gender_type_spinner.setSelection(aa.getCount());

        gender_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender = null;
                if (position != 0) {
                    selectedGender = genderTypeList.get(position - 1);
                }
               // showToastMessage("selectedGender " + selectedGender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(this,SigninActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
