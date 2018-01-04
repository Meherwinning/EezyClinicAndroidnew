package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APICore.BookAppointmentRequestDetails;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.core.EditProfileDetails;
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

public class ProfileSaveMapper extends  AbstractMapper  implements Callback<AbstractResponse> {


    private ProfileSaveListener listener;
    private final EditProfileDetails profileDetails;


    public ProfileSaveMapper(EditProfileDetails profileDetails) {
        this.profileDetails = profileDetails;
    }

    public void setOnProfileSaveListener(ProfileSaveListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid ProfileSaveListener instance.");
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
                listener.profileSave(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
            }
            return;
        }

        MyApplication.showTransparentDialog();
        EezyClinicAPI stashDealAPI = MyApplication.getInstance().getEezyClinicAPI();

       RequestBody requestBody = getMyRequestBody();
        if (requestBody == null) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.profileSave(null,null);
            }
            return;
        }

        Call<AbstractResponse> apiResponseCall = stashDealAPI.patientProfileSave(requestBody);

        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<AbstractResponse> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<AbstractResponse>() {
            @Override
            public void getMyResponse(AbstractResponse responseBody, String errorMsg) {
                listener.profileSave(responseBody,errorMsg);
            }
        });




    }

    @Override
    public void onFailure(Throwable error) {
        MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<AbstractResponse>() {
            @Override
            public void getMyResponse(AbstractResponse responseBody, String errorMsg) {
                listener.profileSave(responseBody,errorMsg);
            }
        });


    }

    public RequestBody getMyRequestBody() {
        if(profileDetails==null )
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


/*
  "access_key": "360f19438d8cb884057f79a1a55df441",
 "patientName" : "Swathi k",
    "gender" : "Female",
    "maritial_status" : "married",
    "dateofBirth" : "01-01-1986",
    "mobile" : "9876543210",
    "secondarymobile" :"1234567890",
    "residencemobile" :"8965321025",
    "country" : "ind",
    "city" : "Hyd",
    "locality":"airport area",
    "address" : "begumpet",
    "languagesKnown" : "Telugu,Hindi",
    "nationality" :"Indian" ,
    "idType": "Aadhar Card",
    "idNumber": "343434534534536468",
    "occupation"  : "emp1",
    "organisation" : "abc",
    "bloodGroup" : "O-"  ,
    "height" : "5.6",
    "knownAllergies" : "test knownAllergies",
    "tpa": "1" ,
    "tpaid" : "TPA002",
    "insurancePackage"  : "Ace Life"  ,
    "insuranceNumber" : "98745632102154" ,
    "policy" : "1",
    "policy_number" : "1236987456",
    "memberid" : "845632569",
    "type" : "1",
    "scheme" : "1",
    "reason" : "1",
    "copay" : "3" ,
    "maxlimit"  : "5",
    "fromvalidity" :  "2017-01-02",
    "tovalidity" : "2017-08-02",
    "secondaryinsurancePackage"  : "LIC",
    "secondaryinsuranceNumber" : "45568877654566879",
    "emergencyContactName"  : "suresh" ,
    "emergencyContactRelationship" : "Brother",
    "emergencyContactNumber" : "7894561236",
    "emergencyContactEmail"   : "suresh@gmail.com"
 */


        JSONObject jsonObject = new JSONObject();
         try {
           //  if(!TextUtils.isEmpty(access_key)) {
                 jsonObject.put("access_key", access_key);
            // }

             if(!TextUtils.isEmpty(profileDetails.patientName)) {
                 jsonObject.put("patientName", profileDetails.patientName);
             }
             if(!TextUtils.isEmpty(profileDetails.gender)) {
                 jsonObject.put("gender", profileDetails.gender);
             }
             if(!TextUtils.isEmpty(profileDetails.maritialStatus)) {
                 jsonObject.put("maritial_status", profileDetails.maritialStatus);
             }

             if(!TextUtils.isEmpty(profileDetails.dateofBirth)) {
                 jsonObject.put("dateofBirth", profileDetails.dateofBirth);
             }

             if(!TextUtils.isEmpty(profileDetails.mobile)) {
                 jsonObject.put("mobile", profileDetails.mobile);
             }
             if(!TextUtils.isEmpty(profileDetails.secondarymobile)) {
                 jsonObject.put("secondarymobile", profileDetails.secondarymobile);
             }

             if(!TextUtils.isEmpty(profileDetails.residencemobile)) {
                 jsonObject.put("residencemobile", profileDetails.residencemobile);
             }
             if(!TextUtils.isEmpty(profileDetails.country)) {
                 jsonObject.put("country", profileDetails.country);
             }
             if(!TextUtils.isEmpty(profileDetails.city)) {
                 jsonObject.put("city", profileDetails.city);
             }
             if(!TextUtils.isEmpty(profileDetails.locality)) {
                 jsonObject.put("locality", profileDetails.locality);
             }
             if(!TextUtils.isEmpty(profileDetails.address)) {
                 jsonObject.put("address", profileDetails.address);
             }
             if(!TextUtils.isEmpty(profileDetails.languagesKnown)) {
                 jsonObject.put("languagesKnown", profileDetails.languagesKnown);
             }
             if(!TextUtils.isEmpty(profileDetails.nationality)) {
                 jsonObject.put("nationality", profileDetails.nationality);
             }

             if(!TextUtils.isEmpty(profileDetails.idType)) {
                 jsonObject.put("idType", profileDetails.idType);
             }
             if(!TextUtils.isEmpty(profileDetails.idNumber)) {
                 jsonObject.put("idNumber", profileDetails.idNumber);
             }
             if(!TextUtils.isEmpty(profileDetails.occupation)) {
                 jsonObject.put("occupation", profileDetails.occupation);
             }
             if(!TextUtils.isEmpty(profileDetails.organisation)) {
                 jsonObject.put("organisation", profileDetails.organisation);
             }
             if(!TextUtils.isEmpty(profileDetails.bloodGroup)) {
                 jsonObject.put("bloodGroup", profileDetails.bloodGroup);
             }
             if(!TextUtils.isEmpty(profileDetails.height)) {
                 jsonObject.put("height", profileDetails.height);
             }
             if(!TextUtils.isEmpty(profileDetails.knownAllergies)) {
                 jsonObject.put("knownAllergies", profileDetails.knownAllergies);
             }
             if(!TextUtils.isEmpty(profileDetails.tpa)) {
                 jsonObject.put("tpa", profileDetails.tpa);
             }
             if(!TextUtils.isEmpty(profileDetails.tpaid)) {
                 jsonObject.put("tpaid", profileDetails.tpaid);
             }

             if(!TextUtils.isEmpty(profileDetails.insurancePackage)) {
                 jsonObject.put("insurancePackage", profileDetails.insurancePackage);
             }
             if(!TextUtils.isEmpty(profileDetails.insuranceNumber)) {
                 jsonObject.put("insuranceNumber", profileDetails.insuranceNumber);
             }
             if(!TextUtils.isEmpty(profileDetails.policy)) {
                 jsonObject.put("policy", profileDetails.policy);
             }
             if(!TextUtils.isEmpty(profileDetails.policy_number)) {
                 jsonObject.put("policy_number", profileDetails.policy_number);
             }

             if(!TextUtils.isEmpty(profileDetails.memberid)) {
                 jsonObject.put("memberid", profileDetails.memberid);
             }

             if(!TextUtils.isEmpty(profileDetails.type)) {
                 jsonObject.put("type", profileDetails.type);
             }

             if(!TextUtils.isEmpty(profileDetails.scheme)) {
                 jsonObject.put("scheme", profileDetails.scheme);
             }
             if(!TextUtils.isEmpty(profileDetails.reason)) {
                 jsonObject.put("reason", profileDetails.reason);
             }
             if(!TextUtils.isEmpty(profileDetails.copay)) {
                 jsonObject.put("copay", profileDetails.copay);
             }
             if(!TextUtils.isEmpty(profileDetails.maxlimit)) {
                 jsonObject.put("maxlimit", profileDetails.maxlimit);
             }


             if(!TextUtils.isEmpty(profileDetails.fromvalidity)) {
                 jsonObject.put("fromvalidity", profileDetails.fromvalidity);
             }
             if(!TextUtils.isEmpty(profileDetails.tovalidity)) {
                 jsonObject.put("tovalidity", profileDetails.tovalidity);
             }
             if(!TextUtils.isEmpty(profileDetails.secondaryinsurancePackage)) {
                 jsonObject.put("secondaryinsurancePackage", profileDetails.secondaryinsurancePackage);
             }
             if(!TextUtils.isEmpty(profileDetails.secondaryinsuranceNumber)) {
                 jsonObject.put("secondaryinsuranceNumber", profileDetails.secondaryinsuranceNumber);
             }
             if(!TextUtils.isEmpty(profileDetails.emergencyContactName)) {
                 jsonObject.put("emergencyContactName", profileDetails.emergencyContactName);
             }
             if(!TextUtils.isEmpty(profileDetails.emergencyContactRelationship)) {
                 jsonObject.put("emergencyContactRelationship", profileDetails.emergencyContactRelationship);
             }
             if(!TextUtils.isEmpty(profileDetails.emergencyContactNumber)) {
                 jsonObject.put("emergencyContactNumber", profileDetails.emergencyContactNumber);
             }

             if(!TextUtils.isEmpty(profileDetails.emergencyContactEmail)) {
                 jsonObject.put("emergencyContactEmail", profileDetails.emergencyContactEmail);
             }




        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public interface ProfileSaveListener {
        public void profileSave(AbstractResponse response, String errorMessage);
    }
}
