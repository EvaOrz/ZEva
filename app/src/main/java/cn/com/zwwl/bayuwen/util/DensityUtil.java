package cn.com.zwwl.bayuwen.util;

import android.content.res.Resources;


/**
 *  DensityUtil:单位转换
 *  Created by zhumangmang at 2018/6/5 14:22
 */
public class DensityUtil {
    public static int dip2px(Resources resources, int resId) {
        final float scale = resources.getDisplayMetrics().density;
        int pixelOffset =resources.getDimensionPixelOffset(resId);
        return (int) (pixelOffset * scale + 0.5f);
    }


    public static int px2dip(Resources resources, float pxValue) {
        final float scale = resources.getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
