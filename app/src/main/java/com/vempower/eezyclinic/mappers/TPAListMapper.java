package com.vempower.eezyclinic.mappers;

import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.IdCardTypeAPI;
import com.vempower.eezyclinic.APIResponce.TPAListAPI;
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

public class TPAListMapper extends  AbstractMapper  implements Callback<TPAListAPI> {


    private TPAListListener listener;



    public void setOnTPAListListener(TPAListListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid TPAListListener instance.");
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
                listener.getTPAListAPI(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
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

        Call<TPAListAPI> apiResponseCall = stashDealAPI.getTPAListAPI();

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<TPAListAPI> response, Retrofit retrofit) {
        //MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<TPAListAPI>() {
            @Override
            public void getMyResponse(TPAListAPI responseBody, String errorMsg) {
                listener.getTPAListAPI(responseBody,errorMsg);
            }
        });




    }

    @Override
    public void onFailure(Throwable error) {
        //MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<TPAListAPI>() {
            @Override
            public void getMyResponse(TPAListAPI responseBody, String errorMsg) {
                listener.getTPAListAPI(responseBody,errorMsg);
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

    public interface TPAListListener {
        public void getTPAListAPI(TPAListAPI tpaListAPI, String errorMessage);
    }
}
