package com.vempower.eezyclinic.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * Created by satish on 20/11/17.
 */

public class MyEditText extends EditText {
    public MyEditText(Context context) {
        super(context);
        setDisableCopyPaste();
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDisableCopyPaste();
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setDisableCopyPaste();
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
