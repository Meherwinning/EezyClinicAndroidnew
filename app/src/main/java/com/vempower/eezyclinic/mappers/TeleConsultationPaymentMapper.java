package com.vempower.eezyclinic.mappers;

        import android.text.TextUtils;
        import android.util.Log;

        import com.squareup.okhttp.RequestBody;
        import com.vempower.eezyclinic.API.EezyClinicAPI;
        import com.vempower.eezyclinic.APIResponce.AbstractResponse;
        import com.vempower.eezyclinic.APIResponce.SignupAPI;
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
 * Created by Meher on 05/09/2020.
 */

public class TeleConsultationPaymentMapper extends  AbstractMapper  implements Callback<AbstractResponse> {


    private TeleConsultationPaymentListener listener;
    //private final String userId,password;
   /* cancelResaon(0)
    appointmentId(1)*/
    private final String appointment_id,consultation_fee,currency,ref;
    public TeleConsultationPaymentMapper(String appointment_id,String ref,String consultation_fee,String currency) {
        this.appointment_id=appointment_id;
        this.consultation_fee=consultation_fee;
        this.currency=currency;
        this.ref=ref;

    }


    public void setOnTeleConsultationPaymentListener(TeleConsultationPaymentListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid CancelAppointmentListener instance.");
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
                listener.teleConsultationPayment(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

        MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

        RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.teleConsultationPayment(null,null);
            }
            return;
        }

        Call<AbstractResponse> apiResponseCall = stashDealAPI.teleConsultationPayment(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<AbstractResponse> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();
        // listener.getSignupAPI(response.body());
        getMyResponse(response, new MyResponse<AbstractResponse>() {
            @Override
            public void getMyResponse(AbstractResponse responseBody, String errorMsg) {
                listener.teleConsultationPayment(responseBody,errorMsg);
            }
        });

    }

    @Override
    public void onFailure(Throwable error) {
        MyApplication.hideTransaprentDialog();
        //listener.getSignupAPI(null);
        onMyFailure(error, new MyResponse<AbstractResponse>() {
            @Override
            public void getMyResponse(AbstractResponse responseBody, String errorMsg) {
                listener.teleConsultationPayment(responseBody,errorMsg);
            }
        });

    }

    public RequestBody getMyRequestBody() {



        /*String access_key= SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,null);
        if (TextUtils.isEmpty(access_key) ) {
            return null;
        }*/

        if (TextUtils.isEmpty(appointment_id)) {
            return null;
        }


  /*
        access_key (1)
cancelResaon(0)
appointmentId(1)
         */
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("appointment_id", appointment_id);
            if(!TextUtils.isEmpty(ref)) {
                jsonObject.put("transactionid", ref);
            }
            if(!TextUtils.isEmpty(consultation_fee)) {
                jsonObject.put("consultation_fee", consultation_fee);
            }
            if(!TextUtils.isEmpty(currency)) {
                jsonObject.put("currency", currency);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface TeleConsultationPaymentListener {
        public void teleConsultationPayment(AbstractResponse abstractResponse, String errorMessage);
    }
}
