package com.vempower.eezyclinic.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by satish on 20/11/17.
 */

public class MyEditTextBlackCursorRR extends MyEditTextBlackCursor {
    public MyEditTextBlackCursorRR(Context context) {
        super(context);
        setFontStyle();
        setDisableCopyPaste();

    }

    public MyEditTextBlackCursorRR(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontStyle();
        setDisableCopyPaste();
    }

    public MyEditTextBlackCursorRR(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontStyle();
        setDisableCopyPaste();
    }

    @Override
    public void setInputType(int type) {
        super.setInputType(type);
        setFontStyle();
        //setDisableCopyPaste();
        //setCursorDrawableColor(this, R.color.white);
    }

    private void setFontStyle()
    {
        Typeface face = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Roboto-Regular.ttf");
        setTypeface(face);
        setDisableCopyPaste();
        //setCursorColor();
        //setCursorDrawableColor(this, R.color.white);

    }

    /*private void setDisableCopyPaste()
    {
        setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
        setLongClickable(false);
        setTextIsSelectable(false);

        setCursorVisible(true);

    }*/

}
