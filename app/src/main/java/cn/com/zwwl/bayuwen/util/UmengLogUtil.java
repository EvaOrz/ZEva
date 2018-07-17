package cn.com.zwwl.bayuwen.util;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.Map;

public class UmengLogUtil {

    /**
     * 选课首页各项目点击
     */
    public static void logTagClick(Context mContext) {
        onEvnet(mContext, "gxmdj");
    }

    /**
     * 计数事件
     *
     * @param mContext
     * @param eventId
     */
    private static void onEvnet(Context mContext, String eventId) {
        MobclickAgent.onEvent(mContext, eventId);
    }

    /**
     * 含标签、渠道的计数事件
     *
     * @param mContext
     * @param eventId
     * @param channal
     */
    private static void onEvnet(Context mContext, String eventId, String channal) {
        MobclickAgent.onEvent(mContext, eventId, channal);
    }

    /**
     * 含多种参数的计数事件
     *
     * @param mContext
     * @param eventId
     * @param map
     */
    public static void onEvent(Context mContext, String eventId, Map<String, String> map) {
        MobclickAgent.onEvent(mContext, eventId, map);
    }


}
