package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.APIResponce.ChangeMobileNumberAPI;
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

public class CheckUserIDMapper extends AbstractMapper implements Callback<AbstractResponse> {


    private UserIdListener listener;
    // private final String moduleName;
    private final String emailId;

    public CheckUserIDMapper(String emailId) {
        this.emailId = emailId;
    }

    public void setOnUserIdListener(UserIdListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid UserIdListener instance.");
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
                listener.getUser(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

         MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.getUser(null, null);
            }
            return;
        }

        Call<AbstractResponse> apiResponseCall = stashDealAPI.getuser(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<AbstractResponse> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<AbstractResponse>() {
            @Override
            public void getMyResponse(AbstractResponse responseBody, String errorMsg) {
                listener.getUser(responseBody, errorMsg);
            }
        });


    }

    @Override
    public void onFailure(Throwable error) {
        MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<AbstractResponse>() {
            @Override
            public void getMyResponse(AbstractResponse responseBody, String errorMsg) {
                listener.getUser(responseBody, errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {

       /* String access_key = SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY, null);
        if (TextUtils.isEmpty(access_key)) {
            return null;
        }
*/
        if (TextUtils.isEmpty(emailId)) {
            return null;
        }

  /*
{
	"email":"padmanabham2005@gmail.com"
}


     */

        JSONObject jsonObject = new JSONObject();
        try {
           // jsonObject.put("access_key", access_key);
            jsonObject.put("email", emailId);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface UserIdListener {
        public void getUser(AbstractResponse response, String errorMessage);
    }
}
