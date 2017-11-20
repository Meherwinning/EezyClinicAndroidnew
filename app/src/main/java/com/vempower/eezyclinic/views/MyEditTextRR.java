package com.vempower.eezyclinic.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by satish on 20/11/17.
 */

public class MyEditTextRR extends EditText {
    public MyEditTextRR(Context context) {
        super(context);
        setFontStyle();

    }

    public MyEditTextRR(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontStyle();
    }

    public MyEditTextRR(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontStyle();
    }

    @Override
    public void setInputType(int type) {
        super.setInputType(type);
        setFontStyle();
    }

    private void setFontStyle()
    {
        Typeface face = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Roboto-Regular.ttf");
        setTypeface(face);
    }

}
