package com.vempower.eezyclinic.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.google.android.material.textfield.TextInputLayout;

/**
 * Created by satish on 20/11/17.
 */

public class MyTextInputLayout extends TextInputLayout {
    public MyTextInputLayout(Context context) {
        super(context);
        setFontStyle();
    }

    public MyTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontStyle();
    }

    public MyTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontStyle();
    }

    private void setFontStyle()
    {
        Typeface face = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Roboto-Regular.ttf");
        setTypeface(face);
    }
}
