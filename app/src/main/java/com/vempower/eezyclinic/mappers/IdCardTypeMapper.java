package com.vempower.eezyclinic.mappers;

import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.IdCardTypeAPI;
import com.vempower.eezyclinic.APIResponce.NationalityListAPI;
import com.vempower.eezyclinic.R;
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

public class IdCardTypeMapper extends  AbstractMapper  implements Callback<IdCardTypeAPI> {


    private IdTypeListListener listener;



    public void setOnIdTypeListListener(IdTypeListListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid IdTypeListListener instance.");
            return;

        }

        this.listener = listener;
        init();
    }

    private void init() {

        if (!Utils.isNetworkAvailable(MyApplication
                .getCurrentActivityContext())) {
           // Utils.showToastMsgForNetworkNotAvalable();
            if (listener != null) {
                listener.getIdTypeListAPI(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

        //MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

       /* RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.getSpecalitiesAPII(null,null);
            }
            return;
        }*/

        Call<IdCardTypeAPI> apiResponseCall = stashDealAPI.getIdCardTypeAPI();

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<IdCardTypeAPI> response, Retrofit retrofit) {
        //MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<IdCardTypeAPI>() {
            @Override
            public void getMyResponse(IdCardTypeAPI responseBody, String errorMsg) {
                listener.getIdTypeListAPI(responseBody,errorMsg);
            }
        });




    }

    @Override
    public void onFailure(Throwable error) {
        //MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<IdCardTypeAPI>() {
            @Override
            public void getMyResponse(IdCardTypeAPI responseBody, String errorMsg) {
                listener.getIdTypeListAPI(responseBody,errorMsg);
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

    public interface IdTypeListListener {
        public void getIdTypeListAPI(IdCardTypeAPI idCardTypeAPI, String errorMessage);
    }
}
