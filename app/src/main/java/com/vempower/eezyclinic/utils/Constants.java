package com.vempower.eezyclinic.utils;

import com.vempower.eezyclinic.API.EezyClinicAPI;
import com.vempower.eezyclinic.R;

import org.jetbrains.annotations.Nullable;

/**
 * Created by Satishk on 8/9/2017.
 */

public interface Constants {
    boolean IS_TESTING=true;

    String BASE_URL_SUB="/eezyclinic-meher/";//  /eezyclinic-git/




    String BASIC_URL= URL_MANAGE.getURL();
    String GRAPH_BASE_URL=Constants.BASIC_URL +BASE_URL_SUB+ "patient/healthcheckreports?access_key=";
    ///betav/api/
    String MORE_BUTTON=Constants.BASIC_URL +BASE_URL_SUB+ "uploads/icons/more.png";
    String LESS_BUTTON=Constants.BASIC_URL +BASE_URL_SUB+ "uploads/icons/less.png";
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
    String REQUEST_DATE_FORMAT="yyyy-MM-dd";//2018-08-24
    int MAX_TABS_SIZE = 5;
    @Nullable
    String IS_WATCH_APP_TOUR_KEY="is_watch_app_tour_key";
    String TAWK_CHAT_URL = "https://tawk.to/chat/53fb249a8a6482154c000b0b/default/?$_tawk_popout=true" ;
    String SMS_SENDER_NAME="WAY2SMS";
    int RESULT_PAGE_ITEMS_LIMIT = 1000;
    int RESULT_PAGE_ITEMS_LIMIT1 = Integer.MAX_VALUE;
    String RECORD_FOLDER_NAME=Utils.getStringFromResources(R.string.directory_name);
    String APP_DIRECTORY_NAME = "Eezyclinic";
    String FCM_DEVICE_TYPE = "android";
    String STORE_ID = "23685";
    String KEY = "ttnpf^f6Dx2#84T5";
    String EMAIL = "meher@yopmail.com";

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
        String GENDER = "Select Gender";
        String MALE = "Male";
        String FEMALE="Female";
    }

     interface MaritalStatusValues {
        String MARITAL_STATUS = "Marital Status";
        String MARRIED = "Married";
        String SINGLE="Single";
    }
    interface BloodGroupValues {
        String O_NEGE = "O-";
        String O_POSI = "O+";
        String A_NEGE = "A-";
        String A_POSI = "A+";
        String B_NEGE = "B-";
        String B_POSI = "B+";
        String AB_NEGE= "AB-";
        String AB_POSI= "AB+";
        String BLOOD_GROUP="Select Blood Group";
    }

    public interface Home {
        int NEW_HOME_ACTIVITY = 0;
        int HOME_ACTIVITY = 1;
        int HEALTH_CHECKS=2;
        int MEDICAL_RECORDS=3;
        //int SETTINGS=4;

        int MY_PROFILE=4;

    }

    public interface ImagePic {
        int FROM_PROFILE = 125;
        int FROM_ID_FRONT = 734;
        int FROM_ID_BACK = 356;
        int FROM_INSURANCE_FRONT = 324;
        int FROM_INSURANCE_BACK = 426;


        int FROM_ADD_NEW_IMAGE = 125;


        int SECONDARY_FROM_INSURANCE_FRONT = 824;
        int SECONDARY_FROM_INSURANCE_BACK = 826;
    }

    //http://202.63.103.194:8008/api/patient/signin
    class URL_MANAGE
    {
        public static String getURL()
        {
            if(IS_TESTING)
            {
               // return "http://202.63.103.194:8008";
                //return "http://202.63.103.194:8003";
                //https://www.eezyclinic.com/betav
               // return "https://dev.v-empower.com:81";
                return "https://dev.v-empower.com:81";
            }else
            {
                return "https://www.eezyclinic.com";
                //return "https://www.stashdeal.com";
            }
        }
    }


    class DATA_MANAGE
    {
        public static String getURL()
        {
            if(IS_TESTING)
            {
                // return "http://202.63.103.194:8008";
                //return "http://202.63.103.194:8003";
                //https://www.eezyclinic.com/betav
                // return "https://dev.v-empower.com:81";
                //return "/eezyclinic-git/api/";
                return "/eezyclinic-meher/api/";
            }else
            {
                return "/betav/api/";//"https://www.eezyclinic.com";
                //return "https://www.stashdeal.com";
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
        String TITLE_BAR_NAME_KEY = "title_bar_name_key";
        String IS_FROM_DASH_BOARD = "is_from_dash_board";
        String SELECTED_SCHDULE_DATE_TIME_KEY = "selected_schdule_date_time_key";
        String IS_FROM_RE_SCHUDULE_KEY="is_from_re_schudule_key";
        String APPOINTMENT_ID_KEY = "appointment_id_key";
        String TELECOUNSULTATION_ID_KEY = "telecounsulation_id_key";
        String CASESHEET_APPOINTMENT_ID_KEY = "casesheet_appointment_id_key";
        String IS_FROM_FOLLOWUPS = "is_from_followups";
        String IS_FROM_ADD_PRESCRIPTION_KEY ="is_from_add_prescription_key" ;
        String IS_FROM_UPDATE_PRESCRIPTION_KEY ="is_from_update_prescription_key" ;
        String GRAPH_URL_STR = "graph_url_str";
        String GRAPH_TYPE = "graph_type";
        String IS_FROM_NEW_HOME_SPESHALITY = "is_from_new_home_speshality";
        String DIPLAY_SPESHALITY_NAME = "diplay_speshality_name";
        String IMAGE_VIEW_URL_KEY = "image_view_url_key";
        String COUNTRY_CODE_KEY = "country_code_key";
        String IS_CALL_FEATURS_AND_BENIFITS = "is_call_featurs_and_benifits";
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

    interface RangeBarValues {
        float MIN_VALUE = 100;
        float MAX_VALUE = 10000;
    }

    interface DefaultImage {
        String MALE_URL="https://res.cloudinary.com/eezyclinic/image/upload/f_auto,h_98,q_auto,w_98/v1/images/Dr-Male-min.png";
        String FEMALE_URL="https://res.cloudinary.com/eezyclinic/image/upload/f_auto,h_98,q_auto,w_98/v1/images/Dr-Female-min.png";
        String UNISEX_URL="https://res.cloudinary.com/eezyclinic/image/upload/f_auto,h_98,q_auto,w_98/v1/images/Dr-Unisex-min.png";
        String CLINIC_URL="https://res.cloudinary.com/eezyclinic/image/upload/f_auto,q_auto/v1/images/servicesp3.jpg";
        String VIDEO_URL="https://dev.v-empower.com:81/eezyclinic-meher/assets/images/video-camera_3.png";
    }
}
