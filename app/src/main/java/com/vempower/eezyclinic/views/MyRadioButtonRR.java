package com.vempower.eezyclinic.views;

import android.content.Context;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatRadioButton;
import android.util.AttributeSet;

//import com.gc.materialdesign.views.CheckBox;

/**
 * Created by satish on 20/11/17.
 */

public class MyRadioButtonRR extends AppCompatRadioButton {
    public MyRadioButtonRR(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontStyle();
    }

    protected void setTextViewTypeface()
    {
        setFontStyle();
    }

    private void setFontStyle()
    {
        Typeface face = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Roboto-Regular.ttf");
        //setTextViewTypeface(face);
        setTypeface(face);
    }

}
