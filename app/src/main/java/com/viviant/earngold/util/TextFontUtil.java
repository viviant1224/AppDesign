package com.viviant.earngold.util;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by weiwei.huang on 2017/9/4.
 */

public class TextFontUtil {

    private static final String FONT_PATH = "fonts/fz_jiankai.ttf";

    public static Typeface getTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), FONT_PATH);
    }
}
