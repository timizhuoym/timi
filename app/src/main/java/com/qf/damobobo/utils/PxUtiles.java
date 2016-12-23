package com.qf.damobobo.utils;

import android.content.Context;

/**
 * Created by TimiZhuo on 2016/11/21.
 */
public class PxUtiles {
    /**
     * 将px转换成dp,dp=px/密度系数
     * @param px
     * @param context
     */
    public static float pxToDp(int px, Context context){
        float density=context.getResources().getDisplayMetrics().density;
        return px/density;
    }

    /**
     * 将dp转换成px，px=dp*密度系数
     * @param dp
     * @param context
     * @return
     */
    public static float dpToPx(int dp,Context context){
        float density = context.getResources().getDisplayMetrics().density;
        return dp*density;
    }
}
