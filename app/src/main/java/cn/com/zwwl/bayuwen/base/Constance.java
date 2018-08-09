package cn.com.zwwl.bayuwen.base;

/**
 * 常量
 */
public class Constance {
    /**
     * 悬浮框
     */
    public static final int OVERLAY_PERMISSION_REQ_CODE = 1101;
    public static final int OVERLAY_PERMISSION_REQ_CODE_RESUME = 1102;

    /**
     * PID；APPKEY; DES_KEY;请向商务获取
     */
    //直播用
    public static final String PID = "20150513100502"; //partnerId
    public static final String APPKEY = "91f12fdea3b1440a9cba03eca73f92ad"; //appkey
    //离线回放用
    public static final String DES_KEY = "MuAbYxEy";

    /**
     * 身份认证
     */
    public static class Authentication {
        //正面
        public static final int POSITIVE = 111;
        //反面
        public static final int BACK = 112;
        //手持
        public static final int HOLD = 113;
    }
}
