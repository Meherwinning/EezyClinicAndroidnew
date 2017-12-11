package com.vempower.eezyclinic.mappers;

import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.CityListAPI;
import com.vempower.eezyclinic.APIResponce.SpecalitiesAPI;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Utils;

import org.json.JSONObject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Satishk on 4/10/2017.
 */

public class CityListMapper extends  AbstractMapper  implements Callback<CityListAPI> {


    private CityListListener listener;
    private final int  countryCode;

    public CityListMapper(int countryCode) {
        this.countryCode = countryCode;
    }

    public void setOnCityListListener(CityListListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid CityListListener instance.");
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

       /* RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.getSpecalitiesAPII(null,null);
            }
            return;
        }*/

        Call<CityListAPI> apiResponseCall = stashDealAPI.getCityListAPI(countryCode+"");

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<CityListAPI> response, Retrofit retrofit) {
        //MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<CityListAPI>() {
            @Override
            public void getMyResponse(CityListAPI responseBody, String errorMsg) {
                listener.getCityListAPI(responseBody,errorMsg);
            }
        });




    }

    @Override
    public void onFailure(Throwable error) {
        //MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<CityListAPI>() {
            @Override
            public void getMyResponse(CityListAPI responseBody, String errorMsg) {
                listener.getCityListAPI(responseBody,errorMsg);
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
        /* try {
            jsonObject.put("access_key", access_key);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        return getRequestBody(jsonObject);
    }

    public interface CityListListener {
        public void getCityListAPI(CityListAPI cityListAPI, String errorMessage);
    }
}
