package com.vempower.eezyclinic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.vempower.eezyclinic.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter<T> extends ArrayAdapter<T> {
    private boolean isEnableHint;
    private final String MY_DEBUG_TAG = "CustomerAdapter";
    private List<T> items;
    private List<T> itemsAll;
    private List<T> suggestions;
    private int viewResourceId;


    public CustomerAdapter(Context context, int viewResourceId, List<T> items) {
        super(context, viewResourceId, items);
        this.items = items;
        // this.itemsAll = (ArrayList<T>) items.clone();
        this.itemsAll = new ArrayList<>();
        itemsAll.addAll(items);
        this.suggestions = new ArrayList<T>();
        this.viewResourceId = viewResourceId;
        isEnableHint = true;
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

    @Override
    public int getCount() {
        // don't display last item. It is used as hint.
        int count = super.getCount();
        if (isEnableHint) {

            return count > 0 ? count - 1 : count;
        } else {
            return count;
        }
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
       /* @Override
        public String convertResultToString(Object resultValue) {
            String str = ( (resultValue)).toString();
            return str;
        }*/

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null) {
                suggestions.clear();
                T temp=null;
                for (T customer : itemsAll) {
                    if (customer == null) {
                        continue;
                    }
                   String str1= customer.toString().toLowerCase().trim().replaceAll(" ","");
                    String str2= constraint.toString().toLowerCase().trim().replaceAll(" ","");
                    if ( str1.contains(str2)  ||  str1.startsWith(str2)  ||  str2.contains(str1) || str2.startsWith(str1)) {
                        suggestions.add(customer);
                        temp=customer;
                    }
                }
                if(temp!=null && suggestions.size()>0)
                {
                    suggestions.add(suggestions.size(),temp);
                }
                //suggestions.add(new T());
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();

            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<T> filteredList = (ArrayList<T>) results.values;
            if (results != null && results.count > 0) {
                clear();
                addAll(filteredList);
                /*for (T c : filteredList) {
                    add(c);
                }*/
                notifyDataSetChanged();
            }
        }
    };
}
