package com.vempower.eezyclinic.utils;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecimalDigitsInputFilter implements InputFilter {

    private final int decimalDigits;
    private final int digits;

    /**
     * Constructor.
     *
     * @param decimalDigits maximum decimal digits
     */
    public DecimalDigitsInputFilter(int digits,int decimalDigits) {
        this.decimalDigits = decimalDigits;
        this.digits=digits;
    }

    @Override
    public CharSequence filter(CharSequence source,
                               int start,
                               int end,
                               Spanned dest,
                               int dstart,
                               int dend) {


        int dotPos = -1;
        int len = dest.length();
        for (int i = 0; i < len; i++) {
            char c = dest.charAt(i);
            if (c == '.' || c == ',') {
                dotPos = i;
                break;
            }
        }
        if (dotPos >= 0) {

            // protects against many dots
            if (source.equals(".") || source.equals(","))
            {
                return "";
            }
            // if the text is entered before the dot
            if (dend <= dotPos) {
                return null;
            }
            if (len - dotPos > decimalDigits) {
                return "";
            }
        }else
        {
            if(len  == digits && !(source.equals(".") || source.equals(",")))
            {
                return "";
            }

            if (len  > digits) {
                if (source.equals(".") || source.equals(","))
                {
                    return null;
                }
                return "";
            }
        }



        return null;
    }
}