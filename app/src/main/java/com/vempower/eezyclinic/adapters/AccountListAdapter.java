package com.vempower.eezyclinic.adapters;

import android.content.Context;

import com.vempower.eezyclinic.APICore.UserAccount;
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
            UserAccount item= (UserAccount) mItems.get(i);
            viewHolder.name_tv.setText(item.getPatientName());
            viewHolder.patient_id_tv.setText(item.getPatientUniqueId());
            viewHolder.email_tv.setText(item.getPatentEmail());
        }

    }
}
