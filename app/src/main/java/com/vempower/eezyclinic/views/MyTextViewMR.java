package com.vempower.eezyclinic.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by satish on 20/11/17.
 */

public class MyTextViewMR extends TextView {
    public MyTextViewMR(Context context) {
        super(context);
        setFontStyle(context);

    }

    public MyTextViewMR(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFontStyle(context);
    }

    public MyTextViewMR(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontStyle(context);
    }


    private void setFontStyle(Context context)
    {
        Typeface face = Typeface.createFromAsset(context.getAssets(),
                "fonts/Montserrat-Regular.ttf");
        setTypeface(face);
    }


}
