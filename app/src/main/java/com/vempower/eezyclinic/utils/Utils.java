package com.vempower.eezyclinic.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.iid.FirebaseInstanceId;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.MyFirebaseInstanceIDService;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.mappers.FCMRegisterMapper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static com.vempower.eezyclinic.MyFirebaseInstanceIDService.IS_SEND_TO_SERVER;


/**
 * Created by Satishk on 4/10/2017.
 */

public class Utils {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final int BUFFER = 120;

    public static boolean isNetworkAvailable() {

        Context context = MyApplication.getCurrentActivityContext();


        if (context == null) {
            return false;
        }
        return ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo() != null;
    }

    public static boolean isNetworkAvailable(Context context) {

        if (context == null) {
            context = MyApplication.getCurrentActivityContext();

        }

        if (context == null) {
            return false;
        }
        return ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo() != null;
    }

    public static void showToastMsgForNetworkNotAvalable() {
        if (MyApplication.getCurrentActivityContext() == null) {
            return;
        }
        showToastMsg(MyApplication.getCurrentActivityContext(),
                "Network not available");
    }

    public static void showToastMsg(int id) {
        if (id < 0) {
            return;
        }
        String msz = Utils.getStringFromResources(id);
        if (!TextUtils.isEmpty(msz)) {
            showToastMsg(msz);
        }
    }

