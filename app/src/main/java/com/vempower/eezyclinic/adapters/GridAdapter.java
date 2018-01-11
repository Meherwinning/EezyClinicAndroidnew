package com.vempower.eezyclinic.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.vempower.eezyclinic.APIResponce.SpecalitiyData;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.eezyclinic.views.MyTextViewRR;

import java.util.List;

/**
 * Created by satish on 11/1/18.
 */

public class GridAdapter extends BaseAdapter {

    List<SpecalitiyData> dataList;
    private LayoutInflater inflater;

    public GridAdapter(List<SpecalitiyData> dataList) {
        this.dataList = dataList;
        inflater = (LayoutInflater) MyApplication.getCurrentActivityContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrdersListHolder holder;

        if(convertView==null) {
            convertView = inflater
                    .inflate(R.layout.speciality_single_layout, parent, false);
            holder= new OrdersListHolder(convertView);
            convertView.setTag(holder);

        }else
        {
            holder= (OrdersListHolder) convertView.getTag();
        }
        holder.bindData(dataList.get(position));
        return convertView;
    }


    public class OrdersListHolder {

        private MyTextViewRR item_name_tv;

        public OrdersListHolder(View itemView) {
            // super(itemView);
            item_name_tv = itemView.findViewById(R.id.item_name_tv);

        }

        public void bindData(final SpecalitiyData data) {
            if (data == null) {
                return;
            }

            item_name_tv.setText(data.getName());


        }
    }
}
