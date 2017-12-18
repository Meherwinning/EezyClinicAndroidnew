package com.vempower.eezyclinic.interfaces;

import android.os.IBinder;
import android.support.annotation.Nullable;

import com.vempower.eezyclinic.IMyAidlInterface;

/**
 * Created by satish on 7/12/17.
 */

public abstract class AbstractIBinder extends IMyAidlInterface.Stub {

    @Override
    public IBinder asBinder() {
        return  getMyObject();
    }

    protected abstract IntentObjectListener getMyObject();

}
