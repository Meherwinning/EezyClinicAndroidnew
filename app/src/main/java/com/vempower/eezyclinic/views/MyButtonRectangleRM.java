package com.vempower.eezyclinic.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.gc.materialdesign.views.ButtonRectangle;
import com.vempower.eezyclinic.R;

/**
 * Created by satish on 20/11/17.
 */

public class MyButtonRectangleRM extends ButtonRectangle {


    public MyButtonRectangleRM(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontStyle();
        
    }

    protected void setButtonTypeface()
    {
        setFontStyle();
    }

    private void setFontStyle()
    {
        Typeface face = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Roboto-Medium.ttf");
        setButtonTypeface(face);
        setLetterSpacing(0.04f);

      //  setLetterSpacing(getContext().getResources().getDimension(R.dimen.button_text_line_space));

    }

}
