package com.vempower.eezyclinic.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import com.vempower.eezyclinic.R;

import java.lang.reflect.Field;

/**
 * Created by satish on 11/12/17.
 */

public class MyAutoCompleteBlackCursorTextView extends AppCompatAutoCompleteTextView {
    public MyAutoCompleteBlackCursorTextView(Context context) {
        super(context);
        setFontStyle();
    }

    public MyAutoCompleteBlackCursorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontStyle();
    }

    public MyAutoCompleteBlackCursorTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontStyle();
    }
    private void setFontStyle()
    {
        Typeface face = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Roboto-Regular.ttf");
        setTypeface(face);
        setCursorColor();
        //setCursorColor();
        //setCursorDrawableColor(this, R.color.white);

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
}
