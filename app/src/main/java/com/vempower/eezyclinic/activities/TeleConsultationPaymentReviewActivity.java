package com.vempower.eezyclinic.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.telr.mobile.sdk.activty.WebviewActivity;
import com.telr.mobile.sdk.entity.request.payment.Address;
import com.telr.mobile.sdk.entity.request.payment.App;
import com.telr.mobile.sdk.entity.request.payment.Billing;
import com.telr.mobile.sdk.entity.request.payment.Device;
import com.telr.mobile.sdk.entity.request.payment.MobileRequest;
import com.telr.mobile.sdk.entity.request.payment.Name;
import com.telr.mobile.sdk.entity.request.payment.Tran;
import com.vempower.eezyclinic.APICore.Appointment;
import com.vempower.eezyclinic.APICore.TeleConsultation;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.activities.stripe.StripCheckoutActivityJava;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.application.TelrApplication;
import com.vempower.eezyclinic.callbacks.ListenerKey;
import com.vempower.eezyclinic.fragments.AbstractFragment;
import com.vempower.eezyclinic.fragments.AppointmentHistoryFragment;
import com.vempower.eezyclinic.fragments.TeleConsultationPaymentReviewFragment;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;

import java.math.BigInteger;
import java.util.Random;

public class TeleConsultationPaymentReviewActivity extends AbstractMenuActivity {
    private TeleConsultation teleConsultation;
    public static final String IS_FROM_TELE_CONSULTATION_LIST_KEY = "is_from_tele_consultation_list_key";
    public static final int REQUESTCODE = 6234;
    private String consultation_fee = "300";
    public static final boolean isSecurityEnabled = false;      // Mark false to test on simulator, True to test on actual device and Production
    private boolean show = true;

    @Override
    protected void setMyContectntView() {
        super.setMyContectntView();
        SharedPreferences sharedPref =  getApplicationContext().getSharedPreferences("telr", Context.MODE_PRIVATE);

        myInit();
    }

    protected void onResume() {
        super.onResume();
        MyApplication.setCurrentActivityContext(this);
    }

    private void myInit() {
        Object obj = getObjectFromIntent(getIntent(), ListenerKey.ObjectKey.APPOINTMENT_OBJECT_KEY);

        if (obj != null && obj instanceof TeleConsultation) {
            teleConsultation = (TeleConsultation) obj;

            // showToastMessage("Data :" + data);
        } else {
            showMyAlertDialog("Alert", "Invalid Appointment details.Please try again", "Close", true);
            return;
        }

        if (teleConsultation == null) {
            showMyAlertDialog("Alert", "Invalid Appointment details .Please try again", "Close", true);
            return;

        }

        TeleConsultationPaymentReviewFragment fragment= new TeleConsultationPaymentReviewFragment();
        fragment.setTeleConsultation(teleConsultation);
        setFragment(fragment);

    }

