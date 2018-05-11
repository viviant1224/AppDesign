package com.viviant.earngold.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by weiwei.huang on 2017/8/16.
 */

public class CommonUtils {

    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;

//        int result = 0;
//        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
//        if (resourceId > 0) {
//            result = context.getResources().getDimensionPixelSize(resourceId);
//        }
//        return result;
    }

    public static int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && checkDeviceHasNavigationBar(context)) {
            navigationBarHeight = rs.getDimensionPixelSize(id);
        }
        return navigationBarHeight;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static boolean checkDeviceHasNavigationBar(Context context) {

        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(context)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // 没有物理按键则一定有navigationbar，不然怎么退出
            return true;
        }
        //有物理按键依然可能有navigationbar，给华为，魅族跪了。。
        return checkDeviceHasNavigationBarAgain(context);
    }

    private static boolean checkDeviceHasNavigationBarAgain(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
        }

        return hasNavigationBar;

    }

    public static void setViewFixStatusHeight(View view, Context context) {
        int paddingTop = view.getPaddingTop();
        int paddingBottom = view.getPaddingBottom();
        int paddingLeft = view.getPaddingLeft();
        int paddingRight = view.getPaddingRight();
        int statusHeight = CommonUtils.getStatusBarHeight(context);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        int height = params.height;
        /**
         * 利用状态栏的高度，4.4及以上版本给View设置一个paddingTop值为status_bar的高度，
         * Toolbar延伸到status_bar顶部
         **/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            paddingTop += statusHeight;
            height += statusHeight;
        }
        params.height = height;
        view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public static void Toast(Context context, String text){
        android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取SharedPreferences
     * @return SharedPreferences
     */
    public static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }


}
