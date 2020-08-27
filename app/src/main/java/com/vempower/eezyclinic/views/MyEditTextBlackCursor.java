package com.vempower.eezyclinic.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.vempower.eezyclinic.R;

import java.lang.reflect.Field;

/**
 * Created by satish on 20/11/17.
 */

public class MyEditTextBlackCursor extends AppCompatEditText {
    public MyEditTextBlackCursor(Context context) {
        super(context);
        setDisableCopyPaste();
    }

    public MyEditTextBlackCursor(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDisableCopyPaste();
    }

    public MyEditTextBlackCursor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setDisableCopyPaste();
    }

    protected void setDisableCopyPaste()
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

       // setCursorVisible(true);
       // setCursorDrawableColor(this, R.color.white);
        setCursorColor();

    }

    private void setCursorColor()
    {
        try {
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(this, R.drawable.my_black_cursor);
        } catch (Exception ignored) {
        }
    }

    protected  void setCursorDrawableColor1(EditText editText, int color) {
        try {
            Field fCursorDrawableRes =
                    TextView.class.getDeclaredField("mCursorDrawableRes");
            fCursorDrawableRes.setAccessible(true);
            int mCursorDrawableRes = fCursorDrawableRes.getInt(editText);
            Field fEditor = TextView.class.getDeclaredField("mEditor");
            fEditor.setAccessible(true);
            Object editor = fEditor.get(editText);
            Class<?> clazz = editor.getClass();
            Field fCursorDrawable = clazz.getDeclaredField("mCursorDrawable");
            fCursorDrawable.setAccessible(true);

            Drawable[] drawables = new Drawable[2];
            Resources res = editText.getContext().getResources();
            drawables[0] = res.getDrawable(mCursorDrawableRes);
            drawables[1] = res.getDrawable(mCursorDrawableRes);
            drawables[0].setColorFilter(color, PorterDuff.Mode.SRC_IN);
            drawables[1].setColorFilter(color, PorterDuff.Mode.SRC_IN);
            fCursorDrawable.set(editor, drawables);
        } catch (final Throwable ignored) {
        }
    }


}
