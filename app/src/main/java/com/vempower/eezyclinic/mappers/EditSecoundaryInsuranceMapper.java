package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.core.SecondaryInsurance;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by sathishkumar on 4/1/17.
 */

public class EditSecoundaryInsuranceMapper extends AbstractMapper implements Callback<AbstractResponse> {

    private EditSecoundaryInsuranceListener listener;
   // private final File frontImage, backImage;
    //private final String keyName;
    private final SecondaryInsurance insurance;


    public EditSecoundaryInsuranceMapper(SecondaryInsurance insurance) {
        //this.frontImage = frontImage;
       // this.backImage = backImage;
        this.insurance = insurance;
    }


    public void setOnEditSecoundaryInsuranceListener(EditSecoundaryInsuranceListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid EditSecoundaryInsuranceListener instance.");
            //addressListListener.validateAddressList(null);
            return;

        }

        this.listener = listener;
        init();
    }

    private void init() {

        if (!Utils.isNetworkAvailable(MyApplication
                .getCurrentActivityContext())) {
            //Utils.showToastMsgForNetworkNotAvalable();
            if (listener != null) {
                listener.editSecoundaryInsurance(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

        MyApplication.showTransparentDialog();


        MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();


       /*   RequestBody body=getMyRequestBody();
        if (body==null) {
            if (listener != null) {
                listener.uploadProfilePic(null, null);
            }
            return;
        }*/

        // RequestBody body=getMyRequestBody();

        if (insurance==null) {
            if (listener != null) {
                listener.editSecoundaryInsurance(null, null);
            }
            return;
        }
        if (TextUtils.isEmpty(insurance.id)) {
            if (listener != null) {
                listener.editSecoundaryInsurance(null, null);
            }
            return;
        }


        String access_key = SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY, null);
        if (TextUtils.isEmpty(access_key)) {
            if (listener != null) {
                listener.editSecoundaryInsurance(null, null);
            }
            return;
        }



        //'id' : "13"


        Map<String, RequestBody> map = new HashMap<>();
        RequestBody accessKeyBody = RequestBody.create(MediaType.parse("text/plain"), access_key);

        map.put("access_key", accessKeyBody);

        addInsuranceDetails(map);

        /*
             "insurance_card_front": "http://202.63.103.194:8003/uploads/1517295719_59452.jpg",
        "insurance_card_rear": "http://202.63.103.194:8003/uploads/1517295719_42683.jpg"

         */

        RequestBody imageBodyFront = null;
        RequestBody imageBodyBack = null;
        if (insurance.frontImageFile != null) {
            imageBodyFront = RequestBody.create(MediaType.parse("image/jpeg"), insurance.frontImageFile);
            map.put("insurance_card_front" + "\"; filename=\"patient_pic.png\" ", imageBodyFront);

        }

        if (insurance.backImageFile != null) {
            imageBodyBack = RequestBody.create(MediaType.parse("image/jpeg"), insurance.backImageFile);
            map.put("insurance_card_rear" + "\"; filename=\"patient_pic.png\" ", imageBodyBack);

        }
        /*if (imageBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.uploadProfilePic(null, null);
            }
            return;
        }*/


        //  Call<UserAPI> apiResponseCall = PreferencesAPI.updateUserProfile(getJsonObject(user).toString());

        // Call<AbstractResponse> apiResponseCall = stashDealAPI.uploadProfileImage(imageBody, mybody);
        Call<AbstractResponse> apiResponseCall = stashDealAPI.editSecondaryInsurance(map);
        apiResponseCall.enqueue(this);
    }

    private RequestBody getMyRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);

    }

    private void addInsuranceDetails(Map<String, RequestBody> map) {
        if (insurance == null) {
            return;
        }
        if (!TextUtils.isEmpty(insurance.id)) {
            map.put("id", getMyRequestBody(insurance.id));
        }
        if (!TextUtils.isEmpty(insurance.insurancePackage)) {
            map.put("insurancePackage", getMyRequestBody(insurance.insurancePackage));
        }
        if (!TextUtils.isEmpty(insurance.insuranceNumber)) {
            map.put("insuranceNumber", getMyRequestBody(insurance.insuranceNumber));
        }
        if (!TextUtils.isEmpty(insurance.tpa)) {
            map.put("tpa", getMyRequestBody(insurance.tpa));
        }
        if (!TextUtils.isEmpty(insurance.tpaid)) {
            map.put("tpaid", getMyRequestBody(insurance.tpaid));
        }
        if (!TextUtils.isEmpty(insurance.policy)) {
            map.put("policy", getMyRequestBody(insurance.policy));
        }
        if (!TextUtils.isEmpty(insurance.policy_number)) {
            map.put("policy_number", getMyRequestBody(insurance.policy_number));
        }
        if (!TextUtils.isEmpty(insurance.memberid)) {
            map.put("memberid", getMyRequestBody(insurance.memberid));
        }
        if (!TextUtils.isEmpty(insurance.type)) {
            map.put("type", getMyRequestBody(insurance.type));
        }
        if (!TextUtils.isEmpty(insurance.scheme)) {
            map.put("scheme", getMyRequestBody(insurance.scheme));
        }
        if (!TextUtils.isEmpty(insurance.reason)) {
            map.put("reason", getMyRequestBody(insurance.reason));
        }
        if (!TextUtils.isEmpty(insurance.copay)) {
            map.put("copay", getMyRequestBody(insurance.copay));
        }
        if (!TextUtils.isEmpty(insurance.maxlimit)) {
            map.put("maxlimit", getMyRequestBody(insurance.maxlimit));
        }
        if (!TextUtils.isEmpty(insurance.fromvalidity)) {
            map.put("fromvalidity", getMyRequestBody(insurance.fromvalidity));
        }
        if (!TextUtils.isEmpty(insurance.tovalidity)) {
            map.put("tovalidity", getMyRequestBody(insurance.tovalidity));
        }


        /*
    {
          "access_key" : "4951dbdf961f4156a02d7394bc5fa3e8",

        "fromvalidity": "2018-01-09",
                "tovalidity": "2018-05-09",

       "insurance_card_front": "http://202.63.103.194:8003/uploads/1517295719_59452.jpg",
        "insurance_card_rear": "http://202.63.103.194:8003/uploads/1517295719_42683.jpg"

          }
     */
    }

    @Override
    public void onResponse(Response<AbstractResponse> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<AbstractResponse>() {
            @Override
            public void getMyResponse(AbstractResponse responseBody, String errorMsg) {
                listener.editSecoundaryInsurance(responseBody, errorMsg);
            }
        });


    }

    @Override
    public void onFailure(Throwable error) {
        MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<AbstractResponse>() {
            @Override
            public void getMyResponse(AbstractResponse responseBody, String errorMsg) {
                listener.editSecoundaryInsurance(responseBody, errorMsg);
            }
        });


    }


    //
//    @NonNull
//    private JSONObject getJsonObject(User user) {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("PreferenceId", preferenceID);
//            jsonObject.put("AuthenticationToken", user.getAuthenticationToken());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonObject;
//    }


    public RequestBody getMyRequestBody() {

        String access_key = SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY, null);
        if (TextUtils.isEmpty(access_key)) {
            return null;
        }



        /*
  /*

                 {
          "access_key": "360f19438d8cb884057f79a1a55df441",
           "newmobileno":"123456789"

}
     */

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("access_key", access_key);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface EditSecoundaryInsuranceListener {
        public void editSecoundaryInsurance(AbstractResponse PreferencesAPI, String errorMessage);
    }

}
