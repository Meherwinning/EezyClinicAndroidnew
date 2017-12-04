package com.vempower.eezyclinic.interfaces;

/**
 * Created by Satishk on 8/31/2017.
 */

public interface MenuScreenListener extends FinishScreenListener {
    void onTitleMenuClick(Object obj);
    void getRightTileDrawableId(int id);
}
