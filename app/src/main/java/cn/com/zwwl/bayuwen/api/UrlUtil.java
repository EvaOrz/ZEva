package cn.com.zwwl.bayuwen.api;

import android.text.TextUtils;
import android.widget.TextView;

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

    // 退费须知
    public static String notificationTuifee() {
        return "http://www.zhugexuetang.com/explain/refund.html";
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
        if (TextUtils.isEmpty(kid))
            return HOST + "/fm/list?page=" + page;
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
    public static String getMainurl(int id) {
        return HOST + "/tuijian/" + id;
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

    //app协议url
    public static String getWebUrl() {
        return HOST + "/common/caption";
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

    // 获取可开发票的课程列表
    public static String getPiaoKeListUrl() {
        return HOST + "/course/invoice";
    }

    // 获取开发票的历史列表
    public static String getPiaoHistoryUrl() {
        return HOST + "/order/past_invoice";
    }

    // 发票详情
    public static String getPiaoDetail() {
        return HOST + "/order/invoice_detail";
    }

    // 申请开发票
    public static String kaiPiao() {
        return HOST + "/order/invoice";
    }

    // 计算开票价格
    public static String countPiaoPrice() {
        return HOST + "/order/reinvoice_cont";
    }

    // 赞列表
    public static String getTopListUrl() {
        return HOST + "/vote/toplist";
    }

    // 教师详情 || 全部教师
    public static String getTeacherUrl(String tid) {
        if (TextUtils.isEmpty(tid))
            return HOST + "/teachers";
        return HOST + "/teachers/detail?tid=" + tid;
    }

    // 课程详情
    public static String getCDetailUrl(String cid) {
        if (TextUtils.isEmpty(cid))
            return HOST + "/course";
        return HOST + "/course/" + cid;
    }

    // 获取课程的子课列表
    public static String getLecturesUrl(String cid, String page) {
        return HOST + "/course/lectures?id=" + cid + "&page=" + page;
    }

    //获取团购信息
    public static String getTuanInfo() {
        return HOST + "/groupdiscount";
    }

    // 发起拼团||我要垫付（获取拼团码）
    public static String faqiTuan() {
        return HOST + "/grouppurchase";
    }

    // 获取垫付之后的码列表
    public static String getTuanCodes() {
        return HOST + "/grouppurchase/code";
    }

    // 根据课程兑换码获取课程详情
    public static String getKemodelByCode() {
        return HOST + "/grouppurchase/course";
    }

    // 兑换拼团码
    public static String useTuanCode() {
        return HOST + "/grouppurchase/open";
    }

    //查看是否可以参与拼团
    public static String checkCanTuan() {
        return HOST + "/grouppurchase/join";
    }

    //我发起的、我参与的团购
    public static String getMyTuan() {
        return HOST + "/grouppurchase/my";
    }

    // 生成订单
    public static String setOrder() {
        return HOST + "/order/get";
    }

    // 获取未付款订单数量
    public static String getOrderNum() {
        return HOST + "/order/num";
    }

    // 取消订单
    public static String cancleOrder() {
        return HOST + "/order/";
    }

    //实时计算订单金额
    public static String countPrice() {
        return HOST + "/order/calculator";
    }

    //获取账户余额
    public static String getMyYue() {
        return HOST + "/user/assets";
    }

    //我的订单
    public static String getMyOrder() {
        return HOST + "/order/myorder";
    }

    //获取订单详情
    public static String getOrderDetail() {
        return HOST + "/order/orderdetail";
    }

    // 加入、获取、删除购课单
    public static String getCarturl() {
        return HOST + "/cart";
    }

    // 礼物、奖状列表
    public static String getHonorurl() {
        return HOST + "/honor";
    }

    //    public
    public static String getMyCourse() {
        return HOST + "/course/my";
    }

    // 获取日历页面事件列表
    public static String getCalendarEvents() {
        return HOST + "/course/calendar";
    }

    // 添加日历事件\获取日历事件详情
    public static String addCalendarEvent() {
        return HOST + "/courserecord";
    }

    // 获取第三方机构列表
    public static String getJiGouList() {
        return HOST + "/organization";
    }

    // 退费列表
    public static String getTuifeeList() {
        return HOST + "/refund/list";
    }

    // 退费详情
    public static String getTuifeeDetail() {
        return HOST + "/refund/detail";
    }

    // 原路退费
    public static String yuanluTuifee() {
        return HOST + "/refund";
    }

    // 账户退费
    public static String accountTuifee() {
        return HOST + "/fund/refund";
    }

    // 退费理由列表
    public static String getTuifeeReasonList() {
        return HOST + "/order/refund_reason";
    }

    // 反馈
    public static String feedback() {
        return HOST + "/userfeedback";
    }

    // 用户可使用的优惠券
    public static String cuponListUrl() {
        return HOST + "/coupon";
    }

    // 领取优惠券
    public static String getCouponUrl() {
        return HOST + "/coupon/grant";
    }

    // 获取首页第一个frag的数据
    public static String getIndex() {
        return HOST + "/my";
    }

    // 获取所有拼图列表
    public static String getPintu() {
        return HOST + "/jigsaw";
    }

    //(点击格子弹出中间页介绍)根据章节id获取子课程信息
    public static String getPintuIntro() {
        return HOST + "/courselecture/";
    }

    // 初始化域名
    public static String getInterface() {
        return "http://api.dev.zhugexuetang.com/v2/apps/version";
    }

    // 获取成就列表
    public static String getAchievement() {
        return HOST + "/achievement";
    }

    // 获取我的余额列表
    public static String getMyyue() {
        return HOST + "/user/assets-list";
    }

    // 获取我的积分列表
    public static String getCoinList() {
        return HOST + "/user/integral";
    }

    // 获取课程是否可转班调课状态
    public static String getCourseState() {
        return HOST + "/course/state";
    }


    // 获取课节报告评价popwindow数据
    public static String getLectureEval() {
        return HOST + "/reportcomment/teacher";
    }
    // 课节报告提交评价
    public static String commitLectureEval() {
        return HOST + "/reportcomment/comment";
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

    /**
     * 获取子课列表
     */
    public static String getLecturesList(int type) {
        if (type == 1) {
            return HOST + "/transfer/second_lectures";
        }
        return HOST + "/transfer/lectures";
    }

    /**
     * 添加课程(换班)
     */
    public static String addCourse() {
        return HOST + "/transfer";
    }

    /**
     * 上传作业
     */
    public static String addWork() {
        return HOST + "/courseware";
    }

    /**
     * 班级详情
     */
    public static String classDetail() {
        return HOST + "/transfer/course_detail";
    }

    /**
     * 签到
     */
    public static String sign() {
        return HOST + "/students/stusign";
    }

    /**
     * 获取月度、课程评价内容
     */
    public static String evalContent() {
        return HOST + "/comment/list";
    }

    /**
     * 获取最新报告
     */
    public static String pushReport() {
        return HOST + "/monthreport/type";
    }

    /**
     * 是否有报告
     */
    public static String haveReport() {
        return HOST + "/monthreport/returnurl";
    }

    /**
     * 教师详情课程列表
     */
    public static String TDetailList() {
        return HOST + "/teachers/tidcourse";
    }

    /**
     * 获取评论
     */
    public static String getEvalList() {
        return HOST + "/comment";
    }

    /**
     * 通知消息
     */
    public static String getNotifyMessage() {
        return HOST + "/message";
    }

    /**
     * 话题列表
     */
    public static String getTopicMessage() {
        return HOST + "/topic";
    }

    /**
     * 添加话题标签
     */
    public static String getAddTopicTabel() {
        return HOST + "/topic/courselist";
    }

    /**
     * v2/topicvote
     * <p>
     * POST
     * 话题点赞/取消点赞
     */
    public static String getCancelOrconfirmVote() {
        return HOST + "/topicvote";
    }

    /**
     * v2/topiccomment
     * <p>
     * POST
     * 添加话题评论
     */
    public static String getTopicComment() {
        return HOST + "/topiccomment";
    }

    /**
     * 城市列表
     */
    public static String getCityList() {
        return HOST + "/district/city";
    }

    /**
     * 获取试题列表
     */
    public static String getQuestionList() {
        return HOST + "/jigsaw/";
    }

    /**
     * 提交闯关答题
     */
    public static String submitAnswer() {
        return HOST + "/jigsaw";
    }

    /**
     * 错题集
     */
    public static String errorList() {
        return HOST + "/jigsaw/errorlist";
    }

    /**
     * 拼图列表
     */
    public static String puzzle() {
        return HOST + "/jigsaw/list";
    }

    /**
     * 获取答题详情
     */
    public static String answerDetail() {
        return HOST + "/jigsaw/questiondetail";
    }

    /**
     * 我的FM我的课程列表
     */
    public static String myFmCourse() {
        return HOST + "/fm/buylist";
    }

    /**
     * 课程跟踪-用户班级信息
     */
    public static String traceClassDetail() {
        return HOST + "/course/userdetail";
    }

    /**
     * 课程跟踪-搜索
     */
    public static String traceSearch() {
        return HOST + "/course/buylectures";
    }

    /**
     * 作业列表v2/courseware/showCourseListByKid
     */
    public static String courseList() {
        return HOST + "/courseware/showCourseListByKid";
    }
}
