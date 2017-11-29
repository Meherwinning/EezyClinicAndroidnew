package com.vempower.eezyclinic.adapters;

import android.content.Context;

import com.vempower.eezyclinic.core.AdapterItem;

import java.util.List;

public class AccountListAdapter<T extends AdapterItem> extends RadioAdapter<T> {
    public AccountListAdapter(Context context, List<T> items) {
        super(context, items);
    }

    @Override
    public void onBindViewHolder(RadioAdapter.ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
        if(mItems.get(i)!=null && mItems.get(i) instanceof AdapterItem)
        {
            AdapterItem item= mItems.get(i);
            viewHolder.mText.setText(item.getItemName());
        }

    }
}