    public static void openPhoneDialScreen(String phoneNum) {
        if (MyApplication.getCurrentActivityContext() == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNum));
        MyApplication.getCurrentActivityContext().startActivity(intent);
    }

    public static void showToastMsg(String msg) {
        showToastMsg(null, msg);
    }

    public static void showToastMsg(Context context, String msg) {
        if (context == null) {
            context = MyApplication.getCurrentActivityContext();
        }

        if (context == null) {
            return;
        }
        try {
            Log.i(context.getClass().getName(), msg);
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            //Nothing.
        }
    }

    public static boolean isValidEmail(@NonNull String email) {
        Matcher matcher = Pattern.compile(EMAIL_PATTERN).matcher(email);
        return matcher.matches();

    }


    public static String printKeyHash(Activity context) {
        if (context == null) {
            return "";
        }
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    /**
     * This marshmallow is used to check device version is equal or more than
     * jelly bean
     *
     * @return
     */
    public static boolean hasMarshmallow() {
        if (Build.VERSION.SDK_INT >= 23) {// Marshmellow
            return true;
        }
        return false;

    }

    public static boolean hasAbovMarshmallowe() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {// Marshmellow
            return true;
        }
        return false;

    }

    public static String getStringFromResources(int id, Context context) {
        if (context == null) {
            return "";
        }
        if (id <= 0) {
            return "";
        }

        return context.getResources().getString(id);
    }

    public static String getStringFromResources(int id) {
        return getStringFromResources(id, MyApplication.getCurrentActivityContext());
    }

   /* public static String getFireBaseCloudMessageId()
    {
        return SharedPreferenceUtils.getStringValueFromSharedPrefarence(MyFirebaseInstanceIDService.FIRE_BASE_TOKEN_ID,"");
    }*/


    public static String getDeviceId() {
        return Settings.Secure.getString(MyApplication.getCurrentActivityContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }


    /*public static void showSnakeBar(View activityContainerView, String msg) {
        if (activityContainerView == null || TextUtils.isEmpty(msg)) {
            return;
        }
        Snackbar mySnackbar = Snackbar.make(activityContainerView,
                msg,
                Snackbar.LENGTH_LONG);
        View view = mySnackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        mySnackbar.show();

    }*/

    public static void showToastMessage(int id) {
        if (id <= 0) {
            return;
        }
        String msz = Utils.getStringFromResources(id);
        if (!TextUtils.isEmpty(msz)) {
            showToastMessage(msz);
        }
    }


    public static void showToastMessage(String message) {
        if (message == null || MyApplication.getCurrentActivityContext() == null) {
            return;
        }
        Log.i(MyApplication.getCurrentActivityContext().getPackageName(), message);
        Utils.showToastMsg(MyApplication.getCurrentActivityContext(), message);
    }

    public static Date changeDateObjctFormat(String dateString, String dateFormat) {
        String dateStr = dateString;//2016-03-17 06:35 AM
        DateFormat sourcFormat = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        try {
            return sourcFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String changeToDateFormat(Date date, String dest) {
        String dateStr = "";//2016-03-17 06:35 AM
        // DateFormat sourcFormat = new SimpleDateFormat(source);
        DateFormat destFormat = new SimpleDateFormat(dest, Locale.ENGLISH);
        dateStr = destFormat.format(date);
        return dateStr;
    }

   /* public static String changeToDateFormat(String fromDateStr) {
        if(TextUtils.isEmpty(fromDateStr))
        {
            return fromDateStr;
        }
        return   changeToDateFormat(fromDateStr, Constants.SERVER_DATE_FORMAT,Constants.DISPLAY_DATE_FORMAT) ;
    }*/

    public static String changeToDateFormat(String fromDateStr, String fromDateFormat, String destDateFormat) {
        if (TextUtils.isEmpty(fromDateStr)) {
            return fromDateStr;
        }

        String dateStr = fromDateStr;
        DateFormat fromFormat = new SimpleDateFormat(fromDateFormat, Locale.ENGLISH);
        DateFormat destFormat = new SimpleDateFormat(destDateFormat, Locale.ENGLISH);
        try {
            dateStr = destFormat.format(fromFormat.parse(fromDateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    public static Date changeStringToDateFormat(String dateStr, String dateFormat) {
        if (TextUtils.isEmpty(dateStr)) {
            return new Date();
        }

        DateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context == null || permissions == null) {
            return false;
        }
        //if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        // }
        return true;
    }

    public static void makeACall(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber) || MyApplication.getCurrentActivityContext() == null) {
            return;
        }
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.CALL_PHONE};

        if (!Utils.hasPermissions(MyApplication.getCurrentActivityContext(), PERMISSIONS)) {
            ActivityCompat.requestPermissions((Activity) MyApplication.getCurrentActivityContext(), PERMISSIONS, PERMISSION_ALL);
        } else {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApplication.getCurrentActivityContext().startActivity(intent);
        }
    }


    public static void sendMail(String toMailId, String subject, String bodyMessage) {
        if (!isNetworkAvailable(MyApplication.getCurrentActivityContext())) {
            showToastMsg(getStringFromResources(R.string.internet_connection_not_available_msg));
            return;
        }

        Intent i;
       /* if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M)
        {
            i = new Intent(Intent.ACTION_SEND);
        }else
        {*/
        i = new Intent(Intent.ACTION_SENDTO);

        // }

        i.setType("message/rfc822");


        //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setType("plain/text");
        i.setData(Uri.parse("mailto:"));
        //i.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");


        i.putExtra(Intent.EXTRA_EMAIL, new String[]{toMailId});
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, bodyMessage);
        try {
            MyApplication.getCurrentActivityContext().startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
            showToastMessage(getStringFromResources(R.string.email_send_error_msg));
        }
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp) {
        Resources resources = MyApplication.getCurrentActivityContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px) {
        Resources resources = MyApplication.getCurrentActivityContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static String getDurationForDisplay(String secStr) {
        String duration = "";

        int[] ints = splitToComponentTimes(secStr);

        if (ints[0] == 0) {
            duration = ints[1] + ":" + ints[2];
        } else {
            duration = ints[0] + ":" + ints[1] + ":" + ints[2];
        }

        return duration;

    }

    public static String getDurationForDisplay(long mySec) {
        String duration = "";

        int[] ints = splitToComponentTimes(mySec);

        if (ints[0] == 0) {
            duration = ints[1] + ":" + ints[2];
        } else {
            duration = ints[0] + ":" + ints[1] + ":" + ints[2];
        }

        return duration;

    }

    public static int[] splitToComponentTimes(String secStr) {
        long sec = 0;
        if (!TextUtils.isEmpty(secStr)) {
            try {
                sec = Long.parseLong(secStr);
            } catch (Exception e) {

            }

        }
        int hours = (int) sec / 3600;
        int remainder = (int) sec - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;

        int[] ints = {hours, mins, secs};
        return ints;
    }

    public static int[] splitToComponentTimes(long sec) {
        //long sec=0;

        int hours = (int) sec / 3600;
        int remainder = (int) sec - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;

        int[] ints = {hours, mins, secs};
        return ints;
    }

    public static int getScreenHeight() {
        try {

            Display display = ((Activity) MyApplication.getCurrentActivityContext()).getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.y;
        } catch (Exception e) {

        }

        return 0;
        //int width = size.x;
        // int height = size.y;
    }

    public static int getScreenWidth() {
        try {

            Display display = ((Activity) MyApplication.getCurrentActivityContext()).getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.x;
        } catch (Exception e) {

        }

        return 0;
        //int width = size.x;
        // int height = size.y;
    }

   /* public static void shareAudioFile(String audioFilePath)
    {

        if(TextUtils.isEmpty(audioFilePath))
        {
            showToastMessage(Utils.getStringFromResources(R.string.invalid_audio_detailslbl));

            return;
        }

        File file= new File(audioFilePath);



        if(file==null || !file.exists())
        {
            showToastMessage(Utils.getStringFromResources(R.string.audio_file_not_avalable_in_sd_card));

            return;
        }
       *//* String sharePath = Environment.getExternalStorageDirectory().getPath()
                + "/Soundboard/Ringtones/custom_ringtone.ogg";*//*
        Uri uri = Uri.parse("file://"+audioFilePath);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("audio*//*");


        share.putExtra(Intent.EXTRA_STREAM, uri);
        //share.setType("message/rfc822");
        MyApplication.getCurrentActivityContext().startActivity(Intent.createChooser(share, "Share Sound File"));
    }
*/

   /* public static void shareZipFile(String zipFolderPath)
    {

        if(TextUtils.isEmpty(zipFolderPath))
        {
            showToastMessage(Utils.getStringFromResources(R.string.invalid_audio_detailslbl));

            return;
        }

        File file= new File(zipFolderPath);



        if(file==null || !file.exists())
        {
            showToastMessage(Utils.getStringFromResources(R.string.audio_file_not_avalable_in_sd_card));

            return;
        }
       *//* String sharePath = Environment.getExternalStorageDirectory().getPath()
                + "/Soundboard/Ringtones/custom_ringtone.ogg";*//*
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+zipFolderPath));
        sendIntent.setType("application/zip");
        MyApplication.getCurrentActivityContext().startActivity(sendIntent);
        //share.setType("message/rfc822");
        //MyApplication.getCurrentActivityContext().startActivity(Intent.createChooser(share, "Share Sound File"));
    }*/


    public static void downloadAudioFile(Activity activity, int requestCode) {
        Intent intent_upload = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        intent_upload.setType("application/zip");
        intent_upload.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(intent_upload, requestCode);
    }

    /*@Override
    public boolean equals(Object obj) {

        if(obj instanceof Contact)
        {
            Contact con= (Contact) obj;
            if(con.getContact_id()==con.getContact_id())
            {
                return true;
            }
        }
        return super.equals(obj);
    }*/

    public void zip(String[] _files, String zipFileName) {
        try {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(zipFileName);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    dest));
            byte data[] = new byte[BUFFER];

            for (int i = 0; i < _files.length; i++) {
                Log.v("Compress", "Adding: " + _files[i]);
                FileInputStream fi = new FileInputStream(_files[i]);
                origin = new BufferedInputStream(fi, BUFFER);

                ZipEntry entry = new ZipEntry(_files[i].substring(_files[i].lastIndexOf("/") + 1));
                out.putNextEntry(entry);
                int count;

                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unzip(String _zipFile, String _targetLocation) {

        //create target location folder if not exist
        //dirChecker(_targetLocatioan);

        try {
            FileInputStream fin = new FileInputStream(_zipFile);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {

                //create dir if required while unzipping
                if (ze.isDirectory()) {
                    // dirChecker(ze.getName());
                } else {
                    FileOutputStream fout = new FileOutputStream(_targetLocation + ze.getName());
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);
                    }

                    zin.closeEntry();
                    fout.close();
                }

            }
            zin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String getHtmlFormatedText(String str) {
        // myTextView.setText(Html.fromHtml("<h2>Title</h2><br><p>Description here</p>"));
        return null;
    }


   /* public static Date changeDateObjctFormat(String dateString, String dateFormat) {
        String dateStr = dateString;//2016-03-17 06:35 AM
        DateFormat sourcFormat = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        try {
            return sourcFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String changeToDateFormat(Date date, String dest) {
        String dateStr = "";//2016-03-17 06:35 AM
        // DateFormat sourcFormat = new SimpleDateFormat(source);
        DateFormat destFormat = new SimpleDateFormat(dest, Locale.ENGLISH);
        dateStr = destFormat.format(date);
        return dateStr;
    }

    public static String changeToDateFormat(String fromDateStr) {
        if(TextUtils.isEmpty(fromDateStr))
        {
            return fromDateStr;
        }
        return   changeToDateFormat(fromDateStr, Constants.SERVER_DATE_FORMAT,Constants.DISPLAY_DATE_FORMAT) ;
    }

    public static String changeToDateFormat(String fromDateStr, String fromDateFormat,String destDateFormat) {
        if(TextUtils.isEmpty(fromDateStr))
        {
            return fromDateStr;
        }

        String dateStr = fromDateStr;
        DateFormat fromFormat = new SimpleDateFormat(fromDateFormat, Locale.ENGLISH);
        DateFormat destFormat = new SimpleDateFormat(destDateFormat, Locale.ENGLISH);
        try {
            dateStr = destFormat.format(fromFormat.parse(fromDateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }
*/

    public static boolean isTablet() {
        boolean xlarge = ((MyApplication.getCurrentActivityContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((MyApplication.getCurrentActivityContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }


    public static float distanceFrom_in_Km(double lat1, double lng1, double lat2, double lng2) {

        /*if (lat1== null || lng1== null || lat2== null || lng2== null)
        {
            return null;
        }*/

        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        return dist;
    }


    public static String distanceInKms(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return String.format("%.2f", (dist / 0.62137)) + " Kms";
    }

    public static double distanceInKms1(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist / 0.62137);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    public static void displayCountDownTimer(final String expDateStr, final String curDateStr, final String dateFormat, final TextView textView) {

        if (textView == null) {
            return;
        }

  /*      Time conferenceTime = new Time(Time.getCurrentTimezone());
        conferenceTime.set(second, minute, hour, monthDay, month, year);
        conferenceTime.normalize(true);
        long confMillis = conferenceTime.toMillis(true);

        Time nowTime = new Time(Time.getCurrentTimezone());
        nowTime.setToNow();
        nowTime.normalize(true);
        long nowMillis = nowTime.toMillis(true);

        long milliDiff = confMillis - nowMillis;*/

        Date expDate = changeStringToDateFormat(expDateStr, dateFormat);
        Date curDate = changeStringToDateFormat(curDateStr, dateFormat);
        long milliDiff = expDate.getTime() - curDate.getTime();


        new CountDownTimer(milliDiff, 1000) {
            //String displayTime;
            int mDisplayDays = 0;
            int mDisplayHours = 0;
            int mDisplayMinutes = 0;
            int mDisplaySeconds = 0;

            @Override
            public void onTick(long millisUntilFinished) {
                if (textView == null) {
                    return;
                }
                // decompose difference into days, hours, minutes and seconds
                mDisplayDays = (int) ((millisUntilFinished / 1000) / 86400);
                mDisplayHours = (int) (((millisUntilFinished / 1000) - (mDisplayDays * 86400)) / 3600);
                mDisplayMinutes = (int) (((millisUntilFinished / 1000) - ((mDisplayDays * 86400) + (mDisplayHours * 3600))) / 60);
                mDisplaySeconds = (int) ((millisUntilFinished / 1000) % 60);

                //displayTime=mDisplayDays+"d "+mDisplayHours+":"+mDisplayMinutes+":"+mDisplaySeconds;

                textView.setText(mDisplayDays + "d " + mDisplayHours + ":" + mDisplayMinutes + ":" + mDisplaySeconds);


            }

            @Override
            public void onFinish() {

            }


        }.start();
    }


    /*public static String getGCMNotificationRegId(Context context) {
        if (context == null) {
            return "";
        }

        String regId = "";
        try {
            GCMRegistrar.checkDevice(context);
            GCMRegistrar.checkManifest(context);
            regId = GCMRegistrar.getRegistrationId(context);
            if (TextUtils.isEmpty(regId)) {
                GCMRegistrar.register(context,
                        Constants.GOOGLE_PROJECT_SENDER_ID);
            }
        } catch (Exception e) { // TODOAuto-generated catch block
            e.printStackTrace();
        }
        Log.v("GCM ID :", regId);
        return regId;

    }*/

    public static String getFireBaseCloudMessageId() {
        String token = SharedPreferenceUtils.getStringValueFromSharedPrefarence(MyFirebaseInstanceIDService.FIRE_BASE_TOKEN_ID, "");
        if (TextUtils.isEmpty(token)) {
            token = FirebaseInstanceId.getInstance().getToken();
            if (!TextUtils.isEmpty(token)) {
                SharedPreferenceUtils.setStringValueToSharedPrefarence(MyFirebaseInstanceIDService.FIRE_BASE_TOKEN_ID, token);

                return token;
            }
            return "";
        }

        if(!TextUtils.isEmpty(token) &&  !SharedPreferenceUtils.getBooleanValueFromSharedPrefarence(IS_SEND_TO_SERVER,false))
        {
            FCMRegisterMapper mapper= new FCMRegisterMapper(token);
            mapper.setOnFCMRegisterListener(new FCMRegisterMapper.FCMRegisterListener() {
                @Override
                public void register(AbstractResponse response, String errorMessage) {
                    if(response!=null && response.getStatusCode().equalsIgnoreCase("1"))
                    {
                        SharedPreferenceUtils.setBooleanValueToSharedPrefarence(IS_SEND_TO_SERVER,true);

                        Utils.showToastMsg("FCM send to server");
                    }
                }
            });
        }
        return token;
    }

    public static String printKeyHash1(Activity context) {
        if (context == null) {
            return "";
        }
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }


    public static Uri getURIfromURL(String myUrlStr)
    {
        URL url;
        try {
            url = new URL(myUrlStr);
            return Uri.parse( url.toURI().toString() );
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getCapLeterString(String str) {
        if(TextUtils.isEmpty(str) )
        {
            return str;
        }
        if(str.length()==1)
        {
            return str.toUpperCase();
        }

       return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
