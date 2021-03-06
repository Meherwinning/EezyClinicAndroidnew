package com.vempower.eezyclinic.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vempower.eezyclinic.APIResponce.SpecalitiyData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.utils.Utils;
import com.vempower.eezyclinic.views.MyCheckedTextViewRR;
 ;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Satishk on 9/7/2017.
 */

public class SpecialityListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SpecalitiyData> dataList;

    private LayoutInflater inflater;
    //private String diabledDateAndtime;
    private DiasableDateListener diasableDateListener;


    public SpecialityListAdapter(List<SpecalitiyData> dataList) {
        inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        SpecalitiyData header =  new SpecalitiyData();
        header.setDataType(SpecalitiyData.ViewType.HEADER_TYPE);
        this.dataList = new ArrayList<>();
        this. dataList.add(header);
        if( dataList!=null)
        {
            this.dataList.addAll(dataList);
        }


    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType== SpecalitiyData.ViewType.HEADER_TYPE)
        {
            final View convertView = inflater
                    .inflate(R.layout.new_home_header, parent, false);
            return new HeaderHolder(convertView);
        }else
        {
            final View convertView = inflater
                    .inflate(R.layout.speciality_single_layout, parent, false);
            return new OrdersListHolder(convertView);
        }


    }


    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getDataType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder!=null && holder instanceof OrdersListHolder)
        {
            OrdersListHolder listHolder= (OrdersListHolder) holder;
            listHolder.bindData(dataList.get(position), position);
        }

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

        private TextView item_name_tv;

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

    public class HeaderHolder extends RecyclerView.ViewHolder {

        //private  TextView item_name_tv;

        public HeaderHolder(View itemView) {
            super(itemView);
            //item_name_tv = itemView.findViewById(R.id.item_name_tv);

        }

        public void bindData(final SpecalitiyData data, final int position) {
            if (data == null) {
                return;
            }

           // item_name_tv.setText(data.getName());





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
