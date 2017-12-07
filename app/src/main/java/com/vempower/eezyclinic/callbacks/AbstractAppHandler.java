package com.vempower.eezyclinic.callbacks;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.vempower.eezyclinic.IMyAidlInterface;

/**
 * Created by satish on 7/12/17.
 */




public abstract class AbstractAppHandler extends Handler implements IMyAidlInterface {
    public abstract void getObject(Object obj);

    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg.replyTo != null) {
            getObject(msg.obj);
        }
    }

    @Override
    public IBinder asBinder() {
        return null;
    }
}