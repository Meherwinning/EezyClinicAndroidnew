package com.vempower.eezyclinic.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vempower.eezyclinic.APICore.SearchResultClinicData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyTextViewRM;
import com.vempower.eezyclinic.views.MyTextViewRR;

import java.util.List;

/**
 * Created by Satishk on 9/7/2017.
 */

public class ReviewsListAdapter extends RecyclerView.Adapter<ReviewsListAdapter.ReviewListHolder> {

    private String[] myist;

    private LayoutInflater inflater;


    public ReviewsListAdapter(String[] myist) {
        this.myist = myist;
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


        holder.bindData("", position);


    }

    @Override
    public int getItemCount() {
        return myist == null ? 0 : myist.length;
    }


    /*public void setUpdatedList(List<SearchResultClinicData> newList) {
        if (newList == null || newList.size() == 0) {
            if (this.doctorsList == null) {
                return;
            }
            this.doctorsList.clear();
            notifyDataSetChanged();
            return;
        }
        this.doctorsList = newList;
        notifyDataSetChanged();
    }*/


    public class ReviewListHolder extends RecyclerView.ViewHolder {



        public ReviewListHolder(View itemView) {
            super(itemView);

        }

        public void bindData(final String data, final int position) {

        }
    }


}
