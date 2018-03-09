package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.PendingFeedbackData;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.SubmitFeedbackMapper;
import com.vempower.eezyclinic.utils.Utils;
import android.widget.CheckBox;
import com.vempower.eezyclinic.views.MyEditTextBlackCursor;

 ;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by satish on 6/12/17.
 */

public class SubmitFeedbackFragment extends AbstractFragment {

    private View fragmentView;

    private TextView docotr_name_tv;
    private TextView specialities_name_tv, clinic_name_tv, date_and_time_tv;
    private MaterialRatingBar waiting_time_rating, diagnasis_ratingbar,
            customer_service_rating_bar;
    private CheckBox recommended_doctor_checkbox;
    //private LinearLayout display_name_in_reviews_linear;
    private com.rey.material.widget.Switch display_name_switch;
    private AppCompatButton submit_bt;
    private PendingFeedbackData pendingFeedbackData;
    private MyEditTextBlackCursor write_areview_et;

    String DISPLAY_DATE_TIME = "d MMMM yyyy EEEE h:mm a";
    String SERVER_DATE_FORMAT_NEW = "yyyy-MM-dd HH:mm:ss";//"2017-12-26 16:55:00"
    SimpleDateFormat DISPLAY_DATE_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_DATE_TIME);
    private SubmitFeedbackStatusListener onSubmitFeedbackStatusListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.feedback_submmit_layout, container, false);

        myInit();

        return fragmentView;
    }

    private void myInit() {
        docotr_name_tv = getFragemtView().findViewById(R.id.docotr_name_tv);
        specialities_name_tv = getFragemtView().findViewById(R.id.specialities_name_tv);
        clinic_name_tv = getFragemtView().findViewById(R.id.clinic_name_tv);
        date_and_time_tv = getFragemtView().findViewById(R.id.date_and_time_tv);

        waiting_time_rating = getFragemtView().findViewById(R.id.waiting_time_rating);
        diagnasis_ratingbar = getFragemtView().findViewById(R.id.diagnasis_ratingbar);
        customer_service_rating_bar = getFragemtView().findViewById(R.id.customer_service_rating_bar);
        recommended_doctor_checkbox = getFragemtView().findViewById(R.id.recommended_doctor_checkbox);
        display_name_switch = getFragemtView().findViewById(R.id.display_name_switch);
        write_areview_et = getFragemtView().findViewById(R.id.write_areview_et);

        submit_bt = getFragemtView().findViewById(R.id.submit_bt);
        submit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitButtonClick();
            }
        });
    }

    private void onSubmitButtonClick() {
        if (pendingFeedbackData == null) {
            showAlertDialog("Alert", Utils.getStringFromResources(R.string.invalid_feddback_details_lbl), true);
            return;
        }

        int waiting_rating = (int) waiting_time_rating.getRating();
        int diagnasis_rating = (int) diagnasis_ratingbar.getRating();
        int customer_service_rating = (int) customer_service_rating_bar.getRating();
        int recomended_doctor = recommended_doctor_checkbox.isChecked() ? 1 : 0;
        int display_name = display_name_switch.isChecked() ? 1 : 0;

        /*
        int appointmentId,int doctorId,int clinicId,
                                String description,int waitingTime,int diagnasis,
                                int customerservice,int recommend,int displayName
         */
        SubmitFeedbackMapper mapper = new SubmitFeedbackMapper(Integer.parseInt(pendingFeedbackData.getAppointmentid()),
                Integer.parseInt(pendingFeedbackData.getDoctorId()), Integer.parseInt(pendingFeedbackData.getClinicId()),
                write_areview_et.getText().toString(), waiting_rating, diagnasis_rating,
                customer_service_rating, recomended_doctor, display_name
        );


        mapper.setOnSubmitFeedbackListener(new SubmitFeedbackMapper.SubmitFeedbackListener() {
            @Override
            public void submitFeedback(AbstractResponse abstractResponse, String errorMessage) {
                if (!isValidResponse(abstractResponse, errorMessage, true, false)) {
                    return;
                }
                showMyDialog("Success", abstractResponse.getStatusMessage(), "Ok", new ApiErrorDialogInterface() {
                    @Override
                    public void onCloseClick() {

                    }

                    @Override
                    public void retryClick() {
                        if(onSubmitFeedbackStatusListener!=null)
                        {
                            onSubmitFeedbackStatusListener.feedbackSubmitStatus(true);
                        }

                    }
                });


            }
        });


        //hhkghg

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setValuesToViews();
    }

    private void setValuesToViews() {
        if (pendingFeedbackData == null) {
            showAlertDialog("Alert", Utils.getStringFromResources(R.string.invalid_feddback_details_lbl), true);
            return;
        }


        docotr_name_tv.setText(pendingFeedbackData.getDoctorfullname());
        specialities_name_tv.setText(pendingFeedbackData.getSpecalities());
        clinic_name_tv.setText(pendingFeedbackData.getClinicName());
        try {
            Date date = Utils.changeStringToDateFormat(pendingFeedbackData.getAppointmentDateTime(), SERVER_DATE_FORMAT_NEW);
            String dateStr = DISPLAY_DATE_TIME_FORMATTER.format(date);
            date_and_time_tv.setText(dateStr);
        } catch (Exception e) {
            date_and_time_tv.setText(null);
        }


    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }

    public void setPendingFeedbackData(PendingFeedbackData pendingFeedbackData) {
        this.pendingFeedbackData = pendingFeedbackData;
    }

    public interface  SubmitFeedbackStatusListener{
        void feedbackSubmitStatus(boolean status);
    }

    public void setOnSubmitFeedbackStatusListener(SubmitFeedbackStatusListener onSubmitFeedbackStatusListener) {
        this.onSubmitFeedbackStatusListener = onSubmitFeedbackStatusListener;
    }
}
