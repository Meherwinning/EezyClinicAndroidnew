package com.vempower.eezyclinic.callbacks;

import com.vempower.eezyclinic.APICore.SubmitedFeedbackListData;

/**
 * Created by satish on 7/12/17.
 */

public interface ListenerKey {
    String HOME_BOTTOM_ITEMS_SELECT_LISTENER_KEY = "home_bottom_items_select_listener";
    String FILTER_REFRESH_LIST_LISTENER_KEY = "filter_refresh_list_listener_key";
    String FROM_ACTIVITY_LISTENER_KEY = "from_activity_listener_key";


    interface ObjectKey
    {
        String SEARCH_RESULT_DOCTOR_LIST_DATA_KEY="doctor_list_data_key";
        String SEARCH_RESULT_CLINIC_LIST_DATA_KEY="clinic_list_data_key";
        String UPCOMING_APPOINTMENT_LIST_DATA_KEY="upcoming_appointment_list_data_key";
        //String APPOINTMENT_OBJECT_KEY="appointment_object_key";
        String RESCHEDULE_APPOINTMENT_OBJECT_KEY="ReScheduleAppointment_object";
        String APPOINTMENT_OBJECT_KEY="appointment_object_key";
        String PATIENT_PROFILE_OBJECT_KEY="patient_profile_object_key";

        String IMAGE_DRAWABLE_KEY="image_drawable_key";

        String PDF_DETAILS_OBJECT_KEY = "pdf_details_object_key";
        String FOLLOWUP_APPOINTMENT_OBJECT_KEY="followup_appointment_object_key";

        String MEDICAL_HISTORY_OBJECT_KEY="medical_history_object_key";
        String PENDING_FEEDBACK_DATA_OBJECT_KEY="pending_feedback_data_object_key";
        String SUBMITED_FEEDBACK_DATA_OBJECT_KEY="submited_feedback_data_object_key";
    }

}
