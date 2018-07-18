package com.vempower.eezyclinic.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.APIResponce.SignupAPI;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.callbacks.HomeBottomItemClickListener;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.interfaces.AbstractIBinder;
import com.vempower.eezyclinic.interfaces.IntentObjectListener;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;

/**
 * Created by Satish on 11/15/2017.
 */

public class AbstractFragmentActivity extends AbstractActivity  /*implements OTPListener*/ {

   // private InputMethodManager keyBoardHide;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.setCurrentActivityContext(this);
        basicInit();

        //View view= getLayoutInflater().inflate(R.layout.radio_view_item,null);

    }

    private void basicInit() {
       //// OtpReader.bind(this,Constants.SMS_SENDER_NAME);
        FirebaseMessaging.getInstance().subscribeToTopic("news");
       // String refreshedToken = FirebaseInstanceId.getInstance().getToken();
       // Log.v("FCM ID :", "FCM1: " + refreshedToken);

       // String token = FirebaseInstanceId.getInstance().getToken();

        // Log and toast
       //String msg = getString(R.string.msg_token_fmt, token);
        Log.v("FCM ID :", "FCM :"+ Utils.getFireBaseCloudMessageId());
    }

    protected  void callDashboard(){
        Intent  intent= getIntent(); //new Intent(this,HomeActivity.class);
        intent.setClass(this,HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        sendHandlerMessage(getIntent(), ListenerKey.HOME_BOTTOM_ITEMS_SELECT_LISTENER_KEY, getRecordingListTitleBarListener(Constants.Home.HOME_ACTIVITY));

    }


    protected void validateSignupReponse(SignupAPI signupAPI) {
        if(!signupAPI.getStatusCode().equalsIgnoreCase(Constants.SUCCESS_STATUS_CODE) || signupAPI.getData()==null)
        {
            showMyDialog("Alert",signupAPI.getStatusMessage(),false);
            return;
        }
        Intent intent= new Intent(MyApplication.getCurrentActivityContext(),VerifyOTPActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.Pref.OTP_KEY,signupAPI.getOtp());
        intent.putExtra(Constants.Pref.PATIENT_ID_KEY,signupAPI.getData().patientId);

        startActivity(intent);
        finish();
    }

     HomeBottomItemClickListener getRecordingListTitleBarListener(final int flag) {
        return new HomeBottomItemClickListener() {
            public int getItemClicked() {
                return flag;
            }
        };
    }


    /*protected void hideKeyBord(View view ) {
        if (view != null) {
            if (keyBoardHide == null) {
                keyBoardHide = (InputMethodManager) MyApplication.getCurrentActivityContext().getSystemService(INPUT_METHOD_SERVICE);
               // as InputMethodManager
                // keyBoardHide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,
                // 0);
            }
            if (keyBoardHide != null && keyBoardHide.isActive()) {
                // to hide keyboard
                if (view != null) {
                    keyBoardHide.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }
    }*/
   /* @Override
    public void otpReceived(String smsText) {
        //Do whatever you want to do with the text
        //Toast.makeText(this,"Got "+smsText,Toast.LENGTH_LONG).show();
        //Log.d("Otp",smsText);
        showToastMessage(smsText);

    }*/

    protected boolean isValidResponse(AbstractResponse response, String errorMessage/*,boolean isShowDialog,boolean isShowNothing*/)
    {

        return isValidResponse( response,  errorMessage, false,false);
    }

    protected boolean isValidResponse(AbstractResponse response, String errorMessage,boolean isShowDialog,boolean isFinish)
    {
        if(response==null && TextUtils.isEmpty(errorMessage))
        {
            if(isShowDialog)
            {
                showAlertDialog("Alert",  Utils.getStringFromResources(R.string.invalid_service_response_lbl),isFinish);
            }else {
               // Utils.showToastMsg(R.string.invalid_service_response_lbl);
            }
            return false;
        }
        if(response==null && !TextUtils.isEmpty(errorMessage))
        {
            if(isShowDialog) {
                showAlertDialog("Alert", errorMessage,isFinish);
            }else {
                Utils.showToastMsg(errorMessage);
            }
            return false;
        }
        if(!response.getStatusCode().equalsIgnoreCase(Constants.SUCCESS_STATUS_CODE))
        {
            if(isShowDialog) {
                showAlertDialog("Alert",response.getStatusMessage() ,isFinish);
            }else
            {
                Utils.showToastMsg(response.getStatusMessage());
            }
            //showMyAlertDialog("Alert",response.getStatusMessage() ,"Ok",false);

            return false;

        }
        return true;
    }

  /*  protected boolean isValidResponse(AbstractResponse response, String errorMessage)
    {
        if(response==null && TextUtils.isEmpty(errorMessage))
        {
            showMyAlertDialog("Alert",  Utils.getStringFromResources(R.string.invalid_service_response_lbl),"Ok",false);
            return false;
        }
        if(response==null && !TextUtils.isEmpty(errorMessage))
        {
            showMyAlertDialog("Alert", errorMessage,"Ok",false);
            return false;
        }
        if(!response.getStatusCode().equalsIgnoreCase(Constants.SUCCESS_STATUS_CODE))
        {
            showMyAlertDialog("Alert",response.getStatusMessage() ,"Ok",false);
            return false;

        }
        return true;
    }*/

    public void onTermsAndConditionsClick(View view)
    {
       // Intent intent= new Intent(this,TermsAndConditionsActivity.class);
        //startActivity(intent);
        String fileName=/*"chart.html";//*/"EezyClinic_T_C.html";
        webViewDisplayAsDialog(fileName);
    }
    public void onPrivacyPolicyClick(View view)
    {
        // Intent intent= new Intent(this,TermsAndConditionsActivity.class);
        //startActivity(intent);
        String fileName="EezyClinic_Privacy_Policy.html";
        webViewDisplayAsDialog(fileName);
    }

    private void webViewDisplayAsDialog(String fileName) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        DialogFragment newFragment = MyDialogFragment.newInstance(fileName);
        newFragment.show(ft, "dialog");
    }

    public static  class MyDialogFragment extends DialogFragment {

        private static String htmlFileName;
        static MyDialogFragment newInstance(String htmlFileNameInAssets) {
            MyDialogFragment f = new MyDialogFragment();
           htmlFileName=htmlFileNameInAssets;
            return f;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.terms_and_conditions_layout, container, false);
            WebView wv;
            wv =  v.findViewById(R.id.terms_webView);
            //String url="file:///android_asset/EezyClinic_T_C.html";
            String url="file:///android_asset/"+htmlFileName;


            wv.setWebChromeClient(new WebChromeClient());
           // wv.setWebViewClient(new WebChromeClient());
            wv.clearCache(true);
            wv.clearHistory();
            wv.getSettings().setJavaScriptEnabled(true);
            wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);


            wv.loadUrl(url);
            return v;
        }
        @Override
        public void onStart()
        {
            super.onStart();
            Dialog dialog = getDialog();
            if (dialog != null)
            {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setLayout(width, height);
            }
        }

    }

    protected void myStartActivity(Class<? extends Activity> myClass)
    {
        Intent  intent= getIntent(); //new Intent(this,HomeActivity.class);
        intent.setClass(this,myClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

    }


    private Messenger getMessenger(Intent intent, String key) {
        if (intent == null || key == null) {
            return null;
        }
        return (Messenger) intent.getParcelableExtra(key);
    }

    protected void sendHandlerMessage(Intent intent, String key, Object obj) {
        Messenger messenger = getMessenger(intent, key);
        if (messenger != null) {
            Message message = new Message();
            message.obj = obj;
            message.replyTo = messenger;
            try {
                messenger.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected Object getObjectFromIntent(Intent intent,String key)
    {
        if(intent==null || TextUtils.isEmpty(key))
        {
            return null;
        }
            Messenger messenger= intent.getParcelableExtra(key);

            if(messenger==null)
            {
                return null;
            }
                IBinder binder = messenger.getBinder();
                if(!(binder instanceof AbstractIBinder))
                {
                    return null;
                }
                    AbstractIBinder abstractIBinder= (AbstractIBinder) binder;
        if(abstractIBinder==null || !(abstractIBinder.asBinder() instanceof IntentObjectListener))
        {
            return null;
        }
        return ((IntentObjectListener) abstractIBinder.asBinder()).getObject();
       }

}
