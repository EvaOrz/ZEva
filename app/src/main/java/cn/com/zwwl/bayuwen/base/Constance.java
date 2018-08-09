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
    public static final String PID = "20180227173910325267"; //partnerId
    public static final String APPKEY = "69aac9f8008f48338907ccb8965083e9"; //appkey
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
