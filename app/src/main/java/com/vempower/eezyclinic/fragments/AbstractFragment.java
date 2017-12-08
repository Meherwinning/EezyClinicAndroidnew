package com.vempower.eezyclinic.fragments;


import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.vempower.eezyclinic.APIResponce.AbstractResponse;
import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.interfaces.AbstractListener;
import com.vempower.eezyclinic.interfaces.HomeListener;
import com.vempower.eezyclinic.interfaces.MenuScreenListener;
import com.vempower.eezyclinic.utils.Constants;
import com.vempower.eezyclinic.utils.Utils;

/**
 * Created by satish on 17/11/17.
 */

public abstract class AbstractFragment extends Fragment {
    protected MenuScreenListener listener;
    protected AbstractListener myListener;


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

    protected boolean isValidResponse(AbstractResponse response, String errorMessage/*,boolean isShowDialog,boolean isShowNothing*/)
    {
        if(response==null && TextUtils.isEmpty(errorMessage))
        {
            //showMyAlertDialog("Alert",  Utils.getStringFromResources(R.string.invalid_service_response_lbl),"Ok",false);
            Utils.showToastMsg(R.string.invalid_service_response_lbl);
            return false;
        }
        if(response==null && !TextUtils.isEmpty(errorMessage))
        {
            //showMyAlertDialog("Alert", errorMessage,"Ok",false);
            Utils.showToastMsg(errorMessage);
            return false;
        }
        if(!response.getStatusCode().equalsIgnoreCase(Constants.SUCCESS_STATUS_CODE))
        {
            //showMyAlertDialog("Alert",response.getStatusMessage() ,"Ok",false);
            Utils.showToastMsg(response.getStatusMessage());
            return false;

        }
        return true;
    }

    public void setOnMyListener(AbstractListener myListener)
    {
        this.myListener=myListener;
    }
}
