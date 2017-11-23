package com.vempower.eezyclinic.mappers;

import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Satishk on 4/10/2017.
 */

public class AbstractMapper {

    protected RequestBody getRequestBody(JSONObject jsonObject) {

        if (jsonObject == null) {
            jsonObject = new JSONObject();
        }

        /*JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("kay1","value1");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
//put something inside the map, could be null

        //map.put("code", "some_code");

        Log.i("Json object:", jsonObject.toString());

        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        //serviceCaller is the interface initialized with retrofit.create...
        // Call<ResponseBody> response = serviceCaller.login("loginpostfix", body);

        //start
/*
        try {
            return  RequestBody.create(okhttp3.MediaType.parse("application/json"), URLEncoder.encode(jsonObject.toString(),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

            return  null;
        }
        */
        //end
    }


    protected RequestBody getRequestBody(JSONArray jsonArray) {


        Log.i("Json Array:", jsonArray.toString());

        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonArray.toString());

    }
}
