package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APICore.BookAppointmentRequestDetails;
import com.vempower.eezyclinic.APICore.ReScheduleAppointmentRequestDetails;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
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

public class ReScheduleAppointmentMapper extends  AbstractMapper  implements Callback<AbstractResponse> {


    private AppointmentBookingListener listener;
    private final ReScheduleAppointmentRequestDetails requestDetails;


    public ReScheduleAppointmentMapper(ReScheduleAppointmentRequestDetails requestDetails) {
        this.requestDetails = requestDetails;
    }

    public void setOnAppointmentBookingListener(AppointmentBookingListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid AppointmentBookingListener instance.");
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
                listener.getAppointmentBookingAPI(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

        MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

       RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.getAppointmentBookingAPI(null,null);
            }
            return;
        }

        Call<AbstractResponse> apiResponseCall = stashDealAPI.rescheduleAppointment(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<AbstractResponse> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<AbstractResponse>() {
            @Override
            public void getMyResponse(AbstractResponse responseBody, String errorMsg) {
                listener.getAppointmentBookingAPI(responseBody,errorMsg);
            }
        });




    }

    @Override
    public void onFailure(Throwable error) {
        MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<AbstractResponse>() {
            @Override
            public void getMyResponse(AbstractResponse responseBody, String errorMsg) {
                listener.getAppointmentBookingAPI(responseBody,errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {
        if(requestDetails==null )
        {
            return null;
        }
/*        access_key (1)
    */
       // PatientData patientData = MyApplication.getInstance().getLoggedUserDetailsFromSharedPref();
       String access_key= SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY,null);


        if(TextUtils.isEmpty(access_key))
        {
            return null;
        }


        /* int docId=-1,branchId=-1;

         try {
             docId= Integer.parseInt(requestDetails.doctor_id);
             branchId= Integer.parseInt(requestDetails.branch_id);
         }catch (Exception e)
         {
             return null;
         }*/


        if( requestDetails.doctor_id==-1 ||
                requestDetails.branch_id==-1  || TextUtils.isEmpty(requestDetails.getNew_appointmenttime()) ||
                TextUtils.isEmpty(requestDetails.oldAppointmentId) ||
                TextUtils.isEmpty(requestDetails.patientId)) {
            return null;
        }


        /*access_key (1)
        branchId (1)
         doctorId (1)
        patientId (1)

        appointmentDateTime (1)

        oldAppointmentId (1)*/

        JSONObject jsonObject = new JSONObject();
         try {
           //  if(!TextUtils.isEmpty(access_key)) {
                 jsonObject.put("access_key", access_key);
            // }


            // if(!TextUtils.isEmpty(requestDetails.getPatientname())) {
                 jsonObject.put("patientId", requestDetails.patientId);
            // }

            // if(docId!=-1) {
                 jsonObject.put("doctorId", requestDetails.doctor_id);
            // }
             //if(branchId!=-1) {
                 jsonObject.put("branchId", requestDetails.branch_id);
            // }
            // if(!TextUtils.isEmpty(requestDetails.appointmenttime)) {
                 jsonObject.put("appointmentDateTime", requestDetails.getNew_appointmenttime());
             //}

            // if(!TextUtils.isEmpty(requestDetails.getFamilymember())) {
                 jsonObject.put("oldAppointmentId", requestDetails.oldAppointmentId);
             //}



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface AppointmentBookingListener {
        public void getAppointmentBookingAPI(AbstractResponse response, String errorMessage);
    }
}
