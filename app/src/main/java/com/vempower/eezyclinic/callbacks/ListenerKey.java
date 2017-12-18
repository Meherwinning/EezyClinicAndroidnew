package com.vempower.eezyclinic.callbacks;

/**
 * Created by satish on 7/12/17.
 */

public interface ListenerKey {
    String HOME_BOTTOM_ITEMS_SELECT_LISTENER_KEY = "home_bottom_items_select_listener";
    String FILTER_REFRESH_LIST_LISTENER_KEY = "filter_refresh_list_listener_key";


    interface ObjectKey
    {
        String SEARCH_RESULT_DOCTOR_LIST_DATA_KEY="doctor_list_data_key";
        String SEARCH_RESULT_CLINIC_LIST_DATA_KEY="clinic_list_data_key";
    }

}
