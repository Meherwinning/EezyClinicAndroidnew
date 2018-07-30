package com.vempower.eezyclinic.application;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.widget.ImageView;

import com.google.gson.Gson;
//import com.rey.material.app.ThemeManager;
import com.rey.material.app.ThemeManager;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.squareup.picasso.Picasso;
import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.APICore.PatientData;
import com.vempower.eezyclinic.APICore.SearchResultDoctorListData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.core.SearchRequest;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.SharedPreferenceUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;


/**
 * Created by Satishk on 8/9/2017.
 */

public class MyApplication extends MultiDexApplication {
    private static MyApplication myApplication;
    private static ProgressDialog pd;
    private static Context mContext;
    private EezyClinicAPI eezyClinicAPI;
    private static SearchRequest searchRequestParms;
    private static SearchResultDoctorListData searchResultDoctorListData;

    // private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        myApplication=this;
        mContext=this;
        /*if(BuildConfig.DEBUG)
            refWatcher = LeakCanary.install(this);*/
        //ThemeManager.init(this, 2, 0, null);
        ThemeManager.init(this, 2, 0, null);


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static MyApplication getInstance()
    {
        return myApplication;
    }

    public static Context getCurrentActivityContext() {
        return mContext;
    }

    public static void setCurrentActivityContext(Context mContext) {
        MyApplication.mContext = mContext;

    }

    public EezyClinicAPI getEezyClinicAPI()
    {
        if(eezyClinicAPI==null)
        {
            eezyClinicAPI=getAPI();
        }
        return eezyClinicAPI;
    }

    public static void showTransparentDialog(String message, String title) {//Transparent
       showTransparentDialog( message,  title,false);

        }

