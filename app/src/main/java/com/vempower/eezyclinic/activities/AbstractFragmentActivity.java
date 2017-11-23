package com.vempower.eezyclinic.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.stashdealcustomer.activities.AbstractActivity;

/**
 * Created by Satish on 11/15/2017.
 */

public class AbstractFragmentActivity extends AbstractActivity {

   // private InputMethodManager keyBoardHide;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.setCurrentActivityContext(this);
    }


    /*protected void hideKeyBord(View view ) {
        if (view != null) {
            if (keyBoardHide == null) {
                keyBoardHide = (InputMethodManager) MyApplication.getCurrentActivityContext().getSystemService(INPUT_METHOD_SERVICE);
               // as InputMethodManager
                // keyBoardHide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,
                // 0);
            }
            if (keyBoardHide != null && keyBoardHide.isActive()) {
                // to hide keyboard
                if (view != null) {
                    keyBoardHide.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }
    }*/
}
