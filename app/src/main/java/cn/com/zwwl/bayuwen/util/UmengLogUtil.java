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
     * 课程跟踪首页点击统计
     */
    public static void courseTrackClick(Context mContext) {
        onEvnet(mContext, "genzong_tab");
    }
    /**
     * 课程跟踪课节报告
     */
    public static void courseReportClick(Context mContext) {
        onEvnet(mContext, "kjbg_btn");
    }
    /**
     * 课程跟踪期中报告
     */
    public static void QiZhongReportClick(Context mContext) {
        onEvnet(mContext, "qizhong_btn");
    }
    /**
     * 课程跟踪期末报告
     */
    public static void QiMoReportClick(Context mContext) {
        onEvnet(mContext, "qimo_btn");
    }
    /**
     * 课程跟踪欢迎致辞报告
     */
    public static void WelReportClick(Context mContext) {
        onEvnet(mContext, "wel_btn");
    }
    /**
     * 课程跟踪往次课节报告wago_wckjbg
     */
    public static void AgoCourseReportClick(Context mContext) {
        onEvnet(mContext, "wago_wckjbg");
    }
    /**
     * 课程跟踪首页“作业“按钮点击,0
     */
    public static void CourseWorkClick(Context mContext) {
        onEvnet(mContext, "genzong_zuoye");
    }
    /**
     * 课程跟踪首页"回放“按钮点击,0
     */
    public static void PlayBackClick(Context mContext) {
        onEvnet(mContext, "genzong_huifang");
    }
    /**
     * 直播课回放列表页面中，“回放”统计,0
     */
    public static void LivePlayBackClick(Context mContext) {
        onEvnet(mContext, "zbkhflb_huifang_btn");
    }
    /**
     * tiaoke,二级页面 “调课”按钮统计,0
     */
    public static void ChangeCourceClick(Context mContext) {
        onEvnet(mContext, "tiaoke");
    }
    /**
     * zhuanban,二级页面 "换班“按钮统计,0
     */
    public static void ConverClassClick(Context mContext) {
        onEvnet(mContext, "zhuanban");
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
