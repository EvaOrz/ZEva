package cn.com.zwwl.bayuwen.api;

import android.text.TextUtils;

import cn.com.zwwl.bayuwen.MyApplication;

public class UrlUtil {

    /**
     * Host信息
     **/
    public static String HOST;

    /**
     * 初始化host
     */
    public static void setHost() {
        if (MyApplication.DEBUG == 0) {// 线上环境
            HOST = "https://api.zhugexuetang.com/v2";

        } else if (MyApplication.DEBUG == 1) {// 测试环境
            HOST = "http://api.dev.zhugexuetang.com/v2";
        }
    }

    // 账号密码登录
    public static String LoginUrl() {
        return HOST + "/user/login";
    }

    // 快捷免密登录
    public static String smsloginUrl() {
        return HOST + "/user/appsignup";
    }

    // 注册
    public static String registerUrl() {
        return HOST + "/user/appsignup";
    }

    // 获取用户信息
    public static String userInfoUrl() {
        return HOST + "/user/info";
    }

    // 喜欢
    public static String likeUrl() {
        return HOST + "/fm/like";
    }

    // 获取课程的专辑列表
    public static String getAlbumListUrl(String kid, int page) {
        return HOST + "/fm/list?type=" + kid + "&page=" + page;
    }

    // 搜索接口
    public static String getSearchUrl(String title) {
        return HOST + "/fm/list?title=" + title;
    }

    // 增加播放量
    public static String addPlayUrl() {
        return HOST + "/fm/play";
    }

    // 修改密码
    public static String changePwd() {
        return HOST + "/user/apprest";
    }

    // 获取fm信息
    public static String getAlbumUrl(String kid) {
        return HOST + "/fm/detail?kid=" + kid;
    }

    //首页
    public static String getMainurl() {
        return HOST + "/tuijian/list";
    }

    //播放历史
    public static String getHistoryurl() {
        return HOST + "/history";
    }

    //获取收藏列表/ 添加收藏/ 删除收藏
    public static String getCollecturl() {
        return HOST + "/collection";
    }

    //获取评论
    public static String getPingListurl(String kid) {
        if (TextUtils.isEmpty(kid))
            return HOST + "/comment";
        return HOST + "/comment?kid=" + kid;
    }

    // 上传文件
    public static String uploadUrl() {
        return HOST + "/upload";
    }

    // 修改用户信息
    public static String changeInfoUrl(String uid) {
        return HOST + "/user/" + uid;
    }

    // 管理收货地址
    public static String addressUrl() {
        return HOST + "/address";
    }

    // 学员信息
    public static String childUrl() {
        return HOST + "/students";
    }

    // 选课首页标签列表
    public static String getKeTagListUrl() {
        return HOST + "/course/type";
    }

    // 赞列表
    public static String getTopListUrl() {
        return HOST + "/vote/toplist";
    }

    // 教师详情
    public static String getTeacherUrl(String tid) {
        if (TextUtils.isEmpty(tid))
            return HOST + "/teachers/detail";
        return HOST + "/teachers/detail?tid=" + tid;
    }

    // 课程详情
    public static String getCDetailUrl(String cid) {
        if (TextUtils.isEmpty(cid))
            return HOST + "/course";
        return HOST + "/course/" + cid;
    }

    // 获取课程列表
    public static String getLecturesUrl(String cid, String page) {
        if (TextUtils.isEmpty(cid))
            return HOST + "/course/lectures";
        return HOST + "/course/lectures?id=" + cid + "&page=" + page;
    }

    //获取团购信息
    public static String getTuanInfo() {
        return HOST + "/groupdiscount";
    }

    // 发起拼团（获取拼团码）
    public static String faqiTuan() {
        return HOST + "/grouppurchase";
    }

    // 生成订单
    public static String setOrder() {
        return HOST + "/order/get";
    }

    //我的订单
    public static String getMyOrder() {
        return HOST + "/order/myorder";
    }

    // 加入、获取、删除购课单
    public static String getCarturl() {
        return HOST + "/cart";
    }

    //    public
    public static String getMyCourse() {
        return HOST + "/course/my";
    }

    // 获取日历页面事件列表
    public static String getCalendarEvents() {
        return HOST + "/course/calendar";
    }

    // 添加日历事件
    public static String addCalendarEvent() {
        return HOST + "/courserecord";
    }

    // 获取第三方机构列表
    public static String getJiGouList() {
        return HOST + "/organization";
    }

    /**
     * 正在进行课程
     */
    public static String getStudyingCourse() {
        return HOST + "/course/second_course";
    }

    /**
     * 课程课次详情首页
     */
    public static String getUnitDetail() {
        return HOST + "/course/third_course";
    }

    /**
     * 评论
     */
    public static String addEval() {
        return HOST + "/comment";
    }

    public static String addVote() {
        return HOST + "/vote";
    }
    /**
     * 获取已完成课次二级列表
     */
    public static String getFCourseList() {
        return HOST + "/course/mytype";
    }
    /**
     * 获取期末考试详情
     */
    public static String getFinal() {
        return HOST + "/course/exam_list";
    }
}
