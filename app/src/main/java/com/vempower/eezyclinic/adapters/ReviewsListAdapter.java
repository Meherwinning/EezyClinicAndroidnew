package com.vempower.eezyclinic.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vempower.eezyclinic.APICore.DoctorReview;
import com.vempower.eezyclinic.APICore.SearchResultClinicData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Utils;

 ;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by Satishk on 9/7/2017.
 */

public class ReviewsListAdapter extends RecyclerView.Adapter<ReviewsListAdapter.ReviewListHolder> {

    private final List<DoctorReview> doctorReviews;

    private LayoutInflater inflater;


    public ReviewsListAdapter(List<DoctorReview> doctorReviews) {
        this.doctorReviews = doctorReviews;
        inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public ReviewListHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        final View convertView = inflater
                .inflate(R.layout.review_single_item_layout, parent, false);
        return new ReviewListHolder(convertView);
    }


    @Override
    public void onBindViewHolder(ReviewListHolder holder, int position) {


        holder.bindData(doctorReviews.get(position), position);


    }

    @Override
    public int getItemCount() {
        return doctorReviews == null ? 0 : doctorReviews.size();
    }


    public class ReviewListHolder extends RecyclerView.ViewHolder {
        TextView name_tv;
         TextView date_tv, description_tv;
        MaterialRatingBar waiting_time_rating, diagnasis_ratingbar, customer_service_rating_bar;
        ImageView recamended_iv;


        public ReviewListHolder(View itemView) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.name_tv);
            date_tv = itemView.findViewById(R.id.date_tv);
            description_tv = itemView.findViewById(R.id.description_tv);
            waiting_time_rating = itemView.findViewById(R.id.waiting_time_rating);
            diagnasis_ratingbar = itemView.findViewById(R.id.diagnasis_ratingbar);
            customer_service_rating_bar = itemView.findViewById(R.id.customer_service_rating_bar);
            recamended_iv = itemView.findViewById(R.id.recamended_iv);


        }

        public void bindData(final DoctorReview review, final int position) {
            if (review == null) {
                return;
            }

            name_tv.setText(review.getDisplayName());
            date_tv.setText(review.getCreatedDate());
            description_tv.setText(review.getDescription());

            waiting_time_rating.setRating(review.getWaitingTime());
            diagnasis_ratingbar.setRating(review.getDiagnasis());
            customer_service_rating_bar.setRating(review.getCustomerservice());
            int imageId = R.drawable.reccom_uncheck;
            if (review.getRecommend().equalsIgnoreCase("1")) {
                imageId = R.drawable.recomm;
            }
            recamended_iv.setBackground(itemView.getResources().getDrawable(imageId));
        }
    }


}
