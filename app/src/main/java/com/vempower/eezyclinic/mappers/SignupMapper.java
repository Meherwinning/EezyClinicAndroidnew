package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.LoginAPI;
import com.vempower.eezyclinic.APIResponce.SignupAPI;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Satishk on 4/10/2017.
 */

public class SignupMapper extends  AbstractMapper  implements Callback<SignupAPI> {


    private SignUpListener listener;
    //private final String userId,password;
    private String registration_type="n";
    private final String name, dob, gender, email, mobile, password;
    private  String formid, social_media_type, social_login_id;
    //Case 1 - name(1), dob(0), gender(0), email(1), mobile(1), password(1) ,
    //Case 2 - name(1), dob(0), gender(0), email(1), mobile(1), formid(1), social_login_type(1), social_login_id(1)
    public SignupMapper(String name, String dob,String gender,String email,String mobile,String password) {
        this.name=name;
        this.dob=dob;
        this.gender=gender;
        this.email=email;
        this.mobile=mobile;
        this.password=password;
        registration_type="n";
        this.formid=null;
        this.social_media_type=null;
        this.social_login_id=null;
    }
    public void setSocialSignupValues(String formid,String  social_media_type,String  social_login_id)
    {
        this.formid=formid;
        this.social_media_type=social_media_type;
        this.social_login_id=social_login_id;
        registration_type="s";

    }

    public void setOnSignUpListener(SignUpListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid SignUpListener instance.");
            return;

        }

        this.listener = listener;
        init();
    }

    private void init() {

        if (!Utils.isNetworkAvailable(MyApplication
                .getCurrentActivityContext())) {
            Utils.showToastMsgForNetworkNotAvalable();
            return;
        }

        MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.getSignupAPI(null);
            }
            return;
        }

        Call<SignupAPI> apiResponseCall = stashDealAPI.getSignupAPI(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<SignupAPI> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();
        listener.getSignupAPI(response.body());

    }

    @Override
    public void onFailure(Throwable t) {
        MyApplication.hideTransaprentDialog();
        listener.getSignupAPI(null);

    }

    public RequestBody getMyRequestBody() {

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(mobile) ) {
            return null;
        }
       //Case 1 - name(1), dob(0), gender(0), email(1), mobile(1), password(1) ,
        //Case 2 -  formid(1), social_login_type(1), social_login_id(1)

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("email", email);
            jsonObject.put("mobile", mobile);
            jsonObject.put("registration_type", registration_type);

            if(!TextUtils.isEmpty(gender)) {
                jsonObject.put("dob", dob);
            }
            if(!TextUtils.isEmpty(gender)) {
                jsonObject.put("gender", gender);
            }

            if(!TextUtils.isEmpty(password)) {
                jsonObject.put("password", password);
            }
            if(!TextUtils.isEmpty(formid)) {
                jsonObject.put("formid", formid);
            }
            if(!TextUtils.isEmpty(social_media_type)) {
                jsonObject.put("social_media_type", social_media_type);
            }
            if(!TextUtils.isEmpty(social_login_id)) {
                jsonObject.put("social_media_id", social_login_id);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface SignUpListener {
        public void getSignupAPI(SignupAPI signupAPI);
    }
}
