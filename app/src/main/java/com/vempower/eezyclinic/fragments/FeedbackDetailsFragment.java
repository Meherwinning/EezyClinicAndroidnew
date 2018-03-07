package com.vempower.eezyclinic.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rey.material.widget.ImageView;
import com.vempower.eezyclinic.APICore.PendingFeedbackData;
import com.vempower.eezyclinic.APICore.SubmitedFeedbackListData;
import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.interfaces.ApiErrorDialogInterface;
import com.vempower.eezyclinic.mappers.SubmitFeedbackMapper;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyCheckBoxRR;
import com.vempower.eezyclinic.views.MyEditTextBlackCursorRR;

 ;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by satish on 6/12/17.
 */

public class FeedbackDetailsFragment extends AbstractFragment {

    private View fragmentView;

    private TextView docotr_name_tv;
    private  TextView specialities_name_tv, clinic_name_tv, date_and_time_tv,feedback_content_tv;
    private MaterialRatingBar waiting_time_rating, diagnasis_ratingbar,
            customer_service_rating_bar;
    private MyCheckBoxRR recommended_doctor_checkbox;
    //private LinearLayout display_name_in_reviews_linear;
    //private AppCompatButton submit_bt;
    //private PendingFeedbackData pendingFeedbackData;
    //private MyEditTextBlackCursorRR write_areview_et;

    String DISPLAY_DATE_TIME = "d MMMM yyyy EEEE h:mm a";
    String SERVER_DATE_FORMAT_NEW = "yyyy-MM-dd HH:mm:ss";//"2017-12-26 16:55:00"
    SimpleDateFormat DISPLAY_DATE_TIME_FORMATTER = new SimpleDateFormat(DISPLAY_DATE_TIME);
    private SubmitFeedbackStatusListener onSubmitFeedbackStatusListener;
    private SubmitedFeedbackListData submitedFeedbackListData;
    private android.widget.ImageView recomended_iv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.feedback_details_layout, container, false);

        myInit();

        return fragmentView;
    }

    private void myInit() {
        docotr_name_tv = getFragemtView().findViewById(R.id.docotr_name_tv);
        specialities_name_tv = getFragemtView().findViewById(R.id.specialities_name_tv);
        clinic_name_tv = getFragemtView().findViewById(R.id.clinic_name_tv);
        date_and_time_tv = getFragemtView().findViewById(R.id.date_and_time_tv);

        feedback_content_tv = getFragemtView().findViewById(R.id.feedback_content_tv);
        waiting_time_rating = getFragemtView().findViewById(R.id.waiting_time_rating);
        diagnasis_ratingbar = getFragemtView().findViewById(R.id.diagnasis_ratingbar);
        customer_service_rating_bar = getFragemtView().findViewById(R.id.customer_service_rating_bar);
        recomended_iv  = getFragemtView().findViewById(R.id.recomended_iv);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setValuesToViews();
    }

    private void setValuesToViews() {
        if (submitedFeedbackListData == null) {
            showAlertDialog("Alert", Utils.getStringFromResources(R.string.invalid_feddback_details_lbl), true);
            return;
        }


        docotr_name_tv.setText(submitedFeedbackListData.getDoctorfullname());
        specialities_name_tv.setText(submitedFeedbackListData.getSpecalities());
        clinic_name_tv.setText(submitedFeedbackListData.getClinicName());
        try {
            Date date = Utils.changeStringToDateFormat(submitedFeedbackListData.getAppointmentDateTime(), SERVER_DATE_FORMAT_NEW);
            String dateStr = DISPLAY_DATE_TIME_FORMATTER.format(date);
            date_and_time_tv.setText(dateStr);
        } catch (Exception e) {
            date_and_time_tv.setText(null);
        }


        feedback_content_tv.setText(submitedFeedbackListData.getDescription());

        waiting_time_rating.setRating(submitedFeedbackListData.getWaitingTime());
        diagnasis_ratingbar.setRating(submitedFeedbackListData.getDiagnasis());
        customer_service_rating_bar.setRating(submitedFeedbackListData.getCustomerservice());

        if(!TextUtils.isEmpty(submitedFeedbackListData.getRecommend()) && submitedFeedbackListData.getRecommend().equalsIgnoreCase("1"))
        {
            recomended_iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.recomm));
        }else
        {
            recomended_iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.reccom_uncheck));
        }




    }

    @Override
    View getFragemtView() {
        return fragmentView;
    }

   /* public void setPendingFeedbackData(PendingFeedbackData pendingFeedbackData) {
        this.pendingFeedbackData = pendingFeedbackData;
    }
*/
    public void setSubmitedFeedbackListData(SubmitedFeedbackListData submitedFeedbackListData) {
        this.submitedFeedbackListData = submitedFeedbackListData;
    }

    public interface  SubmitFeedbackStatusListener{
        void feedbackSubmitStatus(boolean status);
    }

    public void setOnSubmitFeedbackStatusListener(SubmitFeedbackStatusListener onSubmitFeedbackStatusListener) {
        this.onSubmitFeedbackStatusListener = onSubmitFeedbackStatusListener;
    }
}
