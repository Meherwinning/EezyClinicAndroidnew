package com.vempower.eezyclinic.interfaces;

import android.os.IBinder;

import com.vempower.eezyclinic.IMyAidlInterface;

/**
 * Created by satish on 18/12/17.
 */

public abstract class IntentObjectListener  extends IMyAidlInterface.Stub {
    /*@Override
    public IBinder asBinder() {
        return null;
    }*/

    public abstract Object getObject();
}
