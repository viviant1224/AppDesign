package com.viviant.earngold.widget.customtoast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.viviant.earngold.R;

/**
 * Created by weiwei.huang on 2017/8/28.
 */

public class CustomToast{

    private static final int SUCCESS_COLOR = 1;
    private static final int WARNING_COLOR = 2;
    private static final int ERROR_COLOR = 3;

    private Toast mToast;

    public CustomToast(Context context, CharSequence text, int duration, int typeColor) {
        View v = LayoutInflater.from(context).inflate(R.layout.toast_ly, null);
        TextView textView = (TextView) v.findViewById(R.id.message_tv);
        textView.setText(text);
        switch (typeColor) {
            case SUCCESS_COLOR:
                textView.setBackground(context.getResources().getDrawable(R.drawable.custom_toast_success));
                break;
            case WARNING_COLOR:
                textView.setBackground(context.getResources().getDrawable(R.drawable.custom_toast_warning));
                break;
            case ERROR_COLOR:
                textView.setBackground(context.getResources().getDrawable(R.drawable.custom_toast_error));
                break;
            default:
                textView.setBackground(context.getResources().getDrawable(R.drawable.custom_toast_success));
                break;
        }


        mToast = new Toast(context);
        mToast.setDuration(duration);
        mToast.setView(v);
    }

    public static void makeTextSuccessLong(Context context, CharSequence text) {
         (new CustomToast(context, text, Toast.LENGTH_LONG, SUCCESS_COLOR)).show();
    }

    public static void makeTextSuccessShort(Context context, CharSequence text) {
        (new CustomToast(context, text, Toast.LENGTH_SHORT, SUCCESS_COLOR)).show();
    }

    public static void makeTextErrorLong(Context context, CharSequence text) {
        (new CustomToast(context, text, Toast.LENGTH_LONG, ERROR_COLOR)).show();
    }

    public static void makeTextErrorShort(Context context, CharSequence text) {
        (new CustomToast(context, text, Toast.LENGTH_SHORT, ERROR_COLOR)).show();
    }

    public static void makeTextWarningLong(Context context, CharSequence text) {
        (new CustomToast(context, text, Toast.LENGTH_LONG, WARNING_COLOR)).show();
    }

    public static void makeTextWarningShort(Context context, CharSequence text) {
        (new CustomToast(context, text, Toast.LENGTH_SHORT, WARNING_COLOR)).show();
    }

    public void show() {
        if (mToast != null) {
            mToast.show();
        }
    }
}
