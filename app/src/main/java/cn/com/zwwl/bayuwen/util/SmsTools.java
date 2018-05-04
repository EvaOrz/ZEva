package cn.com.zwwl.bayuwen.util;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVSMS;
import com.avos.avoscloud.AVSMSOption;
import com.avos.avoscloud.RequestMobileCodeCallback;

/**
 * 短信发送管理
 */
public class SmsTools {
    //发送模板短信
    public static void sendSms(final String phone) {

        AVSMSOption option = new AVSMSOption();
        option.setTemplateName("Register_Notice");  // 控制台预设的模板名称
        option.setSignatureName("LeanCloud");       // 控制台预设的短信签名
        AVSMS.requestSMSCodeInBackground(phone, option, new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (null == e) {
                    /* 请求成功 */
                    Log.e("smsssssss", phone);
                } else {
                    /* 请求失败 */
                    Log.e("smsssssss", e.getMessage());
                }
            }
        });
    }

    //验证类
    public static void sendLoginSms(final String phone,RequestMobileCodeCallback codeCallback) {
        AVSMSOption option = new AVSMSOption();
        option.setTtl(10);                     // 验证码有效时间为 10 分钟
        option.setApplicationName("诸葛学堂");
        option.setOperation("某种操作");
        AVSMS.requestSMSCodeInBackground(phone, option, codeCallback);
    }

    public static void smsVerify(final String phone, String code, AVMobilePhoneVerifyCallback callback) {

        AVSMS.verifySMSCodeInBackground(code, phone, callback);
    }
}
