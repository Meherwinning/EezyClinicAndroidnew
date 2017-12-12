package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.CityListAPI;
import com.vempower.eezyclinic.APIResponce.DoctorClinicNameListAPI;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.core.DoctorClinicNamesSearch;
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

public class DoctorClinicNamesListMapper extends  AbstractMapper  implements Callback<DoctorClinicNameListAPI> {


    private DoctorClinicNameListListener listener;
    private final DoctorClinicNamesSearch namesSearch;

    public DoctorClinicNamesListMapper(DoctorClinicNamesSearch namesSearch) {
        this.namesSearch=namesSearch;
        //this.countryCode = countryCode;
    }

    public void setOnDoctorClinicNameListListener(DoctorClinicNameListListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid DoctorClinicNameListListener instance.");
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

       // MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.getDoctorClinicNameListAPI(null,null);
            }
            return;
        }

        Call<DoctorClinicNameListAPI> apiResponseCall = stashDealAPI.getDoctorClinicNameListAPI(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<DoctorClinicNameListAPI> response, Retrofit retrofit) {
        //MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<DoctorClinicNameListAPI>() {
            @Override
            public void getMyResponse(DoctorClinicNameListAPI responseBody, String errorMsg) {
                listener.getDoctorClinicNameListAPI(responseBody,errorMsg);
            }
        });




    }

    @Override
    public void onFailure(Throwable error) {
        //MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<DoctorClinicNameListAPI>() {
            @Override
            public void getMyResponse(DoctorClinicNameListAPI responseBody, String errorMsg) {
                listener.getDoctorClinicNameListAPI(responseBody,errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {

      /* String access_key= SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,null);
        if (TextUtils.isEmpty(access_key) ) {
            return null;
        }*/
        //fname
               // email

        //userid(1), password(1)
       JSONObject jsonObject = new JSONObject();
         try {
             //search_text(0), countryId(0), speciality(0), city (0), locality(0)
             if(namesSearch!=null)
             {
                 if(!TextUtils.isEmpty(namesSearch.getSearch_text()))
                 {
                     jsonObject.put("search_text", namesSearch.getSearch_text());
                 }
                 if(!TextUtils.isEmpty(namesSearch.getCountryId()))
                 {
                     jsonObject.put("countryId", namesSearch.getCountryId());
                 }
                 if(!TextUtils.isEmpty(namesSearch.getSpeciality()))
                 {
                     jsonObject.put("speciality", namesSearch.getSpeciality());
                 }

                 if(!TextUtils.isEmpty(namesSearch.getCity()))
                 {
                     jsonObject.put("city", namesSearch.getCity());
                 }

                 if(!TextUtils.isEmpty(namesSearch.getLocality()))
                 {
                     jsonObject.put("locality", namesSearch.getLocality());
                 }



             }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface DoctorClinicNameListListener {
        public void getDoctorClinicNameListAPI(DoctorClinicNameListAPI nameListAPI, String errorMessage);
    }
}