        public static void showTransparentDialog(String message, String title,boolean isShaow) {//Transparent
        try {
            if(!isShaow)
            {
                showTransparentDialogOnly();
                return;
            }
            // if (pd != null) {
            hideTransaprentDialog();
            pd = new ProgressDialog((Activity)getCurrentActivityContext());
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setMessage(message);
            pd.setTitle(title);
           // pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            pd.show();
            // } else {
            // pd.dismiss();
            // pd.show();
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showTransparentDialogOnly() {//Transparent
        try {

            // if (pd != null) {
            hideTransaprentDialog();
            pd = new ProgressDialog(getCurrentActivityContext(), R.style.MyTheme);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            // pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            pd.show();
            // } else {
            // pd.dismiss();
            // pd.show();
            // }
        } catch (Exception e) {
            //e.printStackTrace();
        }

    }

    public static void showTransparentDialog() {
        showTransparentDialog("Fetching data..", "Loading...");
    }
    public static void showTransparentDialog(boolean isShow) {
        showTransparentDialog("Fetching data..", "Loading...",isShow);
    }


    public static void showTransparentDialog(String title) {
        showTransparentDialog("Fetching data..", title);
    }

    public static void hideTransaprentDialog() {

        try {
            if (pd != null && pd.isShowing() ) {
                pd.dismiss();
            }
            pd=null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void setBitmapToImageview(int dwawableId, ImageView imageView) {

        // Uri  uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+getCurrentActivityContext().getPackageName()+"/drawable/" + drawableName;
        Picasso.with(this).load(dwawableId).into(imageView);

    }


    public void setBitmapToImageview(int defaultImageId, ImageView imageView,
                                     String imageUrl) {
        if (TextUtils.isEmpty(imageUrl)) {
            Picasso.with(this).load(defaultImageId).placeholder(defaultImageId)
                    .into(imageView);

            return;
        }

        Picasso.with(this).load(imageUrl).placeholder(defaultImageId)
                .into(imageView);

    }

    public void setBitmapToImageview(int defaultImageId, ImageView imageView,
                                     File file) {
        if (file == null || !file.exists()) {
            Picasso.with(this).load(defaultImageId).placeholder(defaultImageId)
                    .into(imageView);

            return;
        }

        Picasso.with(this).load(file).placeholder(defaultImageId)
                .into(imageView);

    }
    //540x260     270x130
    public void setBitmapToImageview1(int defaultImageId, ImageView imageView,
                                     File file) {
        if (file == null || !file.exists()) {
            Picasso.with(this).load(defaultImageId).placeholder(defaultImageId)
                    .into(imageView);

            return;
        }

        Picasso.with(this).load(file).resize(270,130).placeholder(defaultImageId)
                .into(imageView);

    }
    public void setBitmapToImageviewCircular(int defaultImageId, ImageView imageView,
                                     String imageUrl) {
        if (TextUtils.isEmpty(imageUrl)) {
            Picasso.with(this).load(defaultImageId).placeholder(defaultImageId)
                    .into(imageView);

            return;
        }

        Picasso.with(this).load(imageUrl).transform(new CircleTransform()).placeholder(defaultImageId).into(imageView);

    }

    public void setBitmapToImageviewCircular(int defaultImageId, ImageView imageView,
                                     File file) {
        if (file == null || !file.exists()) {
            Picasso.with(this).load(defaultImageId).placeholder(defaultImageId)
                    .into(imageView);

            return;
        }

     /*   Picasso.with(this).load(file).placeholder(defaultImageId)
                .into(imageView);*/

        Picasso.with(this).load(file).transform(new CircleTransform()).placeholder(defaultImageId).into(imageView);


    }


    public void setBitmapToImageview(ImageView imageView,
                                     int imageResourceId) {
        Picasso.with(this).load(imageResourceId).into(imageView);

    }

    public void setBitmapToImageviewBG(final ImageView imageView,
                                       final int imageResourceId) {
        if (true) {
            Picasso.with(this).load(imageResourceId).into(imageView);
            return;
        }
       /* Picasso.with(this).load(imageResourceId).into(new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                imageView.setBackground(new BitmapDrawable(MyApplication.getCurrentActivityContext().getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(final Drawable errorDrawable) {
                Log.d("TAG", "FAILED");
            }

            @Override
            public void onPrepareLoad(final Drawable placeHolderDrawable) {
                Log.d("TAG", "Prepare Load");
            }
        });*/

    }


    private EezyClinicAPI getAPI() {
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(80 * 1000, TimeUnit.MILLISECONDS);


        // OkHttpClient client = new OkHttpClient();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

//        client.interceptors().add(new HeaderInterceptor());
        client.interceptors().add(interceptor);

        //client.interceptors().add(new BasicAuthInterceptor(Constants.BasicAuthentication.USERNAME, Constants.BasicAuthentication.PASSWORD));
        //TODO for basic authentication remove above comment line

        /*client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                com.squareup.okhttp.Request request = chain.request();
                if (!TextUtils.isEmpty(MyApplication.getInstance().getUserObject().getAuthenticationToken()))
                    request = request.newBuilder()
                            .addHeader("AuthenticationToken", "" + MyApplication.getInstance().getUserObject().getAuthenticationToken())
                            .build();
                Response response = chain.proceed(request);
                return response;
            }
        });*/

       /* client.setAuthenticator(new Authenticator() {
            @Override
            public Request authenticate(java.net.Proxy proxy, Response response) throws IOException {
                String credential = Credentials.basic("developer", "admin123");
                return response.request().newBuilder().header("Authorization", credential).build();

            }

            @Override
            public Request authenticateProxy(java.net.Proxy proxy, Response response) throws IOException {
                return null;
            }


        });*/





        //to be added based on requirement
//        client.interceptors().add(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                Request.Builder requestBuilder = request.newBuilder();
//
//                String postBodyString = bodyToString(request.body());
//                request = requestBuilder
//                        .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString))
//                        .build();
//                return chain.proceed(request);
//            }
//        });
//        client.interceptors().add(new Interceptor() {
//            @Override
//            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                String parameter = "&" + Constants.WS_API_KEY_PARAMETER + "=" + "c12eff9a73dfff58ee4a7e201a3697b4";
//                Request newRequest = interceptRequest(request, parameter);
//                return chain.proceed(newRequest);
//            }
//        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASIC_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

                .build();

        return retrofit.create(EezyClinicAPI.class);

    }


    public void setLoggedUserDetailsToSharedPref(PatientData details) {

        if (details == null) {
            SharedPreferenceUtils.setStringValueToSharedPrefarence(
                    Constants.LOGGED_USER_DETAILS_OBJECT, null);
            return;
        }

        Gson gson = new Gson();
        String json = gson.toJson(details);
        SharedPreferenceUtils.setStringValueToSharedPrefarence(
                Constants.LOGGED_USER_DETAILS_OBJECT, json);
    }

    public PatientData getLoggedUserDetailsFromSharedPref() {
       // MyApplication.showTransparentDialog();
        Gson gson = new Gson();
        String json = SharedPreferenceUtils.getStringValueFromSharedPrefarence(
                Constants.LOGGED_USER_DETAILS_OBJECT, "");
        PatientData obj = gson.fromJson(json, PatientData.class);
        //MyApplication.hideTransaprentDialog();
        return obj;
    }


    public int getCartValue() {
       return SharedPreferenceUtils.getIntValueToSharedPrefarence(Constants.Pref.CART_VALUE_KEY,0);
    }

    public void setCartValue(int cartValue) {
        SharedPreferenceUtils.setIntValueToSharedPrefarence(Constants.Pref.CART_VALUE_KEY,cartValue);
    }

    public void setSearchRequestParms(SearchRequest searchRequestParms) {
        this.searchRequestParms = searchRequestParms;
    }

    public SearchRequest getSearchRequestParms() {
        return searchRequestParms;
    }

    public void setSearchResultDoctorListData(SearchResultDoctorListData searchResultDoctorListData) {
        this.searchResultDoctorListData = searchResultDoctorListData;
    }

    public SearchResultDoctorListData getSearchResultDoctorListData() {
        return searchResultDoctorListData;
    }
    /*
//for DMPlayer
public  void initImageLoader(Context context) {
    // This configuration tuning is custom. You can tune every option, you may tune some of them,
    // or you can create default configuration by
    //  ImageLoaderConfiguration.createDefault(this);
    // method.
    ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
    config.threadPriority(Thread.NORM_PRIORITY - 2);
    config.denyCacheImageMultipleSizesInMemory();
    config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
    config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
    config.tasksProcessingOrder(QueueProcessingType.LIFO);
    config.writeDebugLogs(); // Remove for release app

    // Initialize ImageLoader with configuration.
    ImageLoader.getInstance().init(config.build());
}*/

 /*   public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }*/




}
