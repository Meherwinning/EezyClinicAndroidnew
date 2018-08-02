package com.vempower.eezyclinic.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by satish on 29/11/17.
 */

public class HintAdapter<T> extends ArrayAdapter<T> {


    private boolean isEnableHint;

    public HintAdapter(Context context, int resource) {
        super(context, resource);
        isEnableHint=true;
    }

    public HintAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        isEnableHint=true;
    }

    public HintAdapter(Context context, int resource, T[] objects) {
        super(context, resource, objects);
        isEnableHint=true;
    }

    public HintAdapter(Context context, int resource, int textViewResourceId, T[] objects) {
        super(context, resource, textViewResourceId, objects);
        isEnableHint=true;
    }

    public HintAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);
        isEnableHint=true;
    }

    public HintAdapter(Context context, int resource, int textViewResourceId, List<T> objects) {
        super(context, resource, textViewResourceId, objects);
        isEnableHint=true;
    }

    public void isHintEnable(boolean isEnableHint)
    {
        this.isEnableHint=isEnableHint;
    }

  /*  @Override
    public int getCount() {
        // don't display last item. It is used as hint.
        int count = super.getCount();
        if(isEnableHint) {

            return count > 0 ? count - 1 : count;
        }else {
            return count;
        }
    }*/
}