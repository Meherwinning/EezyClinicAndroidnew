package com.vempower.eezyclinic.activities;


import com.vempower.eezyclinic.R;
import com.vempower.eezyclinic.application.MyApplication;
import com.vempower.stashdealcustomer.activities.AbstractActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sathishkumar on 20/4/16.
 */
class AbstractBackPressActivity extends AbstractActivity {

    private final int DELAY_TIME = 4000;//In millis
    private boolean isBackPressed = false;


    private void autoStopBackPressCallback() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isBackPressed = false;
            }
        }, DELAY_TIME);
    }

    protected void finishAllScreens() {
        if (!isBackPressed) {
            showToastMessage(getResources().getString(R.string.press_again_to_exit));
            isBackPressed = true;
            autoStopBackPressCallback();
            return;
        }
        MyApplication.setCurrentActivityContext(null);
        finish();
    }
    protected void finishAllScreensFromHome() {
        if (!isBackPressed) {
            showToastMessage(getResources().getString(R.string.press_again_to_exit));
            isBackPressed = true;
            autoStopBackPressCallback();
            return;
        }
        MyApplication.setCurrentActivityContext(null);
        finish();
    }

    @Override
    public void onBackPressed() {
        finishAllScreens();
    }
}
