package com.vempower.eezyclinic.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * Created by satish on 20/11/17.
 */

public class MyEditTextRR extends EditText {
    public MyEditTextRR(Context context) {
        super(context);
        setFontStyle();
        setDisableCopyPaste();

    }

    public MyEditTextRR(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontStyle();
        setDisableCopyPaste();
    }

    public MyEditTextRR(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontStyle();
        setDisableCopyPaste();
    }

    @Override
    public void setInputType(int type) {
        super.setInputType(type);
        setFontStyle();
        setDisableCopyPaste();
    }

    private void setFontStyle()
    {
        Typeface face = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Roboto-Regular.ttf");
        setTypeface(face);

    }

    private void setDisableCopyPaste()
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

    }

}
