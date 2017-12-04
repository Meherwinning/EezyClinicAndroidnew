package com.vempower.eezyclinic.fragments;


import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.vempower.eezyclinic.interfaces.MenuScreenListener;

/**
 * Created by satish on 17/11/17.
 */

public abstract class AbstractFragment extends Fragment {
    protected MenuScreenListener listener;


    public void setOnMenuScreenListener(MenuScreenListener listener) {
        this.listener=listener;
    }

    public  void onTitleRightButtonClick(){

    }

    public void setScreenTitle(String screenTitle) {
        if(TextUtils.isEmpty(screenTitle) || getFragemtView()==null)
        {
            return;
        }
        // if(screen_title_tv==null) {
        //TextView    screen_title_tv = getFragemtView().findViewById(R.id.screen_title_tv);
        // }
        /*if(screen_title_tv==null) {
            return;
        }
        screen_title_tv.setText(screenTitle);*/
        // screen_title_tv get
    }


     View getFragemtView(){
        return null;
    }
}
