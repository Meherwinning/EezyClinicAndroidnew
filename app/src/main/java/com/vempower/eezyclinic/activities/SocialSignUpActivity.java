package com.vempower.eezyclinic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.vempower.eezyclinic.APIResponce.SignupAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.core.SocialLoginDetails;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.SignupMapper;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyButtonRectangleRM;
import com.vempower.eezyclinic.views.MyCheckBoxRR;
import com.vempower.eezyclinic.views.MyEditTextRR;
import com.vempower.eezyclinic.views.MyTextViewRR;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by satish on 20/11/17.
 */

public class SocialSignUpActivity extends AbstractSocialLoginActivity implements DatePickerDialog.OnDateSetListener {

    private MyTextViewRR dateofBirth_tv;
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

        if (!TextUtils.isEmpty(details.getName())) {
            name_et.setText(details.getName());
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
            public void getSignupAPI(SignupAPI signupAPI) {

                if(signupAPI==null)
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
            if(terms_and_cond_checkbox!=null && !terms_and_cond_checkbox.isCheck())
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

        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        // dpd.setThemeDark(true);
        dpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
       // String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        String date = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
        dateofBirth_tv.setText(date);
        showToastMessage("You picked the following date: " + date);
    }

    public void setToSpinnerAdapter() {

        final ArrayList<String> genderTypeList = new ArrayList<>();
        genderTypeList.add(Constants.GenderValues.GENDER);
        genderTypeList.add(Constants.GenderValues.MALE);
        genderTypeList.add(Constants.GenderValues.FEMALE);
       // selectedGender = genderTypeList.get(0);
        ArrayAdapter aa = new ArrayAdapter(MyApplication.getCurrentActivityContext(), R.layout.spinner_textview, genderTypeList);
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


}