    @Override
    protected AbstractFragment getFragment() {
        return null;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //  callDashboard();
        finish();
    }
    public void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView titleName = toolbar.findViewById(R.id.title_logo_tv);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        titleName.setText("Payment Review");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something you want
                //callDashboard();
                finish();
            }
        });
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);
        //mImageView = findViewById(R.id.image);
        //View  mToolbarView = findViewById(R.id.toolbar);
        //((Toolbar) findViewById(R.id.toolbar)).setTitle(deal.getEntityName());
        getSupportActionBar().setTitle("");
        // mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.app_red)));

        //super.setActionBar(false);
    }
    public void sendMessage(View view){
        //Intent intent = ((Activity) TelrApplication.getContext()).getIntent();
        //intent.setClass(TelrApplication.getContext(), WebviewActivity.class);
        Intent intent = ((Activity)MyApplication.getCurrentActivityContext()).getIntent();
        intent.setClass(MyApplication.getCurrentActivityContext(), WebviewActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //TextView Textview =(TextView)findViewById(R.id.consultation_fee_tv);
        consultation_fee = teleConsultation.getcounsultancyPrice();
        showToastMessage("Data :" + consultation_fee);
        intent.putExtra(WebviewActivity.EXTRA_MESSAGE, getMobileRequest());
        intent.putExtra(WebviewActivity.SUCCESS_ACTIVTY_CLASS_NAME, "com.vempower.eezyclinic.activities.SuccessTransationActivity");
        intent.putExtra(WebviewActivity.FAILED_ACTIVTY_CLASS_NAME, "com.vempower.eezyclinic.activities.FailedTransationActivity");
        intent.putExtra(WebviewActivity.IS_SECURITY_ENABLED, isSecurityEnabled);
        String currency = teleConsultation.getconsultancyFeeCurrency();
        if(currency.equals("AED")) {
            MyApplication.getCurrentActivityContext().startActivity(intent);
            SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
            editor.putString("appointment_id", teleConsultation.getPatenetRandomId());
            editor.putString("consultation_fee", consultation_fee);
            editor.putString("currency", currency);
            editor.apply();
        }else {
            MyApplication.getCurrentActivityContext().startActivity(new Intent(this, StripCheckoutActivityJava.class));
        }
    }
    // This example used for the first payment, or with new card details.
    private MobileRequest getMobileRequest1() {
        MobileRequest mobile = new MobileRequest();
        mobile.setStore(Constants.STORE_ID);                       // Store ID
        mobile.setKey(Constants.KEY);                              // Authentication Key : The Authentication Key will be supplied by Telr as part of the Mobile API setup process after you request that this integration type is enabled for your account. This should not be stored permanently within the App.
        App app = new App();
        app.setId("123456789");                          // Application installation ID
        app.setName("Telr SDK DEMO");                    // Application name
        app.setUser("123456");                           // Application user ID : Your reference for the customer/user that is running the App. This should relate to their account within your systems.
        app.setVersion("0.0.1");                         // Application version
        app.setSdk("123");
        mobile.setApp(app);
        Tran tran = new Tran();
        tran.setTest("1");                              // Test mode : Test mode of zero indicates a live transaction. If this is set to any other value the transaction will be treated as a test.
        tran.setType("auth");                           /* Transaction type
                                                            'auth'   : Seek authorisation from the card issuer for the amount specified. If authorised, the funds will be reserved but will not be debited until such time as a corresponding capture command is made. This is sometimes known as pre-authorisation.
                                                            'sale'   : Immediate purchase request. This has the same effect as would be had by performing an auth transaction followed by a capture transaction for the full amount. No additional capture stage is required.
                                                            'verify' : Confirm that the card details given are valid. No funds are reserved or taken from the card.
                                                        */
        tran.setClazz("paypage");                       // Transaction class only 'paypage' is allowed on mobile, which means 'use the hosted payment page to capture and process the card details'
        tran.setCartid(String.valueOf(new BigInteger(128, new Random()))); //// Transaction cart ID : An example use of the cart ID field would be your own transaction or order reference.
        tran.setDescription("Test Mobile API");         // Transaction description
        tran.setCurrency("AED");                        // Transaction currency : Currency must be sent as a 3 character ISO code. A list of currency codes can be found at the end of this document. For voids or refunds, this must match the currency of the original transaction.
        tran.setAmount(consultation_fee);                         // Transaction amount : The transaction amount must be sent in major units, for example 9 dollars 50 cents must be sent as 9.50 not 950. There must be no currency symbol, and no thousands separators. Thedecimal part must be separated using a dot.
        //tran.setRef(???);                           // (Optinal) Previous transaction reference : The previous transaction reference is required for any continuous authority transaction. It must contain the reference that was supplied in the response for the original transaction.
        tran.setLangauge("en");                        // (Optinal) default is en -> English
        mobile.setTran(tran);
        Billing billing = new Billing();
        Address address = new Address();
        address.setCity("Dubai");                       // City : the minimum required details for a transaction to be processed
        address.setCountry("AE");                       // Country : Country must be sent as a 2 character ISO code. A list of country codes can be found at the end of this document. the minimum required details for a transaction to be processed
        address.setRegion("Dubai");                     // Region
        address.setLine1("SIT G=Towe");                 // Street address – line 1: the minimum required details for a transaction to be processed
        //address.setLine2("SIT G=Towe");               // (Optinal)
        //address.setLine3("SIT G=Towe");               // (Optinal)
        //address.setZip("SIT G=Towe");                 // (Optinal)
        billing.setAddress(address);
        Name name = new Name();
        name.setFirst("Hany");                          // Forename : the minimum required details for a transaction to be processed
        name.setLast("Sakr");                          // Surname : the minimum required details for a transaction to be processed
        name.setTitle("Mr");                           // Title
        billing.setName(name);
        billing.setEmail(Constants.EMAIL);                 // TODO: Insert your email here : the minimum required details for a transaction to be processed.
        billing.setPhone("588519952");                // Phone number, required if enabled in your merchant dashboard.
        mobile.setBilling(billing);
        return mobile;

    }


    // This example used for the first payment, or with new card details.
    private MobileRequest getMobileRequest() {
        MobileRequest mobile = new MobileRequest();
        mobile.setStore(Constants.STORE_ID);                       // Store ID
        mobile.setKey(Constants.KEY);                              // Authentication Key : The Authentication Key will be supplied by Telr as part of the Mobile API setup process after you request that this integration type is enabled for your account. This should not be stored permanently within the App.

        /*Device device=new Device();
        device.setType("android");
        device.setId(Utils.getDeviceId());
        mobile.setDevice(device);*/


        App app = new App();
        app.setId("123456789");                          // Application installation ID
        app.setName("Telr SDK DEMO");                    // Application name
        app.setUser("123456");                           // Application user ID : Your reference for the customer/user that is running the App. This should relate to their account within your systems.
        app.setVersion("0.0.1");                         // Application version
        app.setSdk("123");
        mobile.setApp(app);
        Tran tran = new Tran();
        tran.setTest("1");                              // Test mode : Test mode of zero indicates a live transaction. If this is set to any other value the transaction will be treated as a test.
        tran.setType("auth");                           /* Transaction type
                                                            'auth'   : Seek authorisation from the card issuer for the amount specified. If authorised, the funds will be reserved but will not be debited until such time as a corresponding capture command is made. This is sometimes known as pre-authorisation.
                                                            'sale'   : Immediate purchase request. This has the same effect as would be had by performing an auth transaction followed by a capture transaction for the full amount. No additional capture stage is required.
                                                            'verify' : Confirm that the card details given are valid. No funds are reserved or taken from the card.
                                                        */
       // tran.setType("sale");
        tran.setClazz("paypage");                       // Transaction class only 'paypage' is allowed on mobile, which means 'use the hosted payment page to capture and process the card details'
        tran.setCartid(String.valueOf(new BigInteger(128, new Random()))); //// Transaction cart ID : An example use of the cart ID field would be your own transaction or order reference.
        tran.setDescription(teleConsultation.getPatenetRandomId());         // Transaction description
        tran.setCurrency("AED");                        // Transaction currency : Currency must be sent as a 3 character ISO code. A list of currency codes can be found at the end of this document. For voids or refunds, this must match the currency of the original transaction.
        tran.setAmount(consultation_fee);                         // Transaction amount : The transaction amount must be sent in major units, for example 9 dollars 50 cents must be sent as 9.50 not 950. There must be no currency symbol, and no thousands separators. Thedecimal part must be separated using a dot.
        tran.setVersion("1.2");

        //tran.setRef(???);                           // (Optinal) Previous transaction reference : The previous transaction reference is required for any continuous authority transaction. It must contain the reference that was supplied in the response for the original transaction.
        tran.setLangauge("en");                        // (Optinal) default is en -> English
        mobile.setTran(tran);
        Billing billing = new Billing();
        Address address = new Address();
        address.setCity(teleConsultation.getCity());                       // City : the minimum required details for a transaction to be processed
        address.setCountry(teleConsultation.getCountry());                       // Country : Country must be sent as a 2 character ISO code. A list of country codes can be found at the end of this document. the minimum required details for a transaction to be processed
        address.setRegion("");                     // Region
        address.setLine1(teleConsultation.getAddress());                 // Street address – line 1: the minimum required details for a transaction to be processed
        //address.setLine2("SIT G=Towe");               // (Optinal)
        //address.setLine3("SIT G=Towe");               // (Optinal)
        //address.setZip("SIT G=Towe");                 // (Optinal)
        billing.setAddress(address);
        Name name = new Name();
        name.setFirst(teleConsultation.getPatientName());                          // Forename : the minimum required details for a transaction to be processed
        name.setLast("");                          // Surname : the minimum required details for a transaction to be processed
        name.setTitle("Mr");                           // Title
        billing.setName(name);
        billing.setEmail(teleConsultation.getpatentEmail());    // TODO: Insert your email here : the minimum required details for a transaction to be processed.
        billing.setPhone(teleConsultation.getMobile());                // Phone number, required if enabled in your merchant dashboard.
        mobile.setBilling(billing);
        return mobile;

    }
}
