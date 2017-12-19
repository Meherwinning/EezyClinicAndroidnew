package com.vempower.eezyclinic.utils;

import org.jetbrains.annotations.Nullable;

/**
 * Created by Satishk on 8/9/2017.
 */

public interface Constants {
    boolean IS_TESTING=true;




    String BASIC_URL= URL_MANAGE.getURL();
    //String SHARED_PREFERENCES_FILE_NAME = "stashdealcustomerpref";
    String LOGGED_USER_DETAILS_OBJECT = "login_user_obj";
    String SUCCESS_STATUS_CODE = "1";
    String ACCOUNT_NOT_ACTIVATE_STATUS_CODE = "0";
    int PASSWORD_MIN_LENGTH = 6;
    String BANNERS_COUNT_LIMIT = "5";
    String PAGE_ITEMS_COUNT_LIMIT = "10";

    String SERVER_DATE_FORMAT="yyyy-MM-dd";//2017-03-31
    String  DISPLAY_DATE_FORMAT1="MMM d,yyyy";//"dd-MM-yyyy";

    String  SERVER_DATE_FORMAT_NEW="yyyy-MM-dd HH:mm:ss";//"2017-11-22 23:59:59";
   String DISPLAY_DATE_FORMAT="dd MMMM, yyyy";//15 December, 2017
    int MAX_TABS_SIZE = 5;
    @Nullable
    String IS_WATCH_APP_TOUR_KEY="is_watch_app_tour_key";
    String TAWK_CHAT_URL = "https://tawk.to/chat/53fb249a8a6482154c000b0b/default/?$_tawk_popout=true" ;
    String SMS_SENDER_NAME="WAY2SMS";
    //int RESULT_PAGE_ITEMS_LIMIT = 20;
    int RESULT_PAGE_ITEMS_LIMIT1 = Integer.MAX_VALUE;

    //http://cfw.samplespace.com/wp-json/cfw/v1/customer


    interface BasicAuthentication {
        String USERNAME = "developer";
        String PASSWORD = "Yis637G*H34";
    }

     interface Filter {
        String MIN_PRICE_KEY = "min_price_key";
         String MAX_PRICE_KEY = "max_price_key";
         String MIN_DISCOUNT_KEY = "min_discount_key";
         String DISTANCE_KEY = "distance_key";
         String LAT_KEY = "lat_key";
         String LNG_KEY = "lng_key";

         String GEO_DATA_KEY = "geo_data_key";
     }

     interface GenderValues {
        String GENDER = "Gender";
        String MALE = "Male";
        String FEMALE="Female";
    }

    public interface Home {
        int HOME_ACTIVITY = 1;
        int MY_PROFILE=2;
        int MEDICAL_RECORDS=3;
        int SETTINGS=4;
    }

    //http://202.63.103.194:8008/api/patient/signin
    class URL_MANAGE
    {
        public static String getURL()
        {
            if(IS_TESTING)
            {
               // return "http://202.63.103.194:8008";
                return "http://202.63.103.194:8003";
            }else
            {
                return "https://www.stashdeal.com";
            }
        }
    }

    interface Pref
    {
       String USER_VALIDATION_KEY ="validation_key";
       String CATEGORY_TYPE_ID_KEY = "categiry_type_id";
        String CATEGORY_NAME_KEY = "category_name";
        String CATEGORY_IMAGE_PATH_KEY = "category_image_path_key";
        String DEAL_OBJECT_KEY = "deal_object_key";
        String TAB_OBJ_KEY = "tab_obj_key";
        String IS_FROM_SEARCH_RESULT_KEY = "is_from_search_result_key";
        String CART_VALUE_KEY = "cart_value_key";
        String ORDER_OBJ_KEY = "order_obj_key";
        String ORDER_ID_KEY = "order_id_key";
        String IS_FROM_CHECKOUT_PAGE = "is_from_checkout_page";
        String OTP_KEY = "otp_key";
        String PATIENT_ID_KEY = "patient_id_key";
        String USER_ACCOUNT_OBJ_KEY="user_account_obj_key";
        String FORGOT_OTP_KEY="forgot_otp_key";
        String IS_FROM_VIEW_DOCTORS_CLICK_KEY ="is_from_view_doctors_click_key" ;
    }

    interface ActivityResult
    {
        int SCREEN_REFRESH_REQUEST_CODE = 8520;
    }

    public interface OrderStatus {
       String PAYMENT_ACCEPTED = "3";
    }

    interface MediaType
    {
       // media_type {facebook, twitter,  google, linkedin}
        String FACEBOOK_TYPE ="facebook";
        String TWITTER_TYPE ="twitter";
        String GOOGLE_TYPE ="google";
        String LINKEDIN_TYPE ="linkedin";
    }

    interface SocialLoginPref {
        String LOGIN_DETAILS_OBJ_KEY = "login_details_obj_key";
        String FORMID_KEY="formid";
        String SOCIAL_MEDIA_TYPE="social_media_type";
        String SOCIAL_LOGIN_ID_KEY="social_login_id";
    }
}
