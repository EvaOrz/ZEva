package cn.com.zwwl.bayuwen.util;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.Map;

public class UmengLogUtil {

    /**
     * 选课首页各项目点击
     *
     * @param mContext
     */
    public static void logTagClick(Context mContext) {
        onEvnet(mContext, "gxmdj");
    }

    /**
     * 各项目视频点击统计
     *
     * @param mContext
     */
    public static void logTagVideoClick(Context mContext) {
        onEvnet(mContext, "gxmspdj");
    }

    /**
     * "查看全部老师"点击统计
     *
     * @param mContext
     */
    public static void logAllTeacherClick(Context mContext) {
        onEvnet(mContext, "ckqtls");
    }

    /**
     * "查看全部班主任"点击统计
     *
     * @param mContext
     */
    public static void logAllBanzhurenClick(Context mContext) {
        onEvnet(mContext, "ckqtbzr");
    }

    /**
     * 课程列表中 点击课程视频播放
     *
     * @param mContext
     */
    public static void logKeListVideoPlay(Context mContext) {
        onEvnet(mContext, "kecheng_spbf");
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
