package com.vempower.eezyclinic.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vempower.eezyclinic.APIResponce.SpecalitiyData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyCheckedTextViewRR;
import com.vempower.eezyclinic.views.MyTextViewRR;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Satishk on 9/7/2017.
 */

public class SpecialityListAdapter extends RecyclerView.Adapter<SpecialityListAdapter.OrdersListHolder> {

    private List<SpecalitiyData> dataList;

    private LayoutInflater inflater;
    //private String diabledDateAndtime;
    private DiasableDateListener diasableDateListener;


    public SpecialityListAdapter(List<SpecalitiyData> dataList) {
        inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dataList=dataList;
    }



    @Override
    public OrdersListHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        final View convertView = inflater
                .inflate(R.layout.speciality_single_layout, parent, false);
        return new OrdersListHolder(convertView);
    }


    @Override
    public void onBindViewHolder(OrdersListHolder holder, int position) {
        holder.bindData(dataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }


    public void setUpdatedList(List<SpecalitiyData> newList) {
        if (newList == null || newList.size() == 0) {
            if (this.dataList == null) {
                return;
            }
            this.dataList.clear();
            notifyDataSetChanged();

            return;
        }
        this.dataList = newList;
        notifyDataSetChanged();
    }



    public class OrdersListHolder extends RecyclerView.ViewHolder {

        private MyTextViewRR item_name_tv;

        public OrdersListHolder(View itemView) {
            super(itemView);
            item_name_tv = itemView.findViewById(R.id.item_name_tv);

        }

        public void bindData(final SpecalitiyData data, final int position) {
            if (data == null) {
                return;
            }

            item_name_tv.setText(data.getName());





        }
    }

    public void setOnDiasableDateListener(DiasableDateListener diasableDateListener)
    {
        this.diasableDateListener=diasableDateListener;
    }

    public interface  DiasableDateListener
    {
        String getDisableDateTime();
    }



}
