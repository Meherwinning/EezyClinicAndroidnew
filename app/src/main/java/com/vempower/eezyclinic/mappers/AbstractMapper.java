package com.vempower.eezyclinic.mappers;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.application.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.HttpException;
import retrofit.Response;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

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

    protected <T extends Object> void getMyResponse(Response<T> response,MyResponse<T> myResponse ) {
        if(myResponse==null)
        {
           return;
        }
        if(response==null)
        {
            myResponse.getMyResponse(null,null);
        }
        if (response.code()==HTTP_OK) {
           // showMessage(response.body().getMessage());
            //Intent intent = new Intent(LoginActivity.this, MainAct.class);
            //startActivity(intent);
           myResponse.getMyResponse(response.body(),null);
        }
        else {
            try {
                String finallyError = null;
                //start
                if (response.code() != HTTP_BAD_REQUEST) {

                    finallyError=response.message()+"\nResponse Code :"+response.code();
                    myResponse.getMyResponse(null, finallyError);
                    return;
                }
                    Gson gson = new GsonBuilder().create();
                  //  AbstractResponce mError=new AbstractResponce();
                    try {
                        AbstractResponse mError= gson.fromJson(response.errorBody().string(),AbstractResponse.class);

                        finallyError= mError.getStatusMessage();
                        //Toast.makeText(getApplicationContext(), mError.getErrorDescription(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        // handle failure to read error
                    }
                //end

                if(TextUtils.isEmpty(finallyError))
                {
                BufferedReader reader = null;
                StringBuilder sb = new StringBuilder();
                try {
                    reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                 finallyError = sb.toString();
                 finallyError=  "Response Code :"+response.code()+"\n"+finallyError;
                }
                myResponse.getMyResponse(null, finallyError);
                //LoginError loginError= gson.fromJson(response.errorBody().string(),LoginError.class);
                //showMessage(loginError.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

       /* if (view == null) {
            return (T) findViewById(id);
        }
        return (T) view.findViewById(id);*/
    }

    protected interface MyResponse<T>
    {
        void getMyResponse(T responseBody, String errorMsg);
    }


    protected <G extends Object> void onMyFailure(Throwable e,MyResponse myResponse ) {
        MyApplication.hideTransaprentDialog();
        String errorMessage=null;

        if (e instanceof HttpException) {
            HttpException exception = (HttpException) e;
            Response response = exception.response();

            try {
                JSONObject jObjError = new JSONObject (response.errorBody().string());
                Log.e("Error ","" + jObjError.optString("message"));
                errorMessage=jObjError.optString("message");

                if(TextUtils.isEmpty(errorMessage))
                {
                    BufferedReader reader = null;
                    StringBuilder sb = new StringBuilder();
                    try {
                        reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
                        String line;
                            while ((line = reader.readLine()) != null) {
                                sb.append(line);
                            }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }

                    errorMessage = sb.toString();
                    errorMessage=  "Response Code :"+response.code()+"\n"+errorMessage;
                }

            } catch (JSONException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
        if(myResponse!=null)
        {
            myResponse.getMyResponse(null,errorMessage);
        }


    }





}
