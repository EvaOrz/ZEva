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
     * 课程详情中 点击课程视频播放
     *
     * @param mContext
     */
    public static void logKeDetailVideoPlay(Context mContext) {
        onEvnet(mContext, "detail_kecheng_spbf");
    }

    /**
     * 课程详情中 课程表|课程大纲|家长评论点击
     *
     * @param mContext
     */
    public static void logKeDetailTabClick(Context mContext, int tabNum) {
        if (tabNum == 0) {
            onEvnet(mContext, "detail_kcb_btn");
        } else if (tabNum == 1) {
            onEvnet(mContext, "detail_kcdg_btn");
        } else if (tabNum == 2) {
            onEvnet(mContext, "detail_jzpl_btn");
        }
    }

    /**
     * 课程详情中 顾问点击
     *
     * @param mContext
     */
    public static void logKeDetailGwClick(Context mContext) {
        onEvnet(mContext, "detail_gw_btn");
    }

    /**
     * 课程详情中 说明点击
     *
     * @param mContext
     */
    public static void logKeDetailSmClick(Context mContext) {
        onEvnet(mContext, "detail_sm_btn");
    }

    /**
     * 课程详情中 关注点击
     *
     * @param mContext
     */
    public static void logKeDetailGzClick(Context mContext) {
        onEvnet(mContext, "detail_gz_btn");
    }

    /**
     * 课程详情中 加入购课单点击
     *
     * @param mContext
     */
    public static void logKeDetailAddCartClick(Context mContext) {
        onEvnet(mContext, "detail_jrgkd");
    }

    /**
     * 课程详情中 立即报名点击
     *
     * @param mContext
     */
    public static void logKeBaomingClick(Context mContext) {
        onEvnet(mContext, "detail_ljbm");
    }

    /**
     * 首页课程日历点击
     *
     * @param mContext
     */
    public static void logRiliClick(Context mContext) {
        onEvnet(mContext, "rili_cell");
    }


    /**
     * 添加课程日历按钮点击
     *
     * @param mContext
     */
    public static void logRiliAddClick(Context mContext) {
        onEvnet(mContext, "rili_plus");
    }


    /**
     * 保存课程日历按钮点击
     *
     * @param mContext
     */
    public static void logRiliSaveClick(Context mContext) {
        onEvnet(mContext, "rili_plus_save");
    }

    /**
     * 添加第三方机构
     *
     * @param mContext
     */
    public static void logRiliThirdSaveClick(Context mContext) {
        onEvnet(mContext, "third_part");
    }

    /**
     * 首页拼图点击
     *
     * @param mContext
     */
    public static void logPintuClick(Context mContext) {
        onEvnet(mContext, "pingtu_click");
    }

    /**
     * 查看全部成就点击
     *
     * @param mContext
     */
    public static void logAllChengjiuClick(Context mContext) {
        onEvnet(mContext, "total_chengjiu");
    }

    /**
     * 拼图页面tab点击 课程内容|学生情况|体系介绍
     *
     * @param mContext
     */
    public static void logPintuTabClick(Context mContext, int tabNum) {
        if (tabNum == 0) {
            onEvnet(mContext, "pintu_kcnr");
        } else if (tabNum == 1) {
            onEvnet(mContext, "pintu_xsqk");
        } else if (tabNum == 2) {
            onEvnet(mContext, "pintu_txjs");
        }
    }

    /**
     * 拼图页面开始闯关点击
     *
     * @param mContext
     */
    public static void logPintuChuangguanClick(Context mContext) {
        onEvnet(mContext, "pingtu_start_cg");
    }

    /**
     * fm专辑喜欢点击
     *
     * @param mContext
     */
    public static void logFmLikeClick(Context mContext) {
        onEvnet(mContext, "kc_like");
    }

    /**
     * fm专辑收藏点击
     *
     * @param mContext
     */
    public static void logFmCollectClick(Context mContext) {
        onEvnet(mContext, "kc_collect");
    }


    /**
     * 发起团购
     *
     * @param mContext
     */
    public static void logFaqiTianClick(Context mContext) {
        onEvnet(mContext, "fqtg");
    }

    /**
     * 兑换课程
     *
     * @param mContext
     */
    public static void logDuihuanClick(Context mContext) {
        onEvnet(mContext, "dhkc");
    }

    /**
     * 邀请好友加入大语文
     *
     * @param mContext
     */
    public static void logInviteClick(Context mContext) {
        onEvnet(mContext, "yqhy");
    }

    /**
     * 意见反馈
     *
     * @param mContext
     */
    public static void logFeedBackClick(Context mContext) {
        onEvnet(mContext, "yjfk");
    }


    /**
     * 修改密码
     *
     * @param mContext
     */
    public static void logForgetPwd(Context mContext) {
        onEvnet(mContext, "xgmm");
    }


    /**
     * 购课单|待付款|已付款
     *
     * @param mContext
     */
    public static void logOrderBtnClick(Context mContext, int tabNum) {
        if (tabNum == 0) {
            onEvnet(mContext, "gkd");
        } else if (tabNum == 1) {
            onEvnet(mContext, "dfk");
        } else if (tabNum == 2) {
            onEvnet(mContext, "yfk");
        }
    }

    /**
     * 申请开票
     *
     * @param mContext
     */
    public static void logSqFapiao(Context mContext) {
        onEvnet(mContext, "sqfp");
    }

    /**
     * 申请退费
     *
     * @param mContext
     */
    public static void logSqTuifee(Context mContext) {
        onEvnet(mContext, "sqtf");
    }

    /**
     * 切换城市
     *
     * @param mContext
     */
    public static void logChangeCity(Context mContext) {
        onEvnet(mContext, "qhcs");
    }

    /**
     * 消息点击
     *
     * @param mContext
     */
    public static void logMessageClick(Context mContext) {
        onEvnet(mContext, "xx_sy_kcgz");
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
