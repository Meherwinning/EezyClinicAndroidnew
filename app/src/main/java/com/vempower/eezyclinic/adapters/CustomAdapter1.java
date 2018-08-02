package com.vempower.eezyclinic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter1<T> extends ArrayAdapter<T> {
    private final Object lock = new Object();
    //private boolean isEnableHint;
    private final String MY_DEBUG_TAG = "CustomerAdapter";
    private List<T> items;
    private final List<T> itemsAll;
    private List<T> suggestions;
    private int viewResourceId;



    public CustomAdapter1(Context context, int viewResourceId, List<T> items) {
        super(context, viewResourceId, items);
        this.items = items;
        // this.itemsAll = (ArrayList<T>) items.clone();
        this.itemsAll = new ArrayList<>();
        itemsAll.addAll(items);
        this.suggestions = new ArrayList<T>();
        this.viewResourceId = viewResourceId;
       // isEnableHint = true;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        T customer = items.get(position);
        if (customer != null) {
            TextView customerNameLabel = (TextView) v;//(TextView) v.findViewById(R.id.item_name_tv);
            if (customerNameLabel != null) {
//              Log.i(MY_DEBUG_TAG, "getView Customer Name:"+customer.getName());
                customerNameLabel.setText(customer.toString());
            }
        }
        return v;
    }

   /* @Override
    public int getCount() {
        // don't display last item. It is used as hint.
        int count = super.getCount();
        // return count;
        if (isEnableHint) {

            return count > 0 ? count - 1 : count;
        } else {
            return count;
        }
    }*/





   /* public void setUpdatedList(List<T> newList) {
        if (newList == null || newList.size() == 0) {
            clear();
            notifyDataSetChanged();
            return;
        }
        addAll(newList);
        notifyDataSetChanged();
    }*/
}
