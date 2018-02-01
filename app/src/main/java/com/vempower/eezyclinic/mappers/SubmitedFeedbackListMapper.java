package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.PendingFeedbackListAPI;
import com.vempower.eezyclinic.APIResponce.SubmitedFeedbackListAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
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

public class SubmitedFeedbackListMapper extends  AbstractMapper  implements Callback<SubmitedFeedbackListAPI> {


    private SubmitedFeedbackListListener listener;




    public void setOnSubmitedFeedbackListListener(SubmitedFeedbackListListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid SubmitedFeedbackListListener instance.");
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
                listener.getSubmitedFeedbackListAPI(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

        MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.getSubmitedFeedbackListAPI(null,null);
            }
            return;
        }

        Call<SubmitedFeedbackListAPI> apiResponseCall = stashDealAPI.getSubmittedfeedback(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<SubmitedFeedbackListAPI> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<SubmitedFeedbackListAPI>() {
            @Override
            public void getMyResponse(SubmitedFeedbackListAPI responseBody, String errorMsg) {
                listener.getSubmitedFeedbackListAPI(responseBody,errorMsg);
            }
        });




    }

    @Override
    public void onFailure(Throwable error) {
        MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<SubmitedFeedbackListAPI>() {
            @Override
            public void getMyResponse(SubmitedFeedbackListAPI responseBody, String errorMsg) {
                listener.getSubmitedFeedbackListAPI(responseBody,errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {

       String access_key= SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,null);
        if (TextUtils.isEmpty(access_key) ) {
            return null;
        }
       /*
        "perpage":"20",
"page":"1"
        */
       JSONObject jsonObject = new JSONObject();
         try {
            jsonObject.put("access_key", access_key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface SubmitedFeedbackListListener {
        public void getSubmitedFeedbackListAPI(SubmitedFeedbackListAPI submitedFeedbackListAPI, String errorMessage);
    }
}
