package com.example.exambooktest.utils;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

public class Utils {

    //根据手机的分辨率从dp的单位转化成为px的单位
    public static int dip2px (Context context, float dpValue){

        //获取当前手机的像素密度
        final  float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dpValue * scale + 0.5f);
    }
    //根据手机的分辨率从px的单位转化成为dp的单位
    public static int px2dip (Context context, float pxValue){

        //获取当前手机的像素密度
        final  float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    //获取屏幕的宽度
    public static int getScreenWidth(Context context){
        //从系统服务中获取窗口管理器

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //显示尺寸的对象
        DisplayMetrics displayMetrics = new DisplayMetrics();

        //从默认显示器windowManager中获取显示参数保存到displayMetrics对象中
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;

    }

    //获取屏幕的高度
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getScreenHeight(Context context){
        //从系统服务中获取窗口管理器

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //显示尺寸的对象
        DisplayMetrics displayMetrics = new DisplayMetrics();

        //从默认显示器windowManager中获取显示参数保存到displayMetrics对象中
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;

    }

    //获取屏幕的像素密度
    public static float getScreenDensity(Context context){
        //从系统服务中获取窗口管理器

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //显示尺寸的对象
        DisplayMetrics displayMetrics = new DisplayMetrics();

        //从默认显示器windowManager中获取显示参数保存到displayMetrics对象中
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.density;

    }
}
