package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;
import com.vempower.eezyclinic.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by sathishkumar on 4/1/17.
 */

public class UpdateHealthRecordsMapper extends AbstractMapper implements Callback<AbstractResponse> {

    private UploadHealthRecordListener listener;
    private final ArrayList<File> fileList;
    private final String file_key="otherFiles_";
    //private final String main_image_key="documentFile";

    private final String documentType, documentDate, doctorName,  clinicName,
            otherDetails  , documentName,id, removedFiles;


    public UpdateHealthRecordsMapper(ArrayList<File> fileList, String documentType, String documentDate,
                                     String doctorName, String clinicName, String otherDetails,
                                     String documentName , String id , String removedFiles  ) {
        this.fileList=fileList;
        this.documentType=documentType;
        this.documentDate=documentDate;
        this.doctorName=doctorName;
        this.clinicName=clinicName;
        this.otherDetails=otherDetails;
        this.documentName=documentName;
        this.id=id;
        this.removedFiles=removedFiles;

    }


    public void setOnUploadHealthRecordListener(UploadHealthRecordListener listener) {
        if (listener == null) {
            Log.i(MyApplication.getCurrentActivityContext().getClass().getName(), "Invalid UploadHealthRecordListener instance.");
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
                listener.uploadHealthRecord(null, Utils.getStringFromResources(R.string.network_not_available_lbl));
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
        String access_key = SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY, null);
        if (TextUtils.isEmpty(access_key)) {
            if (listener != null) {
                listener.uploadHealthRecord(null, null);
            }
            return;
        }

        Map<String, RequestBody> map= getMapList();



        if (map == null || map.size()==0) {
            MyApplication.hideTransaprentDialog();
            if (listener != null) {
                listener.uploadHealthRecord(null, null);
            }
            return;
        }


        //  Call<UserAPI> apiResponseCall = PreferencesAPI.updateUserProfile(getJsonObject(user).toString());

       // Call<AbstractResponse> apiResponseCall = stashDealAPI.uploadProfileImage(imageBody, mybody);
        Call<AbstractResponse> apiResponseCall = stashDealAPI.updatehealthrecordfile(map);
        apiResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<AbstractResponse> response, Retrofit retrofit) {
        MyApplication.hideTransaprentDialog();

        getMyResponse(response, new MyResponse<AbstractResponse>() {
            @Override
            public void getMyResponse(AbstractResponse responseBody, String errorMsg) {
                listener.uploadHealthRecord(responseBody, errorMsg);
            }
        });


    }

    @Override
    public void onFailure(Throwable error) {
        MyApplication.hideTransaprentDialog();

        onMyFailure(error, new MyResponse<AbstractResponse>() {
            @Override
            public void getMyResponse(AbstractResponse responseBody, String errorMsg) {
                listener.uploadHealthRecord(responseBody, errorMsg);
            }
        });


    }




    public RequestBody getMyRequestBody() {

        String access_key = SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY, null);
        if (TextUtils.isEmpty(access_key)) {
            return null;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("access_key", access_key);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getRequestBody(jsonObject);
    }

    public Map<String,RequestBody> getMapList() {

        String access_key = SharedPreferenceUtils.getStringValueFromSharedPrefarence(Constants.Pref.USER_VALIDATION_KEY, null);
        if (TextUtils.isEmpty(access_key)) {
            return null;
        }

        if (TextUtils.isEmpty(access_key)) {
            return null;
        }


        Map<String, RequestBody> map = new HashMap<>();
        setImagesToMap(map);

        if(id!=null) {
            map.put("id", RequestBody.create(MediaType.parse("text/plain"), id));
        }



        if(removedFiles!=null) {
            map.put("removedFiles", RequestBody.create(MediaType.parse("text/plain"), removedFiles));
        }

        map.put("access_key",RequestBody.create(MediaType.parse("text/plain"), access_key));
        if(documentType!=null) {
            map.put("documentType", RequestBody.create(MediaType.parse("text/plain"), documentType));
        }

        if(documentDate!=null) {
            map.put("documentDate", RequestBody.create(MediaType.parse("text/plain"), documentDate));
        }

        if(doctorName!=null) {
            map.put("doctorName", RequestBody.create(MediaType.parse("text/plain"), doctorName));
        }

        if(clinicName!=null) {
            map.put("clinicName", RequestBody.create(MediaType.parse("text/plain"), clinicName));
        }

        if(otherDetails!=null) {
            map.put("otherDetails", RequestBody.create(MediaType.parse("text/plain"), otherDetails));
        }
        if(documentName!=null) {
            map.put("documentName", RequestBody.create(MediaType.parse("text/plain"), documentName));
        }
        return map;
    }

    private void setImagesToMap(Map<String, RequestBody> map) {
        for(int i=0;i<fileList.size();i++)
        {
            File file=fileList.get(i);
            if(file==null)
            {
                continue;
            }

           /* if(map.size()==0)
            {
                RequestBody imageBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
                map.put(main_image_key+"\"; filename=\"patient_pic.png\" ", imageBody);
                continue;
            }*/
            RequestBody imageBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
            map.put(file_key+((map.size()+1))+"\"; filename=\"patient_pic.png\" ", imageBody);
        }
    }

    public interface UploadHealthRecordListener {
        public void uploadHealthRecord(AbstractResponse PreferencesAPI, String errorMessage);
    }

}
